package net.codesup.restbinder.client;

/**
 * Created by klemm0 on 2015-09-22.
 */
public interface Link<P,S> {
	class Multiplicity {
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

	Multiplicity getMultiplicity();

	boolean isCollection();

	String getRole();

	String getTitle();

	Class<P> getParentType();

	Class<S> getSupplierType();

	Object resolve(final P parent);
}
