package net.codesup.restbinder.client.collections;

/**
 * Created by klemm0 on 2015-09-22.
 */
public interface CollectionPage<E> extends Iterable<E> {
	int size();
	int getRequestedSize();
}
