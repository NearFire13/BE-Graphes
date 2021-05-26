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

public class AlgorithmTest {

    // Graphs use for tests
    private static Graph graphCarreDense, graphHauteGaronne;
    
    // Some strings directories or files
    private static String mapDirectory, pathDirectory, mapCarreDense, mapHauteGaronne, pathBikiniName;
    
    // Some maps to read
    private static GraphReader readerCarreDense, readerHauteGaronne;
    
    protected static ShortestPathData dataBikiniInfeasibleLengthMode, dataBikiniLengthMode, dataCarreDenseLengthMode, dataCarreDenseTimeMode, randomDataCarreDenseLengthMode, randomDataCarreDenseLengthModeOnlyCars, randomDataCarreDenseTimeMode, randomDataCarreDenseTimeModeOnlyCars, randomDataCarreDenseTimeModePedestrian;
    
    protected static ShortestPathSolution singleNodeDijkstraLengthMode, singleNodeDijkstraTimeMode, singleNodeAStarLengthMode, singleNodeAStarTimeMode, dijkstraBikiniLengthMode, AStarBikiniLengthMode, dijkstraBikiniInfeasibleLengthMode, AStarBikiniInfeasibleLengthMode;
    
    protected static ShortestPathSolution[] randomDijkstraLengthMode, randomDijkstraLengthModeOnlyCars, randomDijkstraTimeMode, randomDijkstraTimeModeOnlyCars, randomDijkstraTimeModePedestrian,
    										randomBellmanFordLengthMode, randomBellmanFordLengthModeOnlyCars, randomBellmanFordTimeMode, randomBellmanFordTimeModeOnlyCars, randomBellmanFordTimeModePedestrian,
    										randomAStarLengthMode, randomAStarLengthModeOnlyCars, randomAStarTimeMode, randomAStarTimeModeOnlyCars, randomAStarTimeModePedestrian;
    
    protected static DijkstraAlgorithm dijkstraAlgorithm;
    
    protected static BellmanFordAlgorithm bellmanFordAlgorithm;
    
    protected static AStarAlgorithm AStarAlgorithm;
    
    private static PathReader readerBikini;
    
    private static Path pathBikini, singleNodePath;

