package com.caciones.btrssi;

import java.io.IOException;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

class ConnectThread extends Thread {

	private final BluetoothSocket mmSocket;
	private final BluetoothDevice mmDevice;

	public ConnectThread(BluetoothDevice device) {
		// Use a temporary object that is later assigned to mmSocket,
		// because mmSocket is final
		BluetoothSocket tmp = null;
		mmDevice = device;
		Log.i(tag, "construct");
		// Get a BluetoothSocket to connect with the given BluetoothDevice
		try {
			// MY_UUID is the app's UUID string, also used by the server
			// code
			tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
		} catch (IOException e) {
			Log.i(tag, "get socket failed");

		}
		mmSocket = tmp;
	}

	public void run() {
		// Cancel discovery because it will slow down the connection
		btAdapter.cancelDiscovery();
		Log.i(tag, "connect - run");
		try {
			// Connect the device through the socket. This will block
			// until it succeeds or throws an exception
			mmSocket.connect();
			Log.i(tag, "connect - succeeded");
		} catch (IOException connectException) {
			Log.i(tag, "connect failed");
			// Unable to connect; close the socket and get out
			try {
				mmSocket.close();
			} catch (IOException closeException) {
			}
			return;
		}

		// Do work to manage the connection (in a separate thread)

		mHandler.obtainMessage(SUCCESS_CONNECT, mmSocket).sendToTarget();
	}

	/** Will cancel an in-progress connection, and close the socket */
	public void cancel() {
		try {
			mmSocket.close();
		} catch (IOException e) {
		}
	}
}