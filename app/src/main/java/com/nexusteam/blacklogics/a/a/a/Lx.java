package a.a.a;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
public class Lx {
	
	public static String a() {
		return "include ':app'\r\n";
	}
	
	public static String a(String widgetName) {
		return widgetName.substring(0, 1).toUpperCase() +
		widgetName.substring(1) +
		"Adapter";
	}
	
	public static String a(String componentName, int value) {
		return "public final int REQ_CD_" + componentName.toUpperCase() + " = " + value + ";";
	}
	public static String b(String packageName) {
		return "package " + packageName + ";\r\n" +
		"\r\n" +
		"import android.app.Activity;\r\n" +
		"import android.bluetooth.BluetoothAdapter;\r\n" +
		"import android.bluetooth.BluetoothDevice;\r\n" +
		"import android.content.Intent;\r\n" +
		"\r\n" +
		"import java.util.ArrayList;\r\n" +
		"import java.util.HashMap;\r\n" +
		"import java.util.Set;\r\n" +
		"import java.util.UUID;\r\n" +
		"\r\n" +
		"public class BluetoothConnect {\r\n" +
		"private static final String DEFAULT_UUID = \"00001101-0000-1000-8000-00805F9B34FB\";\r\n" +
		"\r\n" +
		"private Activity activity;\r\n" +
		"\r\n" +
		"private BluetoothAdapter bluetoothAdapter;\r\n" +
		"\r\n" +
		"public BluetoothConnect(Activity activity) {\r\n" +
		"this.activity = activity;\r\n" +
		"this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();\r\n" +
		"}\r\n" +
		"\r\n" +
		"public boolean isBluetoothEnabled() {\r\n" +
		"if (bluetoothAdapter != null) return true;\r\n" +
		"\r\n" +
		"return false;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public boolean isBluetoothActivated() {\r\n" +
		"if (bluetoothAdapter == null) return false;\r\n" +
		"\r\n" +
		"return bluetoothAdapter.isEnabled();\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void activateBluetooth() {\r\n" +
		"Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);\r\n" +
		"activity.startActivity(intent);\r\n" +
		"}\r\n" +
		"\r\n" +
		"public String getRandomUUID() {\r\n" +
		"return String.valueOf(UUID.randomUUID());\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void getPairedDevices(ArrayList<HashMap<String, Object>> results) {\r\n" +
		"Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();\r\n" +
		"\r\n" +
		"if (pairedDevices.size() > 0) {\r\n" +
		"for (BluetoothDevice device : pairedDevices) {\r\n" +
		"HashMap<String, Object> result = new HashMap<>();\r\n" +
		"result.put(\"name\", device.getName());\r\n" +
		"result.put(\"address\", device.getAddress());\r\n" +
		"\r\n" +
		"results.add(result);\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void readyConnection(BluetoothConnectionListener listener, String tag) {\r\n" +
		"if (BluetoothController.getInstance().getState().equals(BluetoothController.STATE_NONE)) {\r\n" +
		"BluetoothController.getInstance().start(this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void readyConnection(BluetoothConnectionListener listener, String uuid, String tag) {\r\n" +
		"if (BluetoothController.getInstance().getState().equals(BluetoothController.STATE_NONE)) {\r\n" +
		"BluetoothController.getInstance().start(this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"\r\n" +
		"public void startConnection(BluetoothConnectionListener listener, String address, String tag) {\r\n" +
		"BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);\r\n" +
		"\r\n" +
		"BluetoothController.getInstance().connect(device, this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void startConnection(BluetoothConnectionListener listener, String uuid, String address, String tag) {\r\n" +
		"BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);\r\n" +
		"\r\n" +
		"BluetoothController.getInstance().connect(device, this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void stopConnection(BluetoothConnectionListener listener, String tag) {\r\n" +
		"BluetoothController.getInstance().stop(this, listener, tag);\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void sendData(BluetoothConnectionListener listener, String data, String tag) {\r\n" +
		"String state = BluetoothController.getInstance().getState();\r\n" +
		"\r\n" +
		"if (!state.equals(BluetoothController.STATE_CONNECTED)) {\r\n" +
		"listener.onConnectionError(tag, state, \"Bluetooth is not connected yet\");\r\n" +
		"return;\r\n" +
		"}\r\n" +
		"\r\n" +
		"BluetoothController.getInstance().write(data.getBytes());\r\n" +
		"}\r\n" +
		"\r\n" +
		"public Activity getActivity() {\r\n" +
		"return activity;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public interface BluetoothConnectionListener {\r\n" +
		"void onConnected(String tag, HashMap<String, Object> deviceData);\r\n" +
		"void onDataReceived(String tag, byte[] data, int bytes);\r\n" +
		"void onDataSent(String tag, byte[] data);\r\n" +
		"void onConnectionError(String tag, String connectionState, String message);\r\n" +
		"void onConnectionStopped(String tag);\r\n" +
		"}\r\n" +
		"}\r\n";
	}
	public static String c(String packageName) {
		return "package " + packageName + ";\r\n" +
		"\r\n" +
		"import android.bluetooth.BluetoothAdapter;\r\n" +
		"import android.bluetooth.BluetoothDevice;\r\n" +
		"import android.bluetooth.BluetoothServerSocket;\r\n" +
		"import android.bluetooth.BluetoothSocket;\r\n" +
		"\r\n" +
		"import java.io.InputStream;\r\n" +
		"import java.io.OutputStream;\r\n" +
		"import java.util.HashMap;\r\n" +
		"import java.util.UUID;\r\n" +
		"\r\n" +
		"public class BluetoothController {\r\n" +
		"public static final String STATE_NONE = \"none\";\r\n" +
		"public static final String STATE_LISTEN = \"listen\";\r\n" +
		"public static final String STATE_CONNECTING = \"connecting\";\r\n" +
		"public static final String STATE_CONNECTED = \"connected\";\r\n" +
		"\r\n" +
		"private AcceptThread acceptThread;\r\n" +
		"private ConnectThread connectThread;\r\n" +
		"private ConnectedThread connectedThread;\r\n" +
		"\r\n" +
		"private String state = STATE_NONE;\r\n" +
		"\r\n" +
		"private static BluetoothController instance;\r\n" +
		"\r\n" +
		"public static synchronized BluetoothController getInstance() {\r\n" +
		"if (instance == null) {\r\n" +
		"instance = new BluetoothController();\r\n" +
		"}\r\n" +
		"\r\n" +
		"return instance;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public synchronized void start(BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\r\n" +
		"if (connectThread != null) {\r\n" +
		"connectThread.cancel();\r\n" +
		"connectThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"if (connectedThread != null) {\r\n" +
		"connectedThread.cancel();\r\n" +
		"connectedThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"if (acceptThread != null) {\r\n" +
		"acceptThread.cancel();\r\n" +
		"acceptThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"acceptThread = new AcceptThread(bluetoothConnect, listener, tag, uuid, bluetoothAdapter);\r\n" +
		"acceptThread.start();}\r\n" +
		"\r\n" +
		"public synchronized void connect(BluetoothDevice device, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\r\n" +
		"if (state.equals(STATE_CONNECTING)) {\r\n" +
		"if (connectThread != null) {\r\n" +
		"connectThread.cancel();\r\n" +
		"connectThread = null;\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"if (connectedThread != null) {\r\n" +
		"connectedThread.cancel();\r\n" +
		"connectedThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"connectThread = new ConnectThread(device, bluetoothConnect, listener, tag, uuid, bluetoothAdapter);\r\n" +
		"connectThread.start();\r\n" +
		"}\r\n" +
		"\r\n" +
		"public synchronized void connected(BluetoothSocket socket, final BluetoothDevice device, BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\r\n" +
		"if (connectThread != null) {\r\n" +
		"connectThread.cancel();\r\n" +
		"connectThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"if (connectedThread != null) {\r\n" +
		"connectedThread.cancel();\r\n" +
		"connectedThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"if (acceptThread != null) {\r\n" +
		"acceptThread.cancel();\r\n" +
		"acceptThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"connectedThread = new ConnectedThread(socket, bluetoothConnect, listener, tag);\r\n" +
		"connectedThread.start();\r\n" +
		"\r\n" +
		"bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"HashMap<String, Object> deviceMap = new HashMap<>();\r\n" +
		"deviceMap.put(\"name\", device.getName());\r\n" +
		"deviceMap.put(\"address\", device.getAddress());\r\n" +
		"\r\n" +
		"listener.onConnected(tag, deviceMap);\r\n" +
		"}\r\n" +
		"});\r\n" +
		"}\r\n" +
		"\r\n" +
		"public synchronized void stop(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\r\n" +
		"if (connectThread != null) {\r\n" +
		"connectThread.cancel();\r\n" +
		"connectThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"if (connectedThread != null) {\r\n" +
		"connectedThread.cancel();\r\n" +
		"connectedThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"if (acceptThread != null) {\r\n" +
		"acceptThread.cancel();\r\n" +
		"acceptThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"state = STATE_NONE;\r\n" +
		"\r\n" +
		"bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"listener.onConnectionStopped(tag);\r\n" +
		"}\r\n" +
		"});\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void write(byte[] out) {\r\n" +
		"ConnectedThread r;\r\n" +
		"\r\n" +
		"synchronized (this) {\r\n" +
		"if (!state.equals(STATE_CONNECTED)) return;\r\n" +
		"r = connectedThread;\r\n" +
		"}\r\n" +
		"\r\n" +
		"r.write(out);\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void connectionFailed(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag, final String message) {\r\n" +
		"state = STATE_NONE;\r\n" +
		"\r\n" +
		"bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"listener.onConnectionError(tag, state, message);\r\n" +
		"}\r\n" +
		"});\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void connectionLost(BluetoothConnect bluetoothConnect, final BluetoothConnect.BluetoothConnectionListener listener, final String tag) {\r\n" +
		"state = STATE_NONE;\r\n" +
		"\r\n" +
		"bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"listener.onConnectionError(tag, state, \"Bluetooth connection is disconnected\");\r\n" +
		"}\r\n" +
		"});\r\n" +
		"}\r\n" +
		"\r\n" +
		"public String getState() {\r\n" +
		"return state;\r\n" +
		"}\r\n" +
		"\r\n" +
		"private class AcceptThread extends Thread {\r\n" +
		"private BluetoothServerSocket serverSocket;\r\n" +
		"\r\n" +
		"private BluetoothConnect bluetoothConnect;\r\n" +
		"private BluetoothConnect.BluetoothConnectionListener listener;\r\n" +
		"private String tag;\r\n" +
		"\r\n" +
		"public AcceptThread(BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\r\n" +
		"this.bluetoothConnect = bluetoothConnect;\r\n" +
		"this.listener = listener;\r\n" +
		"this.tag = tag;\r\n" +
		"\r\n" +
		"try {\r\n" +
		"serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(tag, uuid);\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"}\r\n" +
		"\r\n" +
		"state = STATE_LISTEN;\r\n" +
		"}\r\n" +
		"\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"BluetoothSocket bluetoothSocket;\r\n" +
		"\r\n" +
		"while (!state.equals(STATE_CONNECTED)) {\r\n" +
		"try {\r\n" +
		"bluetoothSocket = serverSocket.accept();\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"break;\r\n" +
		"}\r\n" +
		"\r\n" +
		"if (bluetoothSocket != null) {\r\n" +
		"synchronized (BluetoothController.this) {\r\n" +
		"switch (state) {\r\n" +
		"case STATE_LISTEN:\r\n" +
		"case STATE_CONNECTING:\r\n" +
		"connected(bluetoothSocket, bluetoothSocket.getRemoteDevice(), bluetoothConnect, listener, tag);\r\n" +
		"break;\r\n" +
		"case STATE_NONE:\r\n" +
		"case STATE_CONNECTED:\r\n" +
		"try {\r\n" +
		"bluetoothSocket.close();\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"}\r\n" +
		"break;\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void cancel() {\r\n" +
		"try {\r\n" +
		"serverSocket.close();\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"private class ConnectThread extends Thread {\r\n" +
		"private BluetoothDevice device;\r\n" +
		"private BluetoothSocket socket;\r\n" +
		"\r\n" +
		"private BluetoothConnect bluetoothConnect;\r\n" +
		"private BluetoothConnect.BluetoothConnectionListener listener;\r\n" +
		"private String tag;\r\n" +
		"private BluetoothAdapter bluetoothAdapter;\r\n" +
		"\r\n" +
		"public ConnectThread(BluetoothDevice device, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag, UUID uuid, BluetoothAdapter bluetoothAdapter) {\r\n" +
		"this.device = device;\r\n" +
		"this.bluetoothConnect = bluetoothConnect;\r\n" +
		"this.listener = listener;\r\n" +
		"this.tag = tag;\r\n" +
		"this.bluetoothAdapter = bluetoothAdapter;\r\n" +
		"\r\n" +
		"try {\r\n" +
		"socket = device.createRfcommSocketToServiceRecord(uuid);\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"}\r\n" +
		"\r\n" +
		"state = STATE_CONNECTING;\r\n" +
		"}\r\n" +
		"\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"bluetoothAdapter.cancelDiscovery();\r\n" +
		"\r\n" +
		"try {\r\n" +
		"socket.connect();\r\n" +
		"} catch (Exception e) {\r\n" +
		"try {\r\n" +
		"socket.close();\r\n" +
		"} catch (Exception e2) {\r\n" +
		"e2.printStackTrace();\r\n" +
		"}\r\n" +
		"connectionFailed(bluetoothConnect, listener, tag, e.getMessage());\r\n" +
		"return;\r\n" +
		"}\r\n" +
		"\r\n" +
		"synchronized (BluetoothController.this) {\r\n" +
		"connectThread = null;\r\n" +
		"}\r\n" +
		"\r\n" +
		"connected(socket, device, bluetoothConnect, listener, tag);\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void cancel() {\r\n" +
		"try {\r\n" +
		"socket.close();\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"private class ConnectedThread extends Thread {\r\n" +
		"private BluetoothSocket socket;\r\n" +
		"private InputStream inputStream;\r\n" +
		"private OutputStream outputStream;\r\n" +
		"\r\n" +
		"private BluetoothConnect bluetoothConnect;\r\n" +
		"private BluetoothConnect.BluetoothConnectionListener listener;\r\n" +
		"private String tag;\r\n" +
		"\r\n" +
		"public ConnectedThread(BluetoothSocket socket, BluetoothConnect bluetoothConnect, BluetoothConnect.BluetoothConnectionListener listener, String tag) {\r\n" +
		"this.bluetoothConnect = bluetoothConnect;\r\n" +
		"this.listener = listener;\r\n" +
		"this.tag = tag;\r\n" +
		"\r\n" +
		"this.socket = socket;\r\n" +
		"\r\n" +
		"try {\r\n" +
		"inputStream = socket.getInputStream();\r\n" +
		"outputStream = socket.getOutputStream();\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"}\r\n" +
		"\r\n" +
		"state = STATE_CONNECTED;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void run() {\r\n" +
		"while (state.equals(STATE_CONNECTED)) {\r\n" +
		"try {\r\n" +
		"final byte[] buffer = new byte[1024];\r\n" +
		"final int bytes = inputStream.read(buffer);\r\n" +
		"\r\n" +
		"bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"listener.onDataReceived(tag, buffer, bytes);\r\n" +
		"}\r\n" +
		"});\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"connectionLost(bluetoothConnect, listener, tag);\r\n" +
		"break;\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void write(final byte[] buffer) {\r\n" +
		"try {\r\n" +
		"outputStream.write(buffer);\r\n" +
		"\r\n" +
		"bluetoothConnect.getActivity().runOnUiThread(new Runnable() {\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"listener.onDataSent(tag, buffer);\r\n" +
		"}\r\n" +
		"});\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void cancel() {\r\n" +
		"try {\r\n" +
		"socket.close();\r\n" +
		"} catch (Exception e) {\r\n" +
		"e.printStackTrace();\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n";
	}
	
	public static String e(String packageName) {
		return "package " + packageName + ";\r\n" +
		"\r\n" +
		"import android.content.ContentResolver;\r\n" +
		"import android.content.ContentUris;\r\n" +
		"import android.content.Context;\r\n" +
		"import android.database.Cursor;\r\n" +
		"import android.graphics.Bitmap;\r\n" +
		"import android.graphics.BitmapFactory;\r\n" +
		"import android.graphics.Canvas;\r\n" +
		"import android.graphics.ColorFilter;\r\n" +
		"import android.graphics.ColorMatrix;\r\n" +
		"import android.graphics.ColorMatrixColorFilter;\r\n" +
		"import android.graphics.LightingColorFilter;\r\n" +
		"import android.graphics.Matrix;\r\n" +
		"import android.graphics.Paint;\r\n" +
		"import android.graphics.PorterDuff;\r\n" +
		"import android.graphics.PorterDuffXfermode;\r\n" +
		"import android.graphics.Rect;\r\n" +
		"import android.graphics.RectF;\r\n" +
		"import android.media.ExifInterface;\r\n" +
		"import android.net.Uri;\r\n" +
		"import android.os.Environment;\r\n" +
		"import android.provider.DocumentsContract;\r\n" +
		"import android.provider.MediaStore;\r\n" +
		"import android.text.TextUtils;\r\n" +
		"\r\n" +
		"import java.io.File;\r\n" +
		"import java.io.FileInputStream;\r\n" +
		"import java.io.FileOutputStream;\r\n" +
		"import java.io.FileReader;\r\n" +
		"import java.io.FileWriter;\r\n" +
		"import java.io.IOException;\r\n" +
		"import java.net.URLDecoder;\r\n" +
		"import java.text.SimpleDateFormat;\r\n" +
		"import java.util.ArrayList;\r\n" +
		"import java.util.Date;\r\n" +
		"\r\n" +
		"public class FileUtil {\r\n" +
		"\r\n" +
		"    private static void createNewFile(String path) {\r\n" +
		"        int lastSep = path.lastIndexOf(File.separator);\r\n" +
		"        if (lastSep > 0) {\r\n" +
		"            String dirPath = path.substring(0, lastSep);\r\n" +
		"            makeDir(dirPath);\r\n" +
		"        }\r\n" +
		"\r\n" +
		"        File file = new File(path);\r\n" +
		"\r\n" +
		"        try {\r\n" +
		"            if (!file.exists()) file.createNewFile();\r\n" +
		"        } catch (IOException e) {\r\n" +
		"            e.printStackTrace();\r\n" +
		"        }\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static String readFile(String path) {\r\n" +
		"        createNewFile(path);\r\n" +
		"\r\n" +
		"        StringBuilder sb = new StringBuilder();\r\n" +
		"        FileReader fr = null;\r\n" +
		"        try {\r\n" +
		"            fr = new FileReader(new File(path));\r\n" +
		"\r\n" +
		"            char[] buff = new char[1024];\r\n" +
		"            int length = 0;\r\n" +
		"\r\n" +
		"            while ((length = fr.read(buff)) > 0) {\r\n" +
		"                sb.append(new String(buff, 0, length));\r\n" +
		"            }\r\n" +
		"        } catch (IOException e) {\r\n" +
		"            e.printStackTrace();\r\n" +
		"        } finally {\r\n" +
		"            if (fr != null) {\r\n" +
		"                try {\r\n" +
		"                    fr.close();\r\n" +
		"                } catch (Exception e) {\r\n" +
		"                    e.printStackTrace();\r\n" +
		"                }\r\n" +
		"            }\r\n" +
		"        }\r\n" +
		"\r\n" +
		"        return sb.toString();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void writeFile(String path, String str) {\r\n" +
		"        createNewFile(path);\r\n" +
		"        FileWriter fileWriter = null;\r\n" +
		"\r\n" +
		"        try {\r\n" +
		"            fileWriter = new FileWriter(new File(path), false);\r\n" +
		"            fileWriter.write(str);\r\n" +
		"            fileWriter.flush();\r\n" +
		"        } catch (IOException e) {\r\n" +
		"            e.printStackTrace();\r\n" +
		"        } finally {\r\n" +
		"            try {\r\n" +
		"                if (fileWriter != null)\r\n" +
		"                    fileWriter.close();\r\n" +
		"            } catch (IOException e) {\r\n" +
		"                e.printStackTrace();\r\n" +
		"            }\r\n" +
		"        }\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void copyFile(String sourcePath, String destPath) {\r\n" +
		"        if (!isExistFile(sourcePath)) return;\r\n" +
		"        createNewFile(destPath);\r\n" +
		"\r\n" +
		"        FileInputStream fis = null;\r\n" +
		"        FileOutputStream fos = null;\r\n" +
		"\r\n" +
		"        try {\r\n" +
		"            fis = new FileInputStream(sourcePath);\r\n" +
		"            fos = new FileOutputStream(destPath, false);\r\n" +
		"\r\n" +
		"            byte[] buff = new byte[1024];\r\n" +
		"            int length = 0;\r\n" +
		"\r\n" +
		"            while ((length = fis.read(buff)) > 0) {\r\n" +
		"                fos.write(buff, 0, length);\r\n" +
		"            }\r\n" +
		"        } catch (IOException e) {\r\n" +
		"            e.printStackTrace();\r\n" +
		"        } finally {\r\n" +
		"            if (fis != null) {\r\n" +
		"                try {\r\n" +
		"                    fis.close();\r\n" +
		"                } catch (IOException e) {\r\n" +
		"                    e.printStackTrace();\r\n" +
		"                }\r\n" +
		"            }\r\n" +
		"            if (fos != null) {\r\n" +
		"                try {\r\n" +
		"                    fos.close();\r\n" +
		"                } catch (IOException e) {\r\n" +
		"                    e.printStackTrace();\r\n" +
		"                }\r\n" +
		"            }\r\n" +
		"        }\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void copyDir(String oldPath, String newPath) {\r\n" +
		"        File oldFile = new File(oldPath);\r\n" +
		"        File[] files = oldFile.listFiles();\r\n" +
		"        File newFile = new File(newPath);\r\n" +
		"        if (!newFile.exists()) {\r\n" +
		"            newFile.mkdirs();\r\n" +
		"        }\r\n" +
		"        for (File file : files) {\r\n" +
		"            if (file.isFile()) {\r\n" +
		"                copyFile(file.getPath(), newPath + \"/\" + file.getName());\r\n" +
		"            } else if (file.isDirectory()) {\r\n" +
		"                copyDir(file.getPath(), newPath + \"/\" + file.getName());\r\n" +
		"            }\r\n" +
		"        }\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void moveFile(String sourcePath, String destPath) {\r\n" +
		"        copyFile(sourcePath, destPath);\r\n" +
		"        deleteFile(sourcePath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void deleteFile(String path) {\r\n" +
		"        File file = new File(path);\r\n" +
		"\r\n" +
		"        if (!file.exists()) return;\r\n" +
		"\r\n" +
		"        if (file.isFile()) {\r\n" +
		"            file.delete();\r\n" +
		"            return;\r\n" +
		"        }\r\n" +
		"\r\n" +
		"        File[] fileArr = file.listFiles();\r\n" +
		"\r\n" +
		"        if (fileArr != null) {\r\n" +
		"            for (File subFile : fileArr) {\r\n" +
		"                if (subFile.isDirectory()) {\r\n" +
		"                    deleteFile(subFile.getAbsolutePath());\r\n" +
		"                }\r\n" +
		"\r\n" +
		"                if (subFile.isFile()) {\r\n" +
		"                    subFile.delete();\r\n" +
		"                }\r\n" +
		"            }\r\n" +
		"        }\r\n" +
		"\r\n" +
		"        file.delete();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static boolean isExistFile(String path) {\r\n" +
		"        File file = new File(path);\r\n" +
		"        return file.exists();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void makeDir(String path) {\r\n" +
		"        if (!isExistFile(path)) {\r\n" +
		"            File file = new File(path);\r\n" +
		"            file.mkdirs();\r\n" +
		"        }\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void listDir(String path, ArrayList<String> list) {\r\n" +
		"        File dir = new File(path);\r\n" +
		"        if (!dir.exists() || dir.isFile()) return;\r\n" +
		"\r\n" +
		"        File[] listFiles = dir.listFiles();\r\n" +
		"        if (listFiles == null || listFiles.length <= 0) return;\r\n" +
		"\r\n" +
		"        if (list == null) return;\r\n" +
		"        list.clear();\r\n" +
		"        for (File file : listFiles) {\r\n" +
		"            list.add(file.getAbsolutePath());\r\n" +
		"        }\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static boolean isDirectory(String path) {\r\n" +
		"        if (!isExistFile(path)) return false;\r\n" +
		"        return new File(path).isDirectory();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static boolean isFile(String path) {\r\n" +
		"        if (!isExistFile(path)) return false;\r\n" +
		"        return new File(path).isFile();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static long getFileLength(String path) {\r\n" +
		"        if (!isExistFile(path)) return 0;\r\n" +
		"        return new File(path).length();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static String getExternalStorageDir() {\r\n" +
		"        return Environment.getExternalStorageDirectory().getAbsolutePath();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static String getPackageDataDir(Context context) {\r\n" +
		"        return context.getExternalFilesDir(null).getAbsolutePath();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static String getPublicDir(String type) {\r\n" +
		"        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static String convertUriToFilePath(final Context context, final Uri uri) {\r\n" +
		"        String path = null;\r\n" +
		"        if (DocumentsContract.isDocumentUri(context, uri)) {\r\n" +
		"            if (isExternalStorageDocument(uri)) {\r\n" +
		"                final String docId = DocumentsContract.getDocumentId(uri);\r\n" +
		"                final String[] split = docId.split(\":\");\r\n" +
		"                final String type = split[0];\r\n" +
		"\r\n" +
		"                if (\"primary\".equalsIgnoreCase(type)) {\r\n" +
		"                    path = Environment.getExternalStorageDirectory() + \"/\" + split[1];\r\n" +
		"                }\r\n" +
		"            } else if (isDownloadsDocument(uri)) {\r\n" +
		"                final String id = DocumentsContract.getDocumentId(uri);\r\n" +
		"\r\n" +
		"                if (!TextUtils.isEmpty(id)) {\r\n" +
		"                    if (id.startsWith(\"raw:\")) {\r\n" +
		"                        return id.replaceFirst(\"raw:\", \"\");\r\n" +
		"                    }\r\n" +
		"                }\r\n" +
		"\r\n" +
		"                final Uri contentUri = ContentUris\r\n" +
		"                        .withAppendedId(Uri.parse(\"content://downloads/public_downloads\"), Long.valueOf(id));\r\n" +
		"\r\n" +
		"                path = getDataColumn(context, contentUri, null, null);\r\n" +
		"            } else if (isMediaDocument(uri)) {\r\n" +
		"                final String docId = DocumentsContract.getDocumentId(uri);\r\n" +
		"                final String[] split = docId.split(\":\");\r\n" +
		"                final String type = split[0];\r\n" +
		"\r\n" +
		"                Uri contentUri = null;\r\n" +
		"                if (\"image\".equals(type)) {\r\n" +
		"                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;\r\n" +
		"                } else if (\"video\".equals(type)) {\r\n" +
		"                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;\r\n" +
		"                } else if (\"audio\".equals(type)) {\r\n" +
		"                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;\r\n" +
		"                }\r\n" +
		"\r\n" +
		"                final String selection = \"_id=?\";\r\n" +
		"                final String[] selectionArgs = new String[]{\r\n" +
		"                        split[1]\r\n" +
		"                };\r\n" +
		"\r\n" +
		"                path = getDataColumn(context, contentUri, selection, selectionArgs);\r\n" +
		"            }\r\n" +
		"        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {\r\n" +
		"            path = getDataColumn(context, uri, null, null);\r\n" +
		"        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {\r\n" +
		"            path = uri.getPath();\r\n" +
		"        }\r\n" +
		"\r\n" +
		"        if (path != null) {\r\n" +
		"            try {\r\n" +
		"                return URLDecoder.decode(path, \"UTF-8\");\r\n" +
		"            } catch(Exception e) {\r\n" +
		"                return null;\r\n" +
		"            }\r\n" +
		"        }\r\n" +
		"        return null;\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {\r\n" +
		"        final String column = MediaStore.Images.Media.DATA;\r\n" +
		"        final String[] projection = {\r\n" +
		"                column\r\n" +
		"        };\r\n" +
		"\r\n" +
		"        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {\r\n" +
		"            if (cursor != null && cursor.moveToFirst()) {\r\n" +
		"                final int column_index = cursor.getColumnIndexOrThrow(column);\r\n" +
		"                return cursor.getString(column_index);\r\n" +
		"            }\r\n" +
		"        } catch (Exception e) {\r\n" +
		"\r\n" +
		"        }\r\n" +
		"        return null;\r\n" +
		"    }\r\n" +
		"\r\n" +
		"\r\n" +
		"    private static boolean isExternalStorageDocument(Uri uri) {\r\n" +
		"        return \"com.android.externalstorage.documents\".equals(uri.getAuthority());\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    private static boolean isDownloadsDocument(Uri uri) {\r\n" +
		"        return \"com.android.providers.downloads.documents\".equals(uri.getAuthority());\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    private static boolean isMediaDocument(Uri uri) {\r\n" +
		"        return \"com.android.providers.media.documents\".equals(uri.getAuthority());\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    private static void saveBitmap(Bitmap bitmap, String destPath) {\r\n" +
		"        FileUtil.createNewFile(destPath);\r\n" +
		"        try (FileOutputStream out = new FileOutputStream(new File(destPath))) {\r\n" +
		"            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);\r\n" +
		"        } catch (Exception e) {\r\n" +
		"            e.printStackTrace();\r\n" +
		"        }\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static Bitmap getScaledBitmap(String path, int max) {\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(path);\r\n" +
		"\r\n" +
		"        int width = src.getWidth();\r\n" +
		"        int height = src.getHeight();\r\n" +
		"        float rate = 0.0f;\r\n" +
		"\r\n" +
		"        if (width > height) {\r\n" +
		"            rate = max / (float) width;\r\n" +
		"            height = (int) (height * rate);\r\n" +
		"            width = max;\r\n" +
		"        } else {\r\n" +
		"            rate = max / (float) height;\r\n" +
		"            width = (int) (width * rate);\r\n" +
		"            height = max;\r\n" +
		"        }\r\n" +
		"\r\n" +
		"        return Bitmap.createScaledBitmap(src, width, height, true);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {\r\n" +
		"        final int width = options.outWidth;\r\n" +
		"        final int height = options.outHeight;\r\n" +
		"        int inSampleSize = 1;\r\n" +
		"\r\n" +
		"        if (height > reqHeight || width > reqWidth) {\r\n" +
		"            final int halfHeight = height / 2;\r\n" +
		"            final int halfWidth = width / 2;\r\n" +
		"\r\n" +
		"            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {\r\n" +
		"                inSampleSize *= 2;\r\n" +
		"            }\r\n" +
		"        }\r\n" +
		"\r\n" +
		"        return inSampleSize;\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {\r\n" +
		"        final BitmapFactory.Options options = new BitmapFactory.Options();\r\n" +
		"        options.inJustDecodeBounds = true;\r\n" +
		"        BitmapFactory.decodeFile(path, options);\r\n" +
		"\r\n" +
		"        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);\r\n" +
		"\r\n" +
		"        options.inJustDecodeBounds = false;\r\n" +
		"        return BitmapFactory.decodeFile(path, options);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void resizeBitmapFileRetainRatio(String fromPath, String destPath, int max) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap bitmap = getScaledBitmap(fromPath, max);\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void resizeBitmapFileToSquare(String fromPath, String destPath, int max) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"        Bitmap bitmap = Bitmap.createScaledBitmap(src, max, max, true);\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void resizeBitmapFileToCircle(String fromPath, String destPath) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(),\r\n" +
		"                src.getHeight(), Bitmap.Config.ARGB_8888);\r\n" +
		"        Canvas canvas = new Canvas(bitmap);\r\n" +
		"\r\n" +
		"        final int color = 0xff424242;\r\n" +
		"        final Paint paint = new Paint();\r\n" +
		"        final Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());\r\n" +
		"\r\n" +
		"        paint.setAntiAlias(true);\r\n" +
		"        canvas.drawARGB(0, 0, 0, 0);\r\n" +
		"        paint.setColor(color);\r\n" +
		"        canvas.drawCircle(src.getWidth() / 2, src.getHeight() / 2,\r\n" +
		"                src.getWidth() / 2, paint);\r\n" +
		"        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));\r\n" +
		"        canvas.drawBitmap(src, rect, rect, paint);\r\n" +
		"\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void resizeBitmapFileWithRoundedBorder(String fromPath, String destPath, int pixels) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src\r\n" +
		"                .getHeight(), Bitmap.Config.ARGB_8888);\r\n" +
		"        Canvas canvas = new Canvas(bitmap);\r\n" +
		"\r\n" +
		"        final int color = 0xff424242;\r\n" +
		"        final Paint paint = new Paint();\r\n" +
		"        final Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());\r\n" +
		"        final RectF rectF = new RectF(rect);\r\n" +
		"        final float roundPx = pixels;\r\n" +
		"\r\n" +
		"        paint.setAntiAlias(true);\r\n" +
		"        canvas.drawARGB(0, 0, 0, 0);\r\n" +
		"        paint.setColor(color);\r\n" +
		"        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);\r\n" +
		"\r\n" +
		"        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));\r\n" +
		"        canvas.drawBitmap(src, rect, rect, paint);\r\n" +
		"\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void cropBitmapFileFromCenter(String fromPath, String destPath, int w, int h) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"\r\n" +
		"        int width = src.getWidth();\r\n" +
		"        int height = src.getHeight();\r\n" +
		"\r\n" +
		"        if (width < w && height < h) return;\r\n" +
		"\r\n" +
		"        int x = 0;\r\n" +
		"        int y = 0;\r\n" +
		"\r\n" +
		"        if (width > w) x = (width - w) / 2;\r\n" +
		"\r\n" +
		"        if (height > h) y = (height - h) / 2;\r\n" +
		"\r\n" +
		"        int cw = w;\r\n" +
		"        int ch = h;\r\n" +
		"\r\n" +
		"        if (w > width) cw = width;\r\n" +
		"\r\n" +
		"        if (h > height) ch = height;\r\n" +
		"\r\n" +
		"        Bitmap bitmap = Bitmap.createBitmap(src, x, y, cw, ch);\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void rotateBitmapFile(String fromPath, String destPath, float angle) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"        Matrix matrix = new Matrix();\r\n" +
		"        matrix.postRotate(angle);\r\n" +
		"        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void scaleBitmapFile(String fromPath, String destPath, float x, float y) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"        Matrix matrix = new Matrix();\r\n" +
		"        matrix.postScale(x, y);\r\n" +
		"\r\n" +
		"        int w = src.getWidth();\r\n" +
		"        int h = src.getHeight();\r\n" +
		"\r\n" +
		"        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void skewBitmapFile(String fromPath, String destPath, float x, float y) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"        Matrix matrix = new Matrix();\r\n" +
		"        matrix.postSkew(x, y);\r\n" +
		"\r\n" +
		"        int w = src.getWidth();\r\n" +
		"        int h = src.getHeight();\r\n" +
		"\r\n" +
		"        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, w, h, matrix, true);\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void setBitmapFileColorFilter(String fromPath, String destPath, int color) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0,\r\n" +
		"                src.getWidth() - 1, src.getHeight() - 1);\r\n" +
		"        Paint p = new Paint();\r\n" +
		"        ColorFilter filter = new LightingColorFilter(color, 1);\r\n" +
		"        p.setColorFilter(filter);\r\n" +
		"        Canvas canvas = new Canvas(bitmap);\r\n" +
		"        canvas.drawBitmap(bitmap, 0, 0, p);\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void setBitmapFileBrightness(String fromPath, String destPath, float brightness) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"        ColorMatrix cm = new ColorMatrix(new float[]\r\n" +
		"                {\r\n" +
		"                        1, 0, 0, 0, brightness,\r\n" +
		"                        0, 1, 0, 0, brightness,\r\n" +
		"                        0, 0, 1, 0, brightness,\r\n" +
		"                        0, 0, 0, 1, 0\r\n" +
		"                });\r\n" +
		"\r\n" +
		"        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());\r\n" +
		"        Canvas canvas = new Canvas(bitmap);\r\n" +
		"        Paint paint = new Paint();\r\n" +
		"        paint.setColorFilter(new ColorMatrixColorFilter(cm));\r\n" +
		"        canvas.drawBitmap(src, 0, 0, paint);\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void setBitmapFileContrast(String fromPath, String destPath, float contrast) {\r\n" +
		"        if (!isExistFile(fromPath)) return;\r\n" +
		"        Bitmap src = BitmapFactory.decodeFile(fromPath);\r\n" +
		"        ColorMatrix cm = new ColorMatrix(new float[]\r\n" +
		"                {\r\n" +
		"                        contrast, 0, 0, 0, 0,\r\n" +
		"                        0, contrast, 0, 0, 0,\r\n" +
		"                        0, 0, contrast, 0, 0,\r\n" +
		"                        0, 0, 0, 1, 0\r\n" +
		"                });\r\n" +
		"\r\n" +
		"        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());\r\n" +
		"        Canvas canvas = new Canvas(bitmap);\r\n" +
		"        Paint paint = new Paint();\r\n" +
		"        paint.setColorFilter(new ColorMatrixColorFilter(cm));\r\n" +
		"        canvas.drawBitmap(src, 0, 0, paint);\r\n" +
		"\r\n" +
		"        saveBitmap(bitmap, destPath);\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static int getJpegRotate(String filePath) {\r\n" +
		"        int rotate = 0;\r\n" +
		"        try {\r\n" +
		"            ExifInterface exif = new ExifInterface(filePath);\r\n" +
		"            int iOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);\r\n" +
		"\r\n" +
		"            switch (iOrientation) {\r\n" +
		"                case ExifInterface.ORIENTATION_ROTATE_90:\r\n" +
		"                    rotate = 90;\r\n" +
		"                    break;\r\n" +
		"\r\n" +
		"                case ExifInterface.ORIENTATION_ROTATE_180:\r\n" +
		"                    rotate = 180;\r\n" +
		"                    break;\r\n" +
		"\r\n" +
		"                case ExifInterface.ORIENTATION_ROTATE_270:\r\n" +
		"                    rotate = 270;\r\n" +
		"                    break;\r\n" +
		"            }\r\n" +
		"        } catch (IOException e) {\r\n" +
		"            return 0;\r\n" +
		"        }\r\n" +
		"\r\n" +
		"        return rotate;\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static File createNewPictureFile(Context context) {\r\n" +
		"        SimpleDateFormat date = new SimpleDateFormat(\"yyyyMMdd_HHmmss\");\r\n" +
		"        String fileName = date.format(new Date()) + \".jpg\";\r\n" +
		"        return new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + fileName);\r\n" +
		"    }\r\n" +
		"}\r\n";
	}
	
	public static String g(String packageName) {
		return "package " + packageName + ";\r\n" +
		"\r\n" +
		"import com.google.gson.Gson;\r\n" +
		"\r\n" +
		"import java.io.IOException;\r\n" +
		"import java.security.SecureRandom;\r\n" +
		"import java.security.cert.CertificateException;\r\n" +
		"import java.security.cert.X509Certificate;\r\n" +
		"import java.util.HashMap;\r\n" +
		"import java.util.concurrent.TimeUnit;\r\n" +
		"\r\n" +
		"import javax.net.ssl.HostnameVerifier;\r\n" +
		"import javax.net.ssl.SSLContext;\r\n" +
		"import javax.net.ssl.SSLSession;\r\n" +
		"import javax.net.ssl.SSLSocketFactory;\r\n" +
		"import javax.net.ssl.TrustManager;\r\n" +
		"import javax.net.ssl.X509TrustManager;\r\n" +
		"\r\n" +
		"import okhttp3.Call;\r\n" +
		"import okhttp3.Callback;\r\n" +
		"import okhttp3.FormBody;\r\n" +
		"import okhttp3.Headers;\r\n" +
		"import okhttp3.HttpUrl;\r\n" +
		"import okhttp3.MediaType;\r\n" +
		"import okhttp3.OkHttpClient;\r\n" +
		"import okhttp3.Request;\r\n" +
		"import okhttp3.RequestBody;\r\n" +
		"import okhttp3.Response;\r\n" +
		"\r\n" +
		"public class RequestNetworkController {\r\n" +
		"public static final String GET = \"GET\";\r\n" +
		"public static final String POST = \"POST\";\r\n" +
		"public static final String PUT = \"PUT\";\r\n" +
		"public static final String DELETE = \"DELETE\";\r\n" +
		"\r\n" +
		"public static final int REQUEST_PARAM = 0;\r\n" +
		"public static final int REQUEST_BODY = 1;\r\n" +
		"\r\n" +
		"private static final int SOCKET_TIMEOUT = 15000;\r\n" +
		"private static final int READ_TIMEOUT = 25000;\r\n" +
		"\r\n" +
		"protected OkHttpClient client;\r\n" +
		"\r\n" +
		"private static RequestNetworkController mInstance;\r\n" +
		"\r\n" +
		"public static synchronized RequestNetworkController getInstance() {\r\n" +
		"if(mInstance == null) {\r\n" +
		"mInstance = new RequestNetworkController();\r\n" +
		"}\r\n" +
		"return mInstance;\r\n" +
		"}\r\n" +
		"\r\n" +
		"private OkHttpClient getClient() {\r\n" +
		"if (client == null) {\r\n" +
		"OkHttpClient.Builder builder = new OkHttpClient.Builder();\r\n" +
		"\r\n" +
		"try {\r\n" +
		"final TrustManager[] trustAllCerts = new TrustManager[]{\r\n" +
		"new X509TrustManager() {\r\n" +
		"@Override\r\n" +
		"public void checkClientTrusted(X509Certificate[] chain, String authType)\r\n" +
		"throws CertificateException {\r\n" +
		"}\r\n" +
		"\r\n" +
		"@Override\r\n" +
		"public void checkServerTrusted(X509Certificate[] chain, String authType)\r\n" +
		"throws CertificateException {\r\n" +
		"}\r\n" +
		"\r\n" +
		"@Override\r\n" +
		"public X509Certificate[] getAcceptedIssuers() {\r\n" +
		"return new X509Certificate[]{};\r\n" +
		"}\r\n" +
		"}\r\n" +
		"};\r\n" +
		"\r\n" +
		"final SSLContext sslContext = SSLContext.getInstance(\"TLS\");\r\n" +
		"sslContext.init(null, trustAllCerts, new SecureRandom());\r\n" +
		"final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();\r\n" +
		"builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);\r\n" +
		"builder.connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);\r\n" +
		"builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);\r\n" +
		"builder.writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);\r\n" +
		"builder.hostnameVerifier(new HostnameVerifier() {\r\n" +
		"@Override\r\n" +
		"public boolean verify(String hostname, SSLSession session) {\r\n" +
		"return true;\r\n" +
		"}\r\n" +
		"});\r\n" +
		"} catch (Exception e) {\r\n" +
		"}\r\n" +
		"\r\n" +
		"client = builder.build();\r\n" +
		"}\r\n" +
		"\r\n" +
		"return client;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void execute(final RequestNetwork requestNetwork, String method, String url, final String tag, final RequestNetwork.RequestListener requestListener) {\r\n" +
		"Request.Builder reqBuilder = new Request.Builder();\r\n" +
		"Headers.Builder headerBuilder = new Headers.Builder();\r\n" +
		"\r\n" +
		"if (requestNetwork.getHeaders().size() > 0) {\r\n" +
		"HashMap<String, Object> headers = requestNetwork.getHeaders();\r\n" +
		"\r\n" +
		"for (HashMap.Entry<String, Object> header : headers.entrySet()) {\r\n" +
		"headerBuilder.add(header.getKey(), String.valueOf(header.getValue()));\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"try {\r\n" +
		"if (requestNetwork.getRequestType() == REQUEST_PARAM) {\r\n" +
		"if (method.equals(GET)) {\r\n" +
		"HttpUrl.Builder httpBuilder;\r\n" +
		"\r\n" +
		"try {\r\n" +
		"httpBuilder = HttpUrl.parse(url).newBuilder();\r\n" +
		"} catch (NullPointerException ne) {\r\n" +
		"throw new NullPointerException(\"unexpected url: \" + url);\r\n" +
		"}\r\n" +
		"\r\n" +
		"if (requestNetwork.getParams().size() > 0) {\r\n" +
		"HashMap<String, Object> params = requestNetwork.getParams();\r\n" +
		"\r\n" +
		"for (HashMap.Entry<String, Object> param : params.entrySet()) {\r\n" +
		"httpBuilder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"reqBuilder.url(httpBuilder.build()).headers(headerBuilder.build()).get();\r\n" +
		"} else {\r\n" +
		"FormBody.Builder formBuilder = new FormBody.Builder();\r\n" +
		"if (requestNetwork.getParams().size() > 0) {\r\n" +
		"HashMap<String, Object> params = requestNetwork.getParams();\r\n" +
		"\r\n" +
		"for (HashMap.Entry<String, Object> param : params.entrySet()) {\r\n" +
		"formBuilder.add(param.getKey(), String.valueOf(param.getValue()));\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"RequestBody reqBody = formBuilder.build();\r\n" +
		"\r\n" +
		"reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);\r\n" +
		"}\r\n" +
		"} else {\r\n" +
		"RequestBody reqBody = RequestBody.create(MediaType.parse(\"application/json\"), new Gson().toJson(requestNetwork.getParams()));\r\n" +
		"\r\n" +
		"if (method.equals(GET)) {\r\n" +
		"reqBuilder.url(url).headers(headerBuilder.build()).get();\r\n" +
		"} else {\r\n" +
		"reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);\r\n" +
		"}\r\n" +
		"}\r\n" +
		"\r\n" +
		"Request req = reqBuilder.build();\r\n" +
		"\r\n" +
		"getClient().newCall(req).enqueue(new Callback() {\r\n" +
		"@Override\r\n" +
		"public void onFailure(Call call, final IOException e) {\r\n" +
		"requestNetwork.getActivity().runOnUiThread(new Runnable() {\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"requestListener.onErrorResponse(tag, e.getMessage());\r\n" +
		"}\r\n" +
		"});\r\n" +
		"}\r\n" +
		"\r\n" +
		"@Override\r\n" +
		"public void onResponse(Call call, final Response response) throws IOException {\r\n" +
		"final String responseBody = response.body().string().trim();\r\n" +
		"requestNetwork.getActivity().runOnUiThread(new Runnable() {\r\n" +
		"@Override\r\n" +
		"public void run() {\r\n" +
		"Headers b = response.headers();\r\n" +
		"HashMap<String, Object> map = new HashMap<>();\r\n" +
		"for (String s : b.names()) {\r\n" +
		"map.put(s, b.get(s) != null ? b.get(s) : \"null\");\r\n" +
		"}\r\n" +
		"requestListener.onResponse(tag, responseBody, map);\r\n" +
		"}\r\n" +
		"});\r\n" +
		"}\r\n" +
		"});\r\n" +
		"} catch (Exception e) {\r\n" +
		"requestListener.onErrorResponse(tag, e.getMessage());\r\n" +
		"}\r\n" +
		"}\r\n" +
		"}\r\n";
	}
	
	public static String h(String packageName) {
		return "package " + packageName + ";\r\n" +
		"\r\n" +
		"import android.app.Activity;\r\n" +
		"\r\n" +
		"import java.util.HashMap;\r\n" +
		"\r\n" +
		"public class RequestNetwork {\r\n" +
		"private HashMap<String, Object> params = new HashMap<>();\r\n" +
		"private HashMap<String, Object> headers = new HashMap<>();\r\n" +
		"\r\n" +
		"private Activity activity;\r\n" +
		"\r\n" +
		"private int requestType = 0;\r\n" +
		"\r\n" +
		"public RequestNetwork(Activity activity) {\r\n" +
		"this.activity = activity;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void setHeaders(HashMap<String, Object> headers) {\r\n" +
		"this.headers = headers;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void setParams(HashMap<String, Object> params, int requestType) {\r\n" +
		"this.params = params;\r\n" +
		"this.requestType = requestType;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public HashMap<String, Object> getParams() {\r\n" +
		"return params;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public HashMap<String, Object> getHeaders() {\r\n" +
		"return headers;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public Activity getActivity() {\r\n" +
		"return activity;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public int getRequestType() {\r\n" +
		"return requestType;\r\n" +
		"}\r\n" +
		"\r\n" +
		"public void startRequestNetwork(String method, String url, String tag, RequestListener requestListener) {\r\n" +
		"RequestNetworkController.getInstance().execute(this, method, url, tag, requestListener);\r\n" +
		"}\r\n" +
		"\r\n" +
		"public interface RequestListener {\r\n" +
		"public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders);\r\n" +
		"public void onErrorResponse(String tag, String message);\r\n" +
		"}\r\n" +
		"}\r\n";
	}
	
	public static String i(String packageName) {
		return "package " + packageName + ";\r\n" +
		"import android.app.*;\r\n" +
		"import android.content.*;\r\n" +
		"import android.graphics.drawable.*;\r\n" +
		"import android.net.*;\r\n" +
		"import android.util.*;\r\n" +
		"import android.view.*;\r\n" +
		"import android.view.inputmethod.*;\r\n" +
		"import android.widget.*;\r\n" +
		"\r\n" +
		"import java.io.*;\r\n" +
		"import java.util.*;\n" +
		"\r\n" +
		"public class BlackLogicsUtil {\r\n" +
		"\r\n" +
		"    public static int TOP = 1;\r\n" +
		"    public static int CENTER = 2;\r\n" +
		"    public static int BOTTOM = 3;\r\n" +
		"\r\n" +
		"    public static void CustomToast(Context _context, String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity) {\r\n" +
		"        Toast _toast = Toast.makeText(_context, _message, Toast.LENGTH_SHORT);\r\n" +
		"        View _view = _toast.getView();\r\n" +
		"        TextView _textView = _view.findViewById(android.R.id.message);\r\n" +
		"        _textView.setTextSize(_textSize);\r\n" +
		"        _textView.setTextColor(_textColor);\r\n" +
		"        _textView.setGravity(Gravity.CENTER);\r\n" +
		"\r\n" +
		"        GradientDrawable _gradientDrawable = new GradientDrawable();\r\n" +
		"        _gradientDrawable.setColor(_bgColor);\r\n" +
		"        _gradientDrawable.setCornerRadius(_radius);\r\n" +
		"        _view.setBackgroundDrawable(_gradientDrawable);\r\n" +
		"        _view.setPadding(15, 10, 15, 10);\r\n" +
		"        _view.setElevation(10);\r\n" +
		"\r\n" +
		"        switch (_gravity) {\r\n" +
		"            case 1:\r\n" +
		"                _toast.setGravity(Gravity.TOP, 0, 150);\r\n" +
		"                break;\r\n" +
		"\r\n" +
		"            case 2:\r\n" +
		"                _toast.setGravity(Gravity.CENTER, 0, 0);\r\n" +
		"                break;\r\n" +
		"\r\n" +
		"            case 3:\r\n" +
		"                _toast.setGravity(Gravity.BOTTOM, 0, 150);\r\n" +
		"                break;\r\n" +
		"        }\r\n" +
		"        _toast.show();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void CustomToastWithIcon(Context _context, String _message, int _textColor, int _textSize, int _bgColor, int _radius, int _gravity, int _icon) {\r\n" +
		"        Toast _toast = Toast.makeText(_context, _message, Toast.LENGTH_SHORT);\r\n" +
		"        View _view = _toast.getView();\r\n" +
		"        TextView _textView = (TextView) _view.findViewById(android.R.id.message);\r\n" +
		"        _textView.setTextSize(_textSize);\r\n" +
		"        _textView.setTextColor(_textColor);\r\n" +
		"        _textView.setCompoundDrawablesWithIntrinsicBounds(_icon, 0, 0, 0);\r\n" +
		"        _textView.setGravity(Gravity.CENTER);\r\n" +
		"        _textView.setCompoundDrawablePadding(10);\r\n" +
		"\r\n" +
		"        GradientDrawable _gradientDrawable = new GradientDrawable();\r\n" +
		"        _gradientDrawable.setColor(_bgColor);\r\n" +
		"        _gradientDrawable.setCornerRadius(_radius);\r\n" +
		"        _view.setBackgroundDrawable(_gradientDrawable);\r\n" +
		"        _view.setPadding(10, 10, 10, 10);\r\n" +
		"        _view.setElevation(10);\r\n" +
		"\r\n" +
		"        switch (_gravity) {\r\n" +
		"            case 1:\r\n" +
		"                _toast.setGravity(Gravity.TOP, 0, 150);\r\n" +
		"                break;\r\n" +
		"\r\n" +
		"            case 2:\r\n" +
		"                _toast.setGravity(Gravity.CENTER, 0, 0);\r\n" +
		"                break;\r\n" +
		"\r\n" +
		"            case 3:\r\n" +
		"                _toast.setGravity(Gravity.BOTTOM, 0, 150);\r\n" +
		"                break;\r\n" +
		"        }\r\n" +
		"        _toast.show();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void sortListMap(final ArrayList<HashMap<String, Object>> listMap, final String key, final boolean isNumber, final boolean ascending) {\r\n" +
		"        Collections.sort(listMap, new Comparator<HashMap<String, Object>>() {\r\n" +
		"            public int compare(HashMap<String, Object> _compareMap1, HashMap<String, Object> _compareMap2) {\r\n" +
		"                if (isNumber) {\r\n" +
		"                    int _count1 = Integer.valueOf(_compareMap1.get(key).toString());\r\n" +
		"                    int _count2 = Integer.valueOf(_compareMap2.get(key).toString());\r\n" +
		"                    if (ascending) {\r\n" +
		"                        return _count1 < _count2 ? -1 : _count1 < _count2 ? 1 : 0;\r\n" +
		"                    } else {\r\n" +
		"                        return _count1 > _count2 ? -1 : _count1 > _count2 ? 1 : 0;\r\n" +
		"                    }\r\n" +
		"                } else {\r\n" +
		"                    if (ascending) {\r\n" +
		"                        return (_compareMap1.get(key).toString()).compareTo(_compareMap2.get(key).toString());\r\n" +
		"                    } else {\r\n" +
		"                        return (_compareMap2.get(key).toString()).compareTo(_compareMap1.get(key).toString());\r\n" +
		"                    }\r\n" +
		"                }\r\n" +
		"            }\r\n" +
		"        });\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void CropImage(Activity _activity, String _path, int _requestCode) {\r\n" +
		"        try {\r\n" +
		"            Intent _intent = new Intent(\"com.android.camera.action.CROP\");\r\n" +
		"            File _file = new File(_path);\r\n" +
		"            Uri _contentUri = Uri.fromFile(_file);\r\n" +
		"            _intent.setDataAndType(_contentUri, \"image/*\");\r\n" +
		"            _intent.putExtra(\"crop\", \"true\");\r\n" +
		"            _intent.putExtra(\"aspectX\", 1);\r\n" +
		"            _intent.putExtra(\"aspectY\", 1);\r\n" +
		"            _intent.putExtra(\"outputX\", 280);\r\n" +
		"            _intent.putExtra(\"outputY\", 280);\r\n" +
		"            _intent.putExtra(\"return-data\", false);\r\n" +
		"            _activity.startActivityForResult(_intent, _requestCode);\r\n" +
		"        } catch (ActivityNotFoundException _e) {\r\n" +
		"            Toast.makeText(_activity, \"Your device doesn't support the crop action!\", Toast.LENGTH_SHORT).show();\r\n" +
		"        }\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static boolean isConnected(Context _context) {\r\n" +
		"        ConnectivityManager _connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);\r\n" +
		"        NetworkInfo _activeNetworkInfo = _connectivityManager.getActiveNetworkInfo();\r\n" +
		"        return _activeNetworkInfo != null && _activeNetworkInfo.isConnected();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static String copyFromInputStream(InputStream _inputStream) {\r\n" +
		"        ByteArrayOutputStream _outputStream = new ByteArrayOutputStream();\r\n" +
		"        byte[] _buf = new byte[1024];\r\n" +
		"        int _i;\r\n" +
		"        try {\r\n" +
		"            while ((_i = _inputStream.read(_buf)) != -1){\r\n" +
		"                _outputStream.write(_buf, 0, _i);\r\n" +
		"            }\r\n" +
		"            _outputStream.close();\r\n" +
		"            _inputStream.close();\r\n" +
		"        } catch (IOException _e) {\r\n" +
		"        }\r\n" +
		"        \r\n" +
		"        return _outputStream.toString();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void hideKeyboard(Context _context) {\r\n" +
		"        InputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);\r\n" +
		"        _inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);\r\n" +
		"    }\r\n" +
		"    \r\n" +
		"    public static void showKeyboard(Context _context) {\r\n" +
		"        InputMethodManager _inputMethodManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);\r\n" +
		"        _inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);\r\n" +
		"    }\r\n" +
		"    \r\n" +
		"    public static void showMessage(Context _context, String _s) {\r\n" +
		"        Toast.makeText(_context, _s, Toast.LENGTH_SHORT).show();\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static int getLocationX(View _view) {\r\n" +
		"        int _location[] = new int[2];\r\n" +
		"        _view.getLocationInWindow(_location);\r\n" +
		"        return _location[0];\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static int getLocationY(View _view) {\r\n" +
		"        int _location[] = new int[2];\r\n" +
		"        _view.getLocationInWindow(_location);\r\n" +
		"        return _location[1];\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static int getRandom(int _min, int _max) {\r\n" +
		"        Random random = new Random();\r\n" +
		"        return random.nextInt(_max - _min + 1) + _min;\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {\r\n" +
		"        ArrayList<Double> _result = new ArrayList<Double>();\r\n" +
		"        SparseBooleanArray _arr = _list.getCheckedItemPositions();\r\n" +
		"        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {\r\n" +
		"            if (_arr.valueAt(_iIdx))\r\n" +
		"                _result.add((double) _arr.keyAt(_iIdx));\r\n" +
		"        }\r\n" +
		"        return _result;\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static float getDip(Context _context, int _input) {\r\n" +
		"        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static int getDisplayWidthPixels(Context _context) {\r\n" +
		"        return _context.getResources().getDisplayMetrics().widthPixels;\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static int getDisplayHeightPixels(Context _context) {\r\n" +
		"        return _context.getResources().getDisplayMetrics().heightPixels;\r\n" +
		"    }\r\n" +
		"\r\n" +
		"    public static void getAllKeysFromMap(Map<String, Object> _map, ArrayList<String> _output) {\r\n" +
		"        if (_output == null) return;\r\n" +
		"        _output.clear();\r\n" +
		"        if (_map == null || _map.size() < 1) return;\r\n" +
		"        for (Map.Entry<String, Object> _entry : _map.entrySet()) {\r\n" +
		"            _output.add(_entry.getKey());\r\n" +
		"        }\r\n" +
		"    }\r\n" +
		"}\r\n";
	}
	
	public static String j(String code) {
		StringBuilder formattedCode = new StringBuilder(4096);
		char[] codeChars = code.toCharArray();
		boolean var4 = false;
		boolean var5 = false;
		boolean var6 = false;
		int var7 = 0;
		boolean var8 = false;
		
		int var20;
		int codeIndex = 0;
		for (boolean var9 = false; codeIndex < codeChars.length; var7 = var20) {
			boolean var17;
			boolean var18;
			int var19;
			label81:
			{
				char codeBit = codeChars[codeIndex];
				boolean var11;
				int var16;
				if (var4) {
					if (codeBit == '\n') {
						formattedCode.append(codeBit);
						var11 = false;
						var16 = codeIndex;
					} else {
						formattedCode.append(codeBit);
						var11 = true;
						var16 = codeIndex;
					}
				} else {
					if (var5) {
						label78:
						{
							if (codeBit == '*') {
								var20 = codeIndex + 1;
								char var14 = codeChars[var20];
								if (var14 == '/') {
									formattedCode.append(codeBit);
									formattedCode.append(var14);
									var5 = false;
									var11 = false;
									var16 = var20;
									break label78;
								}
							}
							
							formattedCode.append(codeBit);
							var11 = false;
							var16 = codeIndex;
						}
					} else if (var6) {
						formattedCode.append(codeBit);
						var6 = false;
						var11 = false;
						var16 = codeIndex;
					} else if (codeBit == '\\') {
						formattedCode.append(codeBit);
						var6 = true;
						var11 = false;
						var16 = codeIndex;
					} else if (var8) {
						if (codeBit == '\'') {
							formattedCode.append(codeBit);
							var8 = false;
							var11 = false;
							var16 = codeIndex;
						} else {
							formattedCode.append(codeBit);
							var11 = false;
							var16 = codeIndex;
						}
					} else if (var9) {
						if (codeBit == '"') {
							formattedCode.append(codeBit);
							var9 = false;
							var11 = false;
							var16 = codeIndex;
						} else {
							formattedCode.append(codeBit);
							var11 = false;
							var16 = codeIndex;
						}
					} else {
						label87:
						{
							if (codeBit == '/') {
								var20 = codeIndex + 1;
								char var14 = codeChars[var20];
								if (var14 == '/') {
									formattedCode.append(codeBit);
									formattedCode.append(var14);
									var16 = var20;
									var11 = true;
									break label87;
								}
								
								if (var14 == '*') {
									formattedCode.append(codeBit);
									formattedCode.append(var14);
									var5 = true;
									var11 = false;
									var16 = var20;
									break label87;
								}
							}
							
							if (codeBit != '\n') {
								if (codeBit == '\'') {
									var8 = true;
								}
								
								if (codeBit == '"') {
									var9 = true;
								}
								
								if (codeBit == '{') {
									var20 = var7 + 1;
								} else {
									var20 = var7;
								}
								
								var7 = var20;
								if (codeBit == '}') {
									--var20;
									var7 = var20;
									if (formattedCode.charAt(formattedCode.length() - 1) == '\t') {
										formattedCode.deleteCharAt(formattedCode.length() - 1);
										var7 = var20;
									}
								}
								
								formattedCode.append(codeBit);
								var20 = codeIndex;
								var5 = var8;
								codeIndex = var7;
								var18 = false;
								var6 = false;
								var19 = var20;
								break label81;
							}
							
							formattedCode.append(codeBit);
							var11 = false;
							var16 = codeIndex;
						}
					}
				}
				
				var17 = var5;
				boolean var13 = var6;
				var5 = var8;
				var19 = var16;
				var4 = var11;
				var6 = var17;
				var18 = var13;
				codeIndex = var7;
			}
			
			var20 = codeIndex;
			var17 = var18;
			var18 = var6;
			int var21 = var19 + 1;
			var8 = var5;
			var6 = var17;
			var5 = var18;
			codeIndex = var21;
		}
		
		return formattedCode.toString();
	}
	enum AccessModifier {
		PRIVATE("private"),
		PROTECTED("protected"),
		PUBLIC("public");
		
		private String name = "";
		
		AccessModifier(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}
