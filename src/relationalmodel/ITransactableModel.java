package relationalmodel;

import relationalmodel.IModel;

public interface ITransactableModel extends IModel {
	
	/**
	 * Fetches a mutable version of this model. 
	 * This model will NOT be mutable after the call, but the returned object will.
	 * @return a mutable version of this 
	 */
	IModel beginTransaction();
	
	/**
	 * Fetches the immutable version of this  
	 * @return 
	 */
	IModel commitTransaction();
	
	/**
	 * @return the previous version of this model
	 */
	IModel rollbackTransaction();
}
