package com.jaimes.test;

import org.openjdk.jmh.annotations.Benchmark;

public class JMH_HelloWorld {

	@Benchmark
	public void wellHelloThere() {
		// this method was intentionally left blank.
		
//		try {
//			Thread.sleep( 50 );
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) throws Exception {
//        Options opt = new OptionsBuilder()
//                .include(".*" + JMH_HelloWorld.class.getSimpleName() + ".*")
//                .forks(1)
//                .build();
//
//        new Runner(opt).run();
		org.openjdk.jmh.Main.main(args);
    }
	
}
