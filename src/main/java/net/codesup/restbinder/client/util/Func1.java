package net.codesup.restbinder.client.util;

/**
 * @author Mirko Klemm 2015-09-23
 */
public interface Func1<R,P> {
	R eval(final P p);
}
