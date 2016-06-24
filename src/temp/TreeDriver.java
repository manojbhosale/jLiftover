package temp;

import intervalTree.Interval;
import intervalTree.IntervalNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TreeDriver {
	
	
	public static void main(String args[]) throws IOException{
		
		IntervalNode in = new IntervalNode();
		IntervalNode root = new IntervalNode();
		BufferedReader br = new BufferedReader(new FileReader("C:\\Manoj\\BiologicalData\\test.bed"));
		String line = "";
		while((line = br.readLine())!=null){
			
			String[] splited = line.split("\t");
			root = in.insertNode(root, new Interval(Integer.parseInt(splited[1]), Integer.parseInt(splited[2]),splited[0]));
			
		}
		
		//System.out.println(root.inter);
		
		//in.inorder(root);
		System.out.println(in.overlapSearch(root, new Interval(300, 302,"chr1")));
		br.close();
		
	}

}
