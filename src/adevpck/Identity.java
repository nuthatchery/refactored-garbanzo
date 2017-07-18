package adevpck;

import java.util.UUID;

public class Identity implements IIdentity {
	private final String id = UUID.randomUUID().toString();
	/**
	 * Info about model this id elem belongs to. 
	 * Maybe a list of models? or not having this info at all?
	 * May be empty
	 */
	private final String path;
	/**
	 * The name. May be null, may be empty, may be changed
	 */
	private String name;
	
	public Identity(){
		path ="";
	}
	
	public Identity(String name){
		this();
		this.name = name;
	}
	
	public Identity(String path, String name){
		this.path = path;
		this.name = name;
	}
	
	
	@Override
	public String getId() {
		return id;
	}

	public String getPath(){
		return path;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public IIdentity setName(String name){
		this.name=name;
		return this;
	}
	@Override
	public boolean equals(Object o) {
		if(o==null)return false;
		if(this==o)return true;
		if(!( o instanceof Identity)) return false;
		Identity other = (Identity) o;
		return this.id == other.id;
	}

	@Override
	public int hashCode(){
		return id.hashCode();
	}

	@Override
	public String toString(){
		return name;
	}
}
