package intervalTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chain {

	// Declare the chain elements
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

	// Parse each chain and return an object for the chain with all its
	// alignment blocks
	public Chain parseChain(String header, BufferedReader br)
			throws IOException, ChainException {

		String fields[] = {};

		fields = header.split("\\s+");
		//parse chain header and initialize the instance variables
		if (header.startsWith("chain")) {

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
		
		//If lines are of length 3 then these are assumed to be blocks and added to the array list
		//
		//File : 365	72	72
		//Meaning : SizeOfAlignmentBlock SourceGapLength TargetGapLength

		while (fields.length == 3) {
			size = Integer.parseInt(fields[0]);
			sgap = Integer.parseInt(fields[1]);
			tgap = Integer.parseInt(fields[2]);

			ArrayList<Integer> al = new ArrayList<Integer>();
			al.add(sfrom);
			al.add(sfrom + size);
			al.add(tfrom);
			al.add(tfrom + size);
			blocks.add(al);

			sfrom += size + sgap;
			tfrom += size + tgap;
			fields = br.readLine().split("\t");
		}
		//Check the invalid lines and throw an exception
		if (fields.length != 1) {
			throw new ChainException("Unexpected length 1 line in chain file");
		}
		size = Integer.parseInt(fields[0]);
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(sfrom);
		al.add(sfrom + size);
		al.add(tfrom);
		al.add(tfrom + size);
		blocks.add(al);

		// If size of the chain exceeds the sourceChromosome or targetChromosome length then throw exception
		if ((sfrom + size) != sourceEnd || (tfrom + size) != targetEnd) {

			throw new ChainException("Source or target chain length exceeds expected length");

		}

		// Return the chain object with all the blocks information
		return new Chain(score, sourceName, sourceSize, sourceStrand,
				sourceStart, sourceEnd, targetName, targetSize, targetStrand,
				targetStart, targetEnd, id, sfrom, tfrom, blocks, size, sgap,
				tgap);

	}

	public Chain(long score, String sourceName, int sourceSize,
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

	public Chain() {

	}

	//Convert the queried coordinate from source build to target build
	public IntervalNode convertCoordinate(
			HashMap<String, IntervalNode> chainIndex, String chromosome,
			int position, String strand) {
		//default strand is assumed  as plus/+
		strand = "+";
		

		LiftChain lc = new LiftChain();
		
		//Query the position to the interval tree made of chains which returns the overlapping interval
		IntervalNode queryResults = lc.query(chainIndex, chromosome, position);
		
		
		IntervalNode result = new IntervalNode(new Interval());
		int targetStart;
		int resultPosition;
		int sourceStart;
		List<Object> test;
		Chain cf;
		
		if (!queryResults.isValid()) {
			return new IntervalNode();
		} else {
			test = queryResults.inter.data;
			targetStart = (Integer) test.get(0);
			targetEnd = (Integer) test.get(1);
			cf = (Chain) test.get(2);
			sourceStart = queryResults.inter.start;
			sourceEnd = queryResults.inter.end;
			resultPosition = targetStart + (position - sourceStart);
			if (cf.targetStrand.equals('-')) {
				resultPosition = cf.targetSize - 1 - resultPosition;
			}
			// result.inter.data = new ArrayList<>(){chromosome}
			result.inter.start = resultPosition;
			result.inter.end = resultPosition;
		}

		return result;
	}

}
