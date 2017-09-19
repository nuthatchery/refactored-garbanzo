package models;

import java.util.ArrayList;
import java.util.List;

import comp.IIdentity;
import comp.ITransactableModel;
import relationalmodel.IModel;
import relationalmodel.RelationalModel;

public class Operation{
	private static ITransactableModel operation = new RelationalModel().beginTransaction();
	public static final IIdentity OPERATOR = operation.addNode("Operator");
	public static final IIdentity ARITY = operation.addNode("Arity");
	public static final IIdentity OPERAND_ORDINAL_NUM = operation.addNode("OperandOrdinalNum");
	public static final IIdentity OPERAND = operation.addNode("Operand");
	
	static{
		operation.addEdge(OPERATOR, MetaMeta.REQUIRE_ONE, ARITY);
		operation = operation.commitTransaction();
	}

	public List<IIdentity> getNodes() {
		List<IIdentity> ret = new ArrayList<>();
		ret.add(OPERATOR);
		ret.add(ARITY);
		ret.add(OPERAND_ORDINAL_NUM);
		ret.add(OPERAND);
		return ret;
	}

	public IIdentity getId() {
		return operation.getId();
	}

	public static void setOperandOrder(IModel model, IIdentity... args){
		//assert args.length eq model.getEdge(from=model.getNodePointingTo(Operator), to=ARITY).toInt()
		List<IIdentity> operators = model.getNodesPointingTo(OPERATOR); //TODO why did I need this here?
		assert operators.size() > 0 : "No operators found ";
		assert operators.size() <= 1 : "Multiple operators not supported";
		for(int i=0; i<args.length; i++){
			model.addEdge(args[i], Ordinals.fromInt(Integers.identityOf(i+1)), OPERAND_ORDINAL_NUM);
		}
	}


	public static IIdentity getIdentity() {
		return operation.getId();
	}
}
