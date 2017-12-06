package com.mnsuk.castudio;

import java.util.Arrays;
import java.util.List;

import com.ibm.talent.jfst.norm.AbstractStringNormalizer;
import com.ibm.talent.jfst.norm.JFSTNormalizerException;

import murlen.util.fscript.FSFastExtension;
import murlen.util.fscript.FScript;

public class Evaluate extends AbstractStringNormalizer {

	@Override
	public String normalize(String arg0, String[] arg1) throws JFSTNormalizerException {
		//PrintWriter outputStream = null;
		Boolean result = false;
		try {
			/*outputStream = new PrintWriter("C:\\Users\\Martin\\Desktop\\CSLog.txt");
			outputStream.println("arg0: "+arg0);
			outputStream.println("arg1:");
			for (int j=0; j <arg1.length; j++) {
				outputStream.println(arg1[j]);
			}*/

			List<String> params = Arrays.asList(arg1);
			int currentPosition=0;
			String code = params.get(0);
			int numParams = code.length() - code.replace("%", "").length();
			//int numParams = params.size();
			//outputStream.println("numParams: "+numParams);
			//outputStream.println("input code: "+code);
			for (int i=0; i < numParams; i++) {
				int paramPosition = code.indexOf("%", currentPosition);
				if (paramPosition == -1) break;
				String regex = null;
				String replace = null;
				char paramType = code.charAt(paramPosition+1);
				char paramNum = code.charAt(paramPosition+2);
				int idx = Character.getNumericValue(paramNum);
				//outputStream.println("paramType: "+paramType + " paramNum: " + paramNum + " idx: " + idx);
				if (idx > numParams )
					throw new JFSTNormalizerException("Expression contains invalid parameter number");
				switch (paramType) {
				case 's':
					regex = "%s"+idx;
					replace = "\"" + params.get(idx) + "\"";
					break;
				case 'd':
					regex = "%d"+idx;
					replace = params.get(idx);
					break;
				case 'f':
					regex = "%f"+idx;
					replace = params.get(idx);
					break;
				default:
					break;
				}
				if (regex != null) {
					//outputStream.println("regex: "+regex + " replace: " + replace);
					String newcode = code.replaceFirst(regex, replace);
					code = newcode;
				}
				//outputStream.println("interim code: "+code + " i: " + i);
				//CScript cs = new CScript();
				currentPosition = paramPosition + 1;
			}
			//outputStream.println("code: "+code);
			//CScript cs = new CScript();
			FScript cs = new FScript();
			FSFastExtension fastExt = new FSFastExtension();
			cs.registerExtension(fastExt);
			FunctionExtend fe = new FunctionExtend();
			fastExt.addFunctionExtension("length", fe);
			fastExt.addFunctionExtension("substring", fe);
			fastExt.addFunctionExtension("equalsIgnoreCase", fe);
			fastExt.addFunctionExtension("indexOf", fe);
			fastExt.addFunctionExtension("endsWith", fe);
			fastExt.addFunctionExtension("trim", fe);
			Object answer = cs.evaluateExpression(code);
			String ansType = answer.getClass().getName();
			switch(ansType) {
			case "java.lang.Integer":
				if ((Integer) answer > 1)
					result = false;
				else
					result = (Integer) answer == 1 ? true : false;
				break;
			case "java.lang.Boolean":
				result = (boolean) answer;
				break;
			default:
				result = false;
				break;
			}
			//outputStream.println("result: "+result.toString());

		} catch (Exception e) {
			throw new JFSTNormalizerException(e);
		}/* finally {
			if (outputStream != null)
				outputStream.close();
		}*/
		return result.toString();

	}

	public class CScript {
		public Object evaluateExpression(String code) {
			if (code.length() > 10)
				return 1;
			else
				return 0;
		} 
	}
}
