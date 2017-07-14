package comp;

public interface IModifiableModel extends IModel {
	IModel commitTransaction();
	
	IModel rollbackTransaction();

	
	<T> void setData(IIdentity element, T data);

	IIdentity makeIdentity();
	
	IIdentity makeIdentity(String name);
	
	IIdentity makeElement(IIdentity schema);
	
	IIdentity makeElement(IIdentity schema, IIdentity parent);

	IIdentity makeElement(IIdentity schema, IIdentity parent, IIdentity label);

}
