import okhttp3.*;
import okio.ByteString;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by shff on 2017/5/9.
 */
public class Main {

    private static WebSocket webSocket;

    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                createRequest();
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String next = scanner.nextLine();
            if (next.equals("close")){
                break;
            }else {
                webSocket.send(next);
            }
        }



    }

  static   OkHttpClient createClient(){
        return new OkHttpClient.Builder().build();
    }

   static void createRequest(){
        Request request = new Request.Builder().url("http://localhost:8080/echo").build();
        OkHttpClient client = createClient();

       webSocket = client.newWebSocket(request, new WebSocketListener() {
           @Override
           public void onOpen(WebSocket webSocket, Response response) {
               super.onOpen(webSocket, response);
               try {
                   System.out.println(response.body().string());
               } catch (IOException e) {
                   e.printStackTrace();
               }
               System.out.println("onpen");

           }

           @Override
           public void onMessage(WebSocket webSocket, String text) {
               super.onMessage(webSocket, text);

               System.out.println(text);

           }

           @Override
           public void onMessage(WebSocket webSocket, ByteString bytes) {
               super.onMessage(webSocket, bytes);
               System.out.println(bytes);
           }

           @Override
           public void onClosing(WebSocket webSocket, int code, String reason) {
               super.onClosing(webSocket, code, reason);
               System.out.println(reason);
           }

           @Override
           public void onClosed(WebSocket webSocket, int code, String reason) {
               super.onClosed(webSocket, code, reason);
               System.out.println(reason);
           }

           @Override
           public void onFailure(WebSocket webSocket, Throwable t, Response response) {
               super.onFailure(webSocket, t, response);
               System.out.println("onFailure");
               t.printStackTrace();
               try {
                   System.out.println(response.body().string());
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });
       client.dispatcher().executorService().shutdown();
       System.out.println("finish");


    }

}
