package net.codesup.restbinder.client.collections;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class OrderProperty {
	private final String propertyName;
	private final OrderDirection direction;

	public OrderProperty(final String propertyName, final OrderDirection direction) {
		this.propertyName = propertyName;
		this.direction = direction;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public OrderDirection getDirection() {
		return this.direction;
	}
}
