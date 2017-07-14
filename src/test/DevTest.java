package test;

import org.junit.Test;

import comp.IModel;
import comp.Model;
import comp.Node;
import comp.NodeFactory;

public class DevTest {

	
	@Test
	public void makingABasicTree(){
		// make the tree  x = x + y
		IModel m = new Model(NodeFactory.getRoot());
		Node xdecl = NodeFactory.getDecl("x", NodeFactory.getNum(1));
		Node ydecl = NodeFactory.getDecl("y", NodeFactory.getNum(2));
		m.addChild(NodeFactory.getStatement(xdecl), m.getRoot());
		m.addChild(NodeFactory.getStatement(ydecl), m.getRoot());
		Node tree = NodeFactory.getAssign(
				NodeFactory.getVar(xdecl), 
				NodeFactory.getPlus(xdecl, ydecl));
		Node stmt = NodeFactory.getStatement(tree);
		m.addChild(stmt, m.getRoot());
	}
}
