package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import Exceptions.NodeNotFoundException;
import comp.IIdentity;
import comp.Identity;
import datastructures.Triple;
import datastructures.Tuple;
import models.MetaMeta;
import models.Operation;
import relationalmodel.RelationalModel;

public class RelationalModelTest {

	private static final int N = 100;

	private RelationalModel getRandomModel() {
		RelationalModel model = new RelationalModel().beginTransaction();
		Random r = new Random();
		if(r.nextInt(10)==1) return model; //"guarantee" empty model 
		int num = r.nextInt(N)+1;
		List<IIdentity> nodes = new ArrayList<>();
		for(int i = 0; i<num; i++){
			nodes.add(model.addNode());
		}
		int lim = num*r.nextInt(r.nextInt(9)+1);
		for(int i = 0; i<lim; i++){
			try{
				model.addEdge(nodes.get(r.nextInt(nodes.size())), nodes.get(r.nextInt(nodes.size())), nodes.get(r.nextInt(nodes.size())));
			}catch(NodeNotFoundException e){
				fail("something went wrong while adding edges");
			}
		}
		model = model.commitTransaction();
		return model;
	}
	
	
	@Test
	public void testGetNodesEmptyModel(){
		RelationalModel model = new RelationalModel();
		assertTrue("Empty models should have non-null node-list: ", model.getNodes() != null);
		assertTrue("Empty models should have empty node-list:", model.getNodes().isEmpty());
	}

	@Test
	public void testGetEdgesEmptyModel(){
		RelationalModel model = new RelationalModel();
		assertTrue("Empty models should have non-null edge-list: ", model.getEdges() != null);
		assertTrue("Empty models should have empty edge-list:", model.getEdges().isEmpty());
	}
	
	@Test
	public void testContainsNodeEmptyModel(){
		RelationalModel model = new RelationalModel();
		assertFalse("Empty models should not contain any nodes: ", model.containsNode(new Identity(model)));
	}
	
	@Test 
	public void testHasPathEmptyModel(){
		RelationalModel model = new RelationalModel();
		assertFalse("Empty models has no paths: ", model.hasPath(new Identity(model), new Identity(model), new Identity(model)));
	}
	
	@Test 
	public void testMutableModel(){
		RelationalModel model = new RelationalModel();
		try{
			model.addNode();
			fail("Model should be immutable");
		}catch(IllegalStateException e){
		}
		model = model.beginTransaction();
		model.addNode();
		model = model.commitTransaction();
	}
	
	@Test 
	public void testCopyEquals(){
		RelationalModel model;
		for(int i=0; i<N; i++){
			model = getRandomModel();
			assertEquals("Copied models should be equals: ", model, model.copy());
		}
	}
	
	@Test
	public void testAddNodeContainsNode(){
		RelationalModel model;
		for(int i=0; i<N; i++){
			model = getRandomModel();
			IIdentity id = model.addNode();
			assertTrue(model.containsNode(id));
			assertTrue(model.getNodes().contains(id));
			assertFalse(model.containsNode(new Identity(model)));
			assertFalse(model.getNodes().contains(new Identity(model)));
		}
	}
	
	@Test 
	public void testAddEdgeContainsEdge(){
		RelationalModel model;
		for(int i=0; i<N; i++){
			model = getRandomModel();
			model = model.beginTransaction();
			IIdentity id1 = model.addNode();
			IIdentity id2 = model.addNode();
			IIdentity id3 = model.addNode();
			model = model.addEdge(id1, id2, id3);
			model = (RelationalModel) model.commitTransaction();
			assertTrue(model.hasPath(id1, id2, id3));
			assertFalse(model.hasPath(id2, id2, id2));
			
			assertTrue(model.getEdges().contains(new Triple(id1, id2, id3)));
			assertFalse(model.getEdges().contains(new Triple(id2, id2, id2)));
			
			assertTrue(model.getEdges(id1).contains(new Tuple(id2, id3)));
			assertTrue(model.getEdges(id1).contains(new Tuple(id2, id2)));
		}
	}

	@Test
	public void test() {
		
		/* META */
		RelationalModel binoplang = new RelationalModel().beginTransaction();
		IIdentity arg2 = binoplang.addNode("arg2");
		IIdentity arg1 = binoplang.addNode("arg1");
		IIdentity plus = binoplang.addNode("plus");
		assert binoplang.containsNode(plus);
		assert binoplang.containsNode(arg1);
		assert binoplang.containsNode(arg2);
		
		//edges out of graph
		binoplang.addEdge(plus, MetaMeta.CONFORMS_TO, Operation.OPERATOR); 
		binoplang.addEdge(arg1, MetaMeta.CONFORMS_TO, Operation.OPERAND);
		binoplang.addEdge(arg2, MetaMeta.CONFORMS_TO, Operation.OPERAND);

		Operation.setOperandOrder(binoplang, arg1, arg2);
		
		System.out.println(binoplang);
		
		/* + 2 3 */ 
		RelationalModel expr = new RelationalModel().beginTransaction();
		IIdentity p = expr.addNode("+");
		IIdentity to = expr.addNode("2");
		IIdentity tre = expr.addNode("3");
		expr.addEdge(p, MetaMeta.IS, binoplang.getSingleNodePointingTo(Operation.OPERATOR));
	}	
}
