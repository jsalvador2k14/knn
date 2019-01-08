package com.jaimes.knn.utils;

public class DoubleUtils {

	public static int[][] toInt( double[][] values ) {
		
		int[][] xx = new int[values.length][];
	    for( int i=0; i<values.length; i++ )
	    {
	    	xx[i] = new int[values[i].length];
	    	
	    	for( int j=0; j<values[i].length;j++ ) {
	    		xx[i][j] = (int )values[i][j];
	    	}
	    }
	    
	    return xx;
	}
}
