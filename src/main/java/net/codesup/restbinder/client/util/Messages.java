package net.codesup.restbinder.client.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class Messages {
	public static String get(final Class<?> parentClass, final String key) {
		return ResourceBundle.getBundle(parentClass.getName().replace('.','/')).getString(key);
	}

	public static String get(final Class<?> parentClass, final String key, final String... params) {
		return MessageFormat.format(ResourceBundle.getBundle(parentClass.getName().replace('.','/')).getString(key), params);
	}
}
