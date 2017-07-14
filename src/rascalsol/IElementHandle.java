package rascalsol;

/**
 * An element handle combines an identity with the model to which the element belongs.
 * 
 * The handle does not store data directly, all queries are forwarded to an underlying model
 *
 */
public interface IElementHandle /* extends IIdentity? */ {

	
	Iterable<IElementHandle> getChildren();
	
	IIdentity getIdentity();
	
	
}
