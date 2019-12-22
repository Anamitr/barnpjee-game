package com.example.hessianchatclient;
//

import android.util.Log;

import com.caucho.burlap.client.MicroBurlapInput;
import com.caucho.burlap.client.MicroBurlapOutput;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    public static void test() throws Exception {
        MicroBurlapInput burlapInput = new MicroBurlapInput();
//        String url = "http://www.caucho.com/burlap/test/basic";

        URL url = new URL(ConstantsKt.getBURLAP_CHAT_URL());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        OutputStream outputStream = httpURLConnection.getOutputStream();
        MicroBurlapOutput burlapOutput = new MicroBurlapOutput(outputStream);
        burlapOutput.call("getTestChatString", new Object[] {"Lovers in Dangerous Spacetime"});
        outputStream.flush();

        InputStream inputStream = httpURLConnection.getInputStream();
        burlapInput = new MicroBurlapInput(inputStream);
        Object value = burlapInput.readReply(String.class);
        Log.v(TAG, "Burlap response: " + value.toString());
        return;
    }

    public static void runTestAndCatchException() {
        try {
            test();
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
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