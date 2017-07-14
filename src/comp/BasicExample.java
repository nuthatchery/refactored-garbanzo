package comp;

public class BasicExample {
	static IModifiableModel m;

	// stuff for metametamodel
	final static IIdentity NODE_SCHEMA = m.makeIdentity("NodeSchema");
	
	// this would be metamodel/schema things, to be set up elsewhere
	final static IIdentity ASSIGN = m.makeIdentity("Assign");
	final static IIdentity PLUS = m.makeIdentity("Plus");
	final static IIdentity VAR = m.makeIdentity("Var");
	final static IIdentity ROOT = m.makeIdentity("Root");

	
	public static void main(String[] args) {

}
	
	public static void createTree(IModel m1) {
		// make the tree  x = x + y
		
		
		
		// basic interface for creating things is probably pretty clunky
		IModifiableModel mm = m1.beginTransaction();
		IIdentity root = mm.makeElement(ROOT);
		IIdentity a = mm.makeElement(ASSIGN, root);
		IIdentity v = mm.makeElement(VAR, a);
		mm.setData(v, "x");  // these would normally be links to declarations rather than names
		IIdentity p = mm.makeElement(PLUS, a);
		IIdentity x = mm.makeElement(VAR, p);
		mm.setData(x, "x");
		IIdentity y = mm.makeElement(VAR, p);
		mm.setData(y, "y");
		IModel m2 = mm.commitTransaction();
	}
	
	public static void traverse(IElementHandle elt) {
		System.out.print(m.get(NODE_SCHEMA).getIdentity() + "(");
		for(IElementHandle h : elt.getChildren()) {
			traverse(h);
			System.out.print(" ");
			System.out.println(")");
		}
		
	}
}
