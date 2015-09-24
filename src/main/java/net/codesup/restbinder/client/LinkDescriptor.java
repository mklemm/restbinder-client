package net.codesup.restbinder.client;

import java.net.URI;

import net.codesup.restbinder.client.util.Func1;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class LinkDescriptor<P, S> {
	private final Class<S> supplierType;
	private final Multiplicity multiplicity;
	private final Func1<URI, WebDocument<P>> hrefGetter;
	private final Func1<String, WebDocument<P>> titleGetter;
	private final Func1<String, WebDocument<P>> roleGetter;

	public LinkDescriptor(final Class<S> supplierType, final Multiplicity multiplicity, final Func1<URI, WebDocument<P>> hrefGetter, final Func1<String, WebDocument<P>> titleGetter, final Func1<String, WebDocument<P>> roleGetter) {
		this.supplierType = supplierType;
		this.multiplicity = multiplicity;
		this.hrefGetter = hrefGetter;
		this.titleGetter = titleGetter;
		this.roleGetter = roleGetter;
	}

	public Class<S> getSupplierType() {
		return this.supplierType;
	}

	public Multiplicity getMultiplicity() {
		return this.multiplicity;
	}

	public Link<P, S> createLink(final WebDocument<P> parent) {
		return new Link<>(this, parent, this.hrefGetter.eval(parent), this.titleGetter.eval(parent), this.roleGetter.eval(parent));
	}

	public static class Multiplicity {
		public static final int UNBOUNDED = Integer.MAX_VALUE;
		private final int lowerBound;
		private final int upperBound;

		public Multiplicity(final int lowerBound, final int upperBound) {
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
		}

		public int getLowerBound() {
			return this.lowerBound;
		}

		public int getUpperBound() {
			return this.upperBound;
		}
	}
}
