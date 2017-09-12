package relationalmodel;

import adevpck.IIdentity;

public interface ITreeModel extends IModel {

	/**
	 * Checks if there exists a path between start and end node
	 * @param startNode the node the path should start on 
	 * @param endNode the node the path should end on 
	 * @return true if such a path exists, false otherwise 
	 */
	public boolean hasPath(IIdentity startNode, IIdentity endNode);
	
}
