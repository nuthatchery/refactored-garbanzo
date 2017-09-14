package adevpck;

import java.util.HashMap;
import relationalmodel.IModel;

public class VersionMap extends HashMap<Integer, IModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer last=0;
	
	public VersionMap(int i, IModel modelTree) {
		super();
		put(i, modelTree);
	}

	@Override
	public IModel put(Integer version, IModel m){
		last = version;
		return super.put(version, m);
	}
	
	public Integer getLastVersionNr(){
		return last;
	}

	public int put(IModel modelTree) {
		super.put(++last, modelTree);
		return last;
	}

	public IModel getLast() {
		return get(last);
	}

	public IModel removeLast() {
		return super.remove(last--);
	}
	
}
