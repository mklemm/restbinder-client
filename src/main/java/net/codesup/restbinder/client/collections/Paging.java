package net.codesup.restbinder.client.collections;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class Paging {
	private final int pageSize;

	public Paging(final int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return this.pageSize;
	}
}
