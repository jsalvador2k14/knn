package com.jaimes.knn.distances.wbc;

import java.io.Serializable;

import smile.math.distance.Distance;

public class WBCEuclideanBitDistanceImpl implements Distance<int[]>, Serializable {
    
	private static final long serialVersionUID = 1L;

	public static final int MASK= 0b1111;
	public static final int NUM_BITS = Integer.bitCount( MASK );
	
	int cachedComp[] = null;	
	int cached[] = new int[9];
	
	protected void decodeCached( int x[] ) {
		
		cached[0] = (x[0]     & MASK );
		cached[1] = (x[0]>> 4 & MASK );
		cached[2] = (x[0]>> 8 & MASK );
		cached[3] = (x[0]>>12 & MASK );
		cached[4] = (x[0]>>16 & MASK );
		cached[5] = (x[0]>>20 & MASK );
		cached[6] = (x[0]>>24 & MASK );

		cached[7] = (x[1]>>20   & MASK );
		cached[8] = (x[1]>>24 & MASK );
	}
	
	protected int[] decodeAux( int x[] ) {
		int cc[] = new int[9];
		
		cc[0] = (x[0]     & MASK );
		cc[1] = (x[0]>> 4 & MASK );
		cc[2] = (x[0]>> 8 & MASK );
		cc[3] = (x[0]>>12 & MASK );
		cc[4] = (x[0]>>16 & MASK );
		cc[5] = (x[0]>>20 & MASK );
		cc[6] = (x[0]>>24 & MASK );

		cc[7] = (x[1]>>20 & MASK );
		cc[8] = (x[1]>>24 & MASK );
				
		return cc;
	}
	
	@Override
	public double d(int[] x, int[] y) {
		
		if (x.length != y.length)
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));

		if( x!=cachedComp )
		{
			cachedComp = x;
			decodeCached( x );
		}
		
        double dist = 0;
        
		int dif1 = cached[0] - (y[0]     & MASK );
		int dif2 = cached[1] - (y[0]>> 4 & MASK );
		int dif3 = cached[2] - (y[0]>> 8 & MASK );
		int dif4 = cached[3] - (y[0]>>12 & MASK );
		int dif5 = cached[4] - (y[0]>>16 & MASK );
		int dif6 = cached[5] - (y[0]>>20 & MASK );
		int dif7 = cached[6] - (y[0]>>24 & MASK );
		
		int dif8 = cached[7] - (y[1]>>20 & MASK );
		int dif9 = cached[8] - (y[1]>>24 & MASK );
		
		dist = dif1*dif1 + dif2*dif2 + dif3*dif3 + dif4*dif4 + dif5*dif5 + dif6*dif6 + dif7*dif7 + dif8*dif8 + dif9*dif9;
		
		return dist;
	}

}
