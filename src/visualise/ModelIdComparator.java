package visualise;

import java.util.Comparator;

import comp.IIdentity;

public class ModelIdComparator implements Comparator<IIdentity> {

	@Override
	public int compare(IIdentity o1, IIdentity o2) {
		return o1.getModelId().compareTo(o2.getModelId());
	}

}
