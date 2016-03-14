package intervalTree;

public class ChainException extends Exception{
	
	String chainProblem = "Some problem with chain!!!";

	public ChainException(){
		
		
		
	}
	
	public ChainException(String msg){
		
		System.out.println("");
		
	}
	
	public void printChainException(){
		
		System.out.println(this.chainProblem);
		
	}
	
	
}
