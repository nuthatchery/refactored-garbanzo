package adevpck.models;
import java.util.List;

import adevpck.IIdentity;
import relationalmodel.IModel;
import adevpck.Identity;
import adevpck.datastructures.Triple;
import adevpck.datastructures.Tuple;

public class Ordering implements IModel{
	private static final Ordering orderingModel = new Ordering();
	private static final IIdentity modelid = orderingModel.newNode();
	public static final IIdentity FIRST = Integers.ONE;

	private Ordering(){
	}

	public static IModel getInstance() {
		return orderingModel;
	}
	
	@Override
	public int getNumChildren(IIdentity node) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static IIdentity getIdentity() {
		return modelid;
	}

	@Override
	public IIdentity newNode() {
		return new Identity(this);
	}

	@Override
	public IIdentity addNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity addNode(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel addEdge(IIdentity from, IIdentity label, IIdentity to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IIdentity> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNode(IIdentity node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IModel removeNode(IIdentity node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Triple> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tuple> getEdges(IIdentity from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel removeEdge(IIdentity from, IIdentity label, IIdentity to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity getId() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
