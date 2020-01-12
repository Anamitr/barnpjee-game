package com.example.burlapgameclient;

import android.util.Log;

import com.caucho.burlap.client.MicroBurlapInput;
import com.caucho.burlap.client.MicroBurlapOutput;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import api.model.CheckFieldResponse;
import api.model.Minefield;
import api.service.MinesweeperService;

import static com.example.burlapgameclient.UtilKt.generateEmptyMinefield;

public class BurlapMinesweeperService implements MinesweeperService {
    private static final String TAG = BurlapMinesweeperService.class.getSimpleName();

    @Override
    public String hello() {
        return (String) sendBurlapRequest("hello", new Object[]{}, String.class);
    }

    @Override
    public String getTestChatString(String argument) {
        return (String) sendBurlapRequest("getTestChatString", new Object[]{argument}, String.class);
    }

    @Override
    public Minefield getTestMinefield() {
        return null;
    }

    @Override
    public int enterMineField(String username, String minefieldName) {
        return 0;
    }

    @Override
    public CheckFieldResponse checkField(String minefieldId, String username, int x, int y) {
        Hashtable hashtable = (Hashtable) sendBurlapRequest("checkField",
                new Object[]{minefieldId, username, x, y}, CheckFieldResponse.class);
        CheckFieldResponse checkFieldResponse = CheckFieldResponse.valueOf(hashtable.get("name").toString());
        return checkFieldResponse;
    }

    @Override
    public String getCurrentPlayer(String minefieldId) {
        String username = (String) sendBurlapRequest("getCurrentPlayer", new Object[]{minefieldId}, String.class);
        return username;
    }

    @Override
    public String registerForMinefield(String minefieldId, String username) {
        String result = (String) sendBurlapRequest("registerForMinefield", new Object[]{minefieldId, username}, String.class);
        Log.v(TAG, "registerForMinefield(" + minefieldId + ", " + username + ") = " + result);
        return result;
    }

    @Override
    public Minefield getMinefield(String minefieldId) {
        Minefield minefield = new Minefield();
        Hashtable hashtable = (Hashtable) sendBurlapRequest("POST", "getMinefield",
                new Object[]{minefieldId}, Minefield.class);
        minefield.setId((String) hashtable.get("id"));
        minefield.setGameOver((boolean) hashtable.get("gameOver"));
        minefield.setWasGameWon((boolean) hashtable.get("wasGameWon"));
        minefield.setDetonatedBombPosition((int)hashtable.get("detonatedBombPosition"));

        minefield.setFieldsMatrix(generateEmptyMinefield());
        List<Vector> downloadedFields = Collections.list(((Vector) hashtable.get("fieldsMatrix")).elements());
        for (int i = 0; i < downloadedFields.size(); i++) {
            Vector vector = downloadedFields.get(i);
            for (int j = 0; j < vector.size(); j++) {
                Hashtable fieldTypeHashTable = (Hashtable) vector.get(j);
                minefield.getFieldsMatrix().get(i).get(j).setBomb((Boolean) fieldTypeHashTable.get("isBomb"));
                minefield.getFieldsMatrix().get(i).get(j).setRevealed((Boolean) fieldTypeHashTable.get("isRevealed"));
                minefield.getFieldsMatrix().get(i).get(j).setBombsAround((Integer) fieldTypeHashTable.get("bombsAround"));
            }
        }

        return minefield;
    }

    public static Object sendBurlapRequest(String methodName, Object[] arguments, Class responeClass) {
        return sendBurlapRequest("POST", methodName, arguments, responeClass);
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

//    public static Object sendBurlapRequest(String methodName, Object[] arguments, Class responseClass) {
//        return sendBurlapRequest(methodName, arguments, responseClass, "POST");
//    }
//
//    public static Object sendBurlapRequest(String methodName, Object[] arguments, Class responseClass, String requestType) {
//        try {
//            MicroBurlapInput burlapInput = new MicroBurlapInput();
//
//            URL url = new URL(ConstantsKt.getBURLAP_CHAT_URL());
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod(requestType);
//            OutputStream outputStream = httpURLConnection.getOutputStream();
//            MicroBurlapOutput burlapOutput = new MicroBurlapOutput(outputStream);
//            burlapOutput.call(methodName, arguments);
//            outputStream.flush();
//
//            InputStream inputStream = httpURLConnection.getInputStream();
//            burlapInput = new MicroBurlapInput(inputStream);
//            Object value = burlapInput.readReply(responseClass);
////            Log.v(TAG, "Burlap response: " + value.toString());
//            return value;
//        } catch (Exception e) {
//            Log.e(TAG, e.getLocalizedMessage());
//            return e;
//        }
//    }
}
