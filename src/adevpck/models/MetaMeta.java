package adevpck.models;

import relationalmodel.IModel;

import java.util.List;

import adevpck.Identity;
import adevpck.ModelTree;
import adevpck.datastructures.Triple;
import adevpck.datastructures.Tuple;
import comp.IIdentity;
import comp.IUnchangeableModel;
import relationalmodel.RelationalModel;

public class MetaMeta implements IUnchangeableModel{
	private static final RelationalModel model = new RelationalModel(true);
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
		model.addEdge(CONFORMS_TO, CONFORMS_TO, RELATION);
		model.addEdge(RELATION, CONFORMS_TO, RELATION);
		model.addEdge(ELEMENT_OF, CONFORMS_TO, RELATION);
		model.addEdge(LINK, IS, FUNCTION);
		model.addEdge(MULTILINK, IS, RELATION);
	}
	// from megamodelling
	public static final IIdentity IS_REPRESENTATION_OF = id("representationOf");
	public static final IIdentity IS_DECOMPOSED_IN = id("decomposedIn");
	
	
	
	public static final IIdentity NODE = id("node");
	public static final IIdentity BRANCH = id("branch");
	public static final IIdentity ARITY = id("arity");

	static {
		model.addEdge(NODE, REQUIRE_ONE, ARITY);
		model.addEdge(NODE, ZERO_OR_MORE, BRANCH);
		model.addEdge(BRANCH, IS, FUNCTION);
		model.addEdge(BRANCH, SRC_CONFORMS_TO, NODE);
		model.addEdge(BRANCH, DEST_CONFORMS_TO, NODE);
		/*Number of branches should be equal to arity*/
		model.addEdge(ARITY, IS, FUNCTION);
		model.addEdge(ARITY, SRC_CONFORMS_TO, NODE);
		model.addEdge(ARITY, DEST_CONFORMS_TO, Integers.INT_MODEL_ID); // TODO
		
	}

	public static final IIdentity DEST_TYPE = id("destType");
	public static final IIdentity TYPE = id("type");
	public static final IIdentity SUBTYPE_OF = id("subtypeOf");

	static {
		// a typed node 
		model.addEdge(BRANCH, ZERO_OR_ONE, DEST_TYPE);
		model.addEdge(DEST_TYPE, IS, FUNCTION);
		model.addEdge(DEST_TYPE, DEST_CONFORMS_TO, TYPE);
		
		// subtypeOf is a relation between two types
		model.addEdge(SUBTYPE_OF, IS, RELATION);
		model.addEdge(SUBTYPE_OF, SRC_CONFORMS_TO, TYPE);
		model.addEdge(SUBTYPE_OF, DEST_CONFORMS_TO, TYPE);
	}
	
	
	private static IIdentity id(String string) {
		return new Identity("MetaMeta::" + string);
	}


	public static IIdentity getIdentity() {
		return modelid;
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
	
}
