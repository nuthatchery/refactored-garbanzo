package adevpck;


public interface IModel {

	IElementHandle get(IIdentity element);
	
	Iterable<IIdentity> getChildren(IIdentity node);
	
	int getNumChildren(IIdentity node);
	
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
	
	IModel beginTransaction();
	
	void commitTransaction();
	
	IModel rollbackTransaction();

}
