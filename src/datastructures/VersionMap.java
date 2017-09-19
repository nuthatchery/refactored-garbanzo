package datastructures;

import java.util.HashMap;

import comp.ITransactableModel;

public class VersionMap extends HashMap<Integer, ITransactableModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer last=0;
	
	public VersionMap(int i, ITransactableModel modelTree) {
		super();
		put(i, modelTree);
	}

	@Override
	public ITransactableModel put(Integer version, ITransactableModel m){
		last = version;
		return super.put(version, m);
	}
	
	public Integer getLastVersionNr(){
		return last;
	}

	public int put(ITransactableModel modelTree) {
		super.put(++last, modelTree);
		return last;
	}

	public ITransactableModel getLast() {
		return get(last);
	}

	public ITransactableModel removeLast() {
		return super.remove(last--);
	}
	
}
