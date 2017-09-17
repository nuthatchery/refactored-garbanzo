package tests;

import org.junit.Test;

import comp.IIdentity;
import comp.Identity;
import comp.ModelTree;

/**
 * If we have a language 
 * 
 * S -> INT + INT 
 * INT -> INT + INT 
 * INT -> num 
 *  
 * @author anna
 *
 */
public class TestModelLang {

	@Test
	public void testbinoplang(){
		/*
		 * Meta: 
		 * binOp, edge, leftArg
		 * binOp, edge, rightArg
		 */
		IIdentity larg = new Identity("leftArg");
		IIdentity rarg = new Identity("rightArg");
		ModelTree binopmeta = new ModelTree().beginTransaction();
		binopmeta.makeRoot(new Identity("binOp"))
		.addChildToRoot(new Identity("edge"), larg)
		.addChildToRoot(new Identity("edge"), rarg)
		.commitTransaction();

		/*
		 * tree: 
		 * pluss, edge, arg
		 * pluss, edge, arg
		 * 
		 * links:
		 * plus, binop
		 * edge, leftArg
		 * edge, rightArg
		 */	
		IIdentity plus = new Identity("plus");
		IIdentity arg1 = new Identity("arg");
		IIdentity arg2 = new Identity("arg");
		IIdentity edge = new Identity("edge");
		ModelTree binoplang = new ModelTree().beginTransaction();
		binoplang.makeRoot(plus)
		.addLink(plus, binopmeta.getRoot())
		.addChildToRoot(edge, arg1)
		.addLink(edge, larg)
		.addChildToRoot(edge, arg2)
		.addLink(edge, rarg)
		.commitTransaction();

		
		System.out.println(binoplang);
	}	
}
