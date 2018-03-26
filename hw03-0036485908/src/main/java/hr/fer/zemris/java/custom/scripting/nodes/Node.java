package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public class Node {
	
	ArrayIndexedCollection collection;
	
	public void addChildNode(Node child) {
		if(collection==null) {
			collection=new ArrayIndexedCollection();
		}
		
		collection.add(child);
	}
	
	public int numberOfChildren() {
		return collection.size();
	}
	
	public Node getChild(int index) {
		return (Node)collection.get(index);
	}

}
