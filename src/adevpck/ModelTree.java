package adevpck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModelTree implements IIdentity{
	/**
	 * flat edges: such that for any 0<=i<b.size() we have b(i) = parent, b(i+1) = edge, b(i+2) = child
	 */
	private ArrayList<IIdentity> b;

	/**
	 * map: Node -> first entry of children in b such that 
	 * bindex.get(n) = i where i is the smallest index 0<=i<b.size such that (i+1) % 3 = 1 and b(i)=n
	 */
	private HashMap<IIdentity, Integer> bindex = new HashMap<>();

	private ArrayList<IIdentity> l;
	/**
	 * map: Node -> first entry of children in l such that 
	 * lindex.get(n) = i where i is the smallest index 0<=i<l.size such that (i+1) % 3 = 1 and l(i)=n
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

	/**
	 * id of this model
	 */
	private final IIdentity id;

	public ModelTree(IIdentity root){
		id = new Identity();

		b = new ArrayList<IIdentity>();
		l = new ArrayList<IIdentity>();

		bindex.put(root, b.size());
		lindex.put(root, l.size());

		orderednodes.add(root);
		this.root = root;
		datainvariant();
	}

	public ModelTree(ModelTree m){
		id = m.id;
		b = new ArrayList<IIdentity>();
		l = new ArrayList<IIdentity>();

		bindex.put(root, b.size());
		lindex.put(root, l.size());

		orderednodes.add(root);
		this.root = m.root;
		datainvariant();
	}

	private IIdentity getRoot() {
		return root;
	}

	public ModelTree addChild(final IIdentity parent,final IIdentity edge, final IIdentity child){
//		System.out.println("Adding child " + child + " to " + parent);
		int next = orderednodes.indexOf(parent);
		if (next<0) throw new IllegalArgumentException("parent must be a node in this model (" + orderednodes + ": " + parent);
		next = next+1;

		//		System.out.println("Adding " + child);
		ModelTree m = new ModelTree(this);
		//		ModelTree m = this; //mutable

		cloneLinksTo(m);

		//System.arraycopy(B, 0, dest, destPos, length);
		//Collections.addAll(b, [])
		//List.addAll(b)

		int parentIndex = bindex.get(parent);
		int toIndex = getToIndex(next, b, bindex);
		if(next<orderednodes.size()){
			m.lindex.put(child, m.lindex.get(orderednodes.get(next)));
		}
		else {
			m.lindex.put(child, m.l.size());
		}
		
		m.b = new ArrayList<IIdentity>(b.subList(0, parentIndex));
		m.b.add(parent);
		m.b.add(edge);
		m.b.add(child);

		b.subList(parentIndex, b.size()).forEach(elem->m.b.add(elem));

		m.bindex = new HashMap<IIdentity, Integer>();
		bindex.forEach((id, bi)->{
			if(bi<parentIndex) m.bindex.put(id, bi); 
			else m.bindex.put(id,  bi+3);
		}
				);
		m.bindex.put(parent, parentIndex);
		m.bindex.put(child, toIndex+3);

//		System.out.println("added child " + child + " to " + parent);
//		System.out.println(bindex + " -> " +m.bindex);
//		System.out.println(b + " -> " + m.b);
		
		copyAllOrderedNodesTo(m);
		datainvariant(); //TODO not really needed
		m.datainvariant();
		return m;
	}

	/**
	 * Copies all nodes in existing order from {@link this} to the parameter model
	 * @param m the model to copy to 
	 */
	private void copyAllOrderedNodesTo(ModelTree m) {
		m.orderednodes.clear(); 
		m.getAll().forEach(elem-> m.orderednodes.add(elem));
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

		int parentIndex = lindex.get(from);
		int toIndex = getToIndex(next, l, lindex);
		
		m.l = new ArrayList<IIdentity>(l.subList(0, toIndex));
		m.l.add(from);
		m.l.add(link);
		m.l.add(to);

		l.subList(toIndex, l.size()).forEach(elem->m.l.add(elem));

		m.lindex = new HashMap<IIdentity, Integer>();
		lindex.forEach((id, bi)->{
			if(bi<parentIndex) m.lindex.put(id, bi); 
			else m.lindex.put(id,  bi+3);
		}
				);
		m.lindex.put(from, parentIndex);
		//m.lindex.put(to, toIndex+3);
		//			m.lindex.forEach((a, b) -> System.out.println(a + " -> " + b));

		copyAllOrderedNodesTo(m);
		datainvariant(); //TODO not really needed
		m.datainvariant();
		return m;
	}

	private Integer getToIndex(int next, ArrayList<IIdentity> targetList, HashMap<IIdentity, Integer> indexmap) {
		Integer toIndex;
		if(next<orderednodes.size()){
			toIndex = indexmap.get(orderednodes.get(next));
		}
		else {
			toIndex = targetList.size();
		}
		return toIndex;
	}

	private void cloneEdgesTo(ModelTree m) {
		m.b = (ArrayList<IIdentity>) b.clone();
		m.bindex = (HashMap<IIdentity, Integer>) bindex.clone();
	}

	/**
	 * @return all nodes in this model
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
		
//		System.out.println("printing ordered nodes " + orderednodes);
//		System.out.println("printing bindex " + bindex);
//		System.out.println("printing b " + b);
//		System.out.println();

		orderednodes.forEach(item -> {assert bindex.containsKey(item) : "All nodes should be found in bindex: " + item;});

		bindex.forEach((item, i) -> {assert orderednodes.contains(item): "all parent nodes of model should be recorded in the model's list of nodes: " + item + ": from " + bindex.toString() + " in " + Arrays.toString(orderednodes.toArray());});

		orderednodes.forEach(item -> {assert bindex.get(item)!=null: "No nodes should have index null: " + item;});

		for(int i=0; i<orderednodes.size(); i++){
			IIdentity n = orderednodes.get(i);
			if(bindex.get(n)!=b.size()){
				for(int j = i+3; j<orderednodes.size(); j+=3){
					assert bindex.get(n) <= bindex.get(orderednodes.get(j)) : "Ordered nodes should have increasing branch-index: " + n + "->" + bindex.get(n) + " <= " + orderednodes.get(j) + "->" + bindex.get(orderednodes.get(j));
				}
			}
		}

		for(int i=0; i<orderednodes.size(); i++){
			IIdentity n = orderednodes.get(i);
			if(lindex.get(n)!=l.size()){
				for(int j = i+3; j<orderednodes.size(); j+=3){
					assert lindex.get(n) <= lindex.get(orderednodes.get(j)) : "Ordered nodes should have increasing branch-index: " + n + "->" + lindex.get(n) + " <= " + orderednodes.get(j) + "->" + lindex.get(orderednodes.get(j));
				}
			}
		}
	}

	@Override
	public String getId() {
		return this.id.getId();
	}

	@Override
	public String getName() {
		return id.getName();
	}

	@Override
	public IIdentity setName(String name) {
		this.id.setName(name);
		return this;
	}

	public IIdentity getIdObject() {
		return id;
	}

	public boolean containsNode(IIdentity node) {
		return orderednodes.contains(node);
	}

	/**
	 * Adds a child to the "last" node of the tree respective to the preorder traversalusing an unlabeled edge
	 * @param child
	 * @return 
	 */
	public ModelTree addChild(IIdentity child) {
//		System.out.println("addChild(child): child=" + child);
		return addChild(orderednodes.get(orderednodes.size()-1), new Identity("edge"), child);
	}
	
	/**
	 * Deletes a node from the model and all edges pointing to it 
	 * @param node the node and endpoint of edges to be deleted 
	 * @return
	 */
	public ModelTree deleteSubtree(IIdentity node){
		int next = orderednodes.indexOf(node);
		if(next<0)
			throw new IllegalArgumentException("Id must be a node in this model: " + node);
		next=next+1;
		
		ModelTree m = this.copy();
		for(IIdentity child : getChildren(node))
			m = deleteSubtree(child);
		
		int nodeIndex = lindex.get(node);
		int toIndex = getToIndex(next, l, lindex);
		int diff = toIndex-nodeIndex;
		
		for(int i=nodeIndex; i<toIndex; i+=3)
			assert l.get(i).equals(node);
		if(nodeIndex<m.l.size())
			m.l.subList(nodeIndex, toIndex).clear();
		m.lindex.remove(node);
		for(IIdentity n : m.orderednodes.subList(next, m.orderednodes.size())){
			HashMap<IIdentity, Integer> lindex2 = m.lindex;
			Integer nindex = m.lindex.get(n);
			lindex2.put(n, nindex+diff);
		}
		
		nodeIndex = bindex.get(node);
		toIndex = getToIndex(next, b, bindex);
		
		for(int i=nodeIndex; i<toIndex; i+=3)
			assert b.get(i).equals(node);
		if(nodeIndex<m.b.size())
			m.b.subList(nodeIndex, toIndex).clear();
		m.bindex.remove(node);
		for(IIdentity n : m.orderednodes.subList(next, m.orderednodes.size())){
			m.bindex.put(n, m.bindex.get(n)+diff);
		}
		
		for(int i=0; i<m.b.size(); i+=3)
			if(m.b.get(i+2).equals(node)){
				m.b.subList(i, i+3).clear();
				i-=3;
			}
		
		m.orderednodes.remove(node);
		
		m.datainvariant();
		return m;
	}

	public ModelTree deleteEdge(IIdentity parent, IIdentity edge, IIdentity child){
		int next = orderednodes.indexOf(parent);
		if(next<0)
			throw new IllegalArgumentException("Parent must be a node in this model: " + parent);
		next=next+1;
		
		
		ModelTree m = this.copy();
	
		m.datainvariant();
		return m;
	}
	
	public ModelTree copy() {
		ModelTree m = new ModelTree(this);
		m.orderednodes = new ArrayList<>(orderednodes);
		cloneEdgesTo(m);
		cloneLinksTo(m);
		m.datainvariant();
		return m;
	}

	/**
	 * Adds an unlabeled link from node {@link from} to identity {@link to}  
	 * @param from a node in this model
	 * @param to a node in some model
	 * @return the new model
	 */
	public ModelTree addLink(IIdentity from, IIdentity to) {
//		System.out.println("addLink(from, to): from=" + from + ", to=" + to);
		return addLink(from, new Identity("link"), to);
	}

	public Iterable<IIdentity> getChildren(IIdentity parent){
		int next = orderednodes.indexOf(parent);
		if(next<0)
			throw new IllegalArgumentException("Identity is not a node in this model " + parent);
		next++;
		int toIndex;
		if(next<orderednodes.size())
			toIndex = bindex.get(orderednodes.get(next));
		else
			toIndex = b.size();
		
		List<IIdentity> ret = new ArrayList<>();
		for(int i = bindex.get(parent); i<toIndex; i+=3){
			assert b.get(i).equals(parent);
			ret.add(b.get(i+2));
		}
		return ret;
	}

	public HashMap<IIdentity, IIdentity> getLinks(IIdentity node){
		int next = orderednodes.indexOf(node);
		if(next<0)
			throw new IllegalArgumentException("Identity is not a node in this model " + node);
		next++;
		int toIndex;
		if(next<orderednodes.size())
			toIndex = lindex.get(orderednodes.get(next));
		else
			toIndex = l.size();
		
		HashMap<IIdentity, IIdentity> ret = new HashMap<>();
		for(int i = lindex.get(node); i<toIndex; i+=3){
			assert l.get(i).equals(node);
			ret.put(l.get(i+1), l.get(i+2));
		}
		return ret;
	}
	
	/**
	 * Adds an unlabeled edge from node {@link from} to identity {@link to}  
	 * @param from
	 * @param to
	 * @return the new model
	 */
	public ModelTree addChild(IIdentity from, IIdentity to) {
		return addChild(to, new Identity(), from);
	}

	/**
	 * Debugging method: 
	 * 
	 * Gets all nodes where if a node doesn't have a label, but it has a link, 
	 * the node will be replaced with the linked to node in returnobject 
	 * If a node has several links, only the first one will be used 
	 * @return  
	 */
	public Iterable<IIdentity> getAllWithLinkEval() {
		List<IIdentity> ret = new ArrayList<>();
		for(IIdentity node : getAll()){
			if(node.getName() == null || node.getName().isEmpty()){
				HashMap<IIdentity, IIdentity> links = getLinks(node);
				if(links==null || links.isEmpty())ret.add(node);
				else{
					IIdentity[] valuearr = (IIdentity[]) links.values().toArray();
					if(valuearr.length==0)
						ret.add(node);
					else
						ret.add(valuearr[0]);
				}
			}
			else
				ret.add(node);
		}
		return ret;
	}
	
	/**
	 * Registers this modelTree in the register as the last version of its id
	 * @return this modeltree
	 */
	public ModelTree register(){
		Register.addModelVersion(id, this);
		return this;
	}
	
	public String toString(){
		String m = "Id: " + id + "\n";
		m += "orderednodes: " + orderednodes + "\n";
		m += "b: " + b + "\n";
		m += "bindex" + bindex + "\n";
		m += "l: " + l + "\n";
		m += "lindex: " + lindex + "\n";
		return m;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((bindex == null) ? 0 : bindex.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((l == null) ? 0 : l.hashCode());
		result = prime * result + ((lindex == null) ? 0 : lindex.hashCode());
		result = prime * result + ((orderednodes == null) ? 0 : orderednodes.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelTree other = (ModelTree) obj;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b)){
			return false;
		}
		if (bindex == null) {
			if (other.bindex != null)
				return false;
		} else if (!bindex.equals(other.bindex))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (l == null) {
			if (other.l != null)
				return false;
		} else if (!l.equals(other.l))
			return false;
		if (lindex == null) {
			if (other.lindex != null)
				return false;
		} else if (!lindex.equals(other.lindex))
			return false;
		if (orderednodes == null) {
			if (other.orderednodes != null)
				return false;
		} else if (!orderednodes.equals(other.orderednodes))
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}
	
}
