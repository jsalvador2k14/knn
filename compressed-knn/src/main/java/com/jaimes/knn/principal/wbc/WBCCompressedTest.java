package com.jaimes.knn.principal.wbc;

import java.util.concurrent.TimeUnit;

import com.jaimes.knn.distances.wbc.WBCHammingBitDistanceImpl;
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
 *   utilizar xxMetricImpl   para búsqueda con árboles
 *   utilizar xxDistanceImpl para búsqueda lineal
 * 
 * @author jsalvador
 *
 */
public class WBCCompressedTest extends ClassifCommon {

	public static final int COUNT = 1;
	
	public static final int K = 20;
    public static int kfold = 10;

	public static final String TRAIN_DS = "E:/Data - datasets publicos/wbc/wbc_train.in";
	public static final String TEST_DS  = "E:/Data - datasets publicos/wbc/wbc_test.in";	
	public static final int CLASS_INDEX = 9;
	
	public WBCCompressedTest( ) {
		super( TRAIN_DS, TEST_DS, CLASS_INDEX  );
	}

	public static void main(String[] args) throws Exception
	{
		WBCCompressedTest test = new WBCCompressedTest( );
		
		test.clasif( );
	}
	
	public void clasif( ) throws Exception {
		
		init( KNN.class, " bit Hamming" );
		
	    int[][] train = DoubleUtils.toInt( trainX );
	    int[][] test = DoubleUtils.toInt( testX );
	    
	    WBCHammingBitDistanceImpl distance = new WBCHammingBitDistanceImpl( );
	    
	    int[][] compressedTrain = CompressedWriter.toB2B_WBC( train );
	    int[][] compressedTest = CompressedWriter.toB2B_WBC( test );
	    
//	    CompressedWriter.printWbc( compressedTrain, 10 );
	    
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
	    	double estimations = 0;
	    	long miliseconds = 0;
	    	
	    	for( int i=0; i<COUNT; i++ ) {
	    	    estimations += Validation.cv( kfold, trainer, compressedTrain, trainY );    
	    		
//	    		System.out.println( watch.getTime(TimeUnit.MILLISECONDS) );
	    	}
    		watch.stop();
	    	miliseconds = watch.getTime(TimeUnit.MILLISECONDS)/ COUNT;
	    	
	    	estimations = estimations / COUNT;
			
	        System.out.format( "time elapsed = %.2f seconds (%d miliseconds)\n",  (float )miliseconds/1000, miliseconds );
	        
		    System.out.println( );
		    System.out.format( "Cross validation (%d folds): %.3f", kfold, estimations*100 );
	    }
	}
}
