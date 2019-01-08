package com.jaimes.knn.distances.common;

import java.io.Serializable;

import smile.math.distance.Distance;

public class HammingIntDistanceImpl implements Distance<int[]>, Serializable {
    
	private static final long serialVersionUID = 1L;

	@Override
	public double d(int[] x, int[] y) {
		if (x.length != y.length)
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));

        double dist = 0.0;

        for (int i = 0; i < x.length; i++) {
        	dist += (x[i]!=y[i]?1:0);
        }
        
        //VectorUtils.log( x );
        //VectorUtils.log( y );
		//System.out.println( dist );
        //throw new RuntimeException( "test" );

		 return dist;        
	}

}
