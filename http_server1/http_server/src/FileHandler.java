import java.io.*;

public class FileHandler {

    private static final String WEB_ROOT = "./src/www";

    public static void handleGetRequest(OutputStream output, String path) throws IOException {
        File file = new File(WEB_ROOT, path);
        if (file.isDirectory()) {
            sendDirectoryListing(output, file);
        } else if (file.exists() && file.isFile()) {
            HttpResponse.sendFile(output, file);
        } else {
            HttpResponse.sendResponse(output, 404, "Not Found", "Dosya bulunamadı: " + path);
        }
    }

    public static void handlePostRequest(BufferedReader reader, OutputStream output, String path) throws IOException {
        File file = new File(WEB_ROOT, path);

        try (BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream(file))) {
            String line;
            int contentLength = 0;

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                if (line.toLowerCase().startsWith("content-length:")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
            }

            char[] body = new char[contentLength];
            reader.read(body, 0, contentLength);
            fileOutput.write(new String(body).getBytes());

            HttpResponse.sendResponse(output, 201, "Created", "Dosya başarıyla yüklendi: " + path);
        }
    }

    public static void handleDeleteRequest(OutputStream output, String path) throws IOException {
        File file = new File(WEB_ROOT, path);

        if (!file.exists()) {
            HttpResponse.sendResponse(output, 404, "Not Found", "Silinmek istenen dosya bulunamadı: " + path);
            return;
        }

        if (file.isDirectory()) {
            HttpResponse.sendResponse(output, 403, "Forbidden", "Bir dizin silinemez: " + path);
            return;
        }

        if (file.delete()) {
            HttpResponse.sendResponse(output, 200, "OK", "Dosya başarıyla silindi: " + path);
        } else {
            HttpResponse.sendResponse(output, 500, "Internal Server Error", "Dosya silinemedi: " + path);
        }
    }

    public static void handlePutRequest(BufferedReader reader, OutputStream output, String path) throws IOException {
        File file = new File(WEB_ROOT, path);

        try (BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream(file, false))) {
            String line;
            int contentLength = 0;

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                if (line.toLowerCase().startsWith("content-length:")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
            }

            char[] body = new char[contentLength];
            reader.read(body, 0, contentLength);
            fileOutput.write(new String(body).getBytes());

            HttpResponse.sendResponse(output, 200, "OK", "Dosya başarıyla güncellendi: " + path);
        }
    }

    public static void sendDirectoryListing(OutputStream output, File directory) throws IOException {
            File[] files = directory.listFiles();
            if (files == null) {
                HttpResponse.sendResponse(output, 500, "Internal Server Error", "Dizin listelenemedi: " + directory.getName());
                return;
            }

            StringBuilder content = new StringBuilder("<html><head><title>Dosya Listesi</title></head><body>");
            content.append("<h1>Dosyalar</h1><ul>");
            for (File file : files) {
                String fileName = file.getName();
                content.append("<li><a href=\"" + fileName + "\">" + fileName + "</a></li>");
            }
            content.append("</ul></body></html>");

            String response = "HTTP/1.1 200 OK\r\n" +
                              "Content-Type: text/html\r\n" +
                              "Content-Length: " + content.length() + "\r\n" +
                              "\r\n" +
                              content;
            output.write(response.getBytes());
            output.flush();
        }

        private void sendResponse(OutputStream output, int statusCode, String statusMessage, String content) throws IOException {
            String response = "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n" +
                              "Content-Type: text/plain\r\n" +
                              "Content-Length: " + content.length() + "\r\n" +
                              "\r\n" +
                              content;
            output.write(response.getBytes());
            output.flush();
        }

}

