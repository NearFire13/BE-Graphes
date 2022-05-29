package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import org.insa.graphs.algorithm.AbstractSolution;
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

public class AlgorithmsTest {
	
	private static String baseMaps, basePaths, mapHauteGaronne, mapCarreDense, pathINSABikiniCanalName;
    
    private static Graph graphHauteGaronne, graphCarreDense;
    
    private static Path pathINSABikiniCanal, singleNodePath;
    
    private static ShortestPathSolution singleNodeDijkstraLengthMode, singleNodeDijkstraTimeMode, singleNodeAStarLengthMode, singleNodeAStarTimeMode, dijkstraBikiniInfeasibleLengthMode, AStarBikiniInfeasibleLengthMode, dijkstraBikiniLengthMode, AStarBikiniLengthMode;
    
    private static ShortestPathData dataCarreDenseLengthMode, dataCarreDenseTimeMode, randomDataCarreDenseLengthMode, randomDataCarreDenseLengthModeOnlyCars, randomDataCarreDenseTimeMode, randomDataCarreDenseTimeModeOnlyCars, randomDataCarreDenseTimeModePedestrian, dataBikiniInfeasibleLengthMode, dataBikiniLengthMode;
    
    private static BellmanFordAlgorithm bellmanFordAlgo;
    
    private static DijkstraAlgorithm dijkstraAlgo;
    
    private static AStarAlgorithm AStarAlgo;
    
    protected static ShortestPathSolution[] randomDijkstraLengthMode, randomDijkstraLengthModeOnlyCars, randomDijkstraTimeMode, randomDijkstraTimeModeOnlyCars, randomDijkstraTimeModePedestrian,
	randomBellmanFordLengthMode, randomBellmanFordLengthModeOnlyCars, randomBellmanFordTimeMode, randomBellmanFordTimeModeOnlyCars, randomBellmanFordTimeModePedestrian,
	randomAStarLengthMode, randomAStarLengthModeOnlyCars, randomAStarTimeMode, randomAStarTimeModeOnlyCars, randomAStarTimeModePedestrian;
    
