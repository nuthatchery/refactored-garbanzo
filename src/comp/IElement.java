package comp;

public interface IElement {

	
	IElement get(IIdentity id);
	
	Iterable<IElement> getChildren();

	Iterable<IElement> getAll();
	
	IIdentity getIdentity();
	
	
}
