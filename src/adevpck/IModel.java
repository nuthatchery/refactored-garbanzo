package adevpck;


public interface IModel {

	IElementHandle get(IIdentity element);
	
	/**
	 * 
	 * @param node a node in this model
	 * @return an iterable of the children nodes 
	 */
	Iterable<IIdentity> getChildren(IIdentity node);
	
	/**
	 * 
	 * @param node a node in this model
	 * @return number of children of node 
	 */
	int getNumChildren(IIdentity node);
	
	
	/**
	 * Searches the model for a path starting at the parentNode and ending in descendantNode
	 * @param parentNode the startnode of the path
	 * @param descendantNode the endnode of the path
	 * @return true if such a path exists, false otherwise 
	 */
	boolean isDescendantOf(IIdentity parentNode, IIdentity descendantNode);
	
	/**
	 * 
	 * @return a mutable version of this 
	 */
	IModel beginTransaction();
	
	/**
	 * ends the transaction of this, making it immutable 
	 * @return 
	 */
	IModel commitTransaction();
	
	/**
	 * @return the previous version of this model
	 */
	IModel rollbackTransaction();

	IIdentity newNode();

}
