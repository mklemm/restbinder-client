package net.codesup.restbinder.client.collections;

/**
 * Created by klemm0 on 2015-09-22.
 */
public interface PageCollection<E> extends Iterable<CollectionPage<E>> {
	int getPageCount();
	
}
