import java.io.*;

public class HttpResponse {

    public static void sendResponse(OutputStream output, int statusCode, String statusMessage, String content) throws IOException {
        String response = "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n" +
                          "Content-Type: text/plain\r\n" +
                          "Content-Length: " + content.length() + "\r\n" +
                          "\r\n" +
                          content;
        output.write(response.getBytes());
        output.flush();
    }

    public static void sendFile(OutputStream output, File file) throws IOException {
        String header = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + ContentTypeResolver.getContentType(file) + "\r\n" +
                        "Content-Length: " + file.length() + "\r\n" +
                        "\r\n";
        output.write(header.getBytes());

        try (FileInputStream fileInput = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInput.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        output.flush();
    }
}
