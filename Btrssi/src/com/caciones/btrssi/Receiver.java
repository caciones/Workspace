package com.caciones.btrssi;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Receiver extends BroadcastReceiver{

	
	
	
	public Receiver(){
		
		
		
	}
	
	
	
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();

		if (BluetoothDevice.ACTION_FOUND.equals(action)) {
			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			devices.add(device);
			String s = "";
			for (int a = 0; a < pairedDevices.size(); a++) {
				if (device.getName().equals(pairedDevices.get(a))) {
					// acrescentar
					s = "(Paired)";
					break;
				}
			}

			listAdapter.add(device.getName() + " " + s + " " + "\n"
					+ device.getAddress());
		}

		else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED
				.equals(action)) {
			// qq coisa
		} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
				.equals(action)) {
			// qq coisa

		} else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
			if (btAdapter.getState() == btAdapter.STATE_OFF) {
				turnOnBT();
			}
		}

	}
	
}
