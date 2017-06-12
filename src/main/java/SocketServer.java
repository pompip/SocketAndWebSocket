

import javafx.concurrent.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by shff on 2017/5/11.
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8989);
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            Socket accept = serverSocket.accept();

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            if ("count".equals(line)){
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (int i = 0;i<100;i++){
                                            try {
                                                Thread.sleep(100);
                                                writer.write(i+"");
                                                writer.newLine();
                                                writer.flush();
                                            } catch (InterruptedException | IOException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                });
                            }else {
                                writer.write(line );
                                writer.newLine();
                                writer.flush();
                            }




                        }
                        reader.close();
                        writer.flush();
                        writer.close();
                        accept.close();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
