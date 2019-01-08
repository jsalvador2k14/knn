package com.jaimes.knn.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

import smile.data.AttributeDataset;
import smile.data.NominalAttribute;
import smile.data.parser.DelimitedTextParser;
import smile.validation.Accuracy;
import smile.validation.ConfusionMatrix;

public class ClassifCommon {
	
	//public static final String TRAIN_DS = "E:/Data - datasets publicos/adult.csv";
	//public static final String TRAIN_DS = "E:/Data - datasets publicos/adult_train.csv";
	//public static final String TEST_DS  = "E:/Data - datasets publicos/adult_test.csv";
	//public static final int CLASS_INDEX = 14;

	//public static final String TRAIN_DS = "E:/Data - datasets publicos/arritmia/arritmia.in";
	//public static final String TEST_DS  = "E:/Data - datasets publicos/arritmia/arritmia.in";
	//public static final int CLASS_INDEX = 160;

	public String TRAIN_DS = "E:/Data - datasets publicos/wbc/wbc_train.in";
	public String TEST_DS  = "E:/Data - datasets publicos/wbc/wbc_test.in";	
	public int CLASS_INDEX = 9;

	protected StopWatch watch = new StopWatch( );
	
	protected double[][] trainX;
	protected int[] trainY;
	
	protected double[][] testX;
	protected int[] testY;
	
	protected NominalAttribute responseAttr;
	
	private String classifName = "";
	
	public ClassifCommon( String trainDs, String testDs, int classIndex ) {
		TRAIN_DS = trainDs;
		TEST_DS = testDs;
		CLASS_INDEX = classIndex;
	}

	public void init( Class<?> clasif ) throws Exception {
		init( clasif, "" );
	}
	
	public void init( Class<?> clasif, String opt ) throws Exception {
	
		classifName = clasif.getSimpleName() + " " + opt;

		System.out.println( "*****************************************************");
		System.out.println( "**                                                 **");
		System.out.println( "**  " + classifName );
		System.out.println( "**                                                 **");
		System.out.println( "*****************************************************");
		
		System.out.println( "Train dataset: " + TRAIN_DS );
		System.out.println( "Test  dataset: " + TEST_DS );
		
		InputStream streamTrain = new FileInputStream( TRAIN_DS );
		InputStream streamTtest = new FileInputStream( TEST_DS );
		
		//InputStream streamTrain = new FileInputStream( DIRECTORY + "arritmia.in" );
		//InputStream streamTtest = new FileInputStream( DIRECTORY + "arritmia.in" );


		DelimitedTextParser parser = new DelimitedTextParser( );
		parser.setResponseIndex( new NominalAttribute("class"), CLASS_INDEX );
		
		parser.setDelimiter( ";" );
		parser.setColumnNames( true );
		parser.setMissingValuePlaceholder( "NA" );
			
		AttributeDataset train = parser.parse( "ADULT Train", streamTrain );
		AttributeDataset test  = parser.parse( "ADULT Test", streamTtest );
		
		responseAttr = (NominalAttribute )train.responseAttribute( );
		
		System.out.println( );
		System.out.print( responseAttr.getName() + "==>Classes: [" );
		for(String s:responseAttr.values() ) {
			System.out.print( s + ", " );
		}
		System.out.println( "]" );
		System.out.println( );


//		System.out.println( train );
//		System.out.println( test );
		
		trainX = train.toArray( new double[train.size()][]);
		trainY = train.toArray( new int[train.size()]);
		
		testX = test.toArray(new double[test.size()][]);
		testY = test.toArray(new int[test.size()]);
	}
	
	protected void summary( int x[], int y[], StopWatch watch, int count ) {
		int numInstances = x.length;
		int errores = 0;
		
		for( int i=0; i<x.length; i++ ) {
			if( x[i]!=y[i] )
			{
				errores++;
			}
		}

		// metricas
		System.out.println( "=====================================================" );
		System.out.println( "Classification Measures" );
		
		System.out.println( ); 
		
		Accuracy cmAccuracy = new Accuracy();
		double accuracy = cmAccuracy.measure( x, y );
		
		System.out.println( "Errores = " + errores + " de " + numInstances  ); 
		System.out.printf( "%s = %.3f\n", cmAccuracy.toString( ), accuracy*100 ); 
		
		ConfusionMatrix cm = new ConfusionMatrix( x, y );
		
		System.out.println( ); 
		System.out.println( toCMString(cm) ); 
		
		System.out.println( "=====================================================" );
        
		long totTime = watch.getTime(TimeUnit.MILLISECONDS);
		
		long miliseconds = totTime / count;
		
        System.out.format( "time Elapsed = %.2f seconds (%d miliseconds)\n",  (float )miliseconds/1000, miliseconds );
	}
	
	protected void summary( int x[], int y[], StopWatch watch )  {
		
		summary(x, y, watch, 1 );
	}
	
	protected void logTrain( ) {
	    System.out.println( );
        System.out.println( "----------------------------------------------" );
	    System.out.println( "Predicting train set..." );
	}
	
	protected void logTest( ) {
        System.out.println( );
        System.out.println( "----------------------------------------------" );
	    System.out.println( "Predicting test set..." );
	}
	
	public String toCMString( ConfusionMatrix cm ) {
		
		int[][] matrix = cm.getMatrix( );
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("ROW=truth and COL=predicted\n");
		
		for(int i = 0; i < matrix.length; i++){
			sb.append(String.format("class %3s |", responseAttr.toString(i)));
			for(int j = 0; j < matrix.length; j++){
				sb.append(String.format("%8d |", matrix[i][j]));
			}
			sb.append("\n");
 		}
		
		return sb.toString().trim();
	}
}
