package com.jaimes.knn.principal.wbc;

import java.util.concurrent.TimeUnit;

import com.jaimes.knn.distances.wbc.WBCHammingBitMetricImpl;
import com.jaimes.knn.utils.ClassifCommon;
import com.jaimes.knn.utils.CompressedWriter;
import com.jaimes.knn.utils.DoubleUtils;

import smile.classification.ClassifierTrainer;
import smile.classification.KNN;
import smile.validation.Validation;

/**
 * 
 * Test de KNN
 * 
 * train: 24125
 * test : 6037
 * 
 *   utilizar xxMetricImpl   para busqueda con arboles
 *   utilizar xxDistanceImpl para busqueda lineal
 * 
 * @author jsalvador
 *
 */
public class WBCCoverTreeCompressedTest extends ClassifCommon {

	public static final int COUNT = 1;
	
	public static final int K = 20;
    public static int kfold = 10;

	public static final String TRAIN_DS = "E:/Data - datasets publicos/wbc/wbc_train.in";
	public static final String TEST_DS  = "E:/Data - datasets publicos/wbc/wbc_test.in";	
	public static final String TOTAL_DS = "E:/Data - datasets publicos/wbc/wbc.in";
	
	public static final int CLASS_INDEX = 9;
	
	public WBCCoverTreeCompressedTest( ) {
		super( TOTAL_DS, TRAIN_DS, TEST_DS, CLASS_INDEX  );
	}

	public static void main(String[] args) throws Exception
	{
		WBCCoverTreeCompressedTest test = new WBCCoverTreeCompressedTest( );
		
		test.clasif( );
	}
	
	public void clasif( ) throws Exception {
		
		init( KNN.class, " WBCHammingBitMetric" );
		
	    int[][] train = DoubleUtils.toInt( trainX );
	    int[][] test  = DoubleUtils.toInt( testX );
	    int[][] total = DoubleUtils.toInt( totalX );
	    
	    WBCHammingBitMetricImpl distance = new WBCHammingBitMetricImpl( );
	    
	    int[][] compressedTrain = CompressedWriter.toB2B_WBC( train );
	    int[][] compressedTest  = CompressedWriter.toB2B_WBC( test );
	    int[][] compressedTotal = CompressedWriter.toB2B_WBC( total );
	    
//	    CompressedWriter.printWbc( compressedTotal, 10 );
	    
	    KNN<int[]> knn = new KNN<>( compressedTrain, trainY, distance, K );
	    
	    //-- train
	    logTrain( );
	    watch.start();
	    {
	    	int[] pred = null;
	    	
	    	for( int i=0; i<COUNT; i++ ) {
	    		pred = knn.predict( compressedTest );
	    	}
	        
		    watch.stop();
		    
		    summary( testY, pred, watch, COUNT );
	    }
	    
	    System.out.println( );
	    System.out.println( );
	    System.out.println( "Cross validating..." );

	    ClassifierTrainer<int[]> trainer = new KNN.Trainer<int[]>( distance, K );
	    
	    watch.reset();
	    watch.start();
	    {
    		double estimations = Validation.cv( kfold, trainer, compressedTotal, totalY );    

    		watch.stop();
    		
    		long miliseconds = watch.getTime(TimeUnit.MILLISECONDS);	    	
			
	        System.out.format( "time elapsed = %.2f seconds (%d miliseconds)\n",  (float )miliseconds/1000, miliseconds );
	        
		    System.out.println( );
		    System.out.format( "Cross validation (%d folds): %.3f", kfold, estimations*100 );
	    }
	}
}
