package Handler;

import Request.GetSingleEventRequest;
import Result.GetSingleEventResult;
import Service.GetSingleEventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpURLConnection;

public class GetSingleEventHandler extends BaseHandler {

    public void handleEvent(HttpExchange exchange, String eventID) throws IOException {
        try {
            Headers reqHeaders = exchange.getRequestHeaders();
            if (reqHeaders.containsKey("Authorization")) {

                String authToken = reqHeaders.getFirst("Authorization");
                Gson gson = new Gson();

                GetSingleEventService service = new GetSingleEventService();
                GetSingleEventRequest request = new GetSingleEventRequest(eventID, authToken);
                GetSingleEventResult result = service.GetEvent(request);

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
