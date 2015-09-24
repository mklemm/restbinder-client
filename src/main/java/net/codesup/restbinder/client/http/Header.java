package net.codesup.restbinder.client.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class Header {
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String VARY = "Vary";
	public static final String LAST_MODIFIED = "Last-Modified";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_ENCODING = "Content-Encoding";
	public static final String TRANSFER_ENCODING = "Content-Encoding";

	private final String key;
	private final List<Object> values;

	Header(final String key, final Object... values) {
		this.key = key;
		this.values = Collections.unmodifiableList(Arrays.asList(values));
	}

	Header(final String key, final List<Object> values) {
		this.key = key;
		this.values = Collections.unmodifiableList(new ArrayList<>(values));;
	}

	public String getKey() {
		return this.key;
	}

	public List<Object> getValues() {
		return this.values;
	}

	public String getValue() {
		final StringBuilder sb = new StringBuilder();
		for(final Object val : this.values) {
			if(sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(val);
		}
		return sb.toString();
	}

	public static Builder newBuilder(final String key) {
		return new Builder(key);
	}

	public Builder newCopyBuilder() {
		return new Builder(this);
	}

	public static class Builder {
		private final String key;
		private final List<Object> values ;

		Builder(final String key) {
			this.key = key;
			this.values = new ArrayList<>();
		}

		Builder(final Header other) {
			this.key = other.key;
			this.values = new ArrayList<>(other.getValues());
		}

		public Builder addValue(final Object... value) {
			this.values.addAll(Arrays.asList(value));
			return this;
		}

		public Builder addValue(final Collection<Object> value) {
			this.values.addAll(new ArrayList<>(value));
			return this;
		}

		public Builder withValue(final Object... value) {
			this.values.clear();
			return addValue(value);
		}

		public Builder withValue(final Collection<Object> value) {
			this.values.clear();
			return addValue(value);
		}

		public Header build() {
			return new Header(this.key, this.values);
		}
	}

}
