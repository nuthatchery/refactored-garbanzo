package adevpck;

import static org.junit.Assert.*;

import org.junit.Test;

import adevpck.models.Integers;
import adevpck.models.MetaMeta;
import relationalmodel.MutableModel;
import relationalmodel.RelationalModel;

public class RelationalModelTest {

	@Test
	public void test() {
		
		/* METAMETA */ 
		RelationalModel operatormeta = new MutableModel();
		IIdentity operator = operatormeta.newNode();
		IIdentity arity = operatormeta.newNode();
		IIdentity argument = operatormeta.newNode();
		operatormeta.addEdge(operator, MetaMeta.REQUIRE_ONE, arity);

		
		/* META */
		IIdentity plus = new Identity("plus");
		IIdentity arg1 = new Identity("arg");
		IIdentity arg2 = new Identity("arg");
		RelationalModel binoplang = new RelationalModel(true);
		binoplang.addNodes(plus, arg1, arg2);
		
		binoplang.addEdge(plus, MetaMeta.CONFORMS_TO, operator);
		
		
		binoplang.addEdge(arg1, MetaMeta.CONFORMS_TO, arg);
		binoplang.addEdge(arg2, MetaMeta.CONFORMS_TO, arg);
		binoplang.addEdge(plus, Integers.getRoot(), arg);
		binoplang.addEdge(plus, Integers.succ(Integers.getRoot()), arg);
		
		System.out.println(binoplang);
	}	
}
