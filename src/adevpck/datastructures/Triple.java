package adevpck.datastructures;

import adevpck.IIdentity;
import adevpck.Identity;

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

	public IIdentity middle(){
		return second;
	}

	public IIdentity arrow(){
		return second;
	}

	public IIdentity third(){
		return third;
	}

	public IIdentity last(){
		return third;
	}

	@Override
	public String toString(){
		return "("+first + ", " + second + ", " + third +")"; 
	}

	public Tuple tail() {
		return new Tuple(second, third);
	}

	@Override 
	public boolean equals(Object o){
		if(o == null) return false;
		if(this == o) return true;
		if(o instanceof Triple){
			Triple other = (Triple) o;
			return Identity.bothNullOrEquals(other.first, first)
					&& Identity.bothNullOrEquals(other.second, second)
					&& Identity.bothNullOrEquals(other.third, third);
		}
		return false;
	}
}
