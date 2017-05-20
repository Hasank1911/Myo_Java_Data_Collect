package com.thalmic.myo.example;

import java.util.ArrayList;
import java.util.Arrays;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.FirmwareVersion;
import com.thalmic.myo.Myo;

public class EmgDataCollector extends AbstractDeviceListener {
	ArrayList<byte[]> list = new ArrayList<byte[]>();
	private byte[] emgSamples; // it is not used
	private volatile boolean ready = false;

	public EmgDataCollector() {
	}

	@Override
	public void onPair(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
		if (emgSamples != null) {
			for (int i = 0; i < emgSamples.length; i++) {
				emgSamples[i] = 0;
			}
		}
	}

	@Override
	public void onEmgData(Myo myo, long timestamp, byte[] emg) {
		this.emgSamples = emg;
		list.add(emg);
		ready = true;
	}

	@Override
	public String toString() { //return Arrays.toString(emgSamples);
		String MultipleData = "";
		for(byte[] data : list) {
			MultipleData = MultipleData + csvFormat(Arrays.toString(data))+"\n";
		}
		MultipleData = MultipleData.substring(0, MultipleData.length()-1);
		list.clear();
		return MultipleData;
	}

	private String csvFormat(String string) {
		String newString = string.replace("[", "");
		newString = newString.replace("]", "");
		newString = newString.replace(",", ";");
		return newString;
	}

	public String getData() {
		if (ready) {
			ready = false;
			return toString();
		} else {
			return null;
		}
	}
}
