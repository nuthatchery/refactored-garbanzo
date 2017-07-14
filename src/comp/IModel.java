package comp;

public interface IModel {

	IElementHandle get(Node element);
	
	Iterable<Node> getChildren(Node element);
	
	int getNumChildren(Node element);
	
	Iterable<Node> getLinks(Node element);
	
	boolean isDescendantOf(Node parentElement, Node descendantElement);
	
	boolean hasData(Node element, Class<?> type);

	<T> T getData(Node element, Class<T> type);

	<T> void setData(Node element, T data);

	Node makeIdentity();
	
	Node makeIdentity(String name);
	
	Node makeElement(Node schema);
	
	Node makeElement(Node schema, Node parent);

	Node makeElement(Node schema, Node parent, Node label);
	
	void beginTransaction();
	
	void commitTransaction();
	
	void rollbackTransaction();

	Node getRoot();

	IModel addChild(Node tree, Node root);

	int getVersion();
}
