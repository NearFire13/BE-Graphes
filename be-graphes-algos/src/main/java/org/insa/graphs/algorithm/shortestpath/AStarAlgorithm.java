package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    public void initLabels(ShortestPathData data) {
    	final int nbNodes = data.getGraph().size();
        
    	labels = new LabelStar[nbNodes];
        
        for(int i = 0; i < nbNodes; i++) {
        	Node myCurrentNode = data.getGraph().getNodes().get(i);
        	Node myDestination = data.getDestination();

        	if(data.getMode() == Mode.LENGTH) // Mode Longueur
        	{
        		labels[i] = new LabelStar(myCurrentNode, false,  Double.POSITIVE_INFINITY, myCurrentNode.getPoint().distanceTo(myDestination.getPoint()), null);
        	}
        	else // Mode Temps
        	{
        		if(data.getGraph().getGraphInformation().hasMaximumSpeed())
        			labels[i] = new LabelStar(myCurrentNode, false,  Double.POSITIVE_INFINITY, myCurrentNode.getPoint().distanceTo(myDestination.getPoint())/data.getGraph().getGraphInformation().getMaximumSpeed(), null); // t = d/v 
        		else
        			labels[i] = new LabelStar(myCurrentNode, false,  Double.POSITIVE_INFINITY, myCurrentNode.getPoint().distanceTo(myDestination.getPoint())/130, null); // Choix arbitraire de 130, vitesse par défaut
        	}
        }
    }

}
