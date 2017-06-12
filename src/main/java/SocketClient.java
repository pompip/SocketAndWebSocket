import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * Created by shff on 2017/5/11.
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8989);
        Scanner scanner = new Scanner(System.in);
        Writer writer = new OutputStreamWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("receive:" + line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
            }
        });
        while (true) {
            String s = scanner.nextLine();
            System.out.println("send:" + s);
            if ("close".equals(s)) {
                writer.flush();
                writer.close();
                socket.close();
                break;
            } else if ("start".equals(s)) {
                running = true;
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while (running) {
                            i++;
                            try {
                                writer.write(i + "\n");
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

            } else if ("end".equals(s)) {
                running = false;
            }else {
                writer.write(s);
                writer.flush();
            }


        }

    }

    static boolean running;
}
