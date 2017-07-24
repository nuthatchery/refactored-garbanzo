package adevpck.models;

import adevpck.IIdentity;
import adevpck.IModel;
import adevpck.Identity;
import adevpck.ModelTree;

public class Tree {
	public static ModelTree model;

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
		IIdentity identity = new Identity("Tree::" + string);
		model.addChild(identity);
		return identity;
	}

	public static ModelTree getInstance() {
		return model;
	}

	public static IIdentity makeNode(ModelTree m, ModelTree mm, IIdentity constructor, IIdentity... children) {
		assert m.hasLink(m.getIdObject(), MetaMeta.CONFORMS_TO, mm.getIdObject());
		// constructor must be known in metamodel
		assert mm.containsNode(constructor);
		assert mm.hasLink(constructor, MetaMeta.CONFORMS_TO, NODE);

		IIdentity identity = new Identity();
		// add the new node
		m.addChild(identity);
		// state that it is a node
		m.addLink(identity, MetaMeta.CONFORMS_TO, constructor);

		for (int i = 0; i < children.length; i++) {
			IIdentity label = mm.getLink(constructor, Integers.identityOf(i));
			assert mm.hasLink(label, MetaMeta.CONFORMS_TO, BRANCH);
			IIdentity destType = mm.getLink(label, DEST_TYPE);
			IIdentity childCons = m.getLink(children[i], MetaMeta.CONFORMS_TO);
			IIdentity childType = mm.getLink(childCons, TYPE);
			assert mm.hasPath(childType, SUBTYPE_OF, destType);
			m.addChild(identity, label, children[i]);
		}
		return identity;
	}
}
