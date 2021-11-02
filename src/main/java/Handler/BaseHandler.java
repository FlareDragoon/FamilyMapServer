package Handler;

import Request.BaseRequest;
import Request.LoadRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.BaseResult;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class BaseHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }

    public void SerializeResult(BaseResult result, Gson gson, HttpExchange exchange) throws IOException{
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, resBody);
        resBody.close();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        exchange.getResponseBody().close();
    }

    public BaseRequest DeserializeRequest(String requestType, Gson gson, HttpExchange exchange) throws IOException{
        InputStream reqBody = exchange.getRequestBody();
        String reqData = readString(reqBody);

        if (requestType.equalsIgnoreCase("login")) {
            LoginRequest request = gson.fromJson(reqData, LoginRequest.class);
            return request;
        } else if(requestType.equalsIgnoreCase("register")) {
            RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);
            return request;
        } else if(requestType.equalsIgnoreCase("load")) {
            LoadRequest request = gson.fromJson(reqData, LoadRequest.class);
            return request;
        }

        return null;
    }

    public void CheckResult(BaseResult result, HttpExchange exchange) {
        try {
            if (!result.isSuccess()) {
                exchange.sendResponseHeaders(400, 0);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

}
