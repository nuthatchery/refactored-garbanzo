package comp;

import relationalmodel.IModel;

/**
 * A model that does not support changes 
 * @author anna
 *
 */
public interface IUnchangeableModel extends IModel{

	@Override
	default public IIdentity newNode() {
		throw new UnsupportedOperationException();
	}

	@Override
	default public IIdentity addNode() {
		throw new UnsupportedOperationException();
	}

	@Override
	default public IIdentity addNode(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	default public IModel addEdge(IIdentity from, IIdentity label, IIdentity to) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default public IModel removeNode(IIdentity node) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default public IModel removeEdge(IIdentity from, IIdentity label, IIdentity to) {
		throw new UnsupportedOperationException();
	}

}
