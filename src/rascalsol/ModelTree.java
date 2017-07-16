package rascalsol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelTree {
	//	private ArrayList<IIdentity> b = new ArrayList<>();
	private IIdentity[][] B;
	private HashMap<IIdentity, Integer> bindex = new HashMap<>();
	//	private ArrayList<IIdentity> l = new ArrayList<>();
	private IIdentity[][] L;
	private HashMap<IIdentity, Integer> lindex = new HashMap<>();
	private IIdentity root;

	private List<IIdentity> orderednodes = new ArrayList<>(); 

	public ModelTree(IIdentity root){
		B = new IIdentity[0][3];
		L = new IIdentity[0][3];
		orderednodes.add(root);
		bindex.put(root, B.length);
		lindex.put(root, L.length);
		this.root = root;
		datainvariant();
	}

	public ModelTree(ModelTree m){
		this(m.getRoot());
		//add to register as ++version of m
	}

	private IIdentity getRoot() {
		return root;
	}

	public ModelTree addChild(IIdentity child, IIdentity edge, IIdentity parent){
		ModelTree m = new ModelTree(this);
		//		ModelTree m = this; //mutable

		m.B = new IIdentity[B.length+1][3];
		m.bindex = new HashMap<IIdentity, Integer>();

		m.L = L.clone();
		m.lindex = (HashMap<IIdentity, Integer>) lindex.clone();

		if(bindex.get(parent)!=null){ //else what to do 
			int parentIndexB = bindex.get(parent);
			
			bindex.forEach((id, bi)->{
				if(bi<=parentIndexB) m.bindex.put(id, bi); 
				else m.bindex.put(id,  bi+1);
			}
					);
			m.bindex.put(child, B.length);
			
			int i=0;
			//copy all edges before parent's children
			for(; i<parentIndexB; i++){
				m.B[i][0] = B[i][0];
				m.B[i][1] = B[i][1];
				m.B[i][2] = B[i][2];
			}
			//copy parent's children
			for(; i<B.length && B[i][0].equals(parent); i++){
				m.B[i][0] = B[i][0];
				m.B[i][1] = B[i][1];
				m.B[i][2] = B[i][2];
			}
				
			m.B[i][0] = parent;
			m.B[i][1] = edge;
			m.B[i][2] = child;
			
			for(i=i+1; i<B.length; i++){
				m.B[i+1][0] = B[i][0];
				m.B[i+1][1] = B[i][1];
				m.B[i+1][2] = B[i][2];
				print(m.B);
			}
		}

		m.orderednodes.clear(); 
		m.getAll().forEach(elem-> m.orderednodes.add(elem));
		datainvariant();
		return m;
	}

	private void print(IIdentity[][] b2) {
		System.out.println("printing arr");
		for(int i=0; i<b2.length; i++){
			System.out.println(b2[i][0] + ", " + b2[i][1] + ", " +b2[i][2]);
		}
	}

	/**
	 * @return an iterable of the nodes in prefix traversal
	 */
	public Iterable<IIdentity> getAll(){
		List<IIdentity> ls = new ArrayList<>();

		class NodeAdder{
			void addNode(IIdentity n){
				ls.add(n);
				if(bindex.get(n)==null||bindex.get(n)>=B.length)return;
				for(int i=bindex.get(n); i<B.length && B[i] != null && B[i][0].equals(n); i++){
					addNode(B[i][2]);
				}
			}
		};

		new NodeAdder().addNode(root);
		return ls;
	}
	
	public void datainvariant(){
		orderednodes.forEach(item -> {assert bindex.containsKey(item) : "All nodes should be found in bindex: " + item;});
		orderednodes.forEach(item -> {assert bindex.get(item)!=null: "No nodes should have index null: " + item;});
		for(int i=0; i<orderednodes.size(); i++){
			IIdentity n = orderednodes.get(i);
			for(int j = i+1; j<orderednodes.size(); j++){
				if(bindex.get(orderednodes.get(j))!=B.length)
					assert bindex.get(n) <= bindex.get(orderednodes.get(j)) : "Ordered nodes should have increasing branch-index";
			}
		}
	}
}
