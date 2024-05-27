package hska.iwi.eShopMaster.controller;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import java.util.ArrayList;


public class RestHelper {

    private static final String categoryUrl = "http://categories.categories/";
    private static final String productsUrl = "http://products.products/";


    private static String getUrlFromService(String service) {
        if(service == "category") {
            return categoryUrl;
        }
        else if (service == "product") {
            return productsUrl;
        }

        return "";
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

    //only url param, no body
    public static JsonNode postCall (String path, String service, JsonNode data, String requestParam){
        // POST request
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(getUrlFromService(service) + path + requestParam);
            HttpResponse postResponse = httpClient.execute(httpPost);
            return parseResponse(postResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //only body
    public static String postCallParam (String path, String service, ArrayList<NameValuePair> body){
        // POST request
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(getUrlFromService(service) + path);
            //todo: post can currently only use request param and not data in body....

            //StringEntity postEntity = new StringEntity("{\"key\":\"value\"}");
            httpPost.setEntity(new UrlEncodedFormEntity(body, "UTF-8"));

            HttpResponse postResponse = httpClient.execute(httpPost);
            return postResponse.getEntity().toString();
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

