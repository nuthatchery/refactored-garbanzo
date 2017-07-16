package comp;

/**
 * An element handle combines an identity with the model to which the element belongs.
 * 
 * The handle does not store data directly, all queries are forwarded to an underlying model
 *
 */
public interface IElementHandle /* extends IIdentity? */ {

	/**
	 * Gets the handle of an identity if this id denotes a node in the model this handle belongs to
	 * @param id a node in the model this handle belongs to 
	 * @return the handle of {@link id} if any, null if {@link id} is not found in this handle's model
	 */
	IElementHandle get(IIdentity id);
	
	/**
	 * 
	 * @return all children of this handle's node in this handle's model. Empty iterable if none.   
	 */
	Iterable<IElementHandle> getChildren();

	/**
	 * 
	 * @return all handles of all nodes in the model associated with this handle
	 */
	Iterable<IElementHandle> getAll();
	
	/**
	 * @return this handle's identity
	 */
	IIdentity getIdentity();
	
	
}
