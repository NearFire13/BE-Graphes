package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraAlgorithmTest {
	
	private static String baseMaps, basePaths, mapINSAName, mapHauteGaronneName, pathINSARangueilR2Name, pathINSABikiniCanalName;
    
    private static Graph graphMapINSA, graphMapHauteGaronne;
    
    private static Path pathINSARangueilR2, pathINSABikiniCanal;
    
    private static ShortestPathSolution dijkstraAlgoSolutionINSARangueilR2, dijkstraAlgoSolutionINSABikiniCanal, bellmanFordAlgoSolutionINSARangueilR2, bellmanFordAlgoSolutionINSABikiniCanal;
    
    //Cartes routières
    
    //Cartes non routières
    
    //En distance
    
    //En temps
    
    //Origines
    
    //Destinations
    
    //Chemin inexistants
    
    //Chemin de longueur nulle
    
    //Chemin normaux
    
    //Comparaison des résultats avec Bellman-Ford
    
    @BeforeClass
    public static void initAll() throws IOException
    {
    	// Define bases for maps and paths
    	baseMaps = "/home/babonnea/Bureau/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/";
    	basePaths = "/home/babonnea/Bureau/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/";
        
    	// Define maps
    	mapINSAName = baseMaps + "insa.mapgr";
    	mapHauteGaronneName = baseMaps + "haute-garonne.mapgr";
    	
    	// Define paths
        pathINSARangueilR2Name = basePaths + "path_fr31insa_rangueil_r2.path";
        pathINSABikiniCanalName = basePaths + "path_fr31_insa_bikini_canal.path";
        
        // Init Maps
        graphMapINSA = initMyMap(mapINSAName);
        graphMapHauteGaronne = initMyMap(mapHauteGaronneName);
        
        // Init Paths
        pathINSARangueilR2 = initMyPath(pathINSARangueilR2Name, graphMapINSA);
        pathINSABikiniCanal = initMyPath(pathINSABikiniCanalName, graphMapHauteGaronne);
        
        AlgorithmDijkstraTests();
        AlgorithmBellmanFordTests();
    }
    
    public static Graph initMyMap(String myMap) throws IOException
    {
    	// Create a graph reader
    	GraphReader myReaderMap = new BinaryGraphReader(
        		new DataInputStream(new BufferedInputStream(new FileInputStream(myMap))));
    	
        // Read the graph
    	Graph graphMap = myReaderMap.read();
        
        return graphMap;
    }
    
    public static Path initMyPath(String myPath, Graph myGraph) throws IOException
    {
    	// Create a graph reader
    	PathReader myReaderPath = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(myPath))));
    	
        // Read the graph
    	Path path = myReaderPath.readPath(myGraph);
        
        return path;
    }
    
    public static ShortestPathSolution algorithmDijkstra(Graph graph, Node originNode, Node destinationNode) {
    	
    	ShortestPathData shortestPathData = new ShortestPathData(graph, originNode, destinationNode, ArcInspectorFactory.getAllFilters().get(0));
    	
    	DijkstraAlgorithm dijkstraAlgo = new DijkstraAlgorithm(shortestPathData);
        ShortestPathSolution solution = dijkstraAlgo.doRun();
        
        return solution;
    }
    
    public static ShortestPathSolution algorithmBellmanFord(Graph graph, Node originNode, Node destinationNode) {
    	
    	ShortestPathData shortestPathData = new ShortestPathData(graph, originNode, destinationNode, ArcInspectorFactory.getAllFilters().get(0));
    	
    	BellmanFordAlgorithm bellmanFordAlgo = new BellmanFordAlgorithm(shortestPathData);
        ShortestPathSolution solution = bellmanFordAlgo.doRun();
        
        return solution;
    }
    
    public static void AlgorithmDijkstraTests() {
        //dijkstraAlgoSolutionINSARangueilR2 = algorithmDijkstra(graphMapINSA, graphMapINSA.get(552), graphMapINSA.get(526));
        //dijkstraAlgoSolutionINSABikiniCanal = algorithmDijkstra(graphMapHauteGaronne, graphMapHauteGaronne.get(10991), graphMapHauteGaronne.get(63104));
        
        dijkstraAlgoSolutionINSARangueilR2 = algorithmDijkstra(graphMapINSA, pathINSARangueilR2.getOrigin(), pathINSARangueilR2.getDestination());
        dijkstraAlgoSolutionINSABikiniCanal = algorithmDijkstra(graphMapHauteGaronne, pathINSABikiniCanal.getOrigin(), pathINSABikiniCanal.getDestination());
    }
    
    public static void AlgorithmBellmanFordTests() {
        //bellmanFordAlgoSolutionINSARangueilR2 = algorithmBellmanFord(graphMapINSA, graphMapINSA.get(552), graphMapINSA.get(526));
        //bellmanFordAlgoSolutionINSABikiniCanal = algorithmBellmanFord(graphMapHauteGaronne, graphMapHauteGaronne.get(10991), graphMapHauteGaronne.get(63104));
    	
    	bellmanFordAlgoSolutionINSARangueilR2 = algorithmBellmanFord(graphMapINSA, pathINSARangueilR2.getOrigin(), pathINSARangueilR2.getDestination());
        bellmanFordAlgoSolutionINSABikiniCanal = algorithmBellmanFord(graphMapHauteGaronne, pathINSABikiniCanal.getOrigin(), pathINSABikiniCanal.getDestination());
    }
    
    
    
    /*@Test
    public void testIsEmpty() {
    	assertTrue(<>.isEmpty());
    }*/
    
    @Test
    public void testPath() {
    	assertEquals(0, dijkstraAlgoSolutionINSARangueilR2.getPath().getOrigin().compareTo(pathINSARangueilR2.getOrigin()));
    	assertEquals(0, dijkstraAlgoSolutionINSABikiniCanal.getPath().getOrigin().compareTo(pathINSABikiniCanal.getOrigin()));
    	assertEquals(0, dijkstraAlgoSolutionINSARangueilR2.getPath().getOrigin().compareTo(bellmanFordAlgoSolutionINSARangueilR2.getPath().getOrigin()));
    	assertEquals(0, dijkstraAlgoSolutionINSABikiniCanal.getPath().getOrigin().compareTo(bellmanFordAlgoSolutionINSABikiniCanal.getPath().getOrigin()));
    }
    
    

}
