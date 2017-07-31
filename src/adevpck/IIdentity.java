package adevpck;

/**
 * Each node or element in the system has an identity.
 */
public interface IIdentity extends Comparable<IIdentity> {
	
	public String getId();
	
	/**
	 * @return the name, if any. May be null.
	 */
	public String getName();
	
	public boolean equals(Object o);
	
	public int hashCode();
	
	/**
	 * Sets the name. Overwrites the existing name if any.
	 * @param name the new name
	 * @return this
	 */
	IIdentity setName(String name);

	public IIdentity getModelId();
}
