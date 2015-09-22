package net.codesup.restbinder.client;

import net.codesup.restbinder.client.collections.Filtering;
import net.codesup.restbinder.client.collections.Ordering;
import net.codesup.restbinder.client.collections.PageCollection;
import net.codesup.restbinder.client.collections.Paging;

/**
 * Created by klemm0 on 2015-09-22.
 */
public interface PageCollectionLink<P,S> extends Link<P,S> {
	PageCollection<S> resolve(final P parent, final Paging paging, final Ordering ordering, final Filtering filtering);
	PageCollection<S> resolve(final P parent, final Paging paging, final Ordering ordering);
	PageCollection<S> resolve(final P parent, final Paging paging);
	@Override PageCollection<S> resolve(final P parent);

	Paging getDefaultPaging();
	Ordering getDefaultOrdering();
	Filtering getDefaultFiltering();
}
