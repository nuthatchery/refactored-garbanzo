package rascalsol;

import java.util.ArrayList;
import java.util.List;

public class ElementHandle implements IElementHandle {
	private IModel m;
	private IIdentity n;

	@Override
	public Iterable<IElementHandle> getChildren() {
		List<IElementHandle> ls = new ArrayList<>();
		m.getChildren(n).forEach(i->{ls.add(m.get(i));});
		return ls;
	}


	@Override
	public IIdentity getIdentity() {
		// TODO Auto-generated method stub
		return null;
	}

}
