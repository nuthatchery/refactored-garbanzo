package tests;

import comp.IIdentity;
import comp.Identity;
import comp.ModelTree;

public class TestData {

	public static ModelTree getOnePlusTwoTree(){
		IIdentity pluss = new Identity("+");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");

		ModelTree m = new ModelTree(pluss);
		m = m.addChild(pluss, new Identity("left"), en);
		m = m.addChild(pluss, new Identity("right"), to);
		
		return m;
	}
}
