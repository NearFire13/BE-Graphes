package org.insa.graphs.algorithm;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
	
	/**
	 * Sommet associé à ce label (sommet ou numéro de sommet).
	 */
	private Node currentVertex;
	
	/** 
	 * Booléen, vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme.
	 */
	private boolean mark;
	
	/**
	 * Valeur courante du plus court chemin depuis l'origine vers le sommet.
	 */
	private double cost;
	
	/**
	 * Correspond au sommet précédent sur le chemin correspondant au plus court chemin courant.
	 * Afin de reconstruire le chemin à la fin de l'algorithme, mieux vaut stocker l'arc plutôt que seulement le père.
	 */
	private Arc father;
	
	public Label(Node currentVertex, boolean mark, double cost, Arc father)
	{
		this.currentVertex = currentVertex;
		this.mark = mark;
		this.cost = cost;
		this.father = father;
	}
	
	public Node getCurrentVertex() {
		return currentVertex;
	}
	
	public boolean isMark() {
		return mark;
	}
	
	/**
	 * Trouve le coût du label.
	 * 
	 * @return Coût du label.
	 */
	public double getCost() {
		return this.cost;
	}
	
	public Arc getFather() {
		return father;
	}
	
	public void setCurrentVertex(Node currentVertex) {
		this.currentVertex = currentVertex;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setFather(Arc father) {
		this.father = father;
	}

	@Override
	public int compareTo(Label other) {
        return Double.compare(getCost(), other.getCost());
	}

}
