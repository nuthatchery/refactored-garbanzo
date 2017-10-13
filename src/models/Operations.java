package models;

import comp.IIdentity;
import relationalmodel.RelationalModel;

/**
 * Contains models of typical operations 
 * @author anna
 *
 */
public class Operations {
	
	/**
	 * A binary operation on numbers, i.e. \<OPERATOR\> \<NUMBER\> \<NUMBER\>
	 */
	public static RelationalModel BINARY_NUMBER_OPERATION; 
	{
		RelationalModel temp = new RelationalModel().beginTransaction();
		IIdentity binop = temp.addNode("BINARY_NUMBER_OPERATOR");
		IIdentity NUM1 = temp.addNode("Arg1");
		IIdentity NUM2 = temp.addNode("Arg2");
		temp.addEdge(NUM1, MetaMeta.ELEMENT_OF, Integers.getIdentity());
		temp.addEdge(NUM2, MetaMeta.ELEMENT_OF, Integers.getIdentity());
		temp.addEdge(binop, MetaMeta.IS, Operation.OPERATOR);
		temp.addEdge(binop, Integers.identityOf(2), Operation.ARITY);
		temp.addEdge(NUM1, MetaMeta.IS, Operation.OPERAND);
		temp.addEdge(NUM2, MetaMeta.IS, Operation.OPERAND);
		temp.addEdge(NUM1, Ordinals.fromInt(Integers.identityOf(1)), Operation.OPERAND_ORDINAL_NUM);
		temp.addEdge(NUM2, Ordinals.fromInt(Integers.identityOf(2)), Operation.OPERAND_ORDINAL_NUM);
		BINARY_NUMBER_OPERATION = temp.commitTransaction();
//		System.out.println(BINARY_NUMBER_OPERATION);
	}
}
