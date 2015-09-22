package net.codesup.restbinder.client.collections;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class FilterProperty {
	private final String propertyName;
	private final String expression;

	public FilterProperty(final String propertyName, final String expression) {
		this.propertyName = propertyName;
		this.expression = expression;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public String getExpression() {
		return this.expression;
	}
}
