package models;

import relationalmodel.IModel;
import relationalmodel.RelationalModel;

public class Models {
	private static IModel model;
	
	static{
		RelationalModel model = new RelationalModel().beginTransaction();
		model.addNodes(Integers.getIdentity(), 
				MetaMeta.getIdentity(), 
				Tree.getIdentity(), 
				Ordinals.getIdentity(),
				Operation.getIdentity()
				);
		Models.model = model.commitTransaction();
		
	}

}
