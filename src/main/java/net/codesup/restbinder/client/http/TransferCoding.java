package net.codesup.restbinder.client.http;

/**
 * @author Mirko Klemm 2015-09-23
 */
public enum TransferCoding {
	CHUNKED("chunked");

	private final String value;

	TransferCoding(final String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	@Override
	public String toString() {
		return value;
	}
}
