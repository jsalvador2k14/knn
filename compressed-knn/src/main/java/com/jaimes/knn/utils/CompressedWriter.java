package com.jaimes.knn.utils;

public class CompressedWriter {

	public static final String DIRECTORY = "E:/Data - datasets publicos/";
	
	public static final int NUM_BITS_WBC    = 4;

	public static final int NUM_BITS_CENSUS = 7;
	/**
	 * numericas==>3, 11, 12
	 * 
	 * @param data
	 */
	
	public static int[][] toB2B_CensusIncomeDs( int[][] data ) {
		
		System.out.println( data[0].length );
		
		int[][] out = new int[data.length][];
		
		CompressedEncoder utils = new CompressedEncoder( );
		
		for( int i=0; i<data.length; i++ )
		{
			int v1  = data[i] [0];
			int v2  = data[i] [1];
			int v3  = data[i] [2];
			int v4  = data[i] [3];
			int v5  = data[i] [4];
			int v6  = data[i] [5];
			int v7  = data[i] [6];
			int v8  = data[i] [7];
			int v9  = data[i] [8];
			int v10 = data[i] [9];
			int v11 = data[i][10];
			int v12 = data[i][11];
			int v13 = data[i][12];
			int v14 = data[i][13];
			// v15 es la variable a clasificar
//			int v15 = data[i][14];

//			int d[] = utils.encode( NUM_BITS_CENSUS, 
//					v5, v4, v2, v1, 
//					v9, v8, v7, v6, 
//					v14, v13, v10 );
	
			int d[] = utils.encode( NUM_BITS_CENSUS, v1, v2, /*v3,*/ v4, v5, v6, v7, v8, v9, v10, /*v11, v12,*/ v13, v14 );
			
			// v3, v11 y v12 no pueden ser comprimidas
			int d4 = v3;
			int d5 = v11;
			int d6 = v12;
			
			out[i] = new int[6];
			
			out[i][0] = d[0];
			out[i][1] = d[1];
			out[i][2] = d[2];
			out[i][3] = d4;
			out[i][4] = d5;
			out[i][5] = d6;
		}
		
		return out;
	}
	
	public static int[][] toB2B_WBC( int[][] data ) {
		
		System.out.println( data[0].length );
		
		int[][] out = new int[data.length][];
		
		CompressedEncoder utils = new CompressedEncoder( );
		
		for( int i=0; i<data.length; i++ )
		{
			int v1  = data[i] [0];
			int v2  = data[i] [1];
			int v3  = data[i] [2];
			int v4  = data[i] [3];
			int v5  = data[i] [4];
			int v6  = data[i] [5];
			int v7  = data[i] [6];
			int v8  = data[i] [7];
			int v9  = data[i] [8];
			// v10 es la variable a clasificar
			// int v10 = data[i][9];

			int d[] = utils.encode( NUM_BITS_WBC, v1, v2, v3, v4, v5, v6, v7, v8, v9 );
			
			out[i] = new int[6];
			
			out[i][0] = d[0];
			out[i][1] = d[1];
		}
		
		return out;
	}
	
	public static void printCensus( int[][] data, int size ) {
		
		CompressedEncoder enc = new CompressedEncoder();
		
		for( int i=0;i<size; i++ )
		{
			int[] x = enc.decoder( NUM_BITS_CENSUS, data[i][0], data[i][1], data[i][2] );
	        
	        int x13 = data[i][3];
	        int x14 = data[i][4];
	        int x15 = data[i][5];
	        
	        System.out.format( "%3d: %d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t \n", 
	        		(i+1), x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8], x[9], x[10], x[11], x13, x14, x15);
		}
	}
	
	public static void printWbc( int[][] data, int size ) {
		
		CompressedEncoder enc = new CompressedEncoder();
		
		for( int i=0;i<size; i++ )
		{
			int[] x = enc.decoder( NUM_BITS_WBC, data[i][0], data[i][1] );
	        
	        System.out.format( "%3d: %d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d \n", 
	        		(i+1), x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8] );
		}
	}

}
