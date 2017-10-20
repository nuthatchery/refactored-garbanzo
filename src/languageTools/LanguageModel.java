package languageTools;

import java.util.ArrayList;
import java.util.List;

import comp.IIdentity;
import comp.ITransactableModel;
import datastructures.Triple;
import datastructures.Tuple;
import relationalmodel.IModel;
import relationalmodel.RelationalModel;

public interface LanguageModel extends IModel, ITransactableModel<LanguageModel>{

	default void setProperty(IIdentity from, IIdentity label, IIdentity to){
		addEdge(from, label, to);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	default IIdentity getAnyNodeLinkingToNode(IIdentity id){
		for(Triple t : getEdges()){
			if(t.to().equals(id)){
				return t.from();
			}
		}
		return null;
	}
	default IIdentity getAnyNodeLinkingToNodeByLabel(IIdentity label, IIdentity node){
		for(Triple t : getEdges()){
			if(t.to().equals(node) && t.label().equals(label)){
				return t.from();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	default List<IIdentity> getNodesLinkingToNode(IIdentity id){
		List<IIdentity> list = new ArrayList<>();
		for(Triple t : getEdges()){
			if(t.to().equals(id)){
				list.add(t.from());
			}
		}
		return list;
	}

	default List<IIdentity> getNodesLinkingToNodeByLabel(IIdentity id, IIdentity label){
		List<IIdentity> list = new ArrayList<>();
		for(Triple t : getEdges()){
			if(t.to().equals(id) && t.label().equals(label)){
				list.add(t.from());
			}
		}
		return list;
	}

	default IIdentity getAnyNodeWithProperty(IIdentity label, IIdentity node){
		return getAnyNodeLinkingToNodeByLabel(label, node);
	}

	/**
	 * Templatemodel will contain a possibly empty graph, and
	 * for each node in templateModel, one node will be added to this graph, such that
	 * this graph's added node will link to the same elements that the nodes in templateModel links to, 
	 * and have edges to nodes in this graph that corresponds to childnodes in templateModel, labeled as 
	 * edges are there. 
	 * 
	 * TODO add understandable example
	 * TODO even useful? 
	 * 
	 * @param templateModel
	 */
	default void addNodesFromConstructor(RelationalModel templateModel){
		List<Triple> edgesToBeAdded = new ArrayList<>();
		for(IIdentity node : templateModel.getNodes()){
			IIdentity correspondingNode = addNode(node.getName());
			for(Tuple t : templateModel.getEdges(node)){
				if(templateModel.containsNode(t.getTarget())){
					if(hasNode(t.getTarget())){
						addPropertyToNode(correspondingNode, t);
					}
					else{
						edgesToBeAdded.add(new Triple(correspondingNode, t));
					}
				}
				else{
					addPropertyToNode(correspondingNode, t);
				}
			}
		}
	}

	default void addPropertyToNode(IIdentity node, Tuple propertyEdge){
		addEdge(node, propertyEdge.getArrow(), propertyEdge.getTarget());
	}
}
