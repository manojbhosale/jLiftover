public void liftBed(HashMap<String,IntervalTree<List<Object>>> chainIndex,File inbed, File outBed) throws Exception{
		boolean reportMultiple = false;
		double inputRemapRatio = 0.95;
		PrintWriter pw = new PrintWriter(outBed);
		BufferedReader br = new BufferedReader(new FileReader(inbed));
		Chain chn = new Chain();
		PrintWriter pwfail = new PrintWriter(outBed.getParent()+"\\"+outBed.getName()+".failed");

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

				//pw.println(line);

				Integer queryBaseSize = stop - start;
				Integer remapBaseSize = 0; 
				for(BedInterval inter : res){

					remapBaseSize = remapBaseSize + inter.getStop() - inter.getStart();

				}

				

				double remapRatio = (double)remapBaseSize/queryBaseSize;


				if(remapRatio < inputRemapRatio){

					pwfail.println(line);
					System.out.println(line+"\t"+remapRatio+"\t"+remapBaseSize+"\t"+queryBaseSize);
					continue;

				}
				//System.out.println(line+"\t"+remapRatio+"\t"+remapBaseSize+"\t"+queryBaseSize);
				if(reportMultiple){

					for(BedInterval inter : res){

						pw.print(inter.getChromosome()+"\t"+inter.getStart()+"\t"+inter.getStop()+"\t"+inter.getStrand());
						for(int i = 3; i < splited.length ; i++){
							if(i == strandIndex){
								pw.print("\t"+inter.getStrand());
							}else{
								pw.print("\t"+splited[i]);
							}
						}
						pw.print("\n");

					}
				}else{
					
					BedInterval firstInterval = res.first();
					BedInterval lastInterval = res.last();
					
					Integer combinedStart = firstInterval.getStart();
					Integer combinedStop = lastInterval.getStop();
					
					
					
						
						pw.print(firstInterval.getChromosome()+"\t"+combinedStart+"\t"+combinedStop+"\t"+firstInterval.getStrand());
						for(int i = 3; i < splited.length ; i++){
							if(i == strandIndex){
								pw.print("\t"+firstInterval.getStrand());
							}else{
								pw.print("\t"+splited[i]);
							}
						}
						pw.print("\n");

					
					
					

					
				}

			}

		}
		pw.close();
		pwfail.close();


	}

}
