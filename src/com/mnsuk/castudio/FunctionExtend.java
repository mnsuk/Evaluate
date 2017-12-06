package com.mnsuk.castudio;

import java.util.ArrayList;

import murlen.util.fscript.FSException;
import murlen.util.fscript.FSFunctionExtension;
import murlen.util.fscript.FSUnsupportedException;

public class FunctionExtend implements FSFunctionExtension {
	public Object callFunction(String name, ArrayList params) throws FSException {
		Object result = (Boolean) false;
		try{
			switch (name){
			case "length":
				if (params.size()==1)
					result = ((String) params.get(0)).length();
				else
					throw new FSException("Invalid number of parameters to length()");
				break;
			case "substring":
				if (params.size()==2)
					result =  ((String) params.get(0)).substring((int) params.get(1));
				else if (params.size()==3)
					result =  ((String) params.get(0)).substring((int) params.get(1), (int) params.get(2));
				else
					throw new FSException("Invalid number of parameters to substring()");
				break;
			case "equalsIgnoreCase":
				if (params.size()==2)
					result =  ((String) params.get(0)).equalsIgnoreCase((String) params.get(1));
				else
					throw new FSException("Invalid number of parameters to equalsIgnoreCase()");
				break;
			case "indexOf":
				if (params.size()==2)
					result = ((String) params.get(0)).indexOf((String) params.get(1)); 
				else if (params.size()==3)
					result = ((String) params.get(0)).indexOf((String) params.get(1), (int) params.get(2)); 
				else
					throw new FSException("Invalid number of parameters to indexOf()");
				break;
			case "endsWith":
				if (params.size()==2)
					result =  ((String) params.get(0)).endsWith((String) params.get(1));
				else
					throw new FSException("Invalid number of parameters to endsWith()");
				break;
			case "trim":
				if (params.size()==1)
					result =  ((String) params.get(0)).trim();
				else
					throw new FSException("Invalid number of parameters to trim()");
				break;
			default:
				throw new FSUnsupportedException(name); 
			}
		} catch (Exception e){
			throw new FSException(e.getMessage());
		} 
		return result;
	}

}
