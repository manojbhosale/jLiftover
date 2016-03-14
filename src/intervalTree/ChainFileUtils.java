package intervalTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChainFileUtils {

	long score;
	String sourceName;
	int sourceSize;
	String sourceStrand;
	int sourceStart;
	int sourceEnd;
	String targetName;
	int targetSize;
	String targetStrand;
	int targetStart;
	int targetEnd;
	String id;
	int sfrom;
	int tfrom;
	List<List<Integer>> blocks = new ArrayList<List<Integer>>();
	int size;
	int sgap;
	int tgap;
	//public static ChainFileUtils cfu = new ChainFileUtils();


	/*public static void main(String args[]) throws IOException{



	}*/

	public ChainFileUtils parseChain(String header, BufferedReader br) throws IOException, ChainException{

		String fields[] ={};

		fields = header.split("\\s+");
		if(header.startsWith("chain")){
			
			//System.out.println(header);
			//System.out.println(fields[1]);
			score = Long.parseLong(fields[1]);
			
			sourceName = fields[2];
			sourceSize = Integer.parseInt(fields[3]);
			sourceStrand = fields[4];
			sourceStart = Integer.parseInt(fields[5]);
			sourceEnd = Integer.parseInt(fields[6]);
			targetName = fields[7];
			targetSize = Integer.parseInt(fields[8]);
			targetStrand = fields[9];
			targetStart = Integer.parseInt(fields[10]);
			targetEnd = Integer.parseInt(fields[11]);
			id = fields.length == 12 ? "None" : fields[12];

			sfrom = sourceStart;
			tfrom = targetStart;

		}

		fields = br.readLine().split("\\s+");
		blocks = new ArrayList<List<Integer>>();
		while(fields.length == 3){
			//System.out.println(fields[0]);
			size = Integer.parseInt(fields[0]);
			sgap = Integer.parseInt(fields[1]);
			tgap = Integer.parseInt(fields[2]);


			ArrayList<Integer> al = new ArrayList<Integer>();
			al.add(sfrom);
			al.add(sfrom+size);
			al.add(tfrom);
			al.add(tfrom+size);
			blocks.add(al);

			sfrom += size + sgap;
			tfrom += size + tgap;
			fields = br.readLine().split("\t");
		}
		
		
		
		if(fields.length != 1){
			throw new ChainException("Length One");
		}


		size = Integer.parseInt(fields[0]);
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(sfrom);
		al.add(sfrom+size);
		al.add(tfrom);
		al.add(tfrom+size);
		blocks.add(al);
		
		if((sfrom+size )!= sourceEnd || (tfrom+size)!= targetEnd){

			throw new ChainException();

		}

		//System.out.println("Returned Chain Successfully !!"+this.blocks);
		return new ChainFileUtils(score, sourceName, sourceSize, sourceStrand, sourceStart, sourceEnd, targetName, targetSize, targetStrand, targetStart, targetEnd, id, sfrom, tfrom, blocks, size, sgap, tgap);

	}
	public ChainFileUtils(long score, String sourceName, int sourceSize,
			String sourceStrand, int sourceStart, int sourceEnd,
			String targetName, int targetSize, String targetStrand,
			int targetStart, int targetEnd, String id, int sfrom, int tfrom,
			List<List<Integer>> blocks, int size, int sgap, int tgap) {
		super();
		this.score = score;
		this.sourceName = sourceName;
		this.sourceSize = sourceSize;
		this.sourceStrand = sourceStrand;
		this.sourceStart = sourceStart;
		this.sourceEnd = sourceEnd;
		this.targetName = targetName;
		this.targetSize = targetSize;
		this.targetStrand = targetStrand;
		this.targetStart = targetStart;
		this.targetEnd = targetEnd;
		this.id = id;
		this.sfrom = sfrom;
		this.tfrom = tfrom;
		this.blocks = blocks;
		this.size = size;
		this.sgap = sgap;
		this.tgap = tgap;
	}
	
	public ChainFileUtils(){
		
	}
	
	public IntervalNode convertCoordinate(HashMap<String,IntervalNode> chainIndex,String chromosome, int position, String strand){
		
		strand = "+"; 
		LiftChain lc = new LiftChain();
		IntervalNode queryResults = lc.query(chainIndex, chromosome, position);
		IntervalNode result = new IntervalNode(new Interval());
		int targetStart;
		int targetEnd;
		int resultPosition;
		int sourceStart;
		String resultStrand;
		int sourceEnd;
		//Object blocks ;
		List<Object> test;
		ChainFileUtils cf;
		if(!queryResults.isValid()){
			return new IntervalNode();
		}else{
			
			test = queryResults.inter.data;
			targetStart = (Integer)test.get(0);
			targetEnd = (Integer)test.get(1);
			cf = (ChainFileUtils)test.get(2);
			sourceStart = queryResults.inter.start;
			sourceEnd = queryResults.inter.end;
			resultPosition = targetStart + (position - sourceStart);
			if(cf.targetStrand.equals('-')){
				resultPosition = cf.targetSize - 1 - resultPosition;
			}
			//result.inter.data = new ArrayList<>(){chromosome}
			result.inter.start = resultPosition;
			result.inter.end = resultPosition;
		}
		
		
		
		return result;
	}
	
}

