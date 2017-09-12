package comp;

import adevpck.IIdentity;
import relationalmodel.IModel;

public interface ITreeModel extends IModel {

	/**
	 * Checks if there exists a path between start and end node
	 * @param startNode the node the path should start on 
	 * @param endNode the node the path should end on 
	 * @return true if such a path exists, false otherwise 
	 */
	public boolean hasPath(IIdentity startNode, IIdentity endNode);

	/**
	 * Checks there exists a path in the model starting at parent and ending at descendant 
	 * @param parent
	 * @param descendant
	 * @return true if such a path exists, false otherwise
	 */
	public boolean isDescendantOf(IIdentity parent, IIdentity descendant);
	
}
