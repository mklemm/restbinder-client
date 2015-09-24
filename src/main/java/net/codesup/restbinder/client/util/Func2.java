package net.codesup.restbinder.client.util;

/**
 * @author Mirko Klemm 2015-09-23
 */
public interface Func2<R,P1,P2> {
	R eval(final P1 p1, final P2 p2);
}
