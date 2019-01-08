package com.jaimes.knn.utils;

public class CompressedEncoder {

	private int[] masks;
	
	public CompressedEncoder( ) {

		masks = new int[32];
		
		int temp = Integer.MAX_VALUE;
		
		for( int i=0;i<Integer.SIZE; i++ ) {
			
			int index = 31 - i;
			
			masks[index] = temp;
			
			temp = temp>>1;
		}
	}
	
	public int get( int index ) {
		return masks[index];
	}
	
	public int[] encode( int numBits, int... values ) {
		
		int elementsInBlock = 31 / numBits;
		int vectorSize = values.length;
		
		int bufferSize = (int )Math.ceil(  vectorSize / (float )elementsInBlock );
		
		int ret[] = new int[bufferSize];
		
		for( int i=0; i<vectorSize; i++ )
		{
			int bufferPos = i / elementsInBlock;
			int bufferIndex = i % elementsInBlock;
			
			int ss = (elementsInBlock-bufferIndex-1)*numBits ;
			
			ret[bufferPos] = ret[bufferPos] | ( values[i] << ss );
		}
		
		return ret;
	}
	
	public int[] decoder( int numBits, int...values ) 
	{
		int elementsInBlock = 31 / numBits;
		
		int maxElem = elementsInBlock * values.length;
		
		int ret[] = new int[maxElem];
		
		int index = 0;
		
		for( int i=0; i<values.length; i++ )
		{
			int ss = (elementsInBlock-1)*numBits ;
			
			for( int j=0; j<elementsInBlock; j++ )
			{
				int value =  (values[i]>>ss) & masks[numBits];
			
				ret[index] = value;	
						
				ss = ss - numBits;
				index++;
			}
		}
		
		return ret;
	}
	
//	public static void main( String args[] ) {
//		
//		CompressedEncoder masks = new CompressedEncoder();
//		
//		for( int i=0;i<32; i++ ) {
//			
//			System.out.format( "%2d - %s\n", i, Integer.toBinaryString(masks.get(i)) );
//		}
//		
//		System.out.println( Math.ceil(1.8));
//	}
}


