package org.insa.graphs.model;

public class LabelStar extends Label {
	
	/**
	 * Valeur courante du plus court chemin depuis l'origine vers le sommet.
	 */
	private double estimatedCost;

	public LabelStar(Node currentVertex, boolean mark, double realCost, Arc father, double estimatedCost) {
		super(currentVertex, mark, realCost, father);
		this.estimatedCost = estimatedCost;
	}
	
	@Override
	public double getEstimatedCost() {
		return this.estimatedCost;
	}
	
	// Rédéfinition du coût total
	@Override
	public double getTotalCost() {
		return super.getCost() + this.estimatedCost;
	}

}
