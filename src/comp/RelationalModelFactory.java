package comp;

import java.io.File;

import relationalmodel.RelationalModel;

public class RelationalModelFactory {

	
	public void read(File file){
		
	}
	
	public static RelationalModel getRelationalMetaGraph(){
		IIdentity metanode = new Identity("NODE");
		IIdentity metaedge = new Identity("EDGE");
		RelationalModel metagraph = new RelationalModel();
		metagraph.addNodes(metanode, metaedge);
		metagraph.addEdge(metanode, metaedge, metaedge);
		return metagraph;
	}
}
