package Handler;

import Result.ClearResult;
import Service.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class ClearHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            boolean success = false;
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Gson gson = new Gson();

                ClearService service = new ClearService();
                ClearResult result = service.Clear();

                CheckResult(result, exchange);
                SerializeResult(result, gson, exchange);

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }
}
