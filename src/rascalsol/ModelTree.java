package rascalsol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelTree {
	/**
	 * flat edges: such that for any 0<=i<b.size() we have b(i) = parent, b(i+1) = edge, b(i+2) = child
	 */
	private ArrayList<IIdentity> b;

	/**
	 * map: Node -> first entry of children in B such that 
	 * bindex.get(n) = i where i is the smallest index 0<=i<b.size such that (i+1) % 3 = 1 and b(i)=n
	 */
	private HashMap<IIdentity, Integer> bindex = new HashMap<>();

	private ArrayList<IIdentity> l;
	/**
	 * map: Node -> first entry of links in L such that 
	 * lindex.get(n) = i where 0<=i<L.length such that L[i][0] points to n, and there is no j<i such that L[j][0] points to n
	 */
	private HashMap<IIdentity, Integer> lindex = new HashMap<>();
	/**
	 * the root of this model (no good reason to keep this around)
	 */
	private IIdentity root;
	/**
	 * the full set of Ns of this model, ordered by a preorder tree traversal 
	 */
	private List<IIdentity> orderednodes = new ArrayList<>();


	public ModelTree(IIdentity root){

		b = new ArrayList<IIdentity>();
		l = new ArrayList<IIdentity>();

		bindex.put(root, b.size());
		lindex.put(root, l.size());

		orderednodes.add(root);
		this.root = root;
		datainvariant();
	}

	public ModelTree(ModelTree m){
		this(m.getRoot());
		//add to register as ++version of m
	}

	private IIdentity getRoot() {
		return root;
	}

	public ModelTree addChild(IIdentity child, IIdentity edge, IIdentity parent){
		int next = orderednodes.indexOf(parent);
		if (next<0) throw new IllegalArgumentException("parent must be a node in this model: " + parent);
		next = next+1;

		//		System.out.println("Adding " + child);
		ModelTree m = new ModelTree(this);
		//		ModelTree m = this; //mutable

		cloneLinksTo(m);

		//System.arraycopy(B, 0, dest, destPos, length);
		//Collections.addAll(b, [])
		//List.addAll(b)

		int parentIndexB = bindex.get(parent);
		Integer toIndex = bindex.get(next);
		if(toIndex == null)
			toIndex = b.size();
		m.b = new ArrayList<IIdentity>(b.subList(0, toIndex));
		m.b.add(parent);
		m.b.add(edge);
		m.b.add(child);
		
		b.subList(toIndex, b.size()).forEach(elem->m.b.add(elem));

		m.bindex = new HashMap<IIdentity, Integer>();
		bindex.forEach((id, bi)->{
			if(bi<parentIndexB) m.bindex.put(id, bi); 
			else m.bindex.put(id,  bi+3);
		}
				);
		m.bindex.put(parent, parentIndexB);
		m.bindex.put(child, m.b.size());
		//			m.bindex.forEach((a, b) -> System.out.println(a + " -> " + b));

		m.orderednodes.clear(); 
		m.getAll().forEach(elem-> m.orderednodes.add(elem));
		datainvariant(); //TODO not really needed
		m.datainvariant();
		return m;
	}

	private void cloneLinksTo(ModelTree m) {
		m.l = (ArrayList<IIdentity>) l.clone();
		m.lindex = (HashMap<IIdentity, Integer>) lindex.clone();
	}

	public ModelTree addLink(IIdentity from, IIdentity link, IIdentity to){
		int next = orderednodes.indexOf(from);
		if (next<0) throw new IllegalArgumentException("from must be a node in this model: " + from);
		next = next+1;

		//		System.out.println("Adding " + child);
		ModelTree m = new ModelTree(this);
		//		ModelTree m = this; //mutable

		cloneEdgesTo(m);

		//System.arraycopy(B, 0, dest, destPos, length);
		//Collections.addAll(b, [])
		//List.addAll(b)

		int parentIndexB = lindex.get(from);
		Integer toIndex = lindex.get(next);
		if(toIndex == null)
			toIndex = l.size();
		m.b = new ArrayList<IIdentity>(l.subList(0, toIndex));
		m.l.add(from);
		m.l.add(link);
		m.l.add(to);
		
		l.subList(toIndex, l.size()).forEach(elem->m.l.add(elem));

		m.lindex = new HashMap<IIdentity, Integer>();
		lindex.forEach((id, bi)->{
			if(bi<parentIndexB) m.lindex.put(id, bi); 
			else m.lindex.put(id,  bi+3);
		}
				);
		m.lindex.put(from, parentIndexB);
		m.lindex.put(to, m.l.size());
		//			m.lindex.forEach((a, b) -> System.out.println(a + " -> " + b));

		m.orderednodes.clear(); 
		m.getAll().forEach(elem-> m.orderednodes.add(elem));
		datainvariant(); //TODO not really needed
		m.datainvariant();
		return m;
	}

	private void cloneEdgesTo(ModelTree m) {
		m.b = (ArrayList<IIdentity>) b.clone();
		m.bindex = (HashMap<IIdentity, Integer>) bindex.clone();
	}
	
	/**
	 * @return an iterable of the nodes in prefix traversal
	 */
	public Iterable<IIdentity> getAll(){
		List<IIdentity> ls = new ArrayList<>();

		class LocalListNodeAdder{
			void addNode(IIdentity n){
				ls.add(n);
				if(bindex.get(n)==null||bindex.get(n)>=b.size())return;
				for(int i=bindex.get(n); i<b.size() && b.get(i) != null && b.get(i).equals(n); i+=3){
					addNode(b.get(i+2));
				}
			}
		};

		new LocalListNodeAdder().addNode(root);
		//		System.out.println("printing in getAll");
		//		ls.forEach(l->System.out.print(l));
		//		System.out.println();
		return ls;
	}
	
	private void datainvariant(){
		// XXX: for boolean går det an å bruke noe slikt:
		// bindex.entrySet().stream().allMatch(predicate)
		
		orderednodes.forEach(item -> {assert bindex.containsKey(item) : "All nodes should be found in bindex: " + item;});

		bindex.forEach((item, i) -> {assert orderednodes.contains(item): "all parent nodes of model should be recorded in the model's list of nodes: " + item;});

		orderednodes.forEach(item -> {assert bindex.get(item)!=null: "No nodes should have index null: " + item;});

		for(int i=0; i<orderednodes.size(); i++){
			IIdentity n = orderednodes.get(i);
			if(bindex.get(n)!=b.size()){
				for(int j = i+3; j<orderednodes.size(); j+=3){
					assert bindex.get(n) <= bindex.get(orderednodes.get(j)) : "Ordered nodes should have increasing branch-index: " + n + "->" + bindex.get(n) + " <= " + orderednodes.get(j) + "->" + bindex.get(orderednodes.get(j));
				}
			}
		}
	}
}
