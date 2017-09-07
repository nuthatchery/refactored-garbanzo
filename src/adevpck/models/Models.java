package adevpck.models;

import adevpck.RelationalModel;

public class Models {
	public static final RelationalModel model;
	
	static{
		model = new RelationalModel(true);
		model.addNodes(Integers.getIdentity(), MetaMeta.getIdentity(), Ordering.getIdentity(), Numbers.getIdentity());
		
		model.addEdge(Ordering.getIdentity(), MetaMeta.IS_REPRESENTATION_OF, to)
	}

}
