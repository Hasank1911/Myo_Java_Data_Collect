//package com.thalmic.myo.example;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.StreamEmgType;
import com.thalmic.myo.example.EmgDataCollector;

public class Test {
	
	private static long maxRunTimeMillis = 1000; // collects data for 1 seconds / 1000 miliseconds
	
	public static void main(String[] args) {
		try {
			Hub hub = new Hub("com.example.emg-data-sample");

			System.out.println("Attempting to find a Myo...");
			Myo myo = hub.waitForMyo(10000);

			if (myo == null) {
				throw new RuntimeException("Unable to find a Myo!");
			}

			System.out.println("Connected to a Myo armband!");
			myo.setStreamEmg(StreamEmgType.STREAM_EMG_ENABLED);
			DeviceListener dataCollector = new EmgDataCollector(); // i have changed the original EmgDataCollector class so that we can get nearly 200 data per second
			hub.addListener(dataCollector);
			
			long time = System.currentTimeMillis();
			
			while (true) {
				hub.run(1); //old version was: hub.run(1000 / 20);   , hub.run(miliseconds);
				String data = ((EmgDataCollector)dataCollector).getData();
				
				if(data != null) { // if it is null, then there aren't any data yet.
					System.out.println(data); //Or you can write it to a file.
				} else {
					
				}
					
				
				if(System.currentTimeMillis() - time > maxRunTimeMillis) break; // 
			}
		} catch (Exception e) {
			System.err.println("Error: ");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
