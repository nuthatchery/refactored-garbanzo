package adevpck.models;

import java.math.BigInteger;
import java.util.List;

import adevpck.Identity;
import adevpck.datastructures.Triple;
import adevpck.datastructures.Tuple;
import comp.IIdentity;
import comp.IUnchangeableModel;
import relationalmodel.IModel;

/**
 * Ordinals: numbers that tells the position of something in a list, such as 1st, 2nd, 3rd, 4th, 5th etc. 
 * @author anna
 *
 */
public class Ordinals implements IUnchangeableModel{
	private static final Ordinals ordinals = new Ordinals();
	public static final IIdentity MODEL_ID = new Identity(ordinals, "ordinals");
	public static final IIdentity FIRST = Integers.ONE;
	
	public static IIdentity getIdentity() {
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
	public IIdentity getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumChildren(IIdentity node) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static IIdentity fromInt(IIdentity integerid) {
		assert Integers.isIntegerIdentity(integerid) : "Only works with Integers: " + integerid;
		BigInteger b = Integers.bigValueOf(integerid);
		assert b.compareTo(BigInteger.ZERO) > 0 : "Ordinals can only be bigger than zero" + b; 
		return new Identity(ordinals, b.toString());
	}
	
	

}
