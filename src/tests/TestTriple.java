package tests;

import static org.junit.Assert.*;

import datastructures.Triple;

/**
 * Expresses the properties of {@link datastructures.Triple}
 * @author anna
 *
 */
public class TestTriple {

	public boolean firstIsFrom(Triple t){
		return t.first().equals(t.from());
	}
	
	public boolean secondIsLabel(Triple t){
		return t.second().equals(t.label());
	}
	
	public boolean secondIsMiddle(Triple t){
		return t.second().equals(t.middle());
	}
	
	public boolean secondIsArrow(Triple t){
		return t.second().equals(t.arrow());
	}
	
	public boolean thirdIsTo(Triple t){
		return t.third().equals(t.to());
	}
	
	public boolean thirdIsTail(Triple t){
		return t.third().equals(t.tail());
	}
}
