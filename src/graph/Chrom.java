package graph;

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
public class Chrom {

	int [] genes; // array of genes
	CapGraph g ; // graph to repsent the genes length
	int length ; // length of gene
	Random random; // random object used for random acess to genes array
	
	public Chrom(Graph g) {
		// get the max value for that graph bc we need to create
		// a chrom of that length
		int max = Collections.max(((CapGraph)g).graph.keySet());
        int chromLength = max + 1;
		this.genes = new int[chromLength];
		this.length = genes.length;
		random = new Random();
		this.g = (CapGraph)g;
	}
	/*
	 * return string repsention of Chrom object
	 */
	public String toString() 
	{
		StringBuilder s = new StringBuilder();
		for(int g : genes) 
		{
			s.append(g);
		}
		return s.toString();
	}
	/*
	 * return if tow Chrom are equals
	 */
	public boolean equals(Chrom other) 
	{
		return this.genes.equals(other.genes);
	}
	/*
	 * 
	 * Culc the fitnes value of that chrom object 
	 * depend of the valid cirtera
	 * @return fitnes value high value is consederd best
	 */
	public double fitness() 
	{
		// set of solution vertix
		Set<Integer> sol = new HashSet<Integer>();
		// set of how many the solution domnating 
		Set<Integer> domenate = new HashSet<Integer>();
		// loop throw genes
		for(int i = 0; i< genes.length; i++) {
			// if that genes is in the MDS 
			if(genes[i] == 1 && g.graph.containsKey(i)) 
			{
				//System.out.println("itretion "+i);
				// add it to the solution set
				sol.add(i);
				// add it to MDS set bc etch vertix domnating itself
				domenate.add(i);
				// get the nibgbores set
				Set<Integer> nib = g.graph.get(i);
				//System.out.println(nib+" i ="+i);
				// loop for etch nib
				for(int vertix : nib)
				{
					// if tha gene of that vertix is equals to 0 
					// add it to the domenating set
					if(genes[vertix] == 0) 
					{
						//System.out.println(vertix+" is domnating by "+ i);
						//sol.add(vertix);
						domenate.add(vertix);
					}
//					else 
//					{
//						System.out.println(vertix +" is alrady in  solotion "+i);
//					}
				}
			}
			// that means the crr solution set is domnating all vertix in 
			// that graph
			if(domenate.size() == g.graph.keySet().size()) 
			{
				//System.out.println("we have solotion"+g.graph.size());
				// the fit score is culc as follow
				// 
				// Ex: g is graph of size 5 
				
				// if a geven solotion call it x domnating 5 vertix
				// but the solution set contains 5 vertix 
				// the Fitnes score is 0 low is posibole  
				double fit = domenate.size() - sol.size();
				// constant Factore of 10 bc 0 Fitness solotion
				// is valid solution but the Fit is low is posibole
				// we add this constant to high the channse of selected
				fit += 10;
				// turn the Fit into expontial function fo 2
				return Math.pow(fit, 2);
			}
			
		}
		//System.out.println(domenate.size()+" domenate");
		//System.out.println("Solotion Set = "+sol);
		// if we reach this point that mean the currnet
		// solution is not covering all the graph
		// we culc the fitnes value as follow
		// div is the deffrent of how many vertix is connot be domenate 
		// by this solution that mean we get high value regarsless
		// of is valid or not "side Note" High in this context mean high it can reach
		// is one and cannot be this value bc we catch it above and asign expontial 
		// Fitness that help determin the good of non Valid solotion
		double div = g.graph.size() - domenate.size();
		return (1 / div);
	}
	
	
	
	
	/*
	 * implement one point crossover
	 * @return new Chrom object
	 */
	public Chrom croseOver(Chrom other)
	{
		Chrom res = new Chrom(g);
		int pos0 = random.nextInt(this.length-1)+1; // Integer between 0 and crome length
		 
		for(int i = 0; i < pos0; i++) 
		{
			res.genes[i] = this.genes[i];
			//System.out.println("copy "+this.genes[i]);
		}
		
		for(int i = pos0; i< res.length; i++ ) 
		{
			res.genes[i] = other.genes[i];
			//System.out.println("copy "+other.genes[i]);
		}
		
		return res;
	}

	/*
	 * Giting a random index then Flip 
	 * that gene and do a Shuffle
	 */
	public void mutat() 
	{
		int index = random.nextInt(length);
		genes[index] = random.nextInt(2);
		shuffleArray(genes);
	}
	
	
	/*
	 *  Implementing Fisher-Yates Shuffle
	 */
	 static void shuffleArray(int[] ar)
	  {
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	    }
	 
}
