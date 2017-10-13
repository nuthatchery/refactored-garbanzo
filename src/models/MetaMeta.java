package models;

import relationalmodel.IModel;
import relationalmodel.RelationalModel;
import comp.IIdentity;
import comp.ITransactableModel;
import comp.Identity;

public class MetaMeta { //should not impl IModel
	private static final IModel model;
	private static RelationalModel buildModel = new RelationalModel().beginTransaction(); //Should ideally be final.
	private static final IIdentity modelid = new Identity("MetaMeta");
	
	// basic metametamodelling
	/**
	 * reflexive, transitive, 
	 */
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
	/**
	 * reflexive, transitive, sorta like subtype of 
	 */
	public static final IIdentity IS = id("is");
	public static final IIdentity LINK = id("link");
	public static final IIdentity MULTILINK = id("multilink");
	

	static {
		buildModel.addEdge(CONFORMS_TO, CONFORMS_TO, RELATION);
		buildModel.addEdge(RELATION, CONFORMS_TO, RELATION);
		buildModel.addEdge(ELEMENT_OF, CONFORMS_TO, RELATION);
		buildModel.addEdge(LINK, IS, FUNCTION);
		buildModel.addEdge(MULTILINK, IS, RELATION);
	}
	// from megamodelling
	public static final IIdentity IS_REPRESENTATION_OF = id("representationOf");
	public static final IIdentity IS_DECOMPOSED_IN = id("decomposedIn");
	
	
	
	public static final IIdentity NODE = id("node");
	public static final IIdentity BRANCH = id("branch");
	public static final IIdentity ARITY = id("arity");

	static {
		buildModel.addEdge(NODE, REQUIRE_ONE, ARITY);
		buildModel.addEdge(NODE, ZERO_OR_MORE, BRANCH);
		buildModel.addEdge(BRANCH, IS, FUNCTION);
		buildModel.addEdge(BRANCH, SRC_CONFORMS_TO, NODE);
		buildModel.addEdge(BRANCH, DEST_CONFORMS_TO, NODE);
		/*Number of branches should be equal to arity*/
		buildModel.addEdge(ARITY, IS, FUNCTION);
		buildModel.addEdge(ARITY, SRC_CONFORMS_TO, NODE);
		buildModel.addEdge(ARITY, DEST_CONFORMS_TO, Integers.INT_MODEL_ID); // TODO
		
	}

	public static final IIdentity DEST_TYPE = id("destType");
	public static final IIdentity TYPE = id("type");
	public static final IIdentity SUBTYPE_OF = id("subtypeOf");

	static {
		// a typed node 
		buildModel.addEdge(BRANCH, ZERO_OR_ONE, DEST_TYPE);
		buildModel.addEdge(DEST_TYPE, IS, FUNCTION);
		buildModel.addEdge(DEST_TYPE, DEST_CONFORMS_TO, TYPE);
		
		// subtypeOf is a relation between two types
		buildModel.addEdge(SUBTYPE_OF, IS, RELATION);
		buildModel.addEdge(SUBTYPE_OF, SRC_CONFORMS_TO, TYPE);
		buildModel.addEdge(SUBTYPE_OF, DEST_CONFORMS_TO, TYPE);
	}
	
	static {
		model = buildModel.commitTransaction();
	}
	
	
	private static IIdentity id(String string) {
		return buildModel.addNode("MetaMeta::" + string);
	}


	public static IIdentity getIdentity() {
		return modelid;
	}
	
}
