package languageTools;

import java.rmi.server.Operation;

import javax.management.OperationsException;

import comp.IIdentity;
import models.MetaMeta;
import models.Operations;
import relationalmodel.RelationalModel;

public class PlussLangGrammarAsRelationalModel{
	LanguageModel model = new RelationalModel().beginTransaction();
	{
		IIdentity expr = model.addNode("expr");
		model.addNodesFromConstructor(Operations.BINARY_NUMBER_OPERATION);
		IIdentity plussOP = model.getAnyNodeWithProperty(MetaMeta.IS, models.Operation.OPERATOR); 
		IIdentity NUM = model.addNode("NUM");
//		model.setProperty(NUM, MetaMeta.IS, models.Integers.getIdentity());
		// TODO comforms_to, models.operations.binarynumberoperator 
//		model.setProperty(plussOP, MetaMeta.CONFORMS_TO, models.Operation.OPERATOR);
//		model.setProperty(plussOP, MetaMeta.CONFORMS_TO, models.Operation.OPERATOR);
		
	}
	
	{
		model = model.commitTransaction();
	}
	
	public static RelationalModel getModel(){
		return null;
	}

}
