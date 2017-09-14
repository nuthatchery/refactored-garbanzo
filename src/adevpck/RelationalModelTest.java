package adevpck;

import static org.junit.Assert.*;

import org.junit.Test;

import adevpck.models.Integers;
import adevpck.models.MetaMeta;
import adevpck.models.Operation;
import adevpck.models.Ordinals;
import comp.IIdentity;
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
