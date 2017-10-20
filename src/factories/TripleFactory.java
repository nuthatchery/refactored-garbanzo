package factories;
import datastructures.Triple;

import org.apache.commons.collections15.Factory;

import comp.IIdentity;

public class TripleFactory implements Factory<Triple>{

	@Override
	public Triple create() {
		Factory<IIdentity> f = new IdentityFactory();
		return new Triple(f.create(), f.create(), f.create());
	}

}
