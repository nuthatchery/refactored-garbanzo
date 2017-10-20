package factories;
import comp.IIdentity;
import comp.Identity;

import org.apache.commons.collections15.Factory;

public class IdentityFactory implements Factory<IIdentity>{

	@Override
	public IIdentity create() {
		return new Identity("fromFactory");
	}

}
