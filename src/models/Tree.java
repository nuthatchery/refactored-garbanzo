package models;

import relationalmodel.IModel;

import java.util.List;

import comp.IIdentity;
import comp.IUnchangeableModel;
import comp.Identity;
import comp.ModelTree;
import datastructures.Triple;
import datastructures.Tuple;

public class Tree implements IUnchangeableModel{
	public static ModelTree model = new  ModelTree();

	public static final IIdentity NODE = id("node");
	public static final IIdentity BRANCH = id("branch");
	public static final IIdentity ARITY = id("arity");

	static {
		model.addLink(NODE, MetaMeta.REQUIRE_ONE, ARITY);
		model.addLink(NODE, MetaMeta.ZERO_OR_MORE, BRANCH);
		model.addLink(BRANCH, MetaMeta.IS, MetaMeta.FUNCTION);
		model.addLink(BRANCH, MetaMeta.SRC_CONFORMS_TO, NODE);
		model.addLink(BRANCH, MetaMeta.DEST_CONFORMS_TO, NODE);
		model.addLink(ARITY, MetaMeta.IS, MetaMeta.FUNCTION);
		model.addLink(ARITY, MetaMeta.SRC_CONFORMS_TO, NODE);
		model.addLink(ARITY, MetaMeta.DEST_CONFORMS_TO, Integers.INT_MODEL_ID); // TODO

	}

	public static final IIdentity DEST_TYPE = id("destType");
	public static final IIdentity TYPE = id("type");
	public static final IIdentity SUBTYPE_OF = id("subtypeOf");

	static {
		// a typed node
		model.addLink(BRANCH, MetaMeta.ZERO_OR_ONE, DEST_TYPE);
		model.addLink(DEST_TYPE, MetaMeta.IS, MetaMeta.FUNCTION);
		model.addLink(DEST_TYPE, MetaMeta.DEST_CONFORMS_TO, TYPE);

		// subtypeOf is a relation between two types
		model.addLink(SUBTYPE_OF, MetaMeta.IS, MetaMeta.RELATION);
		model.addLink(SUBTYPE_OF, MetaMeta.SRC_CONFORMS_TO, TYPE);
		model.addLink(SUBTYPE_OF, MetaMeta.DEST_CONFORMS_TO, TYPE);
	}

	private static IIdentity id(String string) {
		IIdentity identity = new Identity(model, "Tree::" + string);
		model.addNode(identity); //Anna: hva er meningen her?
		return identity;
	}

	public static ModelTree getInstance() {
		return model;
	}

	/**
	 * Adds a new node and a list of its children to a model according to its metamodel and constructor rules
	 * @param m Model the node should be added to 
	 * @param mm MetaModel of m
	 * @param constructor of the new Node
	 * @param children Node's children, should conform to constructor structure
	 * @return the new node
	 */
	public static IIdentity makeNode(ModelTree m, ModelTree mm, IIdentity constructor, IIdentity... children) {
		assert m.hasLink(m.getId(), MetaMeta.CONFORMS_TO, mm.getId());
		// constructor must be known in metamodel
		assert mm.containsNode(constructor);
		assert mm.hasLink(constructor, MetaMeta.CONFORMS_TO, NODE);
		assert children.length==Integers.intValueOf(mm.getLink(constructor, ARITY));
		
		// m.beginTransaction();
		
		IIdentity identity = new Identity(m.getId());
		// add the new node
		m.addChild(identity); //Anna: erstatt med riktig kall
		// state that it is a node
		m.addLink(identity, MetaMeta.CONFORMS_TO, constructor);

		for (int i = 0; i < children.length; i++) {
			IIdentity label = mm.getLink(constructor, Integers.identityOf(i));
			assert mm.hasLink(label, MetaMeta.CONFORMS_TO, BRANCH);
			IIdentity destType = mm.getLink(label, DEST_TYPE);
			IIdentity childCons = m.getLink(children[i], MetaMeta.CONFORMS_TO);
			IIdentity childType = mm.getLink(childCons, TYPE);
			assert mm.hasPath(childType, SUBTYPE_OF, destType);
			m = m.addChild(identity, label, children[i]);
		}
		// m.endTransaction();
		return identity;
	}

	@Override
	public IIdentity newNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity addNode() {
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
		return model.getId();
	}

	@Override
	public int getNumChildren(IIdentity node) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static IIdentity getIdentity() {
		return model.getId();
	}
}
