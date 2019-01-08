package com.jaimes.knn.distances.cid;

import java.io.Serializable;

import smile.math.distance.Distance;

public class CIDHEOMDistanceImpl implements Distance<int[]>, Serializable {

	private static final long serialVersionUID = 1L;

	double rangos[] = new double[] {
			0,
			0,
			0,
			1484705-13769,
			99999-0,
			4356-0,
			0,
			0};
	
	@Override
	public double d(int[] x, int[] y) {
		
		if (x.length != y.length)
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));

        double dist = 0;
        
		int v1     = x[ 0]!=y[ 0]?1:0;
		int v2     = x[ 1]!=y[ 1]?1:0;
		double v3  = Math.abs(x[ 2] -y[ 2])/rangos[3];
		int v4     = x[ 3]!=y[ 3]?1:0;
		int v5     = x[ 4]!=y[ 4]?1:0;
		int v6     = x[ 5]!=y[ 5]?1:0;
		int v7     = x[ 6]!=y[ 6]?1:0;
		int v8     = x[ 7]!=y[ 7]?1:0;
		int v9     = x[ 8]!=y[ 8]?1:0;
		int v10    = x[ 9]!=y[ 9]?1:0;
		double v11 = Math.abs(x[10] -y[10])/rangos[4];
		double v12 = Math.abs(x[11] -y[11])/rangos[5];
		int v13    = x[12]!=y[12]?1:0;
		int v14    = x[13]!=y[13]?1:0;
			
		dist = v1+v2+v3+v4+v5+v6+v7+v8+v9+v10+v11+v12+v13+v14;
	
        
//		VectorUtils.log( x );
//		VectorUtils.log( y );
//		System.out.println( dist );
//		throw new RuntimeException( "test" );

		return dist;
	}

}
