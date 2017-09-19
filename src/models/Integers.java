package models;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import comp.IIdentity;
import comp.IUnchangeableModel;
import comp.Identity;
import datastructures.Triple;
import datastructures.Tuple;
import relationalmodel.IModel;

public class Integers implements IUnchangeableModel {
	public static final IIdentity INT_MODEL_ID = new Identity("integer");
	private static final Integers intModel = new Integers();
	private static final List<IIdentity> prevNext = Arrays.asList(new Identity("prev"), new Identity("next"));
	private static final IIdentity ZERO = new Identity("0");
	public static final IIdentity ONE = new Identity("1");

	private Integers() {
	}

	public static IModel getInstance() {
		return intModel;
	}

	public static IIdentity identityOf(long i) {
		return new Identity("" + i);
	}
	public static IIdentity identityOf(BigInteger i) {
		return new Identity("" + i);
	}

	public static boolean isIntegerIdentity(IIdentity id) {
		String name = id.getName();
		return name != null && name.matches("^[0-9]+$");
	}

	public static int intValueOf(IIdentity id) {
		long l = longValueOf(id);
		if(l >= Integer.MIN_VALUE || l <= Integer.MAX_VALUE)
			return (int)l;
		throw new ArithmeticException("Value outside range of int");
	}
	public static long longValueOf(IIdentity id) {
		if (!isIntegerIdentity(id))
			throw new IllegalArgumentException("Must be an integer id");
		return Long.parseLong(id.getName());
	}
	public static BigInteger bigValueOf(IIdentity id) {
		if (!isIntegerIdentity(id))
			throw new IllegalArgumentException("Must be an integer id");
		return new BigInteger(id.getName());
	}

	public static IIdentity succ(IIdentity node){
		return followLink(node, prevNext.get(1));
	}
	
	public static IIdentity getIdentity() {
		return INT_MODEL_ID;
	}

	public Iterable<IIdentity> getLinks(IIdentity node) {
		return prevNext;
	}


	public boolean hasData(IIdentity node, Class<?> type) {
		return type == Integer.class || type == Long.class || type == BigInteger.class;
	}
	
	public static IIdentity followLink(IIdentity node, IIdentity link) {
		if(link.equals(prevNext.get(0))) {
			return identityOf(bigValueOf(node).subtract(BigInteger.ONE));
		}
		else if(link.equals(prevNext.get(1))) {
			return identityOf(bigValueOf(node).add(BigInteger.ONE));			
		}
		else {
			throw new IllegalArgumentException("No such link: " + node + " -- " + link + " -> ...");
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(IIdentity node, Class<T> type) {
		if (type == Integer.class) {
			return (T) Integer.valueOf(node.getName());
		} else if (type == Long.class) {
			return (T) Long.valueOf(node.getName());
		} else if (type == BigInteger.class) {
			return (T) new BigInteger(node.getName());
		} else {
			throw new IllegalArgumentException("No data of type " + type);
		}
	}

	@Override
	public List<IIdentity> getNodes() {
		//TODO lazy eval list would work
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNode(IIdentity node) {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public IModel removeNode(IIdentity node) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Triple> getEdges() {
		//TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Tuple> getEdges(IIdentity from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity getId() {
		return INT_MODEL_ID;
	}

	@Override
	public int getNumChildren(IIdentity node) {
		//TODO
		return 0;
	}

}
