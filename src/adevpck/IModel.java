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
	 * 
	 * @param node a node in this model
	 * @return iterable of link targets of node 
	 */
	Iterable<IIdentity> getLinks(IIdentity node);
	
	boolean isDescendantOf(IIdentity parentNode, IIdentity descendantNode);
	
	boolean hasData(IIdentity node, Class<?> type);

	<T> T getData(IIdentity node, Class<T> type);

	<T> void setData(IIdentity node, T data);

	IIdentity makeIdentity();
	
	IIdentity makeIdentity(String name);
	
	IIdentity makeElement(IIdentity schema);
	
	IIdentity makeElement(IIdentity schema, IIdentity parent);

	IIdentity makeElement(IIdentity schema, IIdentity parent, IIdentity label);

	/**
	 * 
	 * @return a mutable version of this 
	 */
	IModel beginTransaction();
	
	/**
	 * ends the transaction of this, making it immutable 
	 */
	void commitTransaction();
	
	/**
	 * @return the previous version of this model
	 */
	IModel rollbackTransaction();

}
