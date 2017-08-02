package adevpck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class ModelTree implements IModel{	
	private boolean mutable = false; 
	private int previousVersion = -1; 

	/**
	 * map: node -> {(edge, target), ..., }
	 * map.get(node) gives a (sorted?) set of tuples of edges and child  
	 */
	private HashMap<IIdentity, TreeSet<Tuple>> children = new HashMap<>();

	/**
	 * map: node -> {(link, target), ..., }
	 * map.get(node) gives a (sorted?) set of tuples of links and target
	 */
	private HashMap<IIdentity, TreeSet<Tuple>> links = new HashMap<>();

	/**
	 * unordered set of nodes in this model
	 */
	private Set<IIdentity> N = new TreeSet<>();

	private IIdentity root;

	/**
	 * id of this model
	 */
	private final IIdentity id;

	public ModelTree(IIdentity root){
		id = new Identity(this);
		this.root = root;
		addNewNodeToModel(root);
		datainvariant();
	}

	/**
	 * @param m the tree that should be copied 
	 */
	public ModelTree(ModelTree m){
		id = m.id;
		this.root = m.root;
		cloneNodesOf(m);
		cloneEdgesOf(m);
		cloneLinksOf(m);
		datainvariant();
	}

	private void addNewNodeToModel(IIdentity node) {
		assert ! N.contains(node);
		assert children.get(node) == null;
		assert links.get(node) == null;
		N.add(node);
		children.put(node, new TreeSet<>());
		links.put(node, new TreeSet<>());
	}

	private void cloneEdgesOf(ModelTree m) {
		m.children.forEach((key, list) -> children.put(key, (TreeSet<Tuple>)list.clone()));
	}

	private void cloneLinksOf(ModelTree m){
		m.links.forEach((key, list) -> links.put(key, (TreeSet<Tuple>)list.clone()));
	}

	private void cloneNodesOf(ModelTree m) {
		m.N.forEach(item -> N.add(item));
	}

	private IIdentity getRoot() {
		return root;
	}

	/**
	 * Adds an edge to the model 
	 * @param parent A node in this model
	 * @param edge The edge id
	 * @param child A node in the model; if not already existent it will be added 
	 * @return a new model with the edge added
	 */
	public ModelTree addChild(final IIdentity parent,final IIdentity edge, final IIdentity child){
		assert N.contains(parent) : "parent must be a node in this model " + N + " : " + parent;
		assert children.get(parent)!=null : "inconsistent datainvariant";
		assert links.get(parent)!=null : "inconsistent datainvariant";

		if(mutable){
			children.get(parent).add(new Tuple(edge, child));
			if(!containsNode(child))
				addNewNodeToModel(child);
			datainvariant();
			return this;
		}
		else{
			ModelTree m = new ModelTree(this);
			m.children.get(parent).add(new Tuple(edge, child));
			if(!m.containsNode(child))
				m.addNewNodeToModel(child);

			datainvariant(); //TODO not really needed
			m.datainvariant();
			return m;
		}
	}


	/**
	 * Adds a new link to the model 
	 * @param from A node or edge in this model
	 * @param link The link identity
	 * @param to The node the link points to 
	 * @return a new model with the added link
	 */
	public ModelTree addLink(IIdentity from, IIdentity link, IIdentity to){
		assert links.get(from)!=null : "the from-id must be a node or edge of this model " + from ;
		if(mutable){
			links.get(from).add(new Tuple(link, to));
			datainvariant();
			return this;
		}
		else{
			ModelTree m = new ModelTree(this);
			m.links.get(from).add(new Tuple(link, to));

			datainvariant(); //TODO not really needed
			m.datainvariant();
			return m;
		}
	}


	/**
	 * @return all nodes in this model
	 */
	public Iterable<IIdentity> getAll(){
		return N;
	}

	public IIdentity getId() {
		return this.id;
	}

	public boolean containsNode(IIdentity node) {
		return N.contains(node);
	}

	/**
	 * Deletes a node from the model and all edges pointing to it 
	 * @param node the node and endpoint of edges to be deleted 
	 * @return
	 */
	public ModelTree deleteSubtree(IIdentity node){
		assert N.contains(node) : "node must belong in this model " + N + " : " + node;
		ModelTree m = this.copy();
		TreeSet<Tuple> childrenOf = m.children.remove(node);
		m.datainvariant();
		return m;
	}

	public ModelTree copy() {
		ModelTree m = new ModelTree(this);
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
		return addLink(from, new Identity(this.id, "link"), to);
	}

	public Iterable<IIdentity> getChildren(IIdentity parent){
		assert containsNode(parent): "Identity is not a node in this model " + parent;
		ArrayList<IIdentity> childrenNodes = new ArrayList<>(); 
		children.get(parent).forEach(tuple -> childrenNodes.add(tuple.getTarget()));
		return childrenNodes;
	}

	public Iterable<IIdentity> getLinks(IIdentity from){
		assert links.containsKey(from): "links must go from a node or edge in this model" + from;
		ArrayList<IIdentity> fromlinks = new ArrayList<>();
		links.get(from).forEach(tuple -> fromlinks.add(tuple.getArrow()));
		return fromlinks;
	}

	/**
	 * Adds an unlabeled edge from node {@link parent} to identity {@link child}  
	 * @param parent
	 * @param child
	 * @return the new model
	 */
	public ModelTree addChild(IIdentity parent, IIdentity child) {
		return addChild(parent, new Identity(id, "edge"), child);
	}


	/**
	 * Registers this modelTree in the register as the last version of its id
	 * @return this modeltree
	 */
	public ModelTree register(){
		Register.addModelVersion(id, this);
		return this;
	}

	private boolean datainvariant(){
		// XXX: for boolean går det an å bruke noe slikt:
		// bindex.entrySet().stream().allMatch(predicate)

		/* could use N more, but considering not keeping N at all*/
		return 	rootIsParent() && 
				linkFunctionCoversAllParents() && 
				childrenClosedUnderN(); 

	}

	/**
	 * Root must have a (possibly empty) child set
	 * @return
	 */
	private boolean rootIsParent() {
		return children.containsKey(root);
	}

	/**
	 * All nodes must have a (possibly empty) link set
	 * @return
	 */
	private boolean linkFunctionCoversAllParents() {
		for(IIdentity key : children.keySet()){
			if(!links.containsKey(key))
				return false;
		}
		return true;
	}

	/**
	 * Children must be closed under N
	 * @return
	 */
	private boolean childrenClosedUnderN() {
		for(IIdentity key : children.keySet()){
			for(Tuple t : children.get(key)){
				if(!children.containsKey(t.getTarget()))
					return false;
			}
		}
		return true;
	}


	public boolean hasLink(IIdentity parent, IIdentity conformsTo, IIdentity node) {
		TreeSet<Tuple> parentlinks = links.get(parent);
		if(parentlinks==null || parentlinks.isEmpty())
			return false;
		return parentlinks.contains(new Tuple(conformsTo, node));
	}

	public IIdentity getLink(IIdentity constructor, IIdentity type) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * a -st-> b -st-> c 
	 * @param childType
	 * @param subtypeOf
	 * @param destType
	 * @return
	 */
	public boolean hasPath(IIdentity childType, IIdentity subtypeOf, IIdentity destType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IElementHandle get(IIdentity element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumChildren(IIdentity node) {
		assert children.containsKey(node) : "argument must be a node in this model " + node;
		return children.get(node).size();
	}

	@Override
	public boolean isDescendantOf(IIdentity parentNode, IIdentity descendantNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasData(IIdentity node, Class<?> type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T getData(IIdentity node, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void setData(IIdentity node, T data) {
		// TODO Auto-generated method stub

	}

	@Override
	public IIdentity makeIdentity() {
		return new Identity(this);
	}

	@Override
	public IIdentity makeIdentity(String name) {
		return new Identity(this.id, name);
	}

	@Override
	public IIdentity makeElement(IIdentity schema) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeElement(IIdentity schema, IIdentity parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeElement(IIdentity schema, IIdentity parent, IIdentity label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelTree beginTransaction() {
		mutable = true;
		Register.addModelVersion(this);
		return this;
	}

	@Override
	public void commitTransaction() {
		mutable = false;
	}

	@Override
	public ModelTree rollbackTransaction() {
		if(previousVersion == -1 )
			return this;
		if(!mutable){
			return Register.get(id, previousVersion);
		}
		else {
			copyAllFrom(Register.get(id, previousVersion));
			return this;
		}
	}

	private void copyAllFrom(ModelTree m) {
		children = m.children;
		links = m.links;
		root = m.root;
		N = m.N;
		previousVersion = m.previousVersion; 
		mutable = m.mutable;
	}

	@Override
	public String toString() {
		return "ModelTree\n[children=" + children + ",\nlinks=" + links + ",\nN=" + N + ",\nid=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((N == null) ? 0 : N.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((links == null) ? 0 : links.hashCode());
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
		if (N == null) {
			if (other.N != null)
				return false;
		} else if (!N.equals(other.N))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		return true;
	}

}
