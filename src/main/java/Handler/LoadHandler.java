package Handler;

import Request.LoadRequest;
import Request.LoginRequest;
import Result.LoadResult;
import Result.LoginResult;
import Service.LoadService;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Gson gson = new Gson();

                LoadRequest request = (LoadRequest) DeserializeRequest("load",
                        gson, exchange);

                LoadService service = new LoadService();
                LoadResult result = service.Load(request);

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
