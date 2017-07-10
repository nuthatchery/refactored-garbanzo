package comp;

public interface IModel {

	IElementHandle get(IIdentity element);
	
	Iterable<IIdentity> getChildren(IIdentity element);
	
	int getNumChildren(IIdentity element);
	
	Iterable<IIdentity> getLinks(IIdentity element);
	
	boolean isDescendantOf(IIdentity parentElement, IIdentity descendantElement);
	
	boolean hasData(IIdentity element, Class<?> type);

	<T> T getData(IIdentity element, Class<T> type);

	<T> void setData(IIdentity element, T data);

	IIdentity makeIdentity();
	
	IIdentity makeIdentity(String name);
	
	IIdentity makeElement(IIdentity schema);
	
	IIdentity makeElement(IIdentity schema, IIdentity parent);

	IIdentity makeElement(IIdentity schema, IIdentity parent, IIdentity label);
	
	void beginTransaction();
	
	void commitTransaction();
	
	void rollbackTransaction();
}
