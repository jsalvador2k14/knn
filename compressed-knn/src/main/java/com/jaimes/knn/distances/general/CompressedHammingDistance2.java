package com.jaimes.knn.distances.general;

import java.io.Serializable;

import com.jaimes.knn.utils.CompressedEncoder;

import smile.math.distance.Distance;

public class CompressedHammingDistance2 implements Distance<int[]>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private int numBits;
	private int elementsPerBlock;
	private CompressedEncoder encoder;
	
	public CompressedHammingDistance2( int numBits ) {
		this.numBits = numBits;
		this.elementsPerBlock = 31 / numBits;
		this.encoder = new CompressedEncoder();
	}
	
	@Override
	public double d(int[] vec1, int[] vec2) {

		if (vec1.length != vec2.length)
			throw new IllegalArgumentException( String.format("Arrays have different length: x[%d], y[%d]", vec1.length, vec2.length) );

		
        double sum = 0;
        
        for( int i=0; i<vec1.length; i++ ) {    
        	int z = vec1[i] ^ vec2[i];
        	int mask = encoder.get( numBits );
        	for( int j=0; j<elementsPerBlock; j++ ) {
        		if( (z & mask) !=0 ) {
        			sum = sum + 1;
        		}
        		mask = mask << numBits;
        	}
		}
        
        return sum;
	}

}
