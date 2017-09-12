package relationalmodel;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import adevpck.IIdentity;
import adevpck.Identity;
import adevpck.Register;
import adevpck.datastructures.Triple;
import adevpck.datastructures.Tuple;


public class RelationalModel implements ITransactableModel{	
	private boolean mutable = false; 
	private int previousVersion = -1; 

	private List<Triple> relations = new ArrayList<Triple>();
	private Set<IIdentity> N = new TreeSet<IIdentity>();

	/**
	 * id of this model
	 */
	private final IIdentity id;

	public RelationalModel(){
		id = new Identity(this);
		datainvariant();
	}

	/**
	 * @param m the tree that should be copied 
	 */
	public RelationalModel(RelationalModel m){
		id = m.id;
		mutable = m.mutable;
		cloneNodesOf(m);
		cloneRelationsOf(m);
		datainvariant();
	}

	public RelationalModel(RelationalModel m, boolean mutable) {
		this(m);
		this.mutable = mutable;
	}

	public RelationalModel(boolean mutable) {
		this();
		this.mutable = mutable;
	}

	private void cloneRelationsOf(RelationalModel m) {
		relations.clear();
		m.relations.forEach(triple -> relations.add(triple));
	}

	/**
	 * TODO allow duplicates? currently doesn't.
	 * 
	 * @param node
	 */
	public void addNodes(IIdentity... nodes) {
		assert mutable : "Can only add nodes to mutable model";
		for(IIdentity node : nodes){
			N.add(node);
		}
	}

	private void cloneNodesOf(RelationalModel m) {
		m.N.forEach(item -> N.add(item));
	}

	/**
	 * Adds an edge to the model 
	 * @param parent A node in this model
	 * @param edge The edge id
	 * @param child A node in the model; if not already existent it will be added 
	 * @return a new model with the edge added
	 */
	public RelationalModel addEdge(final IIdentity from, final IIdentity edge, final IIdentity to){
		assert N.contains(from) : "from node must be a node in this model " + N + " : " + from;

		if(mutable){
			relations.add(new Triple(from, edge, to));
			assert datainvariant();
			return this;
		}
		else{
			RelationalModel m = new RelationalModel(this);
			m.relations.add(new Triple(from, edge, to));
			assert m.datainvariant();
			return m;
		}
	}

	public IIdentity getId() {
		return this.id;
	}

	public boolean containsNode(IIdentity node) {
		return N.contains(node);
	}

	public RelationalModel copy() {
		RelationalModel m = new RelationalModel(this);
		return m;
	}


	/**
	 * Registers this modelTree in the register as the last version of its id
	 * @return this modeltree
	 */
	public RelationalModel register(){
		Register.addModelVersion(id, this);
		return this;
	}

	private boolean datainvariant(){
		// XXX: for boolean går det an å bruke noe slikt:
		// bindex.entrySet().stream().allMatch(predicate)

		class ModelProperties{
			private boolean noNullEdges(){
				for(Triple t : relations){
					if(t.first() == null
							|| t.second() == null
							|| t.third() == null)
						return false;
				}
				return true;
			}
			
			private boolean allFromNodesBelongToModel(){
				for(Triple t : relations)
					if(!N.contains(t.first()))
						return false;
				return true;
			}
		}
		ModelProperties p = new ModelProperties();
		return p.noNullEdges()
				&& p.allFromNodesBelongToModel();

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
		for(Tuple child : getEdges(from)){
			if(child.getArrow().equals(label)){
				if(child.getTarget().equals(to))
					return true;
				else if(hasPath(child.getTarget(), label, to))
					return true;
			}
		}
		return false;
	}

	/**
	 * Gets a list of edges out from the parameter node, in the form of tuples <edgeID, targetID>
	 * @param from
	 * @return
	 */
	public List<Tuple> getEdges(IIdentity from) {
		assert N.contains(from) : "From node must be a node in the graph: " + from;
		List<Tuple> edges = new ArrayList<>();
		relations.forEach(triple -> {if(triple.first().equals(from)){edges.add(new Tuple(triple.second(), triple.third()));};});
		return edges;
	}
	
	@Override
	public String toString() {
		return "RelationalModel\n[relations=" + relations + ",\nN=" + N + ",\nid=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((N == null) ? 0 : N.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((relations == null) ? 0 : relations.hashCode());
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
		RelationalModel other = (RelationalModel) obj;
		if (N == null) {
			if (other.N != null)
				return false;
		} else if (!N.equals(other.N))
			return false;
		if (relations == null) {
			if (other.relations!= null)
				return false;
		} else if (!relations.equals(other.relations))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public IIdentity newNode() {
		IIdentity node = new Identity(this);
		N.add(node);
		return node;
	}

	@Override
	public IIdentity addNode() {
		IIdentity newnode = new Identity(this);
		N.add(newnode);
		assert datainvariant();
		return newnode;
	}

	@Override
	public IIdentity addNode(String name) {
		assert mutable : "Can only add node to mutable model";
		IIdentity newnode = new Identity(this, name);
		N.add(newnode);
		assert datainvariant();
		return newnode;
	}

	@Override
	public List<IIdentity> getNodes() {
		return new ArrayList<>(N);
	}

	@Override
	public boolean hasNode(IIdentity node) {
		return N.contains(node);
	}

	@Override
	public IModel removeNode(IIdentity node) {
		assert containsNode(node) : "Node is not present in model " + node;
		if(mutable){
			N.remove(node);
			removeEdgesFrom(node);
			datainvariant();
			return this;
		}
		else{
			RelationalModel m = new RelationalModel(this, true);
			m.removeNode(node);
			m.mutable = false;
			return m;
		}
	}

	/**
	 * Removes all edges of this model starting at the argument node
	 * NB: will change this even if immutable 
	 * @param node a node in this model 
	 */
	private void removeEdgesFrom(IIdentity node) {
		assert containsNode(node) : "Node is not present in model " + node;
		relations.removeIf(triple -> triple.first().equals(node));
	}

	@Override
	public List<Triple> getEdges() {
		return new ArrayList<>(relations);
	}

	@Override
	public IModel removeEdge(IIdentity from, IIdentity label, IIdentity to) {
		assert containsNode(from) : "from node is not present in model " + from;
		if(mutable){
			relations.remove(new Triple(from, label, to));
			assert datainvariant();
			return this;
		}
		else{
			RelationalModel m = new RelationalModel(this, true);
			m.removeEdge(from, label, to);
			m.mutable = true;
			return m;
		}
	}

	public RelationalModel beginTransaction() {
		RelationalModel mutableTree = new MutableModel(this);
		mutableTree.mutable = true;
		mutableTree.previousVersion = Register.addModelVersion(this);
		return mutableTree;
	}

	@Override
	public IModel commitTransaction() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IModel rollbackTransaction() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getNumChildren(IIdentity node) {
		assert containsNode(node) : "from node is not present in model " + node;
		return getEdges(node).size();
	}
}
