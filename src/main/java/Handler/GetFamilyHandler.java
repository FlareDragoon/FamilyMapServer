package Handler;

import DataAccess.DataAccessException;
import Request.GetFamilyRequest;
import Result.GetFamilyResult;
import Result.RegisterResult;
import Service.GetFamilyService;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class GetFamilyHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {

                    String authToken = reqHeaders.getFirst("Authorization");
                    GetFamilyRequest request = new GetFamilyRequest(authToken);
                    Gson gson = new Gson();

                    GetFamilyService service = new GetFamilyService();
                    GetFamilyResult result = service.FindFamily(request);

                    CheckResult(result, exchange);
                    SerializeResult(result, gson, exchange);

                    success = true;
                }

                if (!success) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
