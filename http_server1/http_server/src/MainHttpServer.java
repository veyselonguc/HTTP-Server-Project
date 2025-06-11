import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MainHttpServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("HTTP Sunucusu çalışıyor. Port: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(new ClientHandler(clientSocket));
            }

        } catch (IOException e) {
            System.err.println("Sunucu başlatılırken bir hata oluştu: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }
}
