package models;

import java.util.ArrayList;
import java.util.List;

import comp.IIdentity;
import comp.IUnchangeableModel;
import comp.Identity;
import datastructures.Triple;
import datastructures.Tuple;
import relationalmodel.IModel;
import relationalmodel.RelationalModel;

public class Operation implements IUnchangeableModel{
	private static final IModel operation = new RelationalModel(true);
	public static final IIdentity OPERATOR = operation.addNode("Operator");
	public static final IIdentity ARITY = operation.addNode("Arity");
	public static final IIdentity OPERAND_ORDINAL_NUM = operation.addNode("OperandOrdinalNum");
	public static final IIdentity OPERAND = operation.addNode("Operand");
	private static final Triple constraint = new Triple(OPERATOR, MetaMeta.REQUIRE_ONE, ARITY);

	private Operation(){
	}


	@Override
	public IIdentity newNode() {
		return new Identity(this);
	}


	public int getNumChildren(IIdentity node) {
		if(constraint.first() == node ) return 1; //if we add more constraints, expand to list + loop
		return 0;
	}

	public boolean hasPath(IIdentity startNode, IIdentity endNode) {
		if(constraint.first() == startNode && constraint.third() == endNode)
			return true;
		return false;
	}

	@Override
	public IIdentity addNode() {
		return new Identity(this);
	}

	@Override
	public IIdentity addNode(String name) {
		return new Identity(this, name);
	}

	@Override
	public IModel addEdge(IIdentity from, IIdentity label, IIdentity to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<IIdentity> getNodes() {
		List<IIdentity> ret = new ArrayList<>();
		ret.add(OPERATOR);
		ret.add(ARITY);
		ret.add(OPERAND_ORDINAL_NUM);
		ret.add(OPERAND);
		return ret;
	}

	@Override
	public boolean hasNode(IIdentity node) {
		return getNodes().contains(node);
	}

	@Override
	public IModel removeNode(IIdentity node) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Triple> getEdges() {
		List<Triple> ret = new ArrayList<>();
		ret.add(constraint);
		return ret;
	}

	@Override
	public List<Tuple> getEdges(IIdentity from) {
		if(from == OPERATOR){
			List<Tuple> ret = new ArrayList<>();
			ret.add(constraint.tail());
			return ret;
		}
		else 
			return new ArrayList<>();
	}

	@Override
	public IModel removeEdge(IIdentity from, IIdentity label, IIdentity to) {
		throw new UnsupportedOperationException();
	}

	@Override
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
