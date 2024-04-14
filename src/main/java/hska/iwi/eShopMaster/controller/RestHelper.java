package hska.iwi.eShopMaster.controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class RestHelper {

    private static final String categoryUrl = "http://microservice-kategorien:8080/";
    private static final String productsUrl = "http://microservice-products:8080/";


    private static String getUrlFromService(String service) {
        switch (service) {
            case "category":
                return categoryUrl;
            break;
            case "product":
                return productsUrl;

            return null;
        }
    }


    public static JsonNode getCall(String path, String service) {
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(getUrlFromService(service) + path); // /helloworld
            CloseableHttpResponse response = httpClient.execute(httpGet);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();

            // Close resources
            response.close();
            httpClient.close();

            // Parse the response string into a JSON object using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(result.toString());
            return jsonResponse;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode putCall (String path, String service, JsonNode data){
        try{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(getUrlFromService(service) + path);
        StringEntity putEntity = new StringEntity("{\"key\":\"value\"}");
        httpPut.setEntity(putEntity);
        HttpResponse putResponse = httpClient.execute(httpPut);
        return parseResponse(putResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode postCall (String path, String service, JsonNode data, String requestParam){
        // POST request
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(getUrlFromService(service) + path + requestParam);
            //todo: post can currently only use request param and not data in body....

            //StringEntity postEntity = new StringEntity("{\"key\":\"value\"}");
            //httpPost.setEntity(postEntity);

            HttpResponse postResponse = httpClient.execute(httpPost);
            return parseResponse(postResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonNode deleteCall (String path,String service){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpDelete httpDelete = new HttpDelete(getUrlFromService(service) + path);
            HttpResponse deleteResponse = httpClient.execute(httpDelete);
            return parseResponse(deleteResponse);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JsonNode parseResponse(HttpResponse response) throws Exception {
        // Read the response as a string
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();

        // Parse the response string into a JSON object using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(result.toString());
        return jsonResponse;
    }



}

