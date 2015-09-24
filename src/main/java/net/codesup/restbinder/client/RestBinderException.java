package net.codesup.restbinder.client;

/**
 * @author Mirko Klemm 2015-09-23
 */
public class RestBinderException extends RuntimeException {
	public RestBinderException() {
	}

	public RestBinderException(final String message) {
		super(message);
	}

	public RestBinderException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RestBinderException(final Throwable cause) {
		super(cause);
	}

	public RestBinderException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
