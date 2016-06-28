package intervalTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;



public class LiftOverDriver {




	public static void main(String args[]) throws Exception{
		Chain chn = new Chain();
		//LiftChain lc = new LiftChain();

		List<Chain> chains = chn.liftOverChain(new File("C:\\Manoj\\Progamming\\JAVA\\jLiftOver\\hg19ToHg18.over.chain")); 

		HashMap<String,IntervalTree<List<Object>>> chainIndex = chn.indexChains(chains);
		/*BufferedReader br = new BufferedReader(new FileReader("C:\\Manoj\\Progamming\\JAVA\\jLiftOver\\testOneSeqCovered_Small.bed"));
		



*/
		SortedSet<BedInterval> res = new TreeSet<>();
		PrintWriter pw=  new PrintWriter("C:\\Manoj\\Progamming\\JAVA\\jLiftOver\\testResSmall.bed");

		try{
			res = chn.convertCoordinate(chainIndex,"chr8", 17440060, 17440260, "+");
		}catch(ChainException e){
			e.printChainException();
		}catch(Exception e1){
			e1.printStackTrace();
		}

		for(BedInterval i : res){

			pw.println(i.getChromosome()+"\t"+i.getStart()+"\t"+i.getStop());

		}


		/*
		String line = "";
		while((line = br.readLine())!= null){

			line = line.trim();
			String[] test = line.split("\t");
			//System.out.println(test[2]);

			try{
				res = chn.convertCoordinate(chainIndex, test[0], Integer.parseInt(test[1]), Integer.parseInt(test[2]), "+");
			}catch(ChainException e){
				e.printChainException();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			for(BedInterval i : res){

				pw.println(i.getChromosome()+"\t"+i.getStart()+"\t"+i.getStop()+"\t"+i.getStrand());

			}

		}	


		br.close();
		pw.close();*/
		//LiftOverDriver lod = new LiftOverDriver();
		//lod.liftBed(chainIndex, new File("C:\\Manoj\\Progamming\\JAVA\\jLiftOver\\TestData\\hg19.rand.bed"),new File("C:\\Manoj\\Progamming\\JAVA\\jLiftOver\\CrossMaptestDatahg19to18.bed"));

	}


	public void liftBed(HashMap<String,IntervalTree<List<Object>>> chainIndex,File inbed, File outBed) throws Exception{

		PrintWriter pw = new PrintWriter(outBed);
		BufferedReader br = new BufferedReader(new FileReader(inbed));
		Chain chn = new Chain();


		String line = "";
		while((line = br.readLine())!= null){

			if(line.startsWith("#") || line.startsWith("track") || line.startsWith("browser"))
				continue;

			line = line.trim();
			String[] splited = line.split("\t");

			String strand = "+";

			if(splited.length < 3)
				throw new Exception("less than 3 fileds in BED. Skipped.");
			String chromosome = splited[0]; 
			Integer start = Integer.parseInt(splited[1]);
			Integer stop = Integer.parseInt(splited[2]);

			if(start > stop){
				throw new Exception("Start greater than stop. Skipped.");
			}

			//deal with bed less than 12 columns
			int strandIndex = 0;
			if(splited.length < 12){

				for(String str : splited){				
					if(str.equals("+") || str.equals("-")){
						strand = str; //change default strand information if present in input file
						break;
					}
					strandIndex++;
				}

				SortedSet<BedInterval> res = new TreeSet<>();
				res = chn.convertCoordinate(chainIndex, chromosome, start, stop, strand);

				pw.println(line);
				for(BedInterval inter : res){

					pw.print(inter.getChromosome()+"\t"+inter.getStart()+"\t"+inter.getStop());
					for(int i = 3; i < splited.length ; i++){
						if(i == strandIndex){
							pw.print("\t"+inter.getStrand());
						}else{
							pw.print("\t"+splited[i]);
						}
					}
					pw.print("\n");

				}

			}

		}
		pw.close();


	}

}
