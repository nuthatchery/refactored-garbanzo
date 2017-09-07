package adevpck;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


public class RelationalModel implements IModel{	
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
	public void addNode(IIdentity node) {
		N.add(node);
	}
	
	/**
	 * TODO allow duplicates? currently doesn't.
	 * 
	 * @param node
	 */
	public void addNodes(IIdentity... nodes) {
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

	/**
	 * @return all nodes in this model
	 */
	public Set<IIdentity> getAll(){
		return N;
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
			private boolean allFromNodesBelongToModel(){
				for(Triple t : relations)
					if(!N.contains(t.first()))
						return false;
				return true;
			}
		}
		ModelProperties p = new ModelProperties();
		return p.allFromNodesBelongToModel();

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
	private List<Tuple> getEdges(IIdentity from) {
		assert N.contains(from) : "From node must be a node in the graph: " + from;
		List<Tuple> edges = new ArrayList<>();
		relations.forEach(triple -> {if(triple.first().equals(from)){edges.add(new Tuple(triple.second(), triple.third()));};});
		return edges;
	}


	@Override
	public RelationalModel beginTransaction() {
		RelationalModel mutableTree = new RelationalModel(this, true);
		mutableTree.mutable = true;
		mutableTree.previousVersion = Register.addModelVersion(this);
		return mutableTree;
	}

	@Override
	public RelationalModel commitTransaction() {
		mutable = false;
		datainvariant();
		return this;
	}

	@Override
	public RelationalModel rollbackTransaction() {
		if(previousVersion == -1 )
			return this;
		assert Register.getVersion(id, previousVersion)!=null : "Previous version missing from register";

		//TODO
		return null;
	}

	@Override
	public String toString() {
		return "ModelTree\n[relations=" + relations + ",\nN=" + N + ",\nid=" + id + "]";
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

	
	// Stupid methods: 
	
	@Override
	public IElementHandle get(IIdentity element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<IIdentity> getChildren(IIdentity node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumChildren(IIdentity node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<IIdentity> getLinks(IIdentity node) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIdentity makeIdentity(String name) {
		// TODO Auto-generated method stub
		return null;
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
}
