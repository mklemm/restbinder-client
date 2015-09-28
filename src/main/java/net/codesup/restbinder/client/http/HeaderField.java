package net.codesup.restbinder.client.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class HeaderField {
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String VARY = "Vary";
	public static final String LAST_MODIFIED = "Last-Modified";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_ENCODING = "Content-Encoding";
	public static final String TRANSFER_ENCODING = "Content-Encoding";

	private final String key;
	private final List<String> values;

	private HeaderField(final String key, final List<String> values) {
		this.key = key;
		this.values = Collections.unmodifiableList(new ArrayList<>(values));;
	}

	public String getKey() {
		return this.key;
	}

	public List<String> getValues() {
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

	@Override
	public String toString() {
		StringBuilder sb = null;
		for(final Object value : this.values) {
			if(sb == null) {
				sb = new StringBuilder(this.key).append(": ");
			} else {
				sb.append(", ");
			}
			sb.append(value);
		}
		return sb == null ? "" : sb.toString();
	}

	public static class Builder {
		private final String key;
		private final List<Object> values;

		Builder(final String key) {
			this.key = key;
			this.values = new ArrayList<>();
		}

		Builder(final HeaderField other) {
			this.key = other.key;
			this.values = new ArrayList<>(other.getValues().size());
			Collections.copy(this.values, other.values);
		}

		public Builder addValue(final String... value) {
			this.values.addAll(Arrays.asList(value));
			return this;
		}

		public Builder addValue(final Collection<?> value) {
			this.values.addAll(new ArrayList<>(value));
			return this;
		}

		public Builder addValue(final Accept<?>... value) {
			this.values.addAll(Arrays.asList(value));
			return this;
		}

		public Builder addValue(final MediaType... value) {
			this.values.addAll(Arrays.asList(value));
			return this;
		}

		public Builder addValue(final ContentCoding... value) {
			this.values.addAll(Arrays.asList(value));
			return this;
		}

		public Builder withValue(final String... value) {
			this.values.clear();
			return addValue(value);
		}

		public Builder withValue(final Accept<?>... value) {
			this.values.clear();
			return addValue(value);
		}

		public Builder withValue(final MediaType... value) {
			this.values.clear();
			return addValue(value);
		}

		public Builder withValue(final ContentCoding... value) {
			this.values.clear();
			return addValue(value);
		}

		public Builder withValue(final Collection<?> value) {
			this.values.clear();
			return addValue(value);
		}

		public HeaderField build() {
			return new HeaderField(this.key, asStringList(this.values));
		}

		private static List<String> asStringList(final Iterable<Object> objects) {
			final List<String> stringList = new ArrayList<>();
			for(final Object obj : objects) {
				stringList.add(obj == null ? null : obj.toString());
			}
			return stringList;
 		}
	}

}
