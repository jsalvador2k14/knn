package com.jaimes.knn.distances.common;

import java.io.Serializable;

import smile.math.distance.Distance;

public class EuclideanIntDistanceImpl implements Distance<int[]>, Serializable {
    
	private static final long serialVersionUID = 1L;
	
	@Override
	public double d(int[] x, int[] y) {
		if (x.length != y.length)
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));

        double dist = 0.0;

        for (int i = 0; i < x.length; i++) {
            double d = x[i] - y[i];
            dist += d * d;
        }
        
        return Math.sqrt(dist);
	}

}
