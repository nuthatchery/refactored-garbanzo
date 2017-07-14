package extras;

import comp.IElementHandle;

public class BasicExample {
	static IModel m;

	// stuff for metametamodel
	final static IIdentity ELEMENTNAME = m.makeIdentity("ElementName");
	
	// this would be metamodel/schema things, to be set up elsewhere
	final static IIdentity ASSIGN = m.makeIdentity("Assign");
	final static IIdentity PLUS = m.makeIdentity("Plus");
	final static IIdentity VAR = m.makeIdentity("Var");
	final static IIdentity ROOT = m.makeIdentity("Root");

	
	public static void main(String[] args) {

}
	
	public static void createTree(IModel m) {
		// make the tree  x = x + y
		
		
		
		// basic interface for creating things is probably pretty clunky
		m.beginTransaction();
		IIdentity root = m.makeElement(ROOT);
		IIdentity a = m.makeElement(ASSIGN, root);
		IIdentity v = m.makeElement(VAR, a);
		m.setData(v, "x");  // these would normally be links to declarations rather than names
		IIdentity p = m.makeElement(PLUS, a);
		IIdentity x = m.makeElement(VAR, p);
		m.setData(x, "x");
		IIdentity y = m.makeElement(VAR, p);
		m.setData(y, "y");
		m.commitTransaction();
	}
	
	public static void traverse(IElementHandle elt) {
		System.out.print(m.get(ELEMENTNAME).getIdentity() + "(");
		for(IElementHandle h : elt.getChildren()) {
			traverse(h);
			System.out.print(" ");
			System.out.println(")");
		}
		
	}
}
