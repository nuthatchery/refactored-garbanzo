package rascalsol;

import org.junit.Test; 

public class TestModelTree {

	@Test
	public void testSingleton(){
		IIdentity en = new Identity("1");
		ModelTree m = new ModelTree(en);
		m.getAll().forEach(elem->System.out.print(elem + " "));
		System.out.println();
	}
	
	@Test
	public void testEdgeVerySmallTree(){
		IIdentity pluss = new Identity("+");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");
		ModelTree m = new ModelTree(pluss);
		m = m.addChild(en, new Identity("left"), pluss);
		m = m.addChild(to, new Identity("right"), pluss);
		m.getAll().forEach(elem->System.out.print(elem + " "));
		System.out.println();
	}
	
	@Test
	public void testEdgeSmallRightHeavyTree(){
		IIdentity pluss = new Identity("+");
		IIdentity minus = new Identity("-");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");
		IIdentity tre = new Identity("3");
		ModelTree m = new ModelTree(pluss);
		m = m.addChild(tre, new Identity("left"), pluss);	//+ 3
//		m.getAll().forEach(elem->System.out.print(elem + " "));
//		System.out.println();
		m = m.addChild(minus, new Identity("right"), pluss); // + 3 -
//		m.getAll().forEach(elem->System.out.print(elem + " "));
//		System.out.println();
		m = m.addChild(to, new Identity("left"), minus);	// + 3 - 2 
//		m.getAll().forEach(elem->System.out.print(elem + " "));
//		System.out.println();
		m = m.addChild(en, new Identity("right"), minus); // + 3 - 2 1 
		m.getAll().forEach(elem->System.out.print(elem + " "));
	}
	
	
}
