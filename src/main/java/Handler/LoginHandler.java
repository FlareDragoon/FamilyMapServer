package Handler;

import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Gson gson = new Gson();

                LoginRequest request = (LoginRequest) DeserializeRequest("login",
                        gson, exchange);

				LoginService service = new LoginService();
				LoginResult result = service.Login(request);

                CheckResult(result, exchange);
				SerializeResult(result, gson, exchange);

                success = true;
            }

            if (!success) {
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
