package net.codesup.restbinder.client;

/**
 * Created by klemm0 on 2015-09-22.
 */
public interface SingularLink<C,S> extends Link<C,S> {
	S resolve(final C client);
}
