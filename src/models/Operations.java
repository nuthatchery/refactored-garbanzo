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
	public static RelationalModel BINARY_NUMBER_OPERATION_GRAMMAR; 
	static {
		RelationalModel temp = new RelationalModel().beginTransaction();
		temp.addNode("MODEL:BINARY_NUMBER_OPERATION_GRAMMAR"); //visual id, may be removed
		IIdentity binop = temp.addNode("BINARY_NUMBER_OPERATOR");
		IIdentity EXPR = temp.addNode("EXPR");
		IIdentity arg1 = temp.addNode("Arg1");
		IIdentity arg2 = temp.addNode("Arg2");	
		IIdentity NUM = temp.addNode("NUM");
		temp.addEdge(binop, MetaMeta.IS, Operation.OPERATOR);
		temp.addEdge(binop, Integers.identityOf(2), Operation.ARITY);
		temp.addEdge(arg1, MetaMeta.IS, Operation.OPERAND);
		temp.addEdge(arg2, MetaMeta.IS, Operation.OPERAND);
		temp.addEdge(NUM, MetaMeta.IS, EXPR);
		temp.addEdge(arg1, MetaMeta.REQUIRES_POINTS_TO, EXPR);
		temp.addEdge(arg2, MetaMeta.REQUIRES_POINTS_TO, EXPR);
		temp.addEdge(binop, MetaMeta.IS, EXPR);
		temp.addEdge(EXPR, MetaMeta.CAN_BE, binop);
		temp.addEdge(EXPR, MetaMeta.CAN_BE, NUM);
		temp.addEdge(arg1, Ordinals.fromInt(Integers.identityOf(1)), Operation.OPERAND_ORDINAL_NUM);
		temp.addEdge(arg2, Ordinals.fromInt(Integers.identityOf(2)), Operation.OPERAND_ORDINAL_NUM);
		BINARY_NUMBER_OPERATION_GRAMMAR = temp.commitTransaction();
//		System.out.println(BINARY_NUMBER_OPERATION);	
	}
}
