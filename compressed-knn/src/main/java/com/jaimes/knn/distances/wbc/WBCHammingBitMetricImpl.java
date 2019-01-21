package com.jaimes.knn.distances.wbc;

import java.io.Serializable;

import smile.math.distance.Metric;

public class WBCHammingBitMetricImpl implements Metric<int[]>, Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int MASK = 0b1111;

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
			throw new IllegalArgumentException( String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length) );

		if( x!=cachedComp )
		{
			cachedComp = x;
			decodeCached( x );
		}
		
        double dist = 0;
        
		dist += cached[0]==(y[0]     & MASK )?0:1;
		dist += cached[1]==(y[0]>> 4 & MASK )?0:1;
		dist += cached[2]==(y[0]>> 8 & MASK )?0:1;
		dist += cached[3]==(y[0]>>12 & MASK )?0:1;
		dist += cached[4]==(y[0]>>16 & MASK )?0:1;
		dist += cached[5]==(y[0]>>20 & MASK )?0:1;
		dist += cached[6]==(y[0]>>24 & MASK )?0:1;
		
		dist += cached[7]==(y[1]>>20 & MASK )?0:1;
		dist += cached[8]==(y[1]>>24 & MASK )?0:1;
		
//		VectorUtils.log( decodeAux(x) );
//		VectorUtils.log( decodeAux(y) );		
//		System.out.println( dist );
//      throw new RuntimeException( "test" );
		
        return dist;
	}

}
