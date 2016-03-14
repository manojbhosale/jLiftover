package intervalTree;

import java.util.List;
import java.util.ArrayList;

public class Interval {

	public int start;
	public int end;
	public List<Object> data;
	public Interval temp = null;
	
	@Override
	public String toString() {
		return "Interval [start=" + start + ", end=" + end + ", data=" 
				+ "]";
	}


	public Interval(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}


	public Interval(int start, int end, List data) {
		super();
		this.start = start;
		this.end = end;
		this.data = data;
	}
	
	public Interval(){
		this.temp = null;
		this.start = 0;
		this.end = 0;
	}
	
}
