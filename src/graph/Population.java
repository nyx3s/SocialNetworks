package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
/*
 * @auther nyx3s 
 * @Date 10 Des 2024
 * 
 */
public class Population {

	Chrom [] cromes; // array of chromosome object 
	double [] fit; // array to store fitness value 
	Random random; // random object 
	int size; // size of the population
	Graph g; // graph that algorithms operate on 
	double bestSoFar ; // best fitness value across all population
	int bestIndex ; // best location index
	double mutatRate = 0.05; // how many chrom wanna to be mutated
	
	double [] relFit; // reltive fitness is following the role of 
	//(FIT of i / sum of all FITNESS)
	double [] cumulative_probability; // is suming the reltive fitness of 
	// (i0) , (i0+i1), (i1+i2) ... (in-2 + in-1)  
	
	
	public Population(Graph g , int size) {
		// TODO Auto-generated constructor stub
		cromes = new Chrom [size];
		fit = new double [size];
		random = new Random();
		this.g = g;
		this.size = size;
	}
	
	
	/*
	 * loop throw all Fitness and determine the
	 * best fit in this generation 
	 */
	public void culcFitneses() 
	{
		for(int i = 0; i < cromes.length; i++) 
		{
			// fitness method live inside Chrom obj
			fit[i] = cromes[i].fitness();
			if(fit[i] > bestSoFar) 
			{
				bestSoFar = fit[i];
				bestIndex = i;
			}
		}
	}
	
	
	/*
	 * print all population
	 */
	public void print() 
	{
		for(int i = 0; i < cromes.length; i++) 
		{
			System.out.println(i+1+" "+cromes[i]+" "+fit[i]);
		}
	}
	
	
	/*
	 * Initialize the population with random chrom object 
	 */
	public void init() 
	{
		// the first chrom is the known solution all the vertices is 1 
		// all is participating in MDS but we know for sure is not the optimal solution 
		Chrom bs = new Chrom(g);
    	//bs.genes = new int [] {0,1,0,0,1,0,0,1,0};
    	Arrays.fill(bs.genes, 1);
    	cromes[0] = bs;
        
        // for etch population individual 
    	for(int i = 1 ; i < size; i++) 
        {
        	Chrom crr = new Chrom(g);
        	cromes[i] = crr;
        	// for etch gene populated with random of 0 or 1 
        	for(int j = 0; j < crr.length; j++) 
        	{
        		crr.genes[j] = random.nextInt(2);     
        	}
        	//System.out.println("befor if ");
//        	if(cromes[i].fitness() < 0) 
//        	{
//        		//System.out.println("un valid sol "+ cromes[i].fitness());
//        		i --;
//        	}
        }
    	//then culc the population fitness
        this.culcFitneses();
        //fit[0] = 999;
	}
	
	
	/*
	 * Getting a random index then if it is inside the cumulative probability
	 * @return that chrom object
	 */
	public Chrom roulette_wheel_selection() 
	{
		double index = random.nextDouble();
		//System.out.println(index);
		
		for(int i = 0; i < size; i++) 
		{
			//System.out.println(cumulative_probability[i]);
			if(index <= cumulative_probability[i]) 
			{
				//System.out.println(fit[i]);
				return cromes[i];
			}
		}
		return null;
		
	}
	
	
	/*
	 * culc the Probability and relitive fitness 
	 * following the above formela in line [22,24]
	 */
	private void culcProp() 
	{
		double sum = Arrays.stream(fit).sum();
		
		relFit = new double [size];
		
		for(int i = 0; i < cromes.length; i++) 
		{
			double prop = fit[i] / sum;
			//System.out.println("expertion "+fit[i]+" "+" "+sum);
			//System.out.println("prop "+i+" = "+prop);
			//System.out.println(Math.abs((index - prop)) <= 0.1);
			 relFit[i] = prop;
		}
		//System.out.println(Arrays.stream(relFit).sum());
		cumulative_probability = new double[size];
		
		cumulative_probability[0] = relFit[0];
		for(int i = 1; i < size; i++) 
		{
			cumulative_probability[i] = cumulative_probability[i-1] + relFit[i];
			//System.out.println(cumulative_probability[i]);
		}
		
	}
	/*
	 * mutate the population depend on mutation rate
	 * 
	 */
	public void mutation() 
	{
		double howMany = (size * mutatRate);
		System.out.println(howMany);
		for(int i = 0; i< howMany; i++) 
		{
			//System.out.println(howMany+" "+cromes[i]+" "+i);
			int ran = random.nextInt(size);
			cromes[ran].mutat();
		}
	}
	/*
	 * 
	 * reGenrete a new Population of that size following
	 * the probability, pick tow parent then crosover 
	 * then mutate, then cluc fitness of all population 
	 * 
	 */
	public void reGen() 
	{
		
		this.culcProp();
		Chrom [] newPop = new Chrom[size];
		
		for(int i = 0; i< size; i++) 
		{
			Chrom p1 = this.roulette_wheel_selection();
			Chrom p2 = this.roulette_wheel_selection();
			//System.out.println(p1+" make it with "+p2);
//			if(p1.equals(p2)) 
//			{
//				i--;
//				continue;
//			}
			newPop[i] = p1.croseOver(p2);
		}
		cromes = newPop;
		
		mutation();
		this.culcFitneses();
	}
	
}
