package adevpck.models;

import java.util.ArrayList;
import java.util.List;

import adevpck.IIdentity;
import adevpck.Identity;
import adevpck.datastructures.Triple;
import adevpck.datastructures.Tuple;
import relationalmodel.IModel;

public class Operation implements IModel{
	private static final Operation operation = new Operation();
	private static final IIdentity modelid = new Identity(operation);
	public static final IIdentity OPERATOR = operation.newNode();
	public static final IIdentity ARITY = operation.newNode();
	public static final IIdentity OPERAND_ORDINAL_NUM = operation.newNode();
	public static final IIdentity OPERAND = operation.newNode();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNode(IIdentity node) {
		if(node == OPERATOR || node == ARITY || node == OPERAND_ORDINAL_NUM || node == OPERAND)
			return true;
		return false;
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
		return modelid;
	}
}
