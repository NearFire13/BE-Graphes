package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.algorithm.utils.EmptyPriorityQueueException;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
    // List of labels
    private static Label[] labels;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution;
        // TODO:
        Graph graph = data.getGraph();
        labels = new Label[graph.getNodes().size()];
        
        //Association d'un label à chaque sommet.
        for (int i = 0; i < labels.length; ++i) {
        	System.out.println(i + " " + data.getGraph().getNodes().get(i).getId());
        	labels[i] = new Label(data.getGraph().getNodes().get(i), false, Double.POSITIVE_INFINITY, null);
        }
        labels[data.getOrigin().getId()].setCost(0);
        
        BinaryHeap<Label> myHeap = new BinaryHeap<Label>();
        myHeap.insert(labels[data.getOrigin().getId()]);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        while(!labels[data.getDestination().getId()].isMark() && !myHeap.isEmpty())
        {
        	Label x;
        	
        	try {
        		x = myHeap.findMin();
        	}
        	catch(EmptyPriorityQueueException e)
        	{
        		break;
        	}
        	
        	try {
        		myHeap.remove(x);
        	}
        	catch (ElementNotFoundException e) { }
        	labels[x.getCurrentVertex().getId()].setMark(true);
        	
        	
        	for(Arc arc : x.getCurrentVertex().getSuccessors())
        	{
        		if (!data.isAllowed(arc)) {
        			continue;
        		}
        		
        		Node y;
        		try {
        			y = arc.getDestination();
        		}
        		catch(EmptyPriorityQueueException e) { break; }
        		if(!labels[y.getId()].isMark())
        		{
                    // Retrieve weight of the arc.
                    double w = data.getCost(arc);
                    double oldDistance = labels[y.getId()].getCost();
                    double newDistance = labels[x.getCurrentVertex().getId()].getCost() + w;

                    if(Double.isInfinite(oldDistance) && Double.isFinite(newDistance)) {
                        notifyNodeReached(arc.getDestination());
                    }
                    
        			if(oldDistance > newDistance)
        			{
        				labels[y.getId()].setCost(newDistance);
        				labels[y.getId()].setFather(arc);
        					
        				try
        				{
        					myHeap.remove(labels[y.getId()]);
        					myHeap.insert(labels[y.getId()]);
        				}
        				catch (ElementNotFoundException e)
        				{
        					myHeap.insert(labels[y.getId()]);
        				}
        			}
        		}
        	}
        }
        
        if (!labels[data.getDestination().getId()].isMark())
        {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else
        {
            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
        	
	        ArrayList<Node> nodes = new ArrayList<>();
	        Node node = data.getDestination();
	        nodes.add(node);
	        while (!(node == data.getOrigin())) {
	            Arc fatherNode;
	            try {
	            	fatherNode = labels[node.getId()].getFather();
	            }
	            catch(EmptyPriorityQueueException e) { break; }
	            nodes.add(fatherNode.getOrigin());
	            node = fatherNode.getOrigin();
	        }
	        Collections.reverse(nodes);
	        
	        Path path;
	        if(data.getMode() == Mode.LENGTH)
	        {
	        	path = Path.createShortestPathFromNodes(graph, nodes);
	        }
	        else
	        {
	        	path = Path.createFastestPathFromNodes(graph, nodes);
	        }
	        
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, path);
        }
        return solution;
    }

}