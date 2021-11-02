package Handler;

import java.io.*;
import java.nio.file.Files;

import com.sun.net.httpserver.*;

import static java.net.HttpURLConnection.HTTP_OK;

public class FileHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
            String urlPath = exchange.getRequestURI().toString();

            if (urlPath.equals("/") || urlPath == null) {
                urlPath = "/index.html";
            }
            String filePath = "web" + urlPath;

            File file = new File(filePath);
            OutputStream respBody = exchange.getResponseBody();

            if (!file.exists()) {
                exchange.sendResponseHeaders(404, 0);
                file = new File("web/HTML/404.html");
            } else {
                exchange.sendResponseHeaders(HTTP_OK, 0);
            }

            Files.copy(file.toPath(), respBody);
            respBody.close();
        } else {
            exchange.sendResponseHeaders(405, 0);
        }
    }
}
