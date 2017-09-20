package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

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

	private RelationalModel getRandomImmutableModel() {
		RelationalModel model = new RelationalModel().beginTransaction();
		Random r = new Random();
		if(r.nextInt(10)==1) return model.commitTransaction(); //"guarantee" empty model 
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
		model.commitTransaction();
		return model;
	}

	private RelationalModel doRandomEdits(RelationalModel model) {
		model = model.beginTransaction();
		Random r = new Random();
		if(r.nextInt(10)==1) return model; //"guarantee" empty model 
		int num = r.nextInt(N/10)+1;
		List<IIdentity> nodes = model.getNodes();
		if(r.nextBoolean()){
			for(int i = 0; i<num; i++){
				model.addNode();
			}
			nodes = model.getNodes();
		}
		if(!model.getNodes().isEmpty() && r.nextBoolean()){
			int lim = num*r.nextInt(r.nextInt(9)+1);
			for(int i = 0; i<lim; i++){
				try{
					model.addEdge(nodes.get(r.nextInt(nodes.size())), nodes.get(r.nextInt(nodes.size())), nodes.get(r.nextInt(nodes.size())));
				}catch(NodeNotFoundException e){
					fail("something went wrong while adding edges");
				}
			}
		}
		model = model.commitTransaction();
		return model;
	}

	private IIdentity getRandomNodeFrom(RelationalModel model) {
		Random r = new Random();
		List<IIdentity> nodes = model.getNodes();
		IIdentity from = model.getNodes().get(r.nextInt(nodes.size()));
		return from;
	}

	@Test
	public void testGetRandomModel(){
		RelationalModel emptyModel = new RelationalModel();
		for (int i = 0; i < N; i++) {
			assertNotEquals("No models should be equals unless the same model, not even empty ones", emptyModel, getRandomImmutableModel());
		}
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
			model = getRandomImmutableModel();
			assertEquals("Copied models should be equals: ", model, model.copy());
		}
	}

	@Test
	public void testAddNodeContainsNode(){
		RelationalModel model;
		for(int i=0; i<N; i++){
			model = getRandomImmutableModel();
			model = model.beginTransaction();
			IIdentity id = model.addNode();
			model = model.commitTransaction();
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
			model = getRandomImmutableModel();
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
			assertFalse(model.getEdges(id1).contains(new Tuple(id2, id2)));
		}
	}

	@Test 
	public void testImmutableGenerator(){
		RelationalModel m;
		for (int i = 0; i < N; i++) {
			m = getRandomImmutableModel();
			assertFalse("Should be false " +m.isMutable(), m.isMutable());
		}
	}

	@Test 
	public void testEquals(){
		RelationalModel m, m1, m2, m3;
		for (int i = 0; i < N; i++) {
			m = getRandomImmutableModel();
			assertFalse(m.isMutable());
			m1 = m.beginTransaction();
			assertEquals("Reflexive equals", m, m);
			assertEquals("Reflexive equals", m1, m1);
			if(m.equals(m1)){
				m.equals(m1);
			}
			assertNotEquals("Should be not eq due to mutability diff", m, m1);
			m2 = m1.commitTransaction();
			assertTrue("Should be the same object", m1 == m2);
			assertEquals("Should be similar", m, m2);
			try {
				m1.addNode();
				fail("Should not be allowed to edit object after commitTransaction()");
			}catch(IllegalStateException e){
			}
		}
	}

	@Test
	public void testConstantModelID(){
		RelationalModel m, m1;
		for (int i = 0; i < N; i++) {
			m = getRandomImmutableModel();
			m1 = m.beginTransaction();
			assertEquals(m.getId(), m1.getId());
			m1.addNode();
			assertEquals(m.getId(), m1.getId());
			doRandomEdits(m);
			assertEquals(m.getId(), m1.getId());
		}
	}

	@Test
	public void testAddContainsRemoveContainsSingleNode(){
		RelationalModel m, m1;
		for (int i = 0; i < N; i++) {
			m = getRandomImmutableModel();
			m1 = m.beginTransaction();
			IIdentity node = m1.addNode();
			assertFalse(m.containsNode(node));
			m1.removeNode(node);
			m1.commitTransaction();
			assertFalse(m1.containsNode(node));
			assertEquals(m, m1);
		}
	}

	@Test
	public void testAddRemoveHasPathSingleEdge(){
		RelationalModel m, m1;
		for (int i = 0; i < N; i++) {
			do{
				m = getRandomImmutableModel();
			}while(m.getNodes().size()<=2);
			m1 = m.beginTransaction();
			IIdentity from = getRandomNodeFrom(m1);
			assert m1.containsNode(from);
			IIdentity to = getRandomNodeFrom(m1);
			assert m1.containsNode(to);
			Triple edge = new Triple(from, new Identity(m1), to);
			m1.addEdge(from, edge.arrow(), to);
			assertFalse(m.hasPath(from, edge.arrow(), to));
			assertTrue(m1.hasPath(from, edge.arrow(), to));
			m1.commitTransaction();
			m1 = m1.beginTransaction();
			m1.removeEdge(from, edge.arrow(), to);
			assertFalse(m1.hasPath(from, edge.arrow(), to));
			m1.commitTransaction();
			assertEquals(m, m1);
		}
	}

	@Test
	public void testAddRemoveHasPathManyEdges(){
		Random r = new Random();
		RelationalModel m, m1;
		int tested = 0;
		for (int i = 0; i < N; i++) {
			int pathLength = r.nextInt(10)+1;
			do{
				m = getRandomImmutableModel();
			}while(m.getNodes().size()<pathLength*2);
			m1 = m.beginTransaction();
			Set<IIdentity> pathSet = new TreeSet<>();
			for (int j = 0; j < pathLength; j++) {
				pathSet.add(getRandomNodeFrom(m1));
			}
			IIdentity edge = new Identity(m1, "edge");
			IIdentity[] path = new Identity[0];
			path =  pathSet.toArray(path);
			if(path.length<2)
				continue;
			tested++;
			for (int j = 0; j<path.length-1; j++) {
				m1.addEdge(path[j], edge , path[j+1]);
			}
			assertTrue(m1.hasPath(path[0], edge, path[path.length-1]));
			int randomNode = r.nextInt(path.length-1);
			m1.removeEdge(path[randomNode], edge, path[randomNode+1]);
			assertFalse(m1.hasPath(path[0], edge, path[path.length-1]));
		}
		if(tested<2)
			fail("Less than two models were tested: " + tested + ". Run again, or adjust setup.");
	}

	@Test
	public void testRemoveNodeIncludesEdgesFrom(){
		RelationalModel m, m1;
		for (int i = 0; i < N; i++) {
			do{
				m = getRandomImmutableModel();
			}while(m.getNodes().size()<1);
			m1 = m.beginTransaction();
			IIdentity edge = new Identity(m1, "edge");
			IIdentity from = getRandomNodeFrom(m1);
			IIdentity to = m1.addNode();
			m1.addEdge(from, edge , to);
			assertTrue(m1.hasPath(from, edge, to));
			m1.removeNode(from);
			assertFalse(m1.getEdges().contains(new Triple(from, edge, to)));
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
