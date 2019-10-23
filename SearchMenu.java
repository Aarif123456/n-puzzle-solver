//Abdullah Arif
//Program different types of searches for COMP 3710 
//handles that menu for searches
import java.util.*;
public class SearchMenu {
	public static final int MISPLACED_TILE=1,MANHATTAN_DISTANCE=2;
	public static void main (String[] args ){		
		int size=4,n,heuristicNumber=2; 
		Scanner sc= new Scanner(System.in); 
		String input;
		ArrayList<Character> grid,goal;		
		goal = createGoal(size); //create goal that search will try to reach		
		//User menu prompt
		System.out.println("Welcome to the 15-puzzle solver!");
		System.out.println("First let's create the box");
		System.out.println("Press 1 to create a box manually and 2 to create a random box");
		input =sc.nextLine();
		try{
			n = Integer.parseInt(input);
		}catch(NumberFormatException e){
			n=1;
			System.out.println("Invalid input you are being defaulted to creating box manually");
		}

		if(n==2) 
			grid = createRandomBox(size);
		else //default to creating a box manually
			grid = getBox(size);
		
		n=-1;
		System.out.println("Now choose a method to search for the goal");
		while(n==-1){ //Make sure user enters correct input
			try{
				System.out.println("Type in the number beside the search");
				System.out.println("1.BFS(Breadth-first search) search");
				System.out.println("2.GBFS(Greedy best-first search) search");
				System.out.println("3.A* search");
				System.out.println("4.IDA*(Iterative deepening A*) search");
				input =sc.nextLine();
				n = Integer.parseInt(input);
				if(n<1 || n>4)
					throw new NumberFormatException("Please make sure number is between 1 and 4");
			}catch(NumberFormatException e){
				System.out.println("Invalid input! Please enter a valid input");
			}
		}
		if(n>1){
				System.out.println("As an informed search you can chose your heuristic");
			try{
				System.out.println("Type in the number beside the desired heuristic method");
				System.out.println("1.Using the number of misplaced tiles");
				System.out.println("2.Using the Manhattan distance");				
				input =sc.nextLine();
				heuristicNumber = Integer.parseInt(input);	
				if(heuristicNumber !=1 || heuristicNumber !=2)
					throw new NumberFormatException("Invalid choice. Running Manhattan distance by default");			
			}catch(NumberFormatException e){
				heuristicNumber=2;
			}
		}		
		String[] searchName = {"BFS","GBFS","A*","IDA*"};
		System.out.println("Running "+searchName[n-1] +" on box:");
		System.out.println(grid);
		if(n==1)			
			BFSSearcher.BFS(grid,goal,size);
		if(n==2)			
			BFSSearcher.GBFS(grid,goal,size,heuristicNumber);
		if(n==3)			
			BFSSearcher.AStar(grid,goal,size,heuristicNumber);
		if(n==4)
			DFSSearcher.IDA(grid,goal,size,heuristicNumber,2);
	}

/////////////////////////Creating box////////////////////////////////////////////////////////
	// create box with given size -> for the 15 box problem size is always 4
	private static ArrayList<Character> getBox(int size){
		LinkedHashSet<Character> grid = new LinkedHashSet<>();
		if(size >6 || size <1){
			System.out.println("Invalid box size");
			return null;
		}
		// create list for box
		Scanner sc= new Scanner(System.in); 
		char in; //hold the character to be added 
		String input; //holds the entire input 
		//holds the prompt if user input invalid input
		String validPrompt = "Invalid input.\nPlease enter a valid character which is a digit(1-"; 
		int num ; //holds the limit of the box
		if(size>3){
			num = ((int)('A') + size*size - 11);
			char c = (char)(num);
			validPrompt += "9), a letter between A and " + c;
		}
		else{
			num =  (int)('9') - (9-(size*size)+1);
			char c = (char)(num);
			validPrompt += c +")";
		}
		validPrompt += " or a blank space";
		for(int i=0; i<size*size; i++){
			while(grid.size()<=i){ //keeping looping till valid character is inputed
				System.out.println("Please enter the character at position " +(i+1)+ " of the box");
				input = sc.nextLine();
				if(input.length()<=0)//fail safe
					continue;
				in = Character.toUpperCase(input.charAt(0));
				if(validateInput(in,num)){
					grid.add(in);
				}
				else{
					System.out.println(validPrompt);
				}
				System.out.println("Make sure characters are not repeated");
				System.out.println("The current list is: " +grid);
			}
		}
		return new ArrayList<>(grid);
	}

	private static boolean validateInput(char in,int limit){
		if(in ==' '){
			return true;
		}
		if(limit >= '1' || limit <= '9'){ //box 3 or smaller
			char c = (char)(limit);
            return (in > '0' && in <= c);
		}
		else{
			char c = (char)(limit);
            return (in > '0' && in <= '9') || (in >= 'A' && in < c);
		}
    }
	//create random grid of size 4 **useful for testing
	private static ArrayList<Character> createRandomBox(int size){
		LinkedHashSet<Character> grid = new LinkedHashSet<>();
		if(size >6 || size <1){
			System.out.println("Invalid box size");
			return null;
		}
		char c;
		ArrayList<Character> charList = new ArrayList<>();
		for(int i=0; i<size*size-1; i++){ //get characters
			c = ( i<9)?((char)((int)('1')+i)):((char)((int)('A')+(i-9)));
			charList.add(c);
		}
		charList.add(' ');
		int r; //r is the random index
		for(int i=size*size; i>0; i--){
			r = (int)(Math.random() * ((i))); 
			grid.add(charList.get(r));
			charList.remove(r);
		}
		return new ArrayList<>(grid);
	}
	//create the goal state
	private static ArrayList<Character> createGoal(int size){
		ArrayList<Character> goal = new ArrayList<>();
		if(size >6 || size <1){
			System.out.println("Invalid box size");
			return goal;
		}
		char c;
		ArrayList<Character> charList = new ArrayList<>();
		for(int i=0; i<size*size-1; i++){ //get characters
			c = ( i<9)?((char)((int)('1')+i)):((char)((int)('A')+(i-9)));
			goal.add(c);
		}
		goal.add(' ');
		return goal;
	}
}
