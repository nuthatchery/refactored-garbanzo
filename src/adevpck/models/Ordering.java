package adevpck.models;

import adevpck.IElementHandle;
import adevpck.IIdentity;
import adevpck.IModel;
import adevpck.Identity;

public class Ordering implements IModel{
	private static final Ordering orderingModel = new Ordering();
	private static final IIdentity modelid = orderingModel.newNode();
	public static final IIdentity FIRST = Integers.ONE;

	private Ordering(){
	}

	public static IModel getInstance() {
		return orderingModel;
	}
	
	//Stupid methods 
	@Override
	public IElementHandle get(IIdentity element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<IIdentity> getChildren(IIdentity node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumChildren(IIdentity node) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean isDescendantOf(IIdentity parentNode, IIdentity descendantNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IModel beginTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel commitTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel rollbackTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	public static IIdentity getIdentity() {
		return modelid;
	}

	@Override
	public IIdentity newNode() {
		return new Identity(this);
	}

	
}
