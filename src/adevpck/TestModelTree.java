package adevpck;

import java.util.ArrayList;
import java.util.List;

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
	
	@Test
	public void testAddGetChildren(){
		IIdentity pluss = new Identity("+");
		IIdentity one = new Identity("1");
		IIdentity two = new Identity("2");
		IIdentity three = new Identity("3");
		ModelTree m = new ModelTree(pluss);
		m = m.addChild(one, pluss);
		m = m.addChild(two, pluss);
		m = m.addChild(three, pluss);
		m.getChildren(pluss).forEach(elem->System.out.print(elem + " "));
		
		List<IIdentity> ls = new ArrayList<IIdentity>();
		ls.add(one);
		ls.add(two);
		ls.add(three);
		List<IIdentity> children = new ArrayList<IIdentity>();
		m.getChildren(pluss).forEach(child -> {children.add(child);});
		children.forEach(item -> {assert ls.contains(item) : "missing item " + item + " in " + ls;});
		ls.forEach(item -> {assert children.contains(item) : "missing item " + item + " in " + children;});
	}
	
//	@Test
	public void testRegisterWithTwoModelTreesUseLink(){
		/* Number tree */
		IIdentity zero = new Identity("0");
		IIdentity one = new Identity("1");
		IIdentity two = new Identity("2");
		IIdentity three = new Identity("3");
		
		ModelTree numbers = new ModelTree(zero);
		numbers.addChild(zero);
		numbers.addChild(one);
		numbers.addChild(two);
		numbers.addChild(three);
		
		IIdentity pluss = new Identity("+");
		IIdentity minus = new Identity("-");
		
		IIdentity en = new Identity();
		IIdentity to = new Identity();
		IIdentity tre = new Identity();
		ModelTree m = new ModelTree(pluss);
		m = m.addChild(tre, new Identity("left"), pluss);	//+ 3
		m = m.addLink(tre, three);
		m = m.addChild(minus, new Identity("right"), pluss); // + 3 -
		m = m.addChild(to, new Identity("left"), minus);	// + 3 - 2
		m = m.addLink(to, two);
		m = m.addChild(en, new Identity("right"), minus); // + 3 - 2 1
		m = m.addLink(en, one);
		m.getAll().forEach(elem->System.out.print(elem + " "));
	}
}
