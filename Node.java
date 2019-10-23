//Abdullah Arif
//a node used by for searches
import java.util.*;

class Node { 
	ArrayList<Character> state; 
	Node parent;
	int depth,cost,spaceIndex;
	protected static int size=4;
	Node(ArrayList<Character> s, Node p) { 
		state = s; //holds data
		depth=p.getDepth()+1; //holds depth of node
		parent =p;
		cost =0;
		spaceIndex=-1;
	}
	Node(ArrayList<Character> s, int d, Node p) {
		state = s; //holds data
		depth=d; //holds depth of node
		parent =p;
		cost =0;
		spaceIndex=-1;		
	}
	public Node getParent(){
		return parent;
	}  
	public int getDepth(){
		return depth;
	}

	public int getCost(){
		return cost;
	}
	public int getPathCost(){
		return cost +depth;
	}
	
	public int setCost(int mode){
		switch(mode){
			case(1):
				return cost =heuristic1(state);
			case(2):
				return cost = heuristic2(state);
		}
		return -1;
	}
	//count misplaced tiles
	public static int heuristic1(ArrayList<Character> current){
		int misplaced=0,i=0;
		char[] chArray = {'1','2','3','4','5','6','7','8','9','A','B','C','D','E','F',' '};
		for(char c: current){
			if(c!=chArray[i]){
				misplaced++;
			}
			i++;
		}
		return misplaced;
	}
	//Manhattan distance
	public static int heuristic2(ArrayList<Character> current){
		int sum=0, numIndex,xDist, yDist,i=0;
		for(char c: current){ //used enhanced for loops because arrayList in Java can be slow
			if(c>='1'&&c<='9'){
				numIndex = (int)(c) -(int)('1');
			}
			else if(c>='A'&&c<='F'){
				numIndex = (int)(c) -(int)('A')+9;
			}
			else{
				numIndex=15;
			}
			yDist = Math.abs(i/size-numIndex/size);
			xDist = Math.abs(i%size-numIndex%size);
			sum +=xDist+yDist; //get how far you are away in absolute distance
			i++;
		}
		return sum;
	}

	public void setSize(int s){
		size=s;
	}
	public int getSize(){
		return size;
	}
	public ArrayList<Character> getElement(){
		return this.state;
	}
	public int getSpaceIndex(){
		if(this.spaceIndex==-1){
			this.spaceIndex = this.state.indexOf(' ');
		}
		return this.spaceIndex;
	}
	public void setSpaceIndex(int s){
		this.spaceIndex = s;
	}

	@Override
	public String toString(){
		String output ="";
	    if(this.getParent()!=null) //go till start
			output+=this.getParent();
		output += (this.depth)==0?"Starting state is":"Step " + (this.depth);
		output+=":\n";
	    int count=0;
	    for(char c: state){
			output+=c;
			output+=" ";
			if(++count % size==0)
			    output+="\n";
	    }
	    output+="\n";
	    return output;
	}  

	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || !(obj instanceof Node)) {
            return false;
        }

    	Node other = (Node) obj;
    	return this.state.equals(other.getElement());

    }

    @Override
    public int hashCode() { //hashcode should be based on the elements in the array
        return this.state.hashCode();
    }

	//deep copy array list in Java and swap position
	private  Node copySwapArray( int targetIndex)	{
		ArrayList<Character> copy = new ArrayList<>();
		copy.addAll(0,state);
		copy.set(spaceIndex,copy.get(targetIndex)); 
		copy.set(targetIndex,' ');
		return new Node(copy,this);
	}

	public Node[] getNextMove(){
		int row,col;
		Node[] temp = new Node[4];
		col = this.getSpaceIndex()%size; //make sure spaceIndex is initialized
        row = spaceIndex/size;
        boolean[] conditions={(row>0),(row<size-1),(col>0),(col<size-1)};
        int[] targetIndexes={(row-1)*size+col,(row+1)*size+col, spaceIndex-1, spaceIndex+1};
        for(int i=0;i<4;i++){
        	if(conditions[i])
        		temp[i] = this.copySwapArray(targetIndexes[i]);
        }
        return temp;
	} 
} 