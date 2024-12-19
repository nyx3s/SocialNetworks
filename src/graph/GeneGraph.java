package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import util.GraphLoader;
/*
 * @auther nyx3s 
 * @Date 10 Des 2024
 * 
 */
public class GeneGraph extends CapGraph {

	//int [] crome;
	public GeneGraph() {
		//crome = new int[graph.size()];
	}
	
	/*
	 * input graph 1000 vertix but is realy not 1000 vertix 
	 * 783 verix
	 * we do a side test and we get a MDS of 566 vertix but its not near optimal
	 * still in the devolment and i hope we can get an optimal solotion
	 * 
	 */
	public static void main(String [] args)
    {
    	String fileName = "/home/nyx/Documents/"
    			+ "Applied-Social-Network-Analysis-in-Java"
    			+ "/data/data/facebook_1000.txt";
    	
    	GeneGraph g = new GeneGraph();
    	//GraphLoader load = new GraphLoader();
    	GraphLoader.loadGraph(g,fileName);
    	//g.print();
    	/*
    		// 			   0,1,2,3,4,5,6,7,8,9,10,11,12,13,14
        	int [] test = {0,1,0,0,1,0,0,1,0,1,0, 0, 1, 0, 0};
        	int [] test2 = {0,1,1,0,0,0,0,0,0,0,0, 0, 0, 0, 0};
        	int crrFit = g.fitness(test);
        	 */
    		
    	Population p = new Population(g, 100);
    	p.init();
    	p.print();
    	for(int i = 0; i < 500; i++) {
    		System.out.println("genertion "+i);
    		p.reGen();
    		p.print();
    		System.out.println("sub optimal sofar = "+p.bestSoFar );
    	}
    //System.out.println(g.size());
    System.out.println(p.cromes[p.bestIndex]);
    System.out.println(p.cromes[p.bestIndex].fitness());	
    	
    }
	    
}
