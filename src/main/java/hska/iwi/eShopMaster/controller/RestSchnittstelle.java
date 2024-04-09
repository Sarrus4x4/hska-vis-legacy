package hska.iwi.eShopMaster.controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RestSchnittstelle {

    public static String makeRestcall() {
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://microservice-kategorien:8080/helloworld");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            String alles = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                alles += line;
            }
            return alles;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}

