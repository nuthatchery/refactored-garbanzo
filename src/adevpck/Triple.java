package adevpck;

public class Triple {
	private IIdentity first, second, third;
	
	public Triple(){
		this.first = null;
		this.second = null;
		this.third = null;
	}
	
	public Triple(IIdentity first, IIdentity second, IIdentity third){
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public IIdentity first(){
		return first;
	}
	
	public IIdentity second(){
		return second;
	}
	
	public IIdentity third(){
		return third;
	}
}
