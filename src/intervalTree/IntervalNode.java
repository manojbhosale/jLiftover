package intervalTree;

public class IntervalNode {

	Interval inter;
	int max;
	IntervalNode left, right;

	public IntervalNode(Interval i){
		this.inter = i;
		this.left = new IntervalNode();
		this.right = new IntervalNode();
		this.max = i.end;
	}

	public IntervalNode(){
		this.inter = null;
		this.left = null;
		this.right = null;
		this.max = 0;
	}

	public IntervalNode insertNode(IntervalNode root, Interval i){

		if(!root.isValid()){
			return new IntervalNode(i);
		}
//System.out.println("Manoj");
		int l = root.inter.start;

		if(i.start < l){
			root.left = insertNode(root.left, i);
		}else{
			root.right = insertNode(root.right, i);
		}

		if(root.inter.end < i.end){
			root.max = i.end;
		}

		return root;
	}

	void inorder(IntervalNode root){

		if(!root.isValid())	return;

		inorder(root.left);
		
		System.out.println(root.inter+" Max = "+root.max);
		
		inorder(root.right);

	}

	public boolean isValid(){
		return this.inter != null;
	}


	public IntervalNode overlapSearch(IntervalNode root, Interval i){

		if(!root.isValid()) return null;

		if(doOverlap(root.inter, i)){
			return root;
		}

		if(root.left.max >= i.start){
			return overlapSearch(root.left, i);
		}

		return overlapSearch(root.right, i);
	}

	public boolean doOverlap(Interval inter1, Interval inter2){

		if(inter1.start <= inter2.end && inter1.end >= inter2.start){
			return true;
		}
		return false;
	}

	public static void main(String args[]){

		IntervalNode in = new IntervalNode();

		//int[][] pairs = {{15, 20}, {10, 30}, {17, 19},{5, 20}, {12, 15}, {30, 40}};
		int[][] pairs = {{50, 100}, {4, 5}, {200, 300}};
		IntervalNode root = new IntervalNode();

		for(int[] apair : pairs){
			root = in.insertNode(root, new Interval(apair[0], apair[1]));
		}

		in.inorder(root);

		//System.out.println(root);

		//System.out.println(in.overlapSearch(root, new Interval(199, 200)));
	}

	@Override
	public String toString() {
		return "IntervalNode [inter=" + inter + ", max=" + max + ", left="
				+ left + ", right=" + right + "]";
	}


}
