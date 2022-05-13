package org.insa.graphs.model;

public class Label implements Comparable<Label> {
	
	private Node node;
	
	private boolean mark;
	
	private double cost;
	
	private Arc fatherArc;
	
	public Label(Node node, boolean mark,  double cost, Arc fatherArc)
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
	
	public double getCost() {
		return this.cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public Arc getFatherArc() {
		return this.fatherArc;
	}
	
	public void setFatherArc(Arc fatherArc) {
		this.fatherArc = fatherArc;
	}
	
	public double getTotalCost() {
		return this.getCost();
	}

	@Override
	public int compareTo(Label other) {
		return Double.compare(this.getTotalCost(), other.getTotalCost());
	}

}
