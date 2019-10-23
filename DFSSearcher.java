//Abdullah Arif
//Handles DFS based searches for n-puzzle program
import java.util.*;
public class  DFSSearcher extends Searcher{
	private static HashSet<Node> deadState;
	private static Node solution,initialState,goalState; 
	private static int accessed,maxSize,depthLimit,expanded;
	private static PriorityQueue<Node> nextState;
	//Setup for IDA
	private static void setup(ArrayList<Character> start, ArrayList<Character> goal,int size,int heuristicNum){
		solution=null;
		initialState = new Node(start,0,null);
        initialState.setSize(size); //node get set to to proper size
        initialState.setCost(heuristicNum);
        goalState = new Node(goal,0,null);
		deadState = new HashSet<>();
	    accessed =0;
	    expanded =0;
	    maxSize=0;
	}
	public static void IDA(ArrayList<Character> start, ArrayList<Character> goal,int size,int heuristicNum,int beamSize){
		setup(start,goal,size,heuristicNum);//setup variable for search	
	    int count =0;
	    //keep increasing depth until we reach a solution
		while(solution==null && depthLimit<Math.pow(size,Math.PI)+initialState.getCost()){  
			deadState.add(initialState);
			depthLimit =beamSize*count +initialState.getCost(); 
			limitedDFS(initialState,goalState,heuristicNum);
			expanded +=deadState.size();
			if(deadState.size()>3500000)
				break;
			deadState.clear();
			System.out.println("Current limit: " +(depthLimit-initialState.getCost()));
			count++;
		}
		printSolution(solution, accessed, 0,expanded); 
	}
	private static Node limitedDFS(Node currentState,Node goalState,int heuristicNum){ 
	    if(deadState.size()>3500000)
			return null;
	    Node localSolution =null;
		if(!(currentState.equals(goalState))){
			//get our next moves
			for(Node n : currentState.getNextMove()){ //for every move
				accessed++;			
				if(n!=null && !(deadState.contains(n)) ) { 
					deadState.add(n); 
					int pathCost = n.getDepth()+n.setCost(heuristicNum); //get total path cost
					if(pathCost<=depthLimit)
						localSolution = limitedDFS(n,goalState,heuristicNum);					
			    }
			}
		}
		else
			localSolution=currentState;		
		if(localSolution != null){ //if a solution was found it is a candidate for the optimal path
			if(solution==null)
				solution=localSolution;			
			else
				solution = (solution.getPathCost())<=(localSolution.getPathCost())?solution:localSolution;
			return solution;    
		}		
	    return null;
	}
	// public static void RBFS(){ }//Recursive Best-First search 	
}