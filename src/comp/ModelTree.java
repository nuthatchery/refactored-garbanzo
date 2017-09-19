package comp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import datastructures.Triple;
import datastructures.Tuple;
import relationalmodel.IModel;


public class ModelTree implements ITransactableTreeModel{	
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

	public ModelTree(){
		id = new Identity(this);
		datainvariant();
	}

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

	/**
	 * 
	 * @param modelTree
	 * @param mutable
	 */
	public ModelTree(ModelTree modelTree, boolean mutable) {
		this(modelTree);
		this.mutable=mutable;
		datainvariant();
	}

	public ModelTree(boolean mutable) {
		this();
		this.mutable = mutable;
	}

	private void addNewNodeToModel(IIdentity node) {
		if(!N.contains(node) 
				&& children.get(node) == null 
				&& links.get(node) == null){
			N.add(node);
			children.put(node, new TreeSet<>());
			links.put(node, new TreeSet<>());
		}
	}

	private void addNewEdgeToModel(IIdentity edge) {
		if(!links.containsKey(edge))
			links.put(edge, new TreeSet<Tuple>());
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

	public IIdentity getRoot() {
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
			addNewEdgeToModel(edge);
			if(!containsNode(child))
				addNewNodeToModel(child);
			assert datainvariant();
			return this;
		}
		else{
			ModelTree m = new ModelTree(this);
			m.children.get(parent).add(new Tuple(edge, child));
			m.addNewEdgeToModel(edge);
			if(!m.containsNode(child))
				m.addNewNodeToModel(child);

			assert datainvariant(); //TODO not really needed
			assert m.datainvariant();
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

			assert m.datainvariant();
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
	 * Deletes a node from the model, and all edges pointing to and from it, and its descendants 
	 * @param node the node and endpoint of edges to be deleted 
	 * @return
	 */
	public ModelTree deleteSubtree(IIdentity node){
		assert N.contains(node) : "node must belong in this model " + N + " : " + node;
		if(!mutable){
			ModelTree m = new ModelTree(this);
			m.mutable = true;
			m.deleteSubtree(node);
			m.mutable = false;
			m.datainvariant();
			return m;
		}
		else{
			//delete all edges to current node 
			for(IIdentity n : children.keySet()){
				for(Iterator<Tuple> iter = children.get(n).iterator(); iter.hasNext();){
					Tuple t = iter.next();
					if(t.getTarget().equals(node))
						iter.remove();
				}
			}

			// for all children c of n, delete subtree starting on c, then delete edge n->c  
			for(Iterator<Tuple> iter = children.get(node).iterator(); iter.hasNext();){
				Tuple edge = iter.next();
				iter.remove();
				deleteSubtree(edge.getTarget());
			}

			links.remove(node);
			children.remove(node);
			N.remove(node);
			datainvariant();
			return this;
		}
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
		return addLink(from, new Identity(this, "link"), to);
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
		return addChild(parent, new Identity(this, "edge"), child);
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

		class ModelProperties{
			/**
			 * Root must have a (possibly empty) child set
			 * @return
			 */
			private boolean rootIsParent() {
				return children.containsKey(root);
			}

			private boolean rootIsNode(){
				return N.contains(root);
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
				for(IIdentity key : children.keySet()){ //per node
					for(Tuple t : children.get(key)){	//per edge 
						if(!children.containsKey(t.getTarget())) //target of edge should be in children
							return false;
					}
				}
				return true;
			}

			private boolean allNsInChildren(){
				for(IIdentity n : N){
					if(!children.containsKey(n))
						return false;
				}
				return true;
			}

			public boolean isEmpty() {
				return root == null && N.isEmpty() && children.isEmpty() && links.isEmpty();
			}
		}
		ModelProperties p = new ModelProperties();
		if(root == null )
			return p.isEmpty();
		else 
			return 	p.rootIsNode() && 
					p.rootIsParent() && 
					p.linkFunctionCoversAllParents() && 
					p.childrenClosedUnderN() &&
					p.allNsInChildren(); 

	}

	public boolean hasLink(IIdentity fromNode, IIdentity label, IIdentity toNode) {
		TreeSet<Tuple> parentlinks = links.get(fromNode);
		if(parentlinks==null || parentlinks.isEmpty())
			return false;
		return parentlinks.contains(new Tuple(label, toNode));
	}

	public IIdentity getLink(IIdentity constructor, IIdentity type) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Searches the model for path of arbitrary length such that for nodes n0=from, .., ni, .., nk=to in N we have a path  
	 * from -label-> ... -label-> ni ... -label-> to 
	 * 
	 * There must exist a node from and a node to and a path inbetween such that each edge in the path is equal to label
	 *  
	 * @param from a node in this model
	 * @param label  
	 * @param to a node in this model
	 * @return true if such a path exists, false otherwise 
	 */
	public boolean hasPath(IIdentity from, IIdentity label, IIdentity to) {
		assert containsNode(from) : "From parameter must be a node in this model" + from ;
		assert containsNode(to) : "To parameter must be a node in this model" + to ;
		for(Tuple child : children.get(from)){
			if(child.getArrow().equals(label)){
				if(child.getTarget().equals(to))
					return true;
				else if(hasPath(child.getTarget(), label, to))
					return true;
			}
		}
		return false;
	}

	@Override
	public int getNumChildren(IIdentity node) {
		assert children.containsKey(node) : "argument must be a node in this model " + node;
		return children.get(node).size();
	}

	@Override
	public boolean isDescendantOf(IIdentity parentNode, IIdentity descendantNode) {
		if(!children.containsKey(parentNode) || !children.containsKey(descendantNode))
			return false;
		if(parentNode.equals(descendantNode))
			return false;

		for(Tuple edge: children.get(parentNode)){
			if(descendantNode.equals(edge.getTarget()))
				return true;
			if(isDescendantOf(edge.getTarget(), descendantNode))
				return true;
		}
		return false;
	}

//	@Override
//	public IIdentity makeIdentity() {
//		return new Identity(this);
//	}
//
//	@Override
//	public IIdentity makeIdentity(String name) {
//		return new Identity(this.id, name);
//	}

	@Override
	public ModelTree beginTransaction() {
		ModelTree mutableTree = new ModelTree(this, true);
		mutableTree.mutable = true;
		mutableTree.previousVersion = Register.addModelVersion(this);
		return mutableTree;
	}

	@Override
	public ModelTree commitTransaction() {
		mutable = false;
		datainvariant();
		return this;
	}

	@Override
	public ITransactableModel rollbackTransaction() {
		if(previousVersion == -1 )
			return this;
		assert Register.getVersion(id, previousVersion)!=null : "Previous version missing from register";

		if(!mutable){
			return Register.getVersion(id, previousVersion);
		}
		else {
			copyAllFrom(Register.getVersion(id, previousVersion));
			return this;
		}
	}

	/**
	 * Copies all attributes from m to this 
	 * @param m the model to copy from, another version of this 
	 */
	private void copyAllFrom(IModel model) {
		assert model instanceof ModelTree : "Cannot copy from different type than " + this.getClass() + " : " + model.getClass();
		ModelTree m = (ModelTree) model;
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

	public ModelTree makeRoot(IIdentity root) {
		assert this.root==null;
		assert N.isEmpty();
		assert children.isEmpty();
		assert links.isEmpty();
		if(mutable){
			this.root = root;
			addNewNodeToModel(root);
			assert datainvariant();
			return this;
		}
		else 
			return new ModelTree(root);
	}

	/**
	 * Adds child to root without needing root ref 
	 * Used in particular for oneliners or "anonymous" models 
	 * @param edge edge id 
	 * @param arg child id
	 * @return
	 */
	public ModelTree addChildToRoot(IIdentity edge, IIdentity arg) {
		return addChild(root, edge, arg);
	}

	@Override
	public IIdentity newNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity addNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity addNode(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel addEdge(IIdentity from, IIdentity label, IIdentity to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IIdentity> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNode(IIdentity node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IModel removeNode(IIdentity node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Triple> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tuple> getEdges(IIdentity from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel removeEdge(IIdentity from, IIdentity label, IIdentity to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPath(IIdentity startNode, IIdentity endNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IIdentity> getNodesPointingTo(IIdentity operator) {
		// TODO Auto-generated method stub
		return null;
	}

}
