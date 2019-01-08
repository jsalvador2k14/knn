package com.jaimes.knn.utils;

public class VectorUtils {
	
	public static void log( int... x ) {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "[" );
		for( int xx:x )
			sb.append( xx + ", " );
		sb.append( "]" );
		
		System.out.println( sb );
	}
}
