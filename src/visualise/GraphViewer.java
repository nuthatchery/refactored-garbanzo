package visualise;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import comp.IIdentity;
import datastructures.Triple;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
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
		List<IIdentity> nodes = model.getNodes();
//		nodes.sort(new ModelIdComparator());
		for(IIdentity id : nodes)
			g.addVertex(id);
		for(Triple edge : model.getEdges())
			g.addEdge(edge, edge.from(), edge.to());
		//TODO Javadoc API for added labels/names

		CircleLayout<IIdentity, Triple> layout = new CircleLayout<IIdentity, Triple>(g);
		layout.setVertexOrder(new ModelIdComparator());
		VisualizationViewer<IIdentity, Triple> vs =
				new VisualizationViewer<>(
						layout, new Dimension(400, 400));
		
		vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Triple>(){
			@Override
			public String transform(Triple edge) {
				return edge.arrow().toString();
			}});
		vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<IIdentity>() {
			@Override
			public String transform(IIdentity v) {
				return v.toString();
			}});
		vs.getRenderContext().setVertexFillPaintTransformer(new Transformer<IIdentity, Paint>(){
			public Paint transform(IIdentity id){
				return getModelPaint(id.getModelId());
			}
		});
//		vs.getRenderContext().setVertexDrawPaintTransformer(new Transformer<IIdentity, Paint>(){
//			public Paint transform(IIdentity id){
//				return Color.PINK;
//			}
//		});
		vs.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		DefaultModalGraphMouse<IIdentity, Triple> gm = new DefaultModalGraphMouse<>();
		gm.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
		vs.setGraphMouse(gm); 
		 
		JFrame frame = new JFrame();
		frame.getContentPane().add(vs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
	private static Paint getModelPaint(IIdentity modelId) {
		return ColorMap.get(modelId)!=null?ColorMap.get(modelId) : ColorMap.put(modelId, getNewRandomColor());
	}
	
	private static Paint getNewRandomColor() {
		int R = (int)(Math.random()*256);
		int G = (int)(Math.random()*256);
		int B= (int)(Math.random()*256);
		Color color = new Color(R, G, B); //random color, but can be bright or dull

		//to get rainbow, pastel colors
		Random random = new Random();
		final float hue = random.nextFloat();
		final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
		final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
		color = Color.getHSBColor(hue, saturation, luminance);
		return color;
	}

	private static HashMap<IIdentity, Paint> ColorMap = new HashMap<>();
}
