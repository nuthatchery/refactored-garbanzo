package rascalsol;

import java.util.HashMap;

public class Model {
	/**
	 * Id (Node) -> Data (whatever that is)
	 */
	private HashMap<IIdentity, Data> nodeData = new HashMap<>();
	
	/**
	 * Gets the data associated with a node, if any
	 * @param node an id belonging to a node in this model
	 * @throws IllegalArgumentException if id is not a node in this modes
	 * @return the data associated with the node. return value may be null 
	 */
	public Data getData(IIdentity node) throws IllegalArgumentException{
		checkNode(node);
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
		checkNode(node);
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
	private void checkNode(IIdentity node) throws IllegalArgumentException{
		if(!isNode(node)){
			throw new IllegalArgumentException("this id is not a node in this model" + node); 
		}
	}
}