    @BeforeClass
    public static void initAll() throws IOException
    {
    	// Define directories for maps and paths
    	baseMaps = "F:/INSA Toulouse/3A/S6/BE-Graphes/Maps/";
    	basePaths = "F:/INSA Toulouse/3A/S6/BE-Graphes/Paths/";
        
    	// Define maps
    	mapHauteGaronne = baseMaps + "haute-garonne.mapgr";
    	mapCarreDense = baseMaps + "carre.mapgr";
    	
    	// Define paths
        pathINSABikiniCanalName = basePaths + "path_fr31_insa_bikini_canal.path";
        
        // Get Graphs
        graphHauteGaronne = initMyMap(mapHauteGaronne);
        graphCarreDense = initMyMap(mapCarreDense);
        
        // Get Paths
        pathINSABikiniCanal = initMyPath(pathINSABikiniCanalName, graphHauteGaronne);
        
        // Single Node Test for Algorithms
        singleNodeTest();
        
        // Several Nodes Test for Algorithms
        severalNodesTest();
        
        // Infeasible Path Test for Algorithms
        infeasiblePathTest();
        
        // Big Path Test for Algorithms
        bigPathTest();
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
    
    /*
     * ------------------
     *  Single Node Test
     * ------------------
     */
    
    public static void singleNodeTest()
    {
        Node singleNode = graphCarreDense.getNodes().get(new Random().nextInt(graphCarreDense.getNodes().size()));
        
        //Define new Path
        singleNodePath = new Path(graphCarreDense, singleNode);
        
        //Define Paths Data
        dataCarreDenseLengthMode = new ShortestPathData(graphCarreDense, singleNode, singleNode, ArcInspectorFactory.getAllFilters().get(0));
        dataCarreDenseTimeMode = new ShortestPathData(graphCarreDense, singleNode, singleNode, ArcInspectorFactory.getAllFilters().get(2));
        
        //Dijkstra Length Mode
        dijkstraAlgo = new DijkstraAlgorithm(dataCarreDenseLengthMode);
        singleNodeDijkstraLengthMode = dijkstraAlgo.doRun();
        
        //Dijkstra Time Mode
        dijkstraAlgo = new DijkstraAlgorithm(dataCarreDenseTimeMode);
        singleNodeDijkstraTimeMode = dijkstraAlgo.doRun();
        
        //A* Length Mode
        AStarAlgo = new AStarAlgorithm(dataCarreDenseLengthMode);
        singleNodeAStarLengthMode = AStarAlgo.doRun();
        
        //A* Time Mode
        AStarAlgo = new AStarAlgorithm(dataCarreDenseTimeMode);
        singleNodeAStarTimeMode = AStarAlgo.doRun();
    }
    
    // Dijkstra Algorithm
    @Test
    public void singleNodePathDijkstraAlgorithmLengthMode()
    {
		assertEquals(0, singleNodePath.getOrigin().compareTo(singleNodeDijkstraLengthMode.getPath().getOrigin()));
		assertTrue(Math.abs(singleNodePath.getLength() - singleNodeDijkstraLengthMode.getPath().getLength()) < 0.01);
		assertTrue(singleNodeDijkstraLengthMode.getPath().isValid());
    }
    
    @Test
    public void singleNodePathDijkstraAlgorithmTimeMode() {
		assertEquals(0, singleNodePath.getOrigin().compareTo(singleNodeDijkstraTimeMode.getPath().getOrigin()));
		assertTrue(Math.abs(singleNodePath.getMinimumTravelTime() - singleNodeDijkstraTimeMode.getPath().getMinimumTravelTime()) < 0.01);
		assertTrue(singleNodeDijkstraTimeMode.getPath().isValid());
    }
    
    // A* Algorithm
    @Test
    public void singleNodePathAStarAlgorithmLengthMode() {
		assertEquals(0, singleNodePath.getOrigin().compareTo(singleNodeAStarLengthMode.getPath().getOrigin()));
		assertTrue(Math.abs(singleNodePath.getLength() - singleNodeAStarLengthMode.getPath().getLength()) < 0.01);
		assertTrue(singleNodeAStarLengthMode.getPath().isValid());
    }
    
    @Test
    public void singleNodePathAStarAlgorithmTimeMode() {
		assertEquals(0, singleNodePath.getOrigin().compareTo(singleNodeAStarTimeMode.getPath().getOrigin()));
		assertTrue(Math.abs(singleNodePath.getMinimumTravelTime() - singleNodeAStarTimeMode.getPath().getMinimumTravelTime()) < 0.01);
		assertTrue(singleNodeAStarTimeMode.getPath().isValid());
    }
    
    /*
     * --------------------
     *  Several Nodes Test
     * --------------------
     */
    
    // Dijkstra Algorithm
    @Test
    public void randomPathDijkstraAlgorithmLengthMode() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomDijkstraLengthMode[i].getPath().getOrigin().compareTo(randomBellmanFordLengthMode[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomDijkstraLengthMode[i].getPath().getLength() - randomBellmanFordLengthMode[i].getPath().getLength()) < 0.01);
			assertTrue(randomDijkstraLengthMode[i].getPath().isValid());
    	}
    }
    
    @Test
    public void randomPathDijkstraAlgorithmLengthModeOnlyCars() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomDijkstraLengthModeOnlyCars[i].getPath().getOrigin().compareTo(randomBellmanFordLengthModeOnlyCars[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomDijkstraLengthModeOnlyCars[i].getPath().getLength() - randomBellmanFordLengthModeOnlyCars[i].getPath().getLength()) < 0.01);
			assertTrue(randomDijkstraLengthModeOnlyCars[i].getPath().isValid());
    	}
    }
    
    @Test
    public void randomPathDijkstraAlgorithmTimeMode() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomDijkstraTimeMode[i].getPath().getOrigin().compareTo(randomBellmanFordTimeMode[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomDijkstraTimeMode[i].getPath().getMinimumTravelTime() - randomBellmanFordTimeMode[i].getPath().getMinimumTravelTime()) < 0.01);
			assertTrue(randomDijkstraTimeMode[i].getPath().isValid());
    	}
    }
    
    @Test
    public void randomPathDijkstraAlgorithmTimeModeOnlyCars() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomDijkstraTimeModeOnlyCars[i].getPath().getOrigin().compareTo(randomBellmanFordTimeModeOnlyCars[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomDijkstraTimeModeOnlyCars[i].getPath().getMinimumTravelTime() - randomBellmanFordTimeModeOnlyCars[i].getPath().getMinimumTravelTime()) < 0.01);
			assertTrue(randomDijkstraTimeModeOnlyCars[i].getPath().isValid());
    	}
    }
    
    @Test
    public void randomPathDijkstraAlgorithmTimeModePedestrian() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomDijkstraTimeModePedestrian[i].getPath().getOrigin().compareTo(randomBellmanFordTimeModePedestrian[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomDijkstraTimeModePedestrian[i].getPath().getMinimumTravelTime() - randomBellmanFordTimeModePedestrian[i].getPath().getMinimumTravelTime()) < 0.01);
			assertTrue(randomDijkstraTimeModePedestrian[i].getPath().isValid());
    	}
    }
    
    // A* Algorithm
    @Test
    public void randomPathAStarAlgorithmLengthMode() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomAStarLengthMode[i].getPath().getOrigin().compareTo(randomBellmanFordLengthMode[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomAStarLengthMode[i].getPath().getLength() - randomBellmanFordLengthMode[i].getPath().getLength()) < 0.01);
			assertTrue(randomAStarLengthMode[i].getPath().isValid());
    	}
    }
    
    @Test
    public void randomPathAStarAlgorithmLengthModeOnlyCars() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomAStarLengthModeOnlyCars[i].getPath().getOrigin().compareTo(randomBellmanFordLengthModeOnlyCars[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomAStarLengthModeOnlyCars[i].getPath().getLength() - randomBellmanFordLengthModeOnlyCars[i].getPath().getLength()) < 0.01);
			assertTrue(randomAStarLengthModeOnlyCars[i].getPath().isValid());
    	}
    }
    
    @Test
    public void randomPathAStarAlgorithmTimeMode() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomAStarTimeMode[i].getPath().getOrigin().compareTo(randomBellmanFordTimeMode[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomAStarTimeMode[i].getPath().getMinimumTravelTime() - randomBellmanFordTimeMode[i].getPath().getMinimumTravelTime()) < 0.01);
			assertTrue(randomAStarTimeMode[i].getPath().isValid());
    	}
    }
    
    @Test
    public void randomPathAStarAlgorithmTimeModeOnlyCars() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomAStarTimeModeOnlyCars[i].getPath().getOrigin().compareTo(randomBellmanFordTimeModeOnlyCars[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomAStarTimeModeOnlyCars[i].getPath().getMinimumTravelTime() - randomBellmanFordTimeModeOnlyCars[i].getPath().getMinimumTravelTime()) < 0.01);
			assertTrue(randomAStarTimeModeOnlyCars[i].getPath().isValid());
    	}
    }
    
    @Test
    public void randomPathAStarAlgorithmTimeModePedestrian() {
    	for(int i = 0; i < 50; i++)
    	{
			assertEquals(0, randomAStarTimeModePedestrian[i].getPath().getOrigin().compareTo(randomBellmanFordTimeModePedestrian[i].getPath().getOrigin()));
			assertTrue(Math.abs(randomAStarTimeModePedestrian[i].getPath().getMinimumTravelTime() - randomBellmanFordTimeModePedestrian[i].getPath().getMinimumTravelTime()) < 0.01);
			assertTrue(randomAStarTimeModePedestrian[i].getPath().isValid());
    	}
    }
    
    public static void severalNodesTest()
    {
    	randomDijkstraLengthMode = new ShortestPathSolution[50];
        randomDijkstraLengthModeOnlyCars = new ShortestPathSolution[50];
        randomDijkstraTimeMode = new ShortestPathSolution[50];
        randomDijkstraTimeModeOnlyCars = new ShortestPathSolution[50];
        randomDijkstraTimeModePedestrian = new ShortestPathSolution[50];
        
        randomBellmanFordLengthMode = new ShortestPathSolution[50];
        randomBellmanFordLengthModeOnlyCars = new ShortestPathSolution[50];
        randomBellmanFordTimeMode = new ShortestPathSolution[50];
        randomBellmanFordTimeModeOnlyCars = new ShortestPathSolution[50];
        randomBellmanFordTimeModePedestrian = new ShortestPathSolution[50];
        
        randomAStarLengthMode = new ShortestPathSolution[50];
        randomAStarLengthModeOnlyCars = new ShortestPathSolution[50];
        randomAStarTimeMode = new ShortestPathSolution[50];
        randomAStarTimeModeOnlyCars = new ShortestPathSolution[50];
        randomAStarTimeModePedestrian = new ShortestPathSolution[50];
        
        for(int i = 0; i < 50; i++)
        {
        	Node origin = graphCarreDense.getNodes().get(new Random().nextInt(graphCarreDense.getNodes().size()));
        	
        	//Fin a destination different from origin
        	Node destination = origin;
        	while(destination.equals(origin))
        		destination = graphCarreDense.getNodes().get(new Random().nextInt(graphCarreDense.getNodes().size()));
        	
        	//Define data for CarreDense's map and for different mode
        	randomDataCarreDenseLengthMode = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        	randomDataCarreDenseLengthModeOnlyCars = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(1));
        	randomDataCarreDenseTimeMode = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(2));
        	randomDataCarreDenseTimeModeOnlyCars = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(3));
        	randomDataCarreDenseTimeModePedestrian = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(4));
        	
            //Dijkstra Length Mode
            dijkstraAlgo = new DijkstraAlgorithm(randomDataCarreDenseLengthMode);
            randomDijkstraLengthMode[i] = dijkstraAlgo.doRun();
            
            //Dijkstra Length Mode Only Cars
            dijkstraAlgo = new DijkstraAlgorithm(randomDataCarreDenseLengthModeOnlyCars);
            randomDijkstraLengthModeOnlyCars[i] = dijkstraAlgo.doRun();
            
            //Dijkstra Time Mode
            dijkstraAlgo = new DijkstraAlgorithm(randomDataCarreDenseTimeMode);
            randomDijkstraTimeMode[i] = dijkstraAlgo.doRun();
            
            //Dijkstra Time Mode Only Cars
            dijkstraAlgo = new DijkstraAlgorithm(randomDataCarreDenseTimeModeOnlyCars);
            randomDijkstraTimeModeOnlyCars[i] = dijkstraAlgo.doRun();
            
            //Dijkstra Time Mode Pedestrian
            dijkstraAlgo = new DijkstraAlgorithm(randomDataCarreDenseTimeModePedestrian);
            randomDijkstraTimeModePedestrian[i] = dijkstraAlgo.doRun();
            
            
            
            //BellmanFord Length Mode
			bellmanFordAlgo = new BellmanFordAlgorithm(randomDataCarreDenseLengthMode) ; 
			randomBellmanFordLengthMode[i] = bellmanFordAlgo.doRun();
			
			//BellmanFord Length Mode Only Cars
			bellmanFordAlgo = new BellmanFordAlgorithm(randomDataCarreDenseLengthModeOnlyCars) ; 
			randomBellmanFordLengthModeOnlyCars[i] = bellmanFordAlgo.doRun();
			
			//BellmanFord Time Mode
			bellmanFordAlgo = new BellmanFordAlgorithm(randomDataCarreDenseTimeMode) ; 
			randomBellmanFordTimeMode[i] = bellmanFordAlgo.doRun();
			
			//BellmanFord Time Mode Only Cars
			bellmanFordAlgo = new BellmanFordAlgorithm(randomDataCarreDenseTimeModeOnlyCars) ; 
			randomBellmanFordTimeModeOnlyCars[i] = bellmanFordAlgo.doRun();
			
			//BellmanFord Time Mode Pedestrian
			bellmanFordAlgo = new BellmanFordAlgorithm(randomDataCarreDenseTimeModePedestrian) ; 
			randomBellmanFordTimeModePedestrian[i] = bellmanFordAlgo.doRun();
			
			
			
            //A* Length Mode
			AStarAlgo = new AStarAlgorithm(randomDataCarreDenseLengthMode) ; 
			randomAStarLengthMode[i] = AStarAlgo.doRun();
			
			//A* Length Mode Only Cars
			AStarAlgo = new AStarAlgorithm(randomDataCarreDenseLengthModeOnlyCars) ; 
			randomAStarLengthModeOnlyCars[i] = AStarAlgo.doRun();
			
			//A* Time Mode
			AStarAlgo = new AStarAlgorithm(randomDataCarreDenseTimeMode) ; 
			randomAStarTimeMode[i] = AStarAlgo.doRun();
			
			//A* Time Mode Only Cars
			AStarAlgo = new AStarAlgorithm(randomDataCarreDenseTimeModeOnlyCars) ; 
			randomAStarTimeModeOnlyCars[i] = AStarAlgo.doRun();
			
			//A* Time Mode Pedestrian
			AStarAlgo = new AStarAlgorithm(randomDataCarreDenseTimeModePedestrian) ; 
			randomAStarTimeModePedestrian[i] = AStarAlgo.doRun();
        }
    }
    
    /*
     * ----------------------
     *  Infeasible Path Test
     * ----------------------
     */
    
    public static void infeasiblePathTest()
    {
        int origin = 120349;
        int destination = 120351;
		dataBikiniInfeasibleLengthMode = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(origin), graphHauteGaronne.get(destination), ArcInspectorFactory.getAllFilters().get(0)) ; 
		
		//Dijkstra Bikini Infeasible Length Mode
		dijkstraAlgo = new DijkstraAlgorithm(dataBikiniInfeasibleLengthMode); 
		dijkstraBikiniInfeasibleLengthMode = dijkstraAlgo.doRun(); 
		
		//A* Bikini Infeasible Length Mode
		AStarAlgo = new AStarAlgorithm(dataBikiniInfeasibleLengthMode); 
		AStarBikiniInfeasibleLengthMode = AStarAlgo.doRun(); 
    }
    
    //Dijkstra Algorithm
	@Test
	public void infeasiblePathDijkstraAlgorithmLengthMode() {
		assertEquals(dijkstraBikiniInfeasibleLengthMode.getStatus(), AbstractSolution.Status.INFEASIBLE);
	}
	
	//A* Algorithm
	@Test
	public void infeasiblePathAStarAlgorithmLengthMode() {
		assertEquals(AStarBikiniInfeasibleLengthMode.getStatus(), AbstractSolution.Status.INFEASIBLE);
	}
	
    /*
     * --------------
     *  Big Path Test
     * --------------
     */
	
	public static void bigPathTest()
	{
        dataBikiniLengthMode = new ShortestPathData(graphHauteGaronne, pathINSABikiniCanal.getOrigin(), pathINSABikiniCanal.getDestination(), ArcInspectorFactory.getAllFilters().get(0));
        
        //Dijkstra Bikini Length Mode (0)
        dijkstraAlgo = new DijkstraAlgorithm(dataBikiniLengthMode);
        dijkstraBikiniLengthMode = dijkstraAlgo.doRun();
        
        //A* Bikini Length Mode (0)
        AStarAlgo = new AStarAlgorithm(dataBikiniLengthMode);
        AStarBikiniLengthMode = AStarAlgo.doRun();
	}
	
	//Dijkstra Algorithm
    @Test
    public void bigPathDijkstraAlgorithmLengthMode() {
		assertEquals(0, dijkstraBikiniLengthMode.getPath().getOrigin().compareTo(pathINSABikiniCanal.getOrigin()));
		assertTrue(Math.abs(dijkstraBikiniLengthMode.getPath().getLength() - pathINSABikiniCanal.getLength()) < 0.01);
		assertTrue(dijkstraBikiniLengthMode.getPath().isValid());
    }
    
    //A* Algorithm
    @Test
    public void bigPathAStarAlgorithmLengthMode() {
		assertEquals(0, AStarBikiniLengthMode.getPath().getOrigin().compareTo(pathINSABikiniCanal.getOrigin()));
		assertTrue(Math.abs(AStarBikiniLengthMode.getPath().getLength() - pathINSABikiniCanal.getLength()) < 0.01);
		assertTrue(AStarBikiniLengthMode.getPath().isValid());
    }
    
	
}
