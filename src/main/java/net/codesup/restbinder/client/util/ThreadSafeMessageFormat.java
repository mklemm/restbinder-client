package net.codesup.restbinder.client.util;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class ThreadSafeMessageFormat extends ThreadLocal<MessageFormat> {
	private final String formatString;
	private final Locale locale;

	public ThreadSafeMessageFormat(final String formatString) {
		this.formatString = formatString;
		this.locale = Locale.getDefault(Locale.Category.FORMAT);
	}

	public ThreadSafeMessageFormat(final String formatString, final Locale locale) {
		this.formatString = formatString;
		this.locale = locale;
	}

	@Override
	protected MessageFormat initialValue() {
		return new MessageFormat(this.formatString, this.locale);
	}

	public String format(Object ... params) {
		return get().format(params);
	}
}
