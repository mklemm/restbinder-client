package net.codesup.restbinder.client.http;

import java.util.Locale;

import net.codesup.restbinder.client.util.ThreadSafeMessageFormat;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class Accept<T> {
	private static final ThreadSafeMessageFormat QUALITY_FORMAT = new ThreadSafeMessageFormat("q={0,number,0.0##}", Locale.ROOT);
	private final T spec;
	private final float qualityValue;

	public Accept(final T spec, final float qualityValue) {
		this.spec = spec;
		this.qualityValue = qualityValue;
	}

	public Accept(final T spec) {
		this.spec = spec;
		this.qualityValue = 1.0f;
	}

	public T getSpec() {
		return this.spec;
	}

	public float getQualityValue() {
		return this.qualityValue;
	}

	@Override
	public String toString() {
		return this.spec + ";" + Accept.QUALITY_FORMAT.format(this.qualityValue);
	}
}
