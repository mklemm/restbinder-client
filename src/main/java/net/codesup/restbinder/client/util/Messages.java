package net.codesup.restbinder.client.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class Messages {
	private final ResourceBundle resourceBundle;

	public Messages(final Class<?> parentClass) {
		this.resourceBundle = ResourceBundle.getBundle(parentClass.getName());
	}

	public Messages(final Object parentObject) {
		this(parentObject.getClass());
	}

	public String get(final String key) {
		return this.resourceBundle.getString(key);
	}

	public String get(final String key, final String... params) {
		return MessageFormat.format(this.resourceBundle.getString(key), params);
	}
}
