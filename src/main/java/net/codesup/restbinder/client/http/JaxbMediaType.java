package net.codesup.restbinder.client.http;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import net.codesup.restbinder.client.RestBinderException;
import net.codesup.restbinder.client.util.ThreadSafeMessageFormat;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class JaxbMediaType<T> extends MediaType {
	public static final String TYPE = "application";
	public static final ThreadSafeMessageFormat SUB_TYPE_FORMAT  = new ThreadSafeMessageFormat("x-vnd-{0}-object+xml");
	public static final String DEFAULT_VENDOR = "rb";

	private final Class<T> jaxbClass;
	private final String vendor;

	public JaxbMediaType(final Class<T> jaxbClass) {
		this(JaxbMediaType.DEFAULT_VENDOR, jaxbClass);
	}

	public JaxbMediaType() {
		this(JaxbMediaType.DEFAULT_VENDOR, null);
	}

	public JaxbMediaType(final String vendor, final Class<T> jaxbClass) {
		super(JaxbMediaType.TYPE, JaxbMediaType.SUB_TYPE_FORMAT.format(vendor), createParameters(jaxbClass));
		this.jaxbClass = jaxbClass;
		this.vendor = vendor;
	}

	public JaxbMediaType(final MediaType other) {
		super(other.getType(), other.getSubType(), other.getParameters());
		try {
			this.vendor = JaxbMediaType.SUB_TYPE_FORMAT.get().parse(other.getSubType())[0].toString();
			//noinspection unchecked
			this.jaxbClass = (Class<T>)Class.forName(other.getParameters().get("class"));
		} catch (ClassNotFoundException | ParseException e) {
			throw new RestBinderException(e);
		}
	}

	private static Map<String,String> createParameters(final Class<?> jaxbClass) {
		final Map<String,String> parameters = new HashMap<>();
		if(jaxbClass != null) {
			parameters.put("class", jaxbClass.getName());
		}
		return parameters;
	}

	public Class<T> getJaxbClass() {
		return this.jaxbClass;
	}

	public String getVendor() {
		return this.vendor;
	}

	public <E> JaxbMediaType<E> match(final MediaType mediaType) {
		try {
			if (mediaType.matchesType(this)) {
				final Class<E> mediaJavaClass = (Class<E>)Class.forName(mediaType.getParameters().get("class"));
				return new JaxbMediaType<>(this.vendor, mediaJavaClass);
			} else {
				return null;
			}
		} catch(final Exception e) {
			throw new RestBinderException(e);
		}
	}
}
