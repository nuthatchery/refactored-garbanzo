package adevpck;

public class Tuple {

	private final IIdentity edge;
	private final IIdentity target;
	
	public Tuple(IIdentity edge, IIdentity target){
		this.edge = edge;
		this.target = target;
	}
	
	public IIdentity getEdge(){
		return edge;
	}
	
	public IIdentity getTarget(){
		return target;
	}
	
}
