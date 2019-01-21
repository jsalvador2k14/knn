package com.jaimes.knn.principal.cid;

import java.util.concurrent.TimeUnit;

import com.jaimes.knn.distances.cid.CIDHEOMDistanceImpl;
import com.jaimes.knn.utils.ClassifCommon;
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
public class CIDUncompressedTest extends ClassifCommon {

	public static final int COUNT = 1;
	
	public static final int K = 20;
	
    public static int kfold = 10;
    
	
	public static final String TRAIN_DS = "E:/Data - datasets publicos/adult_train.csv";
	public static final String TEST_DS  = "E:/Data - datasets publicos/adult_test.csv";
	
	public static final int CLASS_INDEX = 14;
	
	public CIDUncompressedTest( ) {
		super( TRAIN_DS, TEST_DS, CLASS_INDEX  );
	}
	
	public static void main(String[] args) throws Exception
	{
		CIDUncompressedTest test = new CIDUncompressedTest( );
		
		test.clasif( );
	}
	
	public void clasif( ) throws Exception {
		
		init( KNN.class, " HEOM Int" );
		
	    int[][] train = DoubleUtils.toInt( trainX );
	    int[][] test  = DoubleUtils.toInt( testX );
	    
	    CIDHEOMDistanceImpl distance = new CIDHEOMDistanceImpl( );
	    
	    KNN<int[]> knn = new KNN<>( train, trainY, distance, K );
	    
	    //-- train
	    logTrain( );
	    watch.start();
	    {
	    	int[] pred = null;
	    	
	    	for( int i=0; i<COUNT; i++ ) {
	    		pred = knn.predict( test );
	    	}
	        
		    watch.stop();
		    
		    summary( testY, pred, watch );
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
	    		watch.reset();
	    	    watch.start();
	    	    
		    	estimations += Validation.cv( kfold, trainer, train, trainY );
		    
		    	watch.stop();
		    	
		    	miliseconds += watch.getTime(TimeUnit.MILLISECONDS);
	    	}
	    	estimations = estimations / COUNT;
	    	miliseconds = miliseconds / COUNT;
	    	
	        System.out.format( "time elapsed = %.2f seconds (%d miliseconds)\n",  (float )miliseconds/1000, miliseconds );
	        
		    System.out.println( );
		    System.out.format( "Cross validation (%d folds): %.3f", kfold, estimations*100 );
	    }
	}

}
