package rascalsol;

import java.util.HashMap;

public class Model implements IModel{
	private final IIdentity id;
	private IElementHandle modelHandle;

	public Model(){
		id = new Identity();
		modelHandle = new ElementHandle(this, id);
	}
	
	public Model(IIdentity id){
		this.id = id;
		modelHandle = new ElementHandle(this, id);
	}
	
	/**
	 * Id (Node) -> Data (whatever that is)
	 * This shouldn't be in the model. Dunno how to actually do this. 
	 * Let ID point outside of this ontology somehow OR encode stuff in this model.  
	 */
	private HashMap<IIdentity, Data> nodeData = new HashMap<>();
	
	/**
	 * Gets the data associated with a node, if any
	 * @param node an id belonging to a node in this model
	 * @throws IllegalArgumentException if id is not a node in this modes
	 * @return the data associated with the node. return value may be null 
	 */
	public Data getData(IIdentity node) throws IllegalArgumentException{
		checkNodeInModel(node);
		return nodeData.get(node);
	}

	/**
	 * Puts data in a node. Replaces old data if any. 
	 * @param node an id belonging to a node in this model
	 * @param data the data belonging to the node
	 * @throws IllegalArgumentException if id is not a node in this modes
	 * @return the previous data belonging to the node, or null if there was no such data. 
	 */
	public Data putReplaceData(IIdentity node, Data data) throws IllegalArgumentException{
		checkNodeInModel(node);
		return nodeData.put(node, data);
	}
	
	private boolean isNode(IIdentity id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Throws an exception if the node is not found in this model
	 * @param node
	 * @throws IllegalArgumentException if id is not a node in this modes
	 */
	private void checkNodeInModel(IIdentity node) throws IllegalArgumentException{
		if(!isNode(node)){
			throw new IllegalArgumentException("this id is not a node in this model" + node); 
		}
	}

	@Override
	public IElementHandle get(IIdentity element) {
		return modelHandle.getHandle(element);
	}

	@Override
	public Iterable<IIdentity> getChildren(IIdentity node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumChildren(IIdentity node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<IIdentity> getLinks(IIdentity node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDescendantOf(IIdentity parentNode, IIdentity descendantNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasData(IIdentity node, Class<?> type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T getData(IIdentity node, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void setData(IIdentity node, T data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IIdentity makeIdentity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeIdentity(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeElement(IIdentity schema) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeElement(IIdentity schema, IIdentity parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeElement(IIdentity schema, IIdentity parent, IIdentity label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beginTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commitTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollbackTransaction() {
		// TODO Auto-generated method stub
		
	}
}
