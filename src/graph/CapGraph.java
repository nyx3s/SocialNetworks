/**
 * @author nyx
 * @date 22/9/2024
 * @version 0.0.0
 */

package graph;
import java.util.*;

import util.GraphLoader;

public class CapGraph implements Graph {
    
	// repsent the graph using adjecncy list using hashmap and list of nighbers
	Map<Integer,HashSet<Integer>> graph;
	
	public CapGraph()
	{
		graph = new HashMap<Integer,HashSet<Integer>>();
	}
	public int size() 
	{
		return this.graph.size();
	}
    /* 
     * @parm num is vertix
     * add a vertix if not already there
     * print if u add duplecte vertix
     */
    @Override
    public void addVertex(int num) {
    	if(!graph.containsKey(num))
    	{
    		graph.put(num, new HashSet<Integer>());
    	}
    }

    /* ()
     * @pram from int vertix to int vertix
     * if form is in the graph 
     * print if is not in the graph
     */
    @Override
    public void addEdge(int from, int to) {
    	if(graph.containsKey(from) && graph.containsKey(to))
    	{
    		graph.get(from).add(to);
    	}
    	
    }
    
    public boolean isEmpty()
    {
    	return graph.isEmpty();
    }
    
    /* ()
     * @parmter Node that id of user 
     * @return new graph that is sub graph from orgnial
     * that repesent the frindes of given user and the frindchep
     * between them 
     */
    @Override
    public Graph getEgonet(int center) 
    {
    	Graph subGraph = new CapGraph();
    	Set<Integer> viseted = new HashSet<Integer>();
    	Queue<Integer> q = new LinkedList<Integer>();
    	    
    	if(!graph.containsKey(center)) 
    	{
    		return subGraph;
    	}
    	
    	q.add(center);
    	subGraph.addVertex(center);
    	
    	while(!q.isEmpty())
    	{
    		int crrNode = q.poll();
    		viseted.add(crrNode);
    		//System.out.println(crrNode+" marked as visted");
    		Set<Integer> crrNieSet = graph.get(crrNode);
    		
    		for(int crrNie : crrNieSet)
    		{
    			if(!viseted.contains(crrNie) 
    					&& graph.get(center).contains(crrNie))
    			{
    				//System.out.println(crrNode+" \n"+crrNie);
    				
    				subGraph.addVertex(crrNie);			
    				q.add(crrNie);
    			}
    			subGraph.addEdge(crrNode, crrNie);
    		}
    	}
           return subGraph;
    }

    
    /*
     * @return new graph that is the transpose of this graph
     */
    public Graph getTranspose() 
    {
    	Graph trans = new CapGraph();
    	
    	for(int crrNode : graph.keySet())
    	{
    		Set<Integer> nibSet = graph.get(crrNode);
    		trans.addVertex(crrNode);
    		
    		for(int crrNie : nibSet) 
    		{		
    			trans.addVertex(crrNie);
    			trans.addEdge(crrNie, crrNode);
    		}
    	}
    	return trans;
    }
    
    
    /* ()
     * @see graph.Graph#getSCCs()
     */
    @Override
    public List <Graph> getSCCs() {
    	//List<Graph> res = new LinkedList<Graph>();
    	
    	Stack<Integer> finsh = this.dfs();
    	//System.out.println("finsh stack "+finsh);
    	Graph trans = this.getTranspose();
    	//((CapGraph)trans).print();
    	return ((CapGraph)trans).computeSccDFS(finsh);
        
    }
    
    
    private List<Graph> computeSccDFS(Stack<Integer> s)
    {
    	
    	Stack<Integer> finshed = new Stack<Integer>();
    	List<Graph> res = new LinkedList<Graph>();
    	Set<Integer> viseted = new HashSet<Integer>();
    	
    	while(!s.isEmpty()) 
    	{
    		int crrNode = s.pop();
    		CapGraph g = new CapGraph();
    		
    		if(!viseted.contains(crrNode))
    		{
    			dfs_vis(finshed, viseted, crrNode);
    		}
    		
    		//System.out.println("crr Stack "+finshed);
    		//System.out.println("crr sccs "+s);
    		
    		while(!finshed.isEmpty())
    		{
    			int crr = finshed.pop();
    			g.addVertex(crr);
				
    		}
    		if(!g.isEmpty())
    		{
    			res.add(g);
    		} 
    	}
    	return res;
    }
	
    
    public Stack<Integer> dfs() 
    {
    	Stack<Integer> finshed = new Stack<Integer>();
    	Set<Integer> viseted = new HashSet<Integer>();
    	
    	for(int crrNode : graph.keySet()) 
    	{
    		if(!viseted.contains(crrNode)) 
    		{
    			dfs_vis(finshed, viseted, crrNode);
    		}
    		
    	}
    	return finshed ;
    }
    
    
    private void dfs_vis(Stack<Integer> finshed, 
    		Set<Integer> viseted, int crrNode)
    {
    	viseted.add(crrNode);
    	Set<Integer> crrNieSet = graph.get(crrNode);
    	
    	for(int crrNie : crrNieSet) 
    	{
    		if(!viseted.contains(crrNie))
    		{
    			dfs_vis(finshed,viseted,crrNie);
    		}
    		
    	}
    	finshed.push(crrNode);
    }
    
