package tests;

import org.junit.Test;

import adevpck.IIdentity;
import adevpck.Identity;
import adevpck.ModelTree;

public class TestMutable {

	
	@Test
	public void testMutableImmutableObject(){
		IIdentity pluss = new Identity("+");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");

		ModelTree m = new ModelTree(pluss);
		m.beginTransaction();

		m.addChild(pluss, new Identity("left"), en);
		m.addChild(pluss, new Identity("right"), to);
		
		m.commitTransaction();

		assert m.containsNode(pluss) : m ;
		assert m.containsNode(en) : m ;
		assert m.containsNode(to) : m ; 
		assert m.getNumChildren(pluss) == 2;
		m.getChildren(pluss).forEach((item) -> {assert item.equals(en) || item.equals(to);});
	}
}
