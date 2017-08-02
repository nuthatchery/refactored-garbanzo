package adevpck.models;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import adevpck.ElementHandle;
import adevpck.IElementHandle;
import adevpck.IIdentity;
import adevpck.IModel;
import adevpck.Identity;

public class Integers implements IModel {
	public static final IIdentity INT_MODEL_ID = new Identity("integer");
	private static final Integers intModel = new Integers();
	private static final List<IIdentity> prevNext = Arrays.asList(new Identity("prev"), new Identity("next"));

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

	public IIdentity getIdentity() {
		return INT_MODEL_ID;
	}
	@Override
	public IElementHandle get(IIdentity element) {
		return new ElementHandle(this, element);
	}

	@Override
	public Iterable<IIdentity> getChildren(IIdentity node) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public int getNumChildren(IIdentity node) {
		return 0;
	}

	@Override
	public Iterable<IIdentity> getLinks(IIdentity node) {
		return prevNext;
	}

	@Override
	public boolean isDescendantOf(IIdentity parentNode, IIdentity descendantNode) {
		return false;
	}

	@Override
	public boolean hasData(IIdentity node, Class<?> type) {
		return type == Integer.class || type == Long.class || type == BigInteger.class;
	}
	
	public IIdentity followLink(IIdentity node, IIdentity link) {
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
	@Override
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
	public <T> void setData(IIdentity node, T data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IIdentity makeIdentity() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IIdentity makeIdentity(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IIdentity makeElement(IIdentity schema) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IIdentity makeElement(IIdentity schema, IIdentity parent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IIdentity makeElement(IIdentity schema, IIdentity parent, IIdentity label) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IModel beginTransaction() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void commitTransaction() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IModel rollbackTransaction() {
		throw new UnsupportedOperationException();
	}
}