    /*
    public Map<Integer,Set<Integer>> sugFreinds(int userId)
    {
    	Map<Integer,Set<Integer>> re = new HashMap<Integer, Set<Integer>>();
    	List<Integer> friends = new ArrayList(graph.get(userId));
    	
    	
    	for(int i = 0; i < friends.size(); i++)
    	{
    		for(int j = i+1; j < friends.size(); j++)
    		{
    			int fristF = friends.get(i);
    			Set<Integer> crrFFreinds = graph.get(friends.get(j));
    		
    			//System.out.println(fristF+" "+friends.get(j) +" "+crrFFreinds);
    			if(!crrFFreinds.contains(fristF))
    			{
    				if(!re.containsKey(fristF))
    				{
    					Set<Integer> crr = new HashSet<Integer>();
    					crr.add(friends.get(j));
    					re.put(fristF, crr);
    				}
    				else 
    				{
    					re.get(fristF).add(friends.get(j));
    				}
    				
    			}
    				fristF = friends.get(i);
    			}
    		

    	}
    	return re;
    }
    */
    
    public Map<Integer,Set<Integer>> sugFreindsV1(int userId)
    {
    	Map<Integer,Set<Integer>> re = new HashMap<Integer,Set<Integer>>();
    	List<Integer> friends = new ArrayList<Integer>(graph.get(userId));
    	int frist = 0;
    	int Sec = 1;
    	
    	while(Sec < friends.size() && frist < Sec)
    	{
    		Set<Integer> crr = graph.get(friends.get(frist));
    		if(!crr.contains(friends.get(Sec))) 
    		{
    			addSuggestion(re, friends.get(frist), friends.get(Sec));
    			addSuggestion(re, friends.get(Sec), friends.get(frist));
    		}
    		if(Sec == friends.size() - 1)
    		{
    			frist ++;
    			Sec = frist + 1;
    			continue;
    		}
    		Sec ++;
    	}
    	
    	return re;
    }
    
    
    private void addSuggestion(Map<Integer, Set<Integer>> re, int friend, int suggestedFriend) {
        // Add the suggested friend to the friend's suggestion set
        re.computeIfAbsent(friend, k -> new HashSet<>()).add(suggestedFriend);
    }
    
    /* ()
     * @return new map object that repsent the graph
     */
    @Override
    public HashMap <Integer, HashSet <Integer>> exportGraph() {
        return new HashMap <Integer, HashSet <Integer>>(graph);
    }

    /*
     * repsent the graph
     */
    public String toString()
    {
    	return graph.toString();
    }
    
    
    /*
     * print the graph 
     */
    public void print()
    {
    	for(int crr : graph.keySet())
    	{
    		System.out.println(crr+" :"+graph.get(crr));
    	}
    }
    
    public static void main(String [] args)
    {
    	String fileName = "/home/nyx/Documents/"
    			+ "Applied-Social-Network-Analysis-in-Java"
    			+ "/data/data/small_test_graph.txt";
    	Graph g = new CapGraph();
    	//GraphLoader load = new GraphLoader();
    	GraphLoader.loadGraph(g,fileName);
    	((CapGraph)g).print();
		/*
		 * Graph eg = g.getEgonet(5);
		 * System.out.println("_______________________________");
		 * ((CapGraph)eg).print(); Stack<Integer> re = ((CapGraph)g).dfs();
		 * System.out.println(re); System.out.println("______________________");
		 * System.out.println(((CapGraph)g).DFS(re));
		 */
		 System.out.println("_______________________");
//    	Graph trans = ((CapGraph)g).getTranspose();
//    	((CapGraph)trans).print();
//    	
//    	List<Graph> L = ((CapGraph)g).getSCCs();
//    	for(Graph crr : L) {
//    	System.out.println(crr);
//    	}
		 
		 for(int i = 1 ; i < 15; i++) {
			 System.out.println(i+" "+((CapGraph)g).sugFreindsV1(i));
		 }
//		 System.out.println("_______________________");
//		 for(int i = 1 ; i < 15; i++) {
//				System.out.println(i+" "+((CapGraph)g).sugFreinds(i));
//				 }
    }

}