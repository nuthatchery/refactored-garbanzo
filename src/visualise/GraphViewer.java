package visualise;

import java.awt.Dimension;

import javax.swing.JFrame;

import comp.IIdentity;
import datastructures.Triple;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import models.Operations;
import relationalmodel.RelationalModel;

public class GraphViewer {

	
	public static void main(String[] args){
		view(Operations.BINARY_NUMBER_OPERATION);
	}
	
	static void view(RelationalModel model){
		
		//TODO make view of model passed on
		// make delegate method in relationalModel
		DirectedSparseGraph<IIdentity, Triple> g = new DirectedSparseGraph<>();
		for(IIdentity id : model.getNodes())
			g.addVertex(id);
		for(Triple edge : model.getEdges())
			g.addEdge(edge, edge.from(), edge.to());
		
//		
//		
//		DirectedSparseGraph g = new DirectedSparseGraph();
//		g.addVertex("Vertex1");
//		g.addVertex("Vertex2");
//		g.addVertex("Vertex3");
//		g.addEdge("Edge1", "Vertex1", "Vertex2");
//		g.addEdge("Edge2", "Vertex1", "Vertex3");
//		g.addEdge("Edge3", "Vertex3", "Vertex1");
		
		VisualizationImageServer vs =
				new VisualizationImageServer(
						new CircleLayout(g), new Dimension(200, 200));
		JFrame frame = new JFrame();
		frame.getContentPane().add(vs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
}
