package comp;

import java.util.HashMap;

import datastructures.VersionMap;

public class Register {

	/**
	 * ModelId -> (Versionnr -> Model)
	 */
	private static HashMap<IIdentity, VersionMap> reg = new HashMap<>();

	public static int addModelVersion(ITransactableModel modelTree){
		int version = addModelVersion(modelTree.getId(), modelTree);
		datainvariant();
		return version;
	}

	public static int addModelVersion(IIdentity modelid, ITransactableModel modelTree) {
		int version;
		if(reg.containsKey(modelid) && reg.get(modelid)!=null){
			version = reg.get(modelid).put(modelTree);
		}
		else{
			version = 0;
			reg.put(modelid, new VersionMap(version, modelTree));
		}
		datainvariant();
		return version;
	}
	
	/**
	 * Searches the register for a version of the given model id that is equal to the given version
	 * @param m
	 * @return the non-negative versionnr if the model is found, -1 otherwise 
	 */
	public static int getVersionNrOf(IIdentity modelId, ITransactableModel m){
		if(!reg.containsKey(modelId))
			return -1;
		
		VersionMap versionMap = reg.get(modelId);
		for(Integer i : versionMap.keySet()){
			if(versionMap.get(i).equals(m))
				return i;
		}
		
		return -1;
	}

	/**
	 * Gets the last version of the given model
	 * @param modelId
	 * @return the last version of the model, null if no such is found
	 */
	public static ITransactableModel get(IIdentity modelId){
		if(reg.containsKey(modelId)){
			return reg.get(modelId).getLast();
		}
		else {
			return null;
		}
	}

	/**
	 * Gets the only model in which this id is node, if any.
	 * If there are several versions of that model, gets the last version containing the node 
	 * 
	 * TODO added modelid to nodes, don't need traversal any more 
	 * @param node
	 * @return
	 */
	public static ITransactableModel getModelOf(IIdentity node){
		for(VersionMap v : reg.values()){
			for(int i=v.getLastVersionNr(); i>=0; i--){
				if(v.get(i)!=null){
					if(v.get(i).hasNode(node)){
						assert node.getModelId().equals(v.get(i));
						return v.get(i);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Removes and returns all version of the specified model
	 * @param modelId
	 * @return
	 */
	public static VersionMap removeAllVersionsOf(IIdentity modelId){
		VersionMap removed = reg.remove(modelId);
		datainvariant();
		return removed;
		
	}
	
	/**
	 * Removes and returns last version of the specified model
	 * @param modelId
	 * @return
	 */
	public static ITransactableModel removeLastVersion(IIdentity modelId){
		ITransactableModel m = reg.get(modelId).removeLast();
		datainvariant();
		return m;
	}
	
	
	public static void datainvariant(){
		allVersionHasSameIdAsKey();
	}

	private static void allVersionHasSameIdAsKey() {
		reg.forEach(
				(key, value) -> {value.forEach((versionnr, model) -> {assert key.equals(model.getId()) : "keys should be equal: " + key.getId() + " = " + model.getId();});}
				);
	}

	/**
	 * 
	 * @param id
	 * @param version
	 * @return
	 */
	public static ITransactableModel getVersion(IIdentity id, int version) {
		if(reg.containsKey(id) && reg.get(id)!=null)
			return reg.get(id).get(version);
		else 
			return null;
	}


}
