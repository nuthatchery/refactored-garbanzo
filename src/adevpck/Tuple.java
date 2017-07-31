package adevpck;

/**
 * Immutable tuple class 
 * @author anna
 *
 */
public class Tuple implements Comparable<Tuple>{

	private final IIdentity arrow;
	private final IIdentity target;
	
	public Tuple(IIdentity arrow, IIdentity target){
		this.arrow = arrow;
		this.target = target;
	}
	
	public IIdentity getArrow(){
		return arrow;
	}
	
	public IIdentity getTarget(){
		return target;
	}
	
	/**
	 * Does not change {@link this}
	 * @param target the new target 
	 * @return a new Tuple with arrow equal to this, and the given target
	 */
	public Tuple setTarget(IIdentity target){
		return new Tuple(arrow, target);
	}
	
	/**
	 * Does not change {@link this}
	 * @param arrow the new arrow 
	 * @return a new Tuple with target equal to this, and the given arrow
	 */
	public Tuple setArrow(IIdentity arrow){
		return new Tuple(arrow, target);
	}
	
	@Override
	public boolean equals(Object o){
		if(o==null)return false;
		if(o==this)return true;
		if(! (o instanceof Tuple)) return false;
		Tuple t = (Tuple) o;
		return t.arrow.equals(arrow) && t.target.equals(target);
	}
	
	@Override
	public String toString(){
		return "(" + arrow + ", " + target + ")";
	}

	@Override
	public int compareTo(Tuple o) {
		return arrow.equals(o.arrow)?target.compareTo(o.target) : arrow.compareTo(o.arrow);
	}
}
