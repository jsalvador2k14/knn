package com.jaimes.knn.distances.general;

import java.io.Serializable;

import com.jaimes.knn.utils.CompressedEncoder;

import smile.math.distance.Distance;

public class CompressedEuclidianDistance implements Distance<int[]>, Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private int cachedComp[] = null;	
	private int x[] = null;
	private int numBits;
	private CompressedEncoder encoder;
	
	public CompressedEuclidianDistance( int numBits ) {
		this.numBits = numBits;
		this.encoder = new CompressedEncoder();
	}
	
	@Override
	public double d(int[] vec1, int[] vec2) {
		
		if (vec1.length != vec2.length)
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", vec1.length, vec2.length));

		if( vec1!=cachedComp ) {
			cachedComp = vec1;			
			x = encoder.decode( numBits, vec1 );
		}
		
		int[] y = encoder.decode( numBits, vec2 );
		
        double sum = 0;

        for( int i=0; i<x.length; i++ ) {        	
        	int dif = x[i]-y[i];        	
        	sum = sum + dif*dif;
		}
		
		return Math.sqrt(sum);
	}
}
