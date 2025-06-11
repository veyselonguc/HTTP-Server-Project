import java.io.File;

public class ContentTypeResolver {

    public static String getContentType(File file) {
        String name = file.getName().toLowerCase();

        if (name.endsWith(".html") || name.endsWith(".htm")) {
            return "text/html";
        } else if (name.endsWith(".css")) {
            return "text/css";
        } else if (name.endsWith(".js")) {
            return "application/javascript";
        } else if (name.endsWith(".json")) {
            return "application/json";
        } else if (name.endsWith(".png")) {
            return "image/png";
        } else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return "image/jpeg";
        } else {
            return "application/octet-stream";
        }
    }
}
