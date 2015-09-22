package net.codesup.restbinder.client.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class Filtering {
	private final List<FilterProperty> filterProperties;

	public Filtering(final List<FilterProperty> filterProperties) {
		this.filterProperties = Collections.unmodifiableList(new ArrayList<>(filterProperties));
	}

	public Filtering(final FilterProperty... filters) {
		this.filterProperties = Collections.unmodifiableList(Arrays.asList(filters));
	}

	public List<FilterProperty> getFilterProperties() {
		return this.filterProperties;
	}

	public static class Builder {
		private final List<FilterProperty> filterProperties = new ArrayList<>();

		public Builder add(final FilterProperty ... filterProperties) {
			this.filterProperties.addAll(Arrays.asList(filterProperties));
			return this;
		}

		public Builder add(final String propertyName, final String expression) {
			add(new FilterProperty(propertyName, expression));
			return this;
		}

		public Filtering build() {
			return new Filtering(this.filterProperties);
		}
	}
}
