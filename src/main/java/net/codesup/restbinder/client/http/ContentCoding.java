package net.codesup.restbinder.client.http;

/**
 * @author Mirko Klemm 2015-09-23
 */
public enum ContentCoding {
	COMPRESS("compress"),
	DEFLATE("deflate"),
	GZIP("gzip");

	final String value;

	ContentCoding(final String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.value;
	}


}
