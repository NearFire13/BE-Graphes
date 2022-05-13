package org.insa.graphs.model;

public class LabelStar extends Label {
	
	private double costEstimated;

	public LabelStar(Node node, boolean mark, double cost, double costEstimated, Arc fatherArc)
	{
		super(node, mark, cost, fatherArc);
		this.costEstimated = costEstimated;
	}
	
	public double getCostEstimated() {
		return this.costEstimated;
	}
	
	public void setCostEstimated(double costEstimated) {
		this.costEstimated = costEstimated;
	}
	
	// On redéfinie le coût total afin d'y ajouté le coût estimé
	@Override
	public double getTotalCost() {
		return this.getCost() + this.getCostEstimated();
	}

}
