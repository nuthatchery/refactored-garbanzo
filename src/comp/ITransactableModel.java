package comp;

import relationalmodel.IModel;
import relationalmodel.IModel;

/**
 * A model that can be made mutable 	
 * @author anna
 *
 */
public interface ITransactableModel<M> extends IModel {
	
	/**
	 * Fetches a mutable version of this model. 
	 * This model will NOT be mutable after the call, but the returned object will.
	 * @return a mutable version of this 
	 */
	M beginTransaction();
	
	/**
	 * Fetches the immutable version of this  
	 * @return 
	 */
	M commitTransaction();
	
	/**
	 * @return the previous version of this model
	 */
	M rollbackTransaction();
}
