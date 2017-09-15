package adevpck.models;

import relationalmodel.MutableModel;
import relationalmodel.RelationalModel;

public class Models {
	public static final RelationalModel model;
	
	static{
		model = new MutableModel();
		model.addNodes(Integers.getIdentity(), 
				MetaMeta.getIdentity(), 
				Tree.getIdentity(), 
				Ordinals.getIdentity(),
				Operation.getIdentity()
				);
		
	}

}
