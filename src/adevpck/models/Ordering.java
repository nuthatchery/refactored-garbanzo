package adevpck.models;

import adevpck.IElementHandle;
import adevpck.IIdentity;
import adevpck.IModel;
import adevpck.Identity;
import adevpck.RelationalModel;

public class Ordering implements IModel{
	private static final RelationalModel orderingmeta;
	private static final IIdentity modelid = new Identity("Ordering");
	static {
		orderingmeta = new RelationalModel(true);
		IIdentity element = new Identity("element");
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
	public Iterable<IIdentity> getLinks(IIdentity node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDescendantOf(IIdentity parentNode, IIdentity descendantNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasData(IIdentity node, Class<?> type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T getData(IIdentity node, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void setData(IIdentity node, T data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IIdentity makeIdentity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeIdentity(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeElement(IIdentity schema) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeElement(IIdentity schema, IIdentity parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeElement(IIdentity schema, IIdentity parent, IIdentity label) {
		// TODO Auto-generated method stub
		return null;
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

	
}
