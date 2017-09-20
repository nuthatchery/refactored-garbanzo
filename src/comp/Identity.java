package comp;

import java.util.UUID;

import relationalmodel.IModel;

/**
 * Immutable Identity class 
 * @author anna
 */
public class Identity implements IIdentity{
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
	
	/**
	 * Id of the one and only model this id is a node in
	 */
	private final IIdentity modelId; //TODO make final 
	
	public Identity(IModel model){
		this.modelId = model.getId();
		path = "";
	}
	
	public Identity(IIdentity modelId){
		this.modelId = modelId;
		path = "";
	}
	
	public Identity(IModel model, String name){
		this.modelId = model.getId();
		this.name = name;
		this.path = "";
	}
	

	public Identity(String name) {
		this.modelId = this;
		this.name = name;
		this.path = "";
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
		if(!( o instanceof IIdentity)) return false;
		IIdentity other = (IIdentity) o;
		return this.id.equals(other.getId());
	}

	@Override
	public int hashCode(){
		return id.hashCode();
	}

	@Override
	public String toString(){
		return name!=null ? name : id;
	}

	@Override
	public IIdentity getModelId() {
		return modelId;
	}
	
	@Override 
	public int compareTo(IIdentity other){
		return this.id.compareTo(other.getId());
	}
	
	private boolean datainvariant(){
		return id != null;
	}
	
	public static boolean bothNullOrEquals(IIdentity a, IIdentity b) {
		if(a == null && b == null)return true;
		if(a != null) return a.equals(b);
		return false;
	}
}
