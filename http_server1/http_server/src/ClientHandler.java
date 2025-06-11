import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (InputStream input = clientSocket.getInputStream();
             OutputStream output = clientSocket.getOutputStream()) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String requestLine = reader.readLine();

            if (requestLine == null || requestLine.isEmpty()) {
                return;
            }

            System.out.println("İstek alındı: " + requestLine);

            String[] requestParts = requestLine.split(" ");
            if (requestParts.length != 3) {
                HttpResponse.sendResponse(output, 400, "Bad Request", "Geçersiz istek formatı.");
                return;
            }

            String method = requestParts[0];
            String path = requestParts[1];

            switch (method) {
                case "GET":
                    FileHandler.handleGetRequest(output, path);
                    break;
                case "POST":
                    FileHandler.handlePostRequest(reader, output, path);
                    break;
                case "DELETE":
                    FileHandler.handleDeleteRequest(output, path);
                    break;
                case "PUT":
                    FileHandler.handlePutRequest(reader, output, path);
                    break;
                default:
                    HttpResponse.sendResponse(output, 405, "Method Not Allowed", "Desteklenmeyen HTTP metodu.");
                    break;
            }

        } catch (IOException e) {
            System.err.println("İstemci ile iletişim sırasında bir hata oluştu: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("İstemci bağlantısı kapatılamadı: " + e.getMessage());
            }
        }
    }
}
