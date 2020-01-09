package com.example.burlapgameclient;
//

import android.util.Log;

import com.caucho.burlap.client.MicroBurlapInput;
import com.caucho.burlap.client.MicroBurlapOutput;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import api.model.Minefield;
import api.service.MinesweeperService;

//import javax.microedition.io.Connector;
//import javax.microedition.khronos
//import javax.microedition.io.HttpConnection;
//MicroBurlapInput in=new MicroBurlapInput();
//String url="http://www.caucho.com/burlap/test/basic";
//HttpConnection c=(HttpConnection)Connector.open(url);
//c.setRequestMethod(HttpConnection.POST);
//OutputStream os=c.openOutputStream();MicroBurlapOutput out=new MicroBurlapOutput(os);out.call("hello",null);os.flush();is=c.openInputStream();MicroBurlapInput in=new MicroBurlapInput(is);Object value=in.readReply(null);

public class BurlapTest {
    private static final String TAG = BurlapTest.class.getSimpleName();

    public static void testRigidly() throws Exception {
        MicroBurlapInput burlapInput = new MicroBurlapInput();
//        String url = "http://www.caucho.com/burlap/test/basic";

        URL url = new URL(ConstantsKt.getBURLAP_CHAT_URL());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        OutputStream outputStream = httpURLConnection.getOutputStream();
        MicroBurlapOutput burlapOutput = new MicroBurlapOutput(outputStream);
        burlapOutput.call("getTestChatString", new Object[]{"Lovers in Dangerous Spacetime"});
        outputStream.flush();

        InputStream inputStream = httpURLConnection.getInputStream();
        burlapInput = new MicroBurlapInput(inputStream);
        Object value = burlapInput.readReply(String.class);
        Log.v(TAG, "Burlap response: " + value.toString());
        return;
    }

    public static void runTestAndCatchException() {
        try {
            testRigidly();
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    public static void runSomeTests() {
//        Log.v(TAG, "Test getTestChatString: " + sendBurlapRequest("POST", "getTestChatString", new Object[]{"Guacamole"}, String.class));


        Log.v(TAG, "getTestMessage: " + sendBurlapRequest("POST", "getTestMinefield",
                new Object[]{}, Minefield.class));
        Log.v(TAG, "getTestMinefield: " + sendBurlapRequest("POST", "getTestMinefield",
                new Object[]{}, Minefield.class));
        Log.v(TAG, "getMinefield: " + sendBurlapRequest("POST", "getMinefield",
                new Object[]{"sdfagfasd"}, Minefield.class));

        testBurlapMinesweeperService();
    }

    private static void testBurlapMinesweeperService() {
        Log.v(TAG, "BurlapMinesweeperService test:");
        MinesweeperService minesweeperService = new BurlapMinesweeperService();
        Log.v(TAG, "hello: " + minesweeperService.hello());
        Log.v(TAG, "getTestChatString: " + minesweeperService.getTestChatString("Narwhales"));
        Log.v(TAG, "getMinefield: " + minesweeperService.getMinefield("test_minefield"));
    }

    public static Object sendBurlapRequest(String requestType, String methodName, Object[] arguments, Class responeClass) {
        try {
            MicroBurlapInput burlapInput = new MicroBurlapInput();
//        String url = "http://www.caucho.com/burlap/test/basic";

            URL url = new URL(ConstantsKt.getBURLAP_CHAT_URL());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(requestType);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            MicroBurlapOutput burlapOutput = new MicroBurlapOutput(outputStream);
            burlapOutput.call(methodName, arguments);
            outputStream.flush();

            InputStream inputStream = httpURLConnection.getInputStream();
            burlapInput = new MicroBurlapInput(inputStream);
            Object value = burlapInput.readReply(responeClass);
//            Log.v(TAG, "Burlap response: " + value.toString());
            return value;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return e;
        }
    }

//    public static

//    private void template() {
//        MicroBurlapInput in = new MicroBurlapInput();
//        String url = "http://www.caucho.com/burlap/test/basic";
//
//        HttpConnection c = (HttpConnection) Connector.open(url);
//        c.setRequestMethod(HttpConnection.POST);
//        OutputStream os = c.openOutputStream();
//        MicroBurlapOutput out = new MicroBurlapOutput(os);
//        out.call("hello", null);
//        os.flush();
//        is = c.openInputStream();
//        MicroBurlapInput in = new MicroBurlapInput(is);
//        Object value = in.readReply(null);
//    }
}