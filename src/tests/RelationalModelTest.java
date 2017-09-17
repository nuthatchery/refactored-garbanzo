package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import comp.IIdentity;
import comp.Identity;
import models.Integers;
import models.MetaMeta;
import models.Operation;
import models.Ordinals;
import relationalmodel.MutableModel;
import relationalmodel.RelationalModel;

public class RelationalModelTest {

	@Test
	public void test() {
		
		/* META */
		IIdentity plus = new Identity("plus");
		IIdentity arg1 = new Identity("arg");
		IIdentity arg2 = new Identity("arg");
		RelationalModel binoplang = new RelationalModel(true);
		binoplang.addNodes(plus, arg1, arg2);
		
		binoplang.addEdge(plus, MetaMeta.CONFORMS_TO, Operation.OPERATOR);
		
		binoplang.addEdge(arg1, MetaMeta.CONFORMS_TO, Operation.OPERAND);
		binoplang.addEdge(arg2, MetaMeta.CONFORMS_TO, Operation.OPERAND);
		Operation.setOperandOrder(binoplang, arg1, arg2);
		
		System.out.println(binoplang);
	}	
}
