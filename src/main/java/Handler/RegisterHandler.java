package Handler;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import Service.LoginService;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Gson gson = new Gson();

                RegisterRequest request = (RegisterRequest) DeserializeRequest("register",
                        gson, exchange);

                RegisterService service = new RegisterService();
                RegisterResult result = service.Register(request);

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
