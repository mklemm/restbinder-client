package net.codesup.restbinder.client.http;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.codesup.restbinder.client.util.Messages;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class MediaType {
	public static final MediaType APPLICATION_XML = MediaType.valueOf("application/xml");
	public static final MediaType TEXT_XML = MediaType.valueOf("text/xml");
	public static final MediaType APPLICATION_JSON = MediaType.valueOf("application/json");
	public static final MediaType TEXT_PLAIN = MediaType.valueOf("text/plain");
	public static final MediaType MULTIPART_FORM_DATA = MediaType.valueOf("multipart/form-data");
	public static final MediaType APPLICATION_X_WWW_FORM_URLENCODED = MediaType.valueOf("application/x-www-form-urlencoded");
	public static final MediaType XHTML = MediaType.valueOf("application/xhtml+xml");
	public static final MediaType TEXT_HTML = MediaType.valueOf("text/html");

	private static final String TCHAR = "[a-zA-Z0-9!#\\$%&'\\*\\+\\-\\.\\^_`\\|~]"; // chars allowed in RFC7230 "token" type
	private static final Pattern MEDIA_TYPE = Pattern.compile("(" + MediaType.TCHAR + "+)/(" + MediaType.TCHAR + "+)(?:\\s*;\\s*(" + MediaType.TCHAR + "+)\\s*=\\s*\"?(" + MediaType.TCHAR + "+)\"?)*");
	private final String type;
	private final String subType;
	private final Map<String, String> parameters;

	public MediaType(final String type, final String subType, final Map<String, String> parameters) {
		this.type = type;
		this.subType = subType;
		if (parameters.containsKey("q")) {
			throw new IllegalArgumentException(Messages.get(MediaType.class, "paramQNotAllowed"));
		}
		this.parameters = Collections.unmodifiableMap(new TreeMap<>(parameters));
	}

	public String getType() {
		return this.type;
	}

	public String getSubType() {
		return this.subType;
	}

	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public boolean isMediaRange() {
		return "*".equals(this.subType);
	}

	public boolean matchesType(final MediaType other) {
		return other.type.equals(this.type) && other.subType.equals(this.subType)
				|| other.subType.equals("*") && this.type.equals(other.type)
				|| this.subType.equals("*") && other.type.equals(this.type)
				|| this.type.equals("*") || other.type.equals("*");
	}

	public boolean matchesType(final String otherString) {
		return matchesType(MediaType.valueOf(otherString));
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof MediaType) {
			final MediaType other = (MediaType)o;
			if (other.subType.equals(this.subType) && other.type.equals(this.type)) {
				for (final Map.Entry<String, String> myEntry : this.parameters.entrySet()) {
					if (!myEntry.getValue().equals(other.parameters.get(myEntry.getKey()))) {
						return false;
					}
				}
				for (final Map.Entry<String, String> otherEntry : other.parameters.entrySet()) {
					if (!otherEntry.getValue().equals(this.parameters.get(otherEntry.getKey()))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = this.type.hashCode();
		result = 31 * result + this.subType.hashCode();
		result = 31 * result + this.parameters.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.type).append("/").append(this.subType);
		for (final Map.Entry<String, String> parameter : this.parameters.entrySet()) {
			sb.append(";");
			sb.append(parameter.getKey());
			sb.append("=");
			sb.append(parameter.getValue());
		}
		return sb.toString();
	}

	public static MediaType valueOf(final String stringRep) {
		final Matcher matcher = MediaType.MEDIA_TYPE.matcher(stringRep);
		if (matcher.matches()) {
			final String type = matcher.find(1) ? matcher.group() : null;
			final String subType = matcher.find() ? matcher.group() : null;
			final Map<String, String> params = new LinkedHashMap<>();
			while (matcher.find()) {
				final String paramName = matcher.group();
				if (matcher.find()) {
					params.put(paramName, matcher.group());
				}
			}
			return new MediaType(type, subType, params);
		} else {
			throw new IllegalArgumentException(Messages.get(MediaType.class, "invalidMediaTypeString", stringRep));
		}
	}
}
