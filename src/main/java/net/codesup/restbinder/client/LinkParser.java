package net.codesup.restbinder.client;

import java.util.List;

/**
 * Created by klemm0 on 2015-09-22.
 */
public interface LinkParser<T> {
	List<Link<T,?>> parse(final T content);
	Class<T> getContentType();
}
