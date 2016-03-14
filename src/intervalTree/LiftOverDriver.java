package intervalTree;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Chromaticity;

public class LiftOverDriver {

	public static void main(String args[]) throws IOException, ChainException{

		LiftChain lc = new LiftChain();

		List<ChainFileUtils> cfu = lc.liftOverChain(); 


		HashMap<String,IntervalNode> chainIndex = lc.indexChains(cfu);


		/*for (Map.Entry<String, IntervalNode> en : chainIndex.entrySet()) {
			System.out.println(en.getValue());
		}*/
		//IntervalNode in = new IntervalNode();
		//in.inorder(chainIndex.get("chr1"));
			
		//IntervalNode in1 = lc.query(chainIndex, "chr1", 1000000);

		//System.out.println(in1.inter);
		ChainFileUtils cfu1 = new ChainFileUtils();
		
		System.out.println(cfu1.convertCoordinate(chainIndex, "chr7", 127472363,"+"));
		 		
	}

}
