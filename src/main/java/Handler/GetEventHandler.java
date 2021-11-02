package Handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpURLConnection;

public class GetEventHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                String urlPath = exchange.getRequestURI().toString();

                String[] urlComponents = urlPath.split("/");

                if (urlComponents.length == 2) {
                    new GetAllFamilyEventsHandler().handle(exchange);
                } else if (urlComponents.length == 3) {
                    String eventID = urlComponents[2];
                    new GetSingleEventHandler().handleEvent(exchange, eventID);
                } else {
                    throw new IOException("Please don't hack me.");
                }
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
