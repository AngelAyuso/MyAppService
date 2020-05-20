package utils;

import java.util.Date;

public class Utils {
	
	public static String compare(String strEntrada, String strBBDD) {
		String strResult = null;
		if (Data.isValid(strEntrada)) {
			strResult = strEntrada;
		} else if (Data.isValid(strBBDD)) {
			strResult = strBBDD;
		}
		return strResult;
	}
	
	public static Number compare(Number numEntrada, Number numBBDD) {
		Number numResult = null;
		if (Data.isValid(numEntrada)) {
			numResult = numEntrada;
		} else if (Data.isValid(numBBDD)) {
			numResult = numBBDD;
		}
		return numResult;
	}
	
	public static Date compare(Date dateEntrada, Date dateBBDD) {
		Date dateResult = null;
		if (Data.isValid(dateEntrada)) {
			dateResult = dateEntrada;
		} else if (Data.isValid(dateBBDD)) {
			dateResult = dateBBDD;
		}
		return dateResult;
	}
}
