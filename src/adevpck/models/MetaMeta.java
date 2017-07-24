package adevpck.models;

import adevpck.IIdentity;
import adevpck.IModel;
import adevpck.Identity;
import adevpck.ModelTree;

public class Tree {
	public static ModelTree model;
	
	// basic metametamodelling
	public static final IIdentity CONFORMS_TO = id("conformsTo");
	public static final IIdentity SRC_CONFORMS_TO = id("srcConformsTo");
	public static final IIdentity DEST_CONFORMS_TO = id("destConformsTo");
	public static final IIdentity ELEMENT_OF = id("elementOf");
	public static final IIdentity RELATION = id("relation");
	public static final IIdentity FUNCTION = id("function");
	public static final IIdentity IDENTITY = id("identity");
	public static final IIdentity REQUIRE_ONE = id("requireOne");
	public static final IIdentity ZERO_OR_MORE = id("zeroOrMore");
	public static final IIdentity ZERO_OR_ONE = id("zeroOrOne");
	public static final IIdentity ONE_OR_MORE = id("oneOrMore");
	public static final IIdentity IS = id("is");
	public static final IIdentity LINK = id("link");
	public static final IIdentity MULTILINK = id("multilink");
	

	static {
		model.addLink(CONFORMS_TO, CONFORMS_TO, RELATION);
		model.addLink(RELATION, CONFORMS_TO, RELATION);
		model.addLink(ELEMENT_OF, CONFORMS_TO, RELATION);
		model.addLink(LINK, IS, FUNCTION);
		model.addLink(MULTILINK, IS, RELATION);
	}
	// from megamodelling
	public static final IIdentity IS_REPRESENTATION_OF = id("representationOf");
	public static final IIdentity IS_DECOMPOSED_IN = id("decomposedIn");
	
	
	
	public static final IIdentity NODE = id("node");
	public static final IIdentity BRANCH = id("branch");
	public static final IIdentity ARITY = id("arity");

	static {
		model.addLink(NODE, REQUIRE_ONE, ARITY);
		model.addLink(NODE, ZERO_OR_MORE, BRANCH);
		model.addLink(BRANCH, IS, FUNCTION);
		model.addLink(BRANCH, SRC_CONFORMS_TO, NODE);
		model.addLink(BRANCH, DEST_CONFORMS_TO, NODE);
		model.addLink(ARITY, IS, FUNCTION);
		model.addLink(ARITY, SRC_CONFORMS_TO, NODE);
		model.addLink(ARITY, DEST_CONFORMS_TO, Integers.INT_MODEL_ID); // TODO
		
	}

	public static final IIdentity DEST_TYPE = id("destType");
	public static final IIdentity TYPE = id("type");
	public static final IIdentity SUBTYPE_OF = id("subtypeOf");

	static {
		// a typed node 
		model.addLink(BRANCH, ZERO_OR_ONE, DEST_TYPE);
		model.addLink(DEST_TYPE, IS, FUNCTION);
		model.addLink(DEST_TYPE, DEST_CONFORMS_TO, TYPE);
		
		// subtypeOf is a relation between two types
		model.addLink(SUBTYPE_OF, IS, RELATION);
		model.addLink(SUBTYPE_OF, SRC_CONFORMS_TO, TYPE);
		model.addLink(SUBTYPE_OF, DEST_CONFORMS_TO, TYPE);
	}
	
	
	private static IIdentity id(String string) {
		return new Identity("Tree::" + string);
	}
	
}
