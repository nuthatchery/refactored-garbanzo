package comp;

public interface IModel {

	IElementHandle get(IIdentity element);
	
	Iterable<IIdentity> children(IIdentity element);
	
	int getNumChildren(IIdentity element);
	
	Iterable<IIdentity> links(IIdentity element);
	
	boolean isDescendantOf(IIdentity parentElement, IIdentity descendantElement);
	
	boolean hasData(IIdentity element, Class<?> type);

	<T> T getData(IIdentity element, Class<T> type);
	

	
	IModifiableModel beginTransaction();
	
}
