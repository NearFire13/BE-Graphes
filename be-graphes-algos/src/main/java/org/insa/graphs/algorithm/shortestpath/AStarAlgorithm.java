package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    public void initLabels(ShortestPathData data) {
    	labels = new LabelStar[data.getGraph().getNodes().size()];
    	
        //Association d'un label à chaque sommet.
        for (int i = 0; i < labels.length; ++i) {
        	System.out.println(i + " " + this.data.getGraph().getNodes().get(i).getId());
        	
        	Node currentVertex = this.data.getGraph().getNodes().get(i);
        	double estimatedCost = currentVertex.getPoint().distanceTo(data.getDestination().getPoint());
        	
        	if(this.data.getMode().equals(AbstractInputData.Mode.LENGTH))
        	{
        		this.labels[i] = new LabelStar(currentVertex, false, Double.POSITIVE_INFINITY, null, estimatedCost);
        	}
        	else
        	{
        		this.labels[i] = new LabelStar(currentVertex, false, Double.POSITIVE_INFINITY, null, (estimatedCost / 25d)); // v = d/t
        	}
        }
    }

}
