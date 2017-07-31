package adevpck;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test; 

public class TestModelTree {

	@Test
	public void testSingleton(){
		IIdentity en = new Identity("1");
		ModelTree m = new ModelTree(en);
		assert m.containsNode(en);
		m.getAll().forEach(element -> {assert element.equals(en);});
		assert ! m.getChildren(en).iterator().hasNext() : "There should be no children";
	}
	
	@Test
	public void testEdgeVerySmallTree(){
		IIdentity pluss = new Identity("+");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");
		ModelTree m = new ModelTree(pluss);
		m = m.addChild(pluss, new Identity("left"), en);
		m = m.addChild(pluss, new Identity("right"), to);
		m.getAll().forEach(elem->System.out.print(elem + " "));
		System.out.println();
	}
	
	@Test
	public void testOrderedNodes(){
		IIdentity en= new Identity("1");
		IIdentity to = new Identity("2");
		IIdentity tre = new Identity("3");
		IIdentity fire = new Identity("4");
		IIdentity fem = new Identity("5");
		IIdentity seks = new Identity("6");
		IIdentity sju = new Identity("7");
		IIdentity åtte = new Identity("8");
		IIdentity edge = new Identity("edge");
		ModelTree m = new ModelTree(en);
		
		ModelTree copy = m;
		
		m = m.addChild(en, edge, to);
		m = m.addChild(en, edge, tre);
		m = m.addChild(en, edge, fire);
		m = m.addChild(en, edge, fem);
		m = m.addChild(to, edge, seks);
		m = m.addChild(seks, edge, sju);
		m = m.addChild(tre, edge, åtte);
		m.getAll().forEach(elem->System.out.print(elem + " "));
		System.out.println();
		
		copy = copy.addChild(en, edge, to);
		copy = copy.addChild(to, edge, seks);
		copy = copy.addChild(seks, edge, sju);
		copy = copy.addChild(en, edge, fire);
		copy = copy.addChild(en, edge, tre);
		copy = copy.addChild(tre, edge, åtte);
		copy = copy.addChild(en, edge, fem);
		
		assert copy.equals(m) : "should be eq regardless of order: " + copy + " eq " + m;
	}
	
	@Test
	public void testEdgeSmallRightHeavyTree(){
		IIdentity pluss = new Identity("+");
		IIdentity minus = new Identity("-");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");
		IIdentity tre = new Identity("3");
		ModelTree m = new ModelTree(pluss);
		m = m.addChild(pluss, new Identity("left"), tre);	//+ 3
//		m.getAll().forEach(elem->System.out.print(elem + " "));
//		System.out.println();
		m = m.addChild(pluss, new Identity("right"), minus); // + 3 -
//		m.getAll().forEach(elem->System.out.print(elem + " "));
//		System.out.println();
		m = m.addChild(minus, new Identity("left"), to);	// + 3 - 2 
//		m.getAll().forEach(elem->System.out.print(elem + " "));
//		System.out.println();
		m = m.addChild(minus, new Identity("right"), en); // + 3 - 2 1 
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
	
	@Test
	public void testDeepOneChildThree(){
		/* Number tree */
		IIdentity zero = new Identity("0");
		IIdentity one = new Identity("1");
		IIdentity two = new Identity("2");
		IIdentity three = new Identity("3");
		
		ModelTree numbers = new ModelTree(zero);
		numbers = numbers.addChild(zero, one);
		numbers = numbers.addChild(one, two);
		numbers = numbers.addChild(two, three);
		
		assert numbers.getChildren(zero).iterator().next().equals(one);
		assert numbers.getChildren(one).iterator().next().equals(two);
		assert numbers.getChildren(two).iterator().next().equals(three);
		assert !numbers.getChildren(three).iterator().hasNext();
	}
	
	@Test
	public void testCopy(){
		IIdentity zero = new Identity("0");
		IIdentity one = new Identity("1");
		IIdentity two = new Identity("2");
		IIdentity three = new Identity("3");
		
		ModelTree numbers = new ModelTree(zero);
		numbers = numbers.addChild(zero, one);
		numbers = numbers.addChild(one, two);
		numbers = numbers.addChild(two, three);
		
		ModelTree numberCopy = numbers.copy();
		assert numberCopy.equals(numbers);
		numbers = numbers.addChild(three, new Identity("4"));
		assert !numberCopy.equals(numbers) : "Should be not equal: " + numbers.getAll() + " and " + numberCopy.getAll();
	}
	
	@Test
	public void testCopyDelete(){
		IIdentity zero = new Identity("0");
		IIdentity one = new Identity("1");
		IIdentity two = new Identity("2");
		IIdentity three = new Identity("3");
		
		ModelTree numbers = new ModelTree(zero);
		numbers = numbers.addChild(zero, one);
		numbers = numbers.addChild(one, two);
		numbers = numbers.addChild(two, three);
		
		ModelTree numberCopy = numbers.copy();
		assert numberCopy.equals(numbers) : numberCopy + " should be equal to " + numbers;
		numbers = numbers.deleteSubtree(three);
		assert !numberCopy.equals(numbers) : "Should be not equal: " + numbers.getAll() + " and " + numberCopy.getAll();
		for(IIdentity node : numbers.getAll()){
			if(node.equals(three))
				assert !numberCopy.containsNode(node) : "this node should be removed " + node;
			else
				assert numberCopy.containsNode(node) : "this node should be found in the copy " + node;
		}
	}
	
	@Test
	public void testCopyDeleteAddSimple(){
		IIdentity zero = new Identity("0");
		IIdentity one = new Identity("1");
		IIdentity two = new Identity("2");
		IIdentity three = new Identity("3");
		
		IIdentity succ = new Identity("succ");
		
		ModelTree numbers = new ModelTree(zero);
		numbers = numbers.addChild(zero, succ, one);
		numbers = numbers.addChild(one, succ, two);
		numbers = numbers.addChild(two, succ, three);
		
		ModelTree numberCopy = numbers.copy();
		assert numberCopy.equals(numbers);
		numbers = numbers.deleteSubtree(two);
		assert !numberCopy.equals(numbers) : "Should be not equal: " + numbers.getAll() + " and " + numberCopy.getAll();
		numbers = numbers.addChild(one, succ, two);
		numbers = numbers.addChild(two, succ, three);
		assert numberCopy.equals(numbers) : "Should be equal: " + numbers + " and " + numberCopy;
	}
	
	@Test
	public void testCopyDeleteAdd(){
		/* build tree */
		IIdentity pluss = new Identity("+");
		IIdentity minus = new Identity("-");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");
		IIdentity tre = new Identity("3");
		IIdentity left = new Identity("left");
		IIdentity right = new Identity("right");
		ModelTree m = new ModelTree(pluss);
		m = m.addChild(pluss, left, tre);	//+ 3
		ModelTree missingright = m;
		m = m.addChild(pluss, right, minus); // + 3 -
		m = m.addChild(minus, left, to);	// + 3 - 2 
		m = m.addChild(minus, right, en); // + 3 - 2 1 
		m.getAll().forEach(elem->System.out.print(elem + " "));

		ModelTree afterdelete = m.deleteSubtree(minus);
		
		assert afterdelete.equals(missingright) : "should be eq: " + afterdelete + " and " + missingright;

		ModelTree afteradd = afterdelete.addChild(pluss, right, minus); // + 3 -
		afteradd = afteradd.addChild(minus, left, to);	// + 3 - 2 
		afteradd = afteradd.addChild(minus, right, en); // + 3 - 2 1 
		
		assert afteradd.equals(m) : "should be eq: " + afteradd + " and " + m;
		
	}		
}
