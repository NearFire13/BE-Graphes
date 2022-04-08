package org.insa.graphs.model;

public class Label {
	
	private Node node;
	
	private boolean mark;
	
	private int cost;
	
	private Arc fatherArc;
	
	public Label(Node node, boolean mark,  int cost, Arc fatherArc)
	{
		this.node = node;
		this.mark = mark;
		this.cost = cost;
		this.fatherArc = fatherArc;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	public boolean isMark() {
		return this.mark;
	}
	
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public Arc getFatherArc() {
		return this.fatherArc;
	}
	
	public void setFatherArc(Arc fatherArc) {
		this.fatherArc = fatherArc;
	}

}
