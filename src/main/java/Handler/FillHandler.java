package Handler;

import Request.FillRequest;
import Result.FillResult;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class FillHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                String urlPath = exchange.getRequestURI().toString();

                String[] urlComponents = urlPath.split("/");
                FillRequest request;

                if (urlComponents.length == 3) {
                    String username = urlComponents[2];
                    request = new FillRequest(username, 4);
                } else if (urlComponents.length == 4) {
                    String username = urlComponents[2];
                    int generations = Integer.parseInt(urlComponents[3]);
                    request = new FillRequest(username, generations);
                } else {
                    throw new IOException("Please don't hack me.");
                }

                FillService service = new FillService();
                FillResult result = service.FillTree(request);

                Gson gson = new Gson();

                CheckResult(result, exchange);
                SerializeResult(result, gson, exchange);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);

            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}
