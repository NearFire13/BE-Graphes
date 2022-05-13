package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	protected Label labels[];
	
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public void initLabels(ShortestPathData data) {
    	final int nbNodes = data.getGraph().size();
        
    	labels = new Label[nbNodes];
        
        for(int i = 0; i < nbNodes; i++)
        	labels[i] = new Label(data.getGraph().getNodes().get(i), false,  Double.POSITIVE_INFINITY, null);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        
        BinaryHeap<Label> myHeap = new BinaryHeap<Label>();
        
        initLabels(data);

        labels[data.getOrigin().getId()].setCost(0);
        
        myHeap.insert(labels[data.getOrigin().getId()]);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        while(!labels[data.getDestination().getId()].isMark() && !myHeap.isEmpty())
        {
        	Label x = myHeap.deleteMin();
        	x.setMark(true);
        	//System.out.println(x.getTotalCost()); // Le coût est bien croissant au fur et à mesure des itérations.
        	
        	//Notify observers about the nodes marked.
        	notifyNodeMarked(x.getNode());
        	
        	for(Arc arc : x.getNode().getSuccessors())
        	{
        		Label y = labels[arc.getDestination().getId()];
        		if(!y.isMark())
        		{
        			// Small test to check allowed roads...
        			if (!data.isAllowed(arc)) {
        				continue;
        			}
        			
        			// Retrieve weight of the arc.
        			double w = data.getCost(arc);
        			double oldDistance = y.getTotalCost();
        			double newDistance = x.getTotalCost() + w;
        			
        			if (Double.isInfinite(oldDistance) && Double.isFinite(newDistance)) {
        				notifyNodeReached(arc.getDestination());
        			}
        			
        			// Check if new distances would be better, if so update...
        			if (oldDistance > newDistance)
        			{
        				if(y.getTotalCost() != Double.POSITIVE_INFINITY) // Check if y exists in the heap, and in this case, the cost of y has been changed. 
        				{
        					myHeap.remove(y);
        				}
        				y.setCost(x.getTotalCost() + w);
        				myHeap.insert(y);
        				y.setFatherArc(arc);
        			}
        			double costReal = y.getCost();
        			double costEstimated = y.getTotalCost() - y.getCost();
        			if(costEstimated > costReal) {
	        			System.out.println("Coût réel : " + costReal);
	        			System.out.println("Coût estimé :" + costEstimated);
        			}
        		}
        	}
        }
        ShortestPathSolution solution = null;

        // Destination has no predecessor, the solution is infeasible...
        if (labels[data.getDestination().getId()].getFatherArc() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels[data.getDestination().getId()].getFatherArc();
            while (arc != null) {
                arcs.add(arc);
                arc = labels[arc.getOrigin().getId()].getFatherArc();
            }

            // Reverse the path...
            Collections.reverse(arcs);
            
            Path path = null;
            ArrayList<Node> nodes = new ArrayList<>();
            for(Arc myArc : arcs)
            	nodes.add(myArc.getOrigin());
            
            if(data.getMode() == Mode.LENGTH)
            {
            	path = Path.createShortestPathFromNodes(data.getGraph(), nodes);
            }
            else
            {
            	path = Path.createFastestPathFromNodes(data.getGraph(), nodes);
            }

            // Create the final solution.
            //solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));//3.89 & 6.4 for the nodes 1 & 9
            solution = new ShortestPathSolution(data, Status.OPTIMAL, path);//3.85 & 6.34 for the nodes 1 & 9 => MORE EFFICIENT
        }

        return solution;
    }

}
