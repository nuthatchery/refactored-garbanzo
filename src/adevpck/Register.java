package adevpck;

import java.util.HashMap;

public class Register {

	/**
	 * ModelId -> (Versionnr -> Model)
	 */
	private static HashMap<IIdentity, VersionMap> reg = new HashMap<>();

	public static void addModelVersion(ModelTree modelTree){
		addModelVersion(modelTree.getIdObject(), modelTree);
		datainvariant();
	}

	public static void addModelVersion(IIdentity modelid, ModelTree modelTree) {
		if(reg.containsKey(modelid) && reg.get(modelid)!=null){
			reg.get(modelid).put(modelTree);
		}
		else{
			reg.put(modelid, new VersionMap(0, modelTree));
		}
		datainvariant();
	}

	/**
	 * Gets the last version of the given model
	 * @param modelId
	 * @return the last version of the model, null if no such is found
	 */
	public static ModelTree get(IIdentity modelId){
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
	 * @param node
	 * @return
	 */
	public static ModelTree getModelOf(IIdentity node){
		for(VersionMap v : reg.values()){
			for(int i=v.getLastVersionNr(); i>=0; i--){
				if(v.get(i)!=null){
					if(v.get(i).containsNode(node)){
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
	public static ModelTree removeLastVersion(IIdentity modelId){
		ModelTree m = reg.get(modelId).removeLast();
		datainvariant();
		return m;
	}
	
	
	public static void datainvariant(){
		allVersionHasSameIdAsKey();
	}

	private static void allVersionHasSameIdAsKey() {
		reg.forEach(
				(key, value) -> {value.forEach((versionnr, model) -> {assert key.equals(model) : "keys should be equal: " + key.getId() + " = " + model.getId();});}
				);
	}
	
	

}
