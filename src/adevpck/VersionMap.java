package adevpck;

import java.util.HashMap;

public class VersionMap extends HashMap<Integer, ModelTree> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer last=0;
	
	public VersionMap(int i, ModelTree modelTree) {
		super();
		put(i, modelTree);
	}

	@Override
	public ModelTree put(Integer version, ModelTree m){
		last = version;
		return super.put(version, m);
	}
	
	public Integer getLastVersionNr(){
		return last;
	}

	public int put(ModelTree modelTree) {
		super.put(++last, modelTree);
		return last;
	}

	public ModelTree getLast() {
		return get(last);
	}

	public ModelTree removeLast() {
		return super.remove(last--);
	}
	
}
