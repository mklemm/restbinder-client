package net.codesup.restbinder.client;

import java.util.Collection;

/**
 * Created by klemm0 on 2015-09-22.
 */
public interface CollectionLink<C,S> extends Link<C,S> {
	@Override Collection<S> resolve(final C client);
}
