package qPlugin.qInterface;

import java.util.ArrayList;

import ibmi.Result;

public interface QRunQueryPlugin {
	
	public boolean process(StringBuilder query, ArrayList<Result> result);
 

}
