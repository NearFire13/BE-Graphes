package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();
        Label labels[] = new Label[nbNodes];
        BinaryHeap<Label> myHeap = new BinaryHeap<Label>();
        
        for(int i = 0; i < nbNodes; i++)
        	labels[i] = new Label(graph.getNodes().get(i), false,  Double.POSITIVE_INFINITY, null);
        
        labels[data.getOrigin().getId()].setCost(0);
        myHeap.insert(labels[data.getOrigin().getId()]);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        while(!labels[data.getDestination().getId()].isMark() && !myHeap.isEmpty())
        {
        	Label x = myHeap.deleteMin();
        	x.setMark(true);
        	//System.out.println(x.getCost()); // Le coût est bien croissant au fur et à mesure des itérations.
        	
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
        			double oldDistance = y.getCost();
        			double newDistance = x.getCost() + w;
        			
        			if (Double.isInfinite(oldDistance) && Double.isFinite(newDistance)) {
        				notifyNodeReached(arc.getDestination());
        			}
        			
        			// Check if new distances would be better, if so update...
        			if (oldDistance > newDistance)
        			{
        				if(y.getCost() != Double.POSITIVE_INFINITY) // Check if y exists in the heap, and in this case, the cost of y has been changed. 
        				{
        					myHeap.remove(y);
        				}
        				y.setCost(x.getCost() + w);
        				myHeap.insert(y);
        				y.setFatherArc(arc);
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
            	path = Path.createShortestPathFromNodes(graph, nodes);
            }
            else
            {
            	path = Path.createFastestPathFromNodes(graph, nodes);
            }

            // Create the final solution.
            //solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));//3.89 & 6.4 for the nodes 1 & 9
            solution = new ShortestPathSolution(data, Status.OPTIMAL, path);//3.85 & 6.34 for the nodes 1 & 9 => MORE EFFICIENT
        }

        return solution;
    }

}
