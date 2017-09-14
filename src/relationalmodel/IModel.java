package relationalmodel;

import java.util.List;

import adevpck.IIdentity;
import adevpck.datastructures.Tuple;
import adevpck.datastructures.Triple;

public interface IModel{
	/**
	 * Adds a node to the model
	 * same as addNode() 
	 * @return the ID of the added node 
	 */
	IIdentity newNode();
	
	/**
	 * Adds a node to the model 
	 * @return the ID of the added node 
	 */
	IIdentity addNode();
	
	/**
	 * Adds a node to the model 
	 * NB cannot be supported by immutable classes
	 * @param name the name attached to the node 
	 * @return the ID of the added node 
	 */
	IIdentity addNode(String name);
	
	/**
	 * Adds an edge to the model 
	 * @param from an ID in this model 
	 * @param label the edge label ID 
	 * @param to the to-node ID 
	 * @return this model  
	 */
	IModel addEdge(IIdentity from, IIdentity label, IIdentity to);
	
	/**
	 * Fetches all nodes in the model
	 * @return all nodes in the model, in the form of 
	 * [node_1, ..., node_n], 
	 * or empty list if none.  
	 */
	List<IIdentity> getNodes();
	
	/**
	 * Checks if this model contains the argument node (check by ==)
	 * @param node the node to check for 
	 * @return true if this model has a node that is the argument node, false otherwise
	 */
	boolean hasNode(IIdentity node);
	
	/**
	 * Removes a node and all edges from that node from the model 
	 * @param node a node in the model 
	 * @return this model
	 */
	IModel removeNode(IIdentity node);
	
	/**
	 * Fetches all edges in this model 
	 * @return a list of edges in this model, in the form of 
	 * [(from_1, label_1, to_1), .., (from_n, label_n, to_n)] 
	 * or empty list if none 
	 */
	List<Triple> getEdges();
	
	/**
	 * Fetches the all outgoing edges from a node 
	 * @param from A node in the model
	 * @return a list of tuples in the form of [(label_1, to_1), .., (label_n, to_n)]
	 */
	List<Tuple> getEdges(IIdentity from);
	
	/**
	 * Removes an edge from the model
	 * @param from the from-node of the edge
	 * @param label label of the edge 
	 * @param to the to-node of the edge
	 * @return this model 
	 */
	IModel removeEdge(IIdentity from, IIdentity label, IIdentity to);
	
	/**
	 * Fetches the ID of this model 
	 * @return this model's id
	 */
	IIdentity getId();

	/**
	 * Fetches the number of children of the argument node 
	 * @param node a node in this model
	 * @return the number of children, or 0 if none
	 */
	public int getNumChildren(IIdentity node);
}
