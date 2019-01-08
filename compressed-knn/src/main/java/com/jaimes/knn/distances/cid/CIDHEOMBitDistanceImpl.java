package com.jaimes.knn.distances.cid;

import java.io.Serializable;

import com.jaimes.knn.utils.CompressedEncoder;

import smile.math.distance.Distance;

public class CIDHEOMBitDistanceImpl implements Distance<int[]>, Serializable {

	private static final long serialVersionUID = 1L;

	CompressedEncoder enc = new CompressedEncoder();
	
	public static final int MASK= 0b1111111;
	public static final int NUM_BITS = Integer.bitCount( MASK );
	
	double rangos[] = new double[] {
			0,
			0,
			0,
			1484705-13769,
			99999-0,
			4356-0,
			0,
			0};
	
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
	
	protected int[] decodeAux( int x[] ) {
		int cc[] = new int[15];
		
		int index = 0;
		
		for ( int i = 0; i < 3; i++) {
			int xx = x[i];

			//int nn = 0;
			for (int j = 0; j < 4; j++) {
				cc[index++] = ( xx & MASK );
				
				xx = xx >> 7;
			}
		}
		
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
        
        int k = 0;
        
        double d = 0;
        
		for ( int i = 0; i < 3; i++) {
			int dif1 = cached[k+0] == (y[i]     & MASK )?0:1;
			int dif2 = cached[k+1] == (y[i]>> 7 & MASK )?0:1;
			int dif3 = cached[k+2] == (y[i]>>14 & MASK )?0:1;
			int dif4 = cached[k+3] == (y[i]>>21 & MASK )?0:1;

			k = k + 4;
			
			dist += dif1 + dif2 + dif3 + dif4;
		}
	
        for( int i=3; i<6; i++ )
        {
        	d = Math.abs(x[i]-y[i]) / rangos[i];
        	
         	dist += d;
        }
        
//        int xrest[] = ArrayUtils.addAll( enc.decoder( NUM_BITS, x ), x[3], x[4], x[5] );
//        int yrest[] = ArrayUtils.addAll( enc.decoder( NUM_BITS, y ), y[3], y[4], y[5] );
//        
//        VectorUtils.log( xrest );
//        VectorUtils.log( yrest );
//		System.out.println( dist );
//		throw new RuntimeException( "test" );

        return dist;
	}

}
