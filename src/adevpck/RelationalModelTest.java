package adevpck;

import static org.junit.Assert.*;

import org.junit.Test;

import adevpck.models.Integers;
import adevpck.models.MetaMeta;

public class RelationalModelTest {

	@Test
	public void test() {
		IIdentity binop = new Identity("binop");
		IIdentity arg = new Identity("Arg");
		RelationalModel binopmeta = new RelationalModel(true);
		binopmeta.addNodes(binop, Integers.getRoot(), arg);
		binopmeta.addEdge(binop, Integers.succ(Integers.getRoot()), arg);
		
		IIdentity plus = new Identity("plus");
		IIdentity arg1 = new Identity("arg");
		IIdentity arg2 = new Identity("arg");
		RelationalModel binoplang = new RelationalModel(true);
		binoplang.addNodes(plus, arg1, arg2);
		binoplang.addEdge(plus, MetaMeta.CONFORMS_TO, binop);
		binoplang.addEdge(arg1, MetaMeta.CONFORMS_TO, arg);
		binoplang.addEdge(arg2, MetaMeta.CONFORMS_TO, arg);
		binoplang.addEdge(plus, Integers.getRoot(), arg);
		binoplang.addEdge(plus, Integers.succ(Integers.getRoot()), arg);
		
		System.out.println(binoplang);
	}	
}
