package tests;

import org.junit.Test;

import comp.IIdentity;
import comp.Identity;
import comp.ModelTree;

public class TestMutable {

	
	@Test
	public void testMutableObject(){
		IIdentity pluss = new Identity("+");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");

		ModelTree m = new ModelTree(pluss);
		m = m.beginTransaction();

		m.addChild(pluss, new Identity("left"), en);
		m.addChild(pluss, new Identity("right"), to);
		
		m.commitTransaction();

		assert m.containsNode(pluss) : m ;
		assert m.containsNode(en) : m ;
		assert m.containsNode(to) : m ; 
		assert m.getNumChildren(pluss) == 2;
		m.getChildren(pluss).forEach((item) -> {assert item.equals(en) || item.equals(to);});
	}
	
	@Test
	public void testRollback(){
		IIdentity pluss = new Identity("+");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");

		ModelTree m = new ModelTree(pluss);
		ModelTree original = m;
		
		m = m.beginTransaction();

		m.addChild(pluss, new Identity("left"), en);
		m.addChild(pluss, new Identity("right"), to);
		assert !m.equals(original);
		
		m.rollbackTransaction();

		assert m.equals(original) : "not eq: " + m + " and " + original;
	}
	
	
}
