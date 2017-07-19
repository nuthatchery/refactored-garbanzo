package adevpck;

import java.util.ArrayList;
import java.util.List;

public class ElementHandle implements IElementHandle {
	/**
	 * The model this handle belongs to 
	 */
	private IModel m;
	/**
	 * the identity this handle belongs to 
	 */
	private IIdentity n;

	public ElementHandle(IModel m, IIdentity n){
		this.m = m;
		this.n = n;
	}
	
	@Override
	public Iterable<IElementHandle> getChildren() {
		List<IElementHandle> ls = new ArrayList<>();
		m.getChildren(n).forEach(i->{ls.add(m.get(i));});
		// XXX: kanskje bruke Streams i stedet? bortsett fra at de ikke er Iterable
		// return m.getChildren(n).map(i->m.get(i));
		return ls;
	}


	@Override
	public IIdentity getIdentity() {
		return n;
	}

	@Override
	public IElementHandle getHandle(IIdentity id) {
		return new ElementHandle(m, id);
	}


}
