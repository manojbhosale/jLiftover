package intervalTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LiftChain {


	public static String chainFilePath = "";
	
	public List<Chain>  liftOverChain() throws IOException, ChainException{

		BufferedReader br = new BufferedReader(new FileReader("C:\\Manoj\\BiologicalData\\hg19ToHg38.over.chain"));
		String line = "";
		List<Chain> chains = new ArrayList<Chain>();
		Chain cfu = new Chain();


		while((line = br.readLine())!= null){

			if(line.isEmpty() || line.startsWith("#")||line.startsWith("\n|\r")){
				continue;
			}

			if(line.startsWith("chain")){

				chains.add(cfu.parseChain(line, br));	

			}

		}
		//System.out.println("returned Chain");
		return chains;

	}

	public HashMap<String,IntervalNode> indexChains(List<Chain> chains) throws ChainException{

		HashMap<String, Integer> sourceSize = new HashMap<String, Integer>();
		HashMap<String, Integer> targetSize = new HashMap<String, Integer>();
		HashMap<String, IntervalNode> chainIndex = new HashMap<String, IntervalNode>();
		

		for(Chain c: chains){
			
			//System.out.println(c.targetName);
			sourceSize.put(c.sourceName, c.sourceSize);
			if(sourceSize.get(c.sourceName) != c.sourceSize)
				throw new ChainException();
			//System.out.println(c.targetSize);
			targetSize.put(c.targetName, c.targetSize);
			if(targetSize.get(c.targetName) != c.targetSize)
				throw new ChainException();
			IntervalNode tree;
			if(chainIndex.containsKey(c.sourceName)){
				tree = chainIndex.get(c.sourceName);
			}else{
				tree =  new IntervalNode();
			}
			
			//tree = chainIndex.get(c.sourceName);
			//System.out.println(c.blocks.size());
			for(List<Integer> temp: c.blocks){
				
				List<Object> l = new ArrayList<Object>(); 
				l.add(temp.get(2));
				l.add(temp.get(3));
				l.add(c);
				
				tree = tree.insertNode(tree, new Interval(temp.get(0),temp.get(1),l));
				
			}
			//System.out.println(c.sourceName+" "+tree.inter);
			chainIndex.put(c.sourceName, tree);
		}
		return chainIndex;
	}
	
	
	public IntervalNode query(HashMap<String,IntervalNode>  chainIndex ,String chromosome, int position){
		
		IntervalNode in = new IntervalNode();
		//System.out.println(chainIndex.keySet());
		if(!chainIndex.containsKey(chromosome)){
			//System.out.println("Manoj");
			//return new IntervalNode();
		}
		ArrayList<String> al = new ArrayList<String>();
		al.add(chromosome);
		IntervalNode ser = in.overlapSearch(chainIndex.get(chromosome), new Interval(position, position,al ));
		
		return ser;
	}
}
