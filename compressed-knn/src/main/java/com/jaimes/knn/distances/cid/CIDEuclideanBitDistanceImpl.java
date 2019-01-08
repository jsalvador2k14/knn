package com.jaimes.knn.distances.cid;

import java.io.Serializable;

import smile.math.distance.Distance;

public class CIDEuclideanBitDistanceImpl implements Distance<int[]>, Serializable {
    
	private static final long serialVersionUID = 1L;

	public static final int MASK= 0b1111111;
	public static final int NUM_BITS = Integer.bitCount( MASK );
	
	int cachedComp[] = null;	
	int cached[] = new int[20];
	
	protected void decodeCached( int x[] ) {
		
		int index = 0;
		
		for ( int i = 0; i < 3; i++) {
			int xx = x[i];

			//int nn = 0;
			for (int j = 0; j < 4; j++) {
				cached[index++] = ( xx & MASK );
				
				xx = xx >> 7;
			}
		}
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
        
        int k = 0;
        
		for ( int i = 0; i < 3; i++) {
			//int xx = x[i];		
			//int yy = y[i];
			
			int dif1 = cached[k+0] - (y[i]     & MASK );
			int dif2 = cached[k+1] - (y[i]>> 7 & MASK );
			int dif3 = cached[k+2] - (y[i]>>14 & MASK );
			int dif4 = cached[k+3] - (y[i]>>21 & MASK );
			
			k = k + 4;
			
			dist += dif1*dif1 + dif2*dif2 + dif3*dif3 + dif4*dif4;
		}
	
        for( int i=3; i<6; i++ )
        {
        	double d = x[i]-y[i];
        	
        	dist += d * d;
        }
                
        return Math.sqrt(dist);
	}

}
