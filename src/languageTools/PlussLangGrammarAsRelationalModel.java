package languageTools;

import comp.IIdentity;
import models.CharString;
import models.MetaMeta;
import models.Operations;
import relationalmodel.RelationalModel;


/**
 * 
 * @author anna
 *S -> P 
 *P -> P + P 
 *P -> NUM (-> INT) 
 */
public class PlussLangGrammarAsRelationalModel{
	static RelationalModel model = new RelationalModel().beginTransaction();
	static {
		//IIdentity expr = model.addNode("expr");
		model.addNode("MODELID:PLUSSGRAMMAR"); // identity node, used for visualising 
		model.addNodesFromConstructor(Operations.BINARY_NUMBER_OPERATION_GRAMMAR);
		visualise.GraphViewer.view(model);
		IIdentity plussOP = model.getAnyNodeWithProperty(MetaMeta.IS, models.Operation.OPERATOR); 
		model.addEdge(plussOP, CharString.STRING_REP_OF, CharString.GET("+"));
//		model.setProperty(NUM, MetaMeta.IS, models.Integers.getIdentity());
		// TODO comforms_to, models.operations.binarynumberoperator 
//		model.setProperty(plussOP, MetaMeta.CONFORMS_TO, models.Operation.OPERATOR);
//		model.setProperty(plussOP, MetaMeta.CONFORMS_TO, models.Operation.OPERATOR);
		visualise.GraphViewer.view(model);
	}
	
	{
		model = model.commitTransaction();
	}
	
	public static RelationalModel getModel(){
		return model;
	}

	
	public static void main(String[] args){;}
}