    @BeforeClass
    public static void initAll() throws IOException
    {
        mapDirectory = "C:/Users/benbe/OneDrive/Documents/BE-Graphes/Maps/";
        pathDirectory = "C:/Users/benbe/OneDrive/Documents/BE-Graphes/Paths/";
        
        
        //Get CarreDense's Graph
        mapCarreDense = mapDirectory + "carre.mapgr";
        readerCarreDense = new BinaryGraphReader(
        		new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarreDense))));
        graphCarreDense = readerCarreDense.read();
        
        //Get MidiPyrenees's Graph
        mapHauteGaronne = mapDirectory + "haute-garonne.mapgr";
        readerHauteGaronne = new BinaryGraphReader(
        		new DataInputStream(new BufferedInputStream(new FileInputStream(mapHauteGaronne))));
        graphHauteGaronne = readerHauteGaronne.read();
        
        //Get Bikini's Path
        pathBikiniName = pathDirectory + "path_fr31_insa_bikini_canal.path";
        readerBikini = new BinaryPathReader(
        		new DataInputStream(new BufferedInputStream(new FileInputStream(pathBikiniName))));
        pathBikini = readerBikini.readPath(graphHauteGaronne);
        
        /*
         * -----------------------------------------------
         * Single Node Test with Dijkstra and A* Algorithm
         * -----------------------------------------------
         */
        Node singleNode = graphCarreDense.getNodes().get(new Random().nextInt(graphCarreDense.getNodes().size()));
        singleNodePath = new Path(graphCarreDense, singleNode);
        dataCarreDenseLengthMode = new ShortestPathData(graphCarreDense, singleNode, singleNode, ArcInspectorFactory.getAllFilters().get(0));
        dataCarreDenseTimeMode = new ShortestPathData(graphCarreDense, singleNode, singleNode, ArcInspectorFactory.getAllFilters().get(2));
        
        //Dijkstra Length Mode (0)
        dijkstraAlgorithm = new DijkstraAlgorithm(dataCarreDenseLengthMode);
        singleNodeDijkstraLengthMode = dijkstraAlgorithm.doRun();
        
        //Dijkstra Time Mode (2)
        dijkstraAlgorithm = new DijkstraAlgorithm(dataCarreDenseTimeMode);
        singleNodeDijkstraTimeMode = dijkstraAlgorithm.doRun();
        
        
        //A* Length Mode (0)
        AStarAlgorithm = new AStarAlgorithm(dataCarreDenseLengthMode);
        singleNodeAStarLengthMode = AStarAlgorithm.doRun();
        
        //A* Time Mode (2)
        AStarAlgorithm = new AStarAlgorithm(dataCarreDenseTimeMode);
        singleNodeAStarTimeMode = AStarAlgorithm.doRun();
        

        /*
         * ------------------------------------------
         * Pair of Nodes Test with Dijkstra Algorithm
         * ------------------------------------------
         */
        
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
        	Node destination = origin;
        	while(destination.equals(origin))
        		destination = graphCarreDense.getNodes().get(new Random().nextInt(graphCarreDense.getNodes().size()));
        	
        	randomDataCarreDenseLengthMode = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        	randomDataCarreDenseLengthModeOnlyCars = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(1));
        	randomDataCarreDenseTimeMode = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(2));
        	randomDataCarreDenseTimeModeOnlyCars = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(3));
        	randomDataCarreDenseTimeModePedestrian = new ShortestPathData(graphCarreDense, origin, destination, ArcInspectorFactory.getAllFilters().get(4));
        	
            //Dijkstra Length Mode (0)
            dijkstraAlgorithm = new DijkstraAlgorithm(randomDataCarreDenseLengthMode);
            randomDijkstraLengthMode[i] = dijkstraAlgorithm.doRun();
            
            //Dijkstra Length Mode Only Cars (1)
            dijkstraAlgorithm = new DijkstraAlgorithm(randomDataCarreDenseLengthModeOnlyCars);
            randomDijkstraLengthModeOnlyCars[i] = dijkstraAlgorithm.doRun();
            
            //Dijkstra Time Mode (2)
            dijkstraAlgorithm = new DijkstraAlgorithm(randomDataCarreDenseTimeMode);
            randomDijkstraTimeMode[i] = dijkstraAlgorithm.doRun();
            
            //Dijkstra Time Mode Only Cars (3)
            dijkstraAlgorithm = new DijkstraAlgorithm(randomDataCarreDenseTimeModeOnlyCars);
            randomDijkstraTimeModeOnlyCars[i] = dijkstraAlgorithm.doRun();
            
            //Dijkstra Time Mode Pedestrian (4)
            dijkstraAlgorithm = new DijkstraAlgorithm(randomDataCarreDenseTimeModePedestrian);
            randomDijkstraTimeModePedestrian[i] = dijkstraAlgorithm.doRun();
            
            
            
            //BellmanFord Length Mode (0)
			bellmanFordAlgorithm = new BellmanFordAlgorithm(randomDataCarreDenseLengthMode) ; 
			randomBellmanFordLengthMode[i] = bellmanFordAlgorithm.doRun();
			
			//BellmanFord Length Mode Only Cars (1)
			bellmanFordAlgorithm = new BellmanFordAlgorithm(randomDataCarreDenseLengthModeOnlyCars) ; 
			randomBellmanFordLengthModeOnlyCars[i] = bellmanFordAlgorithm.doRun();
			
			//BellmanFord Time Mode (2)
			bellmanFordAlgorithm = new BellmanFordAlgorithm(randomDataCarreDenseTimeMode) ; 
			randomBellmanFordTimeMode[i] = bellmanFordAlgorithm.doRun();
			
			//BellmanFord Time Mode Only Cars (3)
			bellmanFordAlgorithm = new BellmanFordAlgorithm(randomDataCarreDenseTimeModeOnlyCars) ; 
			randomBellmanFordTimeModeOnlyCars[i] = bellmanFordAlgorithm.doRun();
			
			//BellmanFord Time Mode Pedestrian (4)
			bellmanFordAlgorithm = new BellmanFordAlgorithm(randomDataCarreDenseTimeModePedestrian) ; 
			randomBellmanFordTimeModePedestrian[i] = bellmanFordAlgorithm.doRun();
			
			
			
            //A* Length Mode (0)
			AStarAlgorithm = new AStarAlgorithm(randomDataCarreDenseLengthMode) ; 
			randomAStarLengthMode[i] = AStarAlgorithm.doRun();
			
			//A* Length Mode Only Cars (1)
			AStarAlgorithm = new AStarAlgorithm(randomDataCarreDenseLengthModeOnlyCars) ; 
			randomAStarLengthModeOnlyCars[i] = AStarAlgorithm.doRun();
			
			//A* Time Mode (2)
			AStarAlgorithm = new AStarAlgorithm(randomDataCarreDenseTimeMode) ; 
			randomAStarTimeMode[i] = AStarAlgorithm.doRun();
			
			//A* Time Mode Only Cars (3)
			AStarAlgorithm = new AStarAlgorithm(randomDataCarreDenseTimeModeOnlyCars) ; 
			randomAStarTimeModeOnlyCars[i] = AStarAlgorithm.doRun();
			
			//A* Time Mode Pedestrian (4)
			AStarAlgorithm = new AStarAlgorithm(randomDataCarreDenseTimeModePedestrian) ; 
			randomAStarTimeModePedestrian[i] = AStarAlgorithm.doRun();
        }
        
        /*
         * -------------------------------------
         * Infeasible Path Test with Dijkstra Algorithm
         * -------------------------------------
         */ 
        int origin = 120349;
        int destination = 120351;
		dataBikiniInfeasibleLengthMode = new ShortestPathData(graphHauteGaronne, graphHauteGaronne.get(origin), graphHauteGaronne.get(destination), ArcInspectorFactory.getAllFilters().get(0)) ; 
		
		//Dijkstra Bikini Infeasible Length Mode (0)
		dijkstraAlgorithm = new DijkstraAlgorithm(dataBikiniInfeasibleLengthMode); 
		dijkstraBikiniInfeasibleLengthMode = dijkstraAlgorithm.doRun(); 
		
		//A* Bikini Infeasible Length Mode (0)
		AStarAlgorithm = new AStarAlgorithm(dataBikiniInfeasibleLengthMode); 
		AStarBikiniInfeasibleLengthMode = AStarAlgorithm.doRun(); 
        
        /*
         * -------------------------------------
         * Big Path Test with Dijkstra Algorithm
         * -------------------------------------
         */
        dataBikiniLengthMode = new ShortestPathData(graphHauteGaronne, pathBikini.getOrigin(), pathBikini.getDestination(), ArcInspectorFactory.getAllFilters().get(0));
        
        //Dijkstra Bikini Length Mode (0)
        dijkstraAlgorithm = new DijkstraAlgorithm(dataBikiniLengthMode);
        dijkstraBikiniLengthMode = dijkstraAlgorithm.doRun();
        
        //A* Bikini Length Mode (0)
        AStarAlgorithm = new AStarAlgorithm(dataBikiniLengthMode);
        AStarBikiniLengthMode = AStarAlgorithm.doRun();
        

    }
    
    /*
     * -----------------------------------------------
     * Single Node Test with Dijkstra and A* Algorithm
     * -----------------------------------------------
     */
    
    // Dijkstra Algorithm
    @Test
    public void singleNodePathDijkstraAlgorithmLengthMode() {
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
     * ----------------------------------------------------
     * Pair of Nodes Test with Dijkstra and AStar Algorithm
     * ----------------------------------------------------
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
    
    /*
     * ---------------------------------------------------
     * Infeasible Path Test with Dijkstra and A* Algorithm
     * ---------------------------------------------------
     */
    
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
     * -------------------------------------
     * Big Path Test with Dijkstra and A* Algorithm
     * -------------------------------------
     */
	
	//Dijkstra Algorithm
    @Test
    public void bigPathDijkstraAlgorithmLengthMode() {
		assertEquals(0, dijkstraBikiniLengthMode.getPath().getOrigin().compareTo(pathBikini.getOrigin()));
		assertTrue(Math.abs(dijkstraBikiniLengthMode.getPath().getLength() - pathBikini.getLength()) < 0.01);
		assertTrue(dijkstraBikiniLengthMode.getPath().isValid());
    }
    
    //A* Algorithm
    @Test
    public void bigPathAStarAlgorithmLengthMode() {
		assertEquals(0, AStarBikiniLengthMode.getPath().getOrigin().compareTo(pathBikini.getOrigin()));
		assertTrue(Math.abs(AStarBikiniLengthMode.getPath().getLength() - pathBikini.getLength()) < 0.01);
		assertTrue(AStarBikiniLengthMode.getPath().isValid());
    }
}
