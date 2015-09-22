package net.codesup.restbinder.client.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class Ordering {
	private final List<OrderProperty> orderProperties;

	public Ordering(final List<OrderProperty> orderProperties) {
		this.orderProperties = Collections.unmodifiableList(new ArrayList<>(orderProperties));
	}

	public Ordering(final OrderProperty... orderProperties) {
		this.orderProperties = Collections.unmodifiableList(Arrays.asList(orderProperties));
	}

}
