package net.codesup.restbinder.client.jaxb;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import net.codesup.restbinder.client.ClientSession;
import net.codesup.restbinder.client.DocumentDescriptor;
import net.codesup.restbinder.client.DocumentDescriptorFactory;
import net.codesup.restbinder.client.LinkDescriptor;
import net.codesup.restbinder.client.RestBinderException;
import net.codesup.restbinder.client.WebDocument;
import net.codesup.restbinder.client.http.JaxbMediaType;
import net.codesup.restbinder.client.http.MediaType;
import net.codesup.restbinder.client.util.Func1;

/**
 * @author Mirko Klemm 2015-09-24
 */
public class JaxbDocumentDescriptorFactory implements DocumentDescriptorFactory {
	private static Method getMethod(final Class<?> cls, final String name) {
		try {
			return cls.getMethod(name);
		} catch (final NoSuchMethodException nsmx) {
			return null;
		}
	}

	@Override
	public <T> DocumentDescriptor<T> createDocumentDescriptor(final ClientSession clientSession, final MediaType mediaType) {
		final JaxbMediaType<T> contentType = new JaxbMediaType<>(mediaType);
		final List<LinkDescriptor<T, ?>> linkDescriptors = new ArrayList<>();
		findLinkDescriptors(linkDescriptors, contentType.getJaxbClass());
		return new DocumentDescriptor<>(clientSession, contentType, linkDescriptors);
	}

	private <T> void findLinkDescriptors(final List<LinkDescriptor<T, ?>> linkDescriptors, final Class<? super T> type) {
		if (type.getSuperclass() != null) {
			findLinkDescriptors(linkDescriptors, type.getSuperclass());
		}
		for (final Field field : type.getDeclaredFields()) {
			final Class<?> fieldType = field.getType();
			if (!(fieldType.isPrimitive()
					|| fieldType.isArray()
					|| fieldType.isEnum())) {
				final Method hrefGetter = getMethod(fieldType, "getHref");
				if (hrefGetter != null) {
					final Func1<URI, WebDocument<T>> hrefGet = new UriGetter<>(hrefGetter);
					final Method titleGetter = getMethod(fieldType, "getTitle");
					final Func1<String, WebDocument<T>> titleGet = titleGetter == null ? new Getter<T>(null) : new StringGetter<T>(titleGetter);
					final Method roleGetter = getMethod(fieldType, "getRole");
					final Func1<String, WebDocument<T>> roleGet = roleGetter == null ? new Getter<T>(field.getName()) : new StringGetter<T>(roleGetter);
					final LinkDescriptor<T, ?> linkDescriptor = new LinkDescriptor<>(Object.class, new LinkDescriptor.Multiplicity(0, 1), hrefGet, titleGet, roleGet);
					linkDescriptors.add(linkDescriptor);
				}
			}
		}
	}

	private static final class UriGetter<P> implements Func1<URI, WebDocument<P>> {
		private final Method method;

		public UriGetter(final Method method) {
			this.method = method;
		}

		@Override
		public URI eval(final WebDocument<P> c) {
			try {
				final Object o = this.method.invoke(c);
				return o == null ? null : URI.create(o.toString());
			} catch (final InvocationTargetException itx) {
				throw new RestBinderException(itx.getTargetException());
			} catch (final IllegalAccessException e) {
				throw new RestBinderException(e);
			}
		}
	}

	private static final class StringGetter<P> implements Func1<String, WebDocument<P>> {
		private final Method method;

		public StringGetter(final Method method) {
			this.method = method;
		}

		@Override
		public String eval(final WebDocument<P> c) {
			try {
				final Object o = this.method.invoke(c.getContent());
				return o == null ? null : o.toString();
			} catch (final InvocationTargetException itx) {
				throw new RestBinderException(itx.getTargetException());
			} catch (final IllegalAccessException e) {
				throw new RestBinderException(e);
			}
		}
	}

	private static final class Getter<P> implements Func1<String,WebDocument<P>> {
		private final String value;

		public Getter(final String value) {
			this.value = value;
		}

		@Override
		public String eval(final WebDocument<P> t) {
			return this.value;
		}
	}

	;
}
