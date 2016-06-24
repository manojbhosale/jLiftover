package intervalTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Chromaticity;

public class LiftOverDriver {

	public static void main(String args[]) throws IOException, ChainException{

		LiftChain lc = new LiftChain();

		List<Chain> cfu = lc.liftOverChain(); 
		
		//LiftChain.chainFilePath = new File
				
		BufferedReader br = new BufferedReader(new FileReader("C:\\Manoj\\BiologicalData\\test.bed"));
		HashMap<String,IntervalNode> chainIndex = lc.indexChains(cfu);


		/*for (Map.Entry<String, IntervalNode> en : chainIndex.entrySet()) {
			System.out.println(en.getValue());
		}*/
		//IntervalNode in = new IntervalNode();
		//in.inorder(chainIndex.get("chr1"));
			
		//IntervalNode in1 = lc.query(chainIndex, "chr1", 1000000);

		//System.out.println(in1.inter);
		Chain cfu1 = new Chain();
		
		String line = "";
		System.out.println(cfu1.convertCoordinate(chainIndex, "chr1", 10001,"+"));
		while((line = br.readLine())!= null){
			
			line = line.trim();
			String[] test = line.split("\t");
			System.out.println(test[2]);
			
				System.out.println(test[0]+"\t"+cfu1.convertCoordinate(chainIndex, test[0], Integer.parseInt(test[1]),"+")+"\t"+cfu1.convertCoordinate(chainIndex, test[0], Integer.parseInt(test[2]),"+"));
			
			}		
	}

}
