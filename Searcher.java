//Abdullah Arif
//Class to store common between DFS AND BFS based searches
public class Searcher {
	protected static void printSolution(Node solution, int accessed, int maxSize, int expanded){
		if(solution ==null){
        	System.out.println("Search failed :(\nGrid is insolvable or uses too much memory");
            System.out.println("Number of states tried:"+expanded); 
        }
        else{
        	System.out.println("Path of solution "); 
	        System.out.println(solution);
	        System.out.println("depth of solution: "+solution.getDepth());
	        System.out.println("Nodes accessed: "+accessed);
	        System.out.println("Max size of fringe is: "+maxSize);
	        System.out.println("Number of nodes expanded is: "+expanded);
        }
	}
}



