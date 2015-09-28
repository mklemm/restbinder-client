package net.codesup.restbinder.client.jaxb;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.codesup.restbinder.client.ClientSession;
import net.codesup.restbinder.client.DocumentDescriptor;
import net.codesup.restbinder.client.DocumentDescriptorFactory;
import net.codesup.restbinder.client.LinkDescriptor;
import net.codesup.restbinder.client.PlainTextDocumentMarshaller;
import net.codesup.restbinder.client.util.RestBinderException;
import net.codesup.restbinder.client.WebDocument;
import net.codesup.restbinder.client.http.MediaType;
import net.codesup.restbinder.client.util.Func1;

/**
 * @author Mirko Klemm 2015-09-24
 */
public class JaxbDocumentDescriptorFactory implements DocumentDescriptorFactory {
	private final JaxbMediaType<Void> objectMediaType;
	private final JaxbDocumentMarshaller marshaller;
	private final PlainTextDocumentMarshaller plainTextDocumentMarshaller = new PlainTextDocumentMarshaller();

	public JaxbDocumentDescriptorFactory(final String vendor) {
		this.objectMediaType = new JaxbMediaType<>(vendor, null);
		this.marshaller = new JaxbDocumentMarshaller(this.objectMediaType);
	}

	private static Method getMethod(final Class<?> cls, final String name) {
		try {
			return cls.getMethod(name);
		} catch (final NoSuchMethodException nsmx) {
			return null;
		}
	}

	@Override
	public <T> DocumentDescriptor<T> createDocumentDescriptor(final ClientSession clientSession, final Class<T> requestedType, final MediaType contentType) {
		if(contentType != null && contentType.matchesType(this.objectMediaType)) {
			final JaxbMediaType<T> jaxbContentType = new JaxbMediaType<>(contentType);
			final List<LinkDescriptor<T>> linkDescriptors = new ArrayList<>();
			findLinkDescriptors(linkDescriptors, jaxbContentType.getJaxbClass());
			return new DocumentDescriptor<>(clientSession, jaxbContentType, linkDescriptors, this.marshaller);
		} else {
			return new DocumentDescriptor<>(clientSession, contentType, Collections.<LinkDescriptor<T>>emptyList(), this.plainTextDocumentMarshaller);
		}
	}

	private <T> void findLinkDescriptors(final List<LinkDescriptor<T>> linkDescriptors, final Class<? super T> type) {
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
					final Func1<URI, WebDocument<T>> hrefGet = new UriGetter<>(field, hrefGetter);
					final Method titleGetter = getMethod(fieldType, "getTitle");
					final Func1<String, WebDocument<T>> titleGet = titleGetter == null ? new Getter<T>(null) : new StringGetter<T>(field, titleGetter);
					final Method roleGetter = getMethod(fieldType, "getRole");
					final Func1<String, WebDocument<T>> roleGet = roleGetter == null ? new Getter<T>(field.getName()) : new StringGetter<T>(field, roleGetter);
					final Method arcRoleGetter = getMethod(fieldType, "getArcrole");
					final Func1<String, WebDocument<T>> arcRoleGet = arcRoleGetter == null ? new Getter<T>(field.getName()) : new StringGetter<T>(field, arcRoleGetter);
					final LinkDescriptor<T> linkDescriptor = new LinkDescriptor<>(fieldType, new LinkDescriptor.Multiplicity(0, 1), hrefGet, titleGet, roleGet, arcRoleGet);
					linkDescriptors.add(linkDescriptor);
				}
			}
		}
	}

	private static final class UriGetter<P> implements Func1<URI, WebDocument<P>> {
		private final Field linkField;
		private final Method method;

		public UriGetter(final Field linkField, final Method method) {
			this.linkField = linkField;
			this.method = method;
			this.linkField.setAccessible(true);
		}

		@Override
		public URI eval(final WebDocument<P> c) {
			try {
				final Object o = this.method.invoke(this.linkField.get(c.getContent()));
				return o == null ? null : URI.create(o.toString());
			} catch (final InvocationTargetException itx) {
				throw new RestBinderException(itx.getTargetException());
			} catch (final IllegalAccessException e) {
				throw new RestBinderException(e);
			}
		}
	}

	private static final class StringGetter<P> implements Func1<String, WebDocument<P>> {
		private final Field linkField;
		private final Method method;

		public StringGetter(final Field linkField, final Method method) {
			this.linkField = linkField;
			this.method = method;
			this.linkField.setAccessible(true);
		}

		@Override
		public String eval(final WebDocument<P> c) {
			try {
				final Object o = this.method.invoke(this.linkField.get(c.getContent()));
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
}
