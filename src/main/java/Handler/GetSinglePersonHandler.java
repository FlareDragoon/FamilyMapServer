package Handler;

import Request.GetSinglePersonRequest;
import Result.GetSinglePersonResult;
import Service.GetSinglePersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpURLConnection;

public class GetSinglePersonHandler extends BaseHandler {

    public void handlePerson(HttpExchange exchange, String personID) throws IOException {
        try {
            Headers reqHeaders = exchange.getRequestHeaders();
            if (reqHeaders.containsKey("Authorization")) {

                String authToken = reqHeaders.getFirst("Authorization");
                Gson gson = new Gson();

                GetSinglePersonService service = new GetSinglePersonService();
                GetSinglePersonRequest request = new GetSinglePersonRequest(personID, authToken);
                GetSinglePersonResult result = service.GetPerson(request);

                CheckResult(result, exchange);
                SerializeResult(result, gson, exchange);
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);

            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
