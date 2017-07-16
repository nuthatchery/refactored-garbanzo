package rascalsol;


public class Test {

	public static void main(String[] args){
		IIdentity pluss = new Identity("+");
		IIdentity en = new Identity("1");
		IIdentity to = new Identity("2");
		ModelTree m = new ModelTree(pluss);
		m = m.addChild(en, new Identity("left"), pluss);
		m = m.addChild(to, new Identity("right"), pluss);
		m.getAll().forEach(elem->System.out.print(elem + " "));
	}

}
