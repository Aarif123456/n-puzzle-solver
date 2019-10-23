//Abdullah Arif
//Handles BFS based searches for n-puzzle program
import java.util.*;
public class BFSSearcher extends Searcher{
    private static void search(ArrayList<Character> start,ArrayList<Character> goal,int size, 
            Queue<Node> nextState , int heuristicNum) 
    {    
        //state that have already been extended 
        HashSet<Node> deadState = new HashSet<>();
        int  accessed,maxSize; //need for output
        accessed =0;        
        maxSize=0;
        Node currentState, goalState,solution =null;  
        currentState = new Node(start,0,null);
        currentState.setSize(size); //node get set to to proper size
        currentState.setCost(heuristicNum);
        goalState = new Node(goal,0,null);
        if(currentState.equals(goalState))
            solution=currentState;
        while(!(currentState.equals(goalState))){
            // System.out.println(currentState.getElement());
            // System.out.println("F-value"+currentState.getPathCost());
            //check if we can move up
            for(Node n : currentState.getNextMove()){
                accessed++;
                if(n!=null && !(deadState.contains(n))){
                    n.setCost(heuristicNum); //make sure cost is added
                    nextState.add(n); 
                    if(n.equals(goalState)){                        
                        solution=n;
                        break;                   
                    }
                }
            }  
            //currentState becomes dead
            deadState.add(currentState);
            maxSize = (nextState.size()>maxSize)?nextState.size():maxSize; //store max size of fringe
            //If no heuristic then break at first solution
            if(solution!=null && heuristicNum==-1) 
                break;
           //Next item in queue become current state 
            while(currentState!=null  &&(deadState.contains(currentState))) 
                currentState =  nextState.poll();
            //Shockingly this happens more than I expected
            if(nextState.size() ==0 || nextState.size()>=2800000)
                break;
        }
        int expanded = deadState.size();
        deadState.clear();
        printSolution(solution,accessed, maxSize,expanded); 
    }

    public static void BFS(ArrayList<Character> start, ArrayList<Character> goal,int size){ 
        // Comparator<Node> costComparator = Comparator.comparingInt(Node::getDepth);
        ArrayDeque<Node> nextState = new ArrayDeque<>();
        search(start,goal,size,nextState ,-1);
    }
    //Greedy best-first search using heuristic
    public static void GBFS(ArrayList<Character> start,ArrayList<Character> goal,int size,
     int mode){ 
        Comparator<Node> costComparator = Comparator.comparingInt(Node::getCost);
        PriorityQueue<Node> nextState = new PriorityQueue<>(costComparator);
        search(start,goal,size,nextState ,mode);
    }

    public static void AStar(ArrayList<Character> start,ArrayList<Character> goal,int size, 
        int mode){
        Comparator<Node> costComparator = Comparator.comparingInt(Node::getPathCost);
        PriorityQueue<Node> nextState = new PriorityQueue<>(costComparator);
        search(start,goal,size,nextState ,mode);
    }
    //public static void SMA(){ }//Simplified Memory-bounded A*
}