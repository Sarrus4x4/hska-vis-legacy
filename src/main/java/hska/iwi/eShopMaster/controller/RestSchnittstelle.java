package hska.iwi.eShopMaster.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RestSchnittstelle {

    public static String makeRestcall() {

        try {

            URL url = new URL("http://localhost:8080/helloworld");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() >= 300) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            conn.disconnect();
            //String output;
            System.out.println("Output from Server .... \n");
            System.out.println(br.toString());
            return br.toString();
           /*
            while ((output = br.readLine()) != null) {
               JsonObject json = (JsonObject) new JsonParser().parse(output);
                String out = (String) json.get("userid").toString();
                if (!out.equals("null")) {
                    return "SUCCESS";
                }
            }#

            */
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}

