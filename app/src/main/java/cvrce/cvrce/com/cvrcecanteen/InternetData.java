package cvrce.cvrce.com.cvrcecanteen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class InternetData {


    public static HttpURLConnection getHTTPConnected(String url, boolean input, boolean output, String method) throws IOException {
        URL local_url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) local_url.openConnection();
        connection.setDoOutput(output);
        connection.setDoInput(input);
        connection.setRequestMethod(method);
        return connection;
    }


    public static HttpsURLConnection getHTTPSConnected(String url, boolean input, boolean output, String method) throws IOException {
        URL local_url = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) local_url.openConnection();
        connection.setDoOutput(output);
        connection.setDoInput(input);
        connection.setRequestMethod(method);
        return connection;
    }


    public static BufferedReader readData(HttpURLConnection connection) throws IOException {
        InputStream in = connection.getInputStream();
        BufferedReader  reader = new BufferedReader(new InputStreamReader(in));
        return reader;
    }

    public static void writeData(HttpURLConnection connection, String data) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.write(data);
        writer.flush();
    }

    public static String encData(ArrayList<String> key, ArrayList<String> value, String type){

        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<key.size();i++){
            try {
                sb.append(URLEncoder.encode(key.get(i),type));
                sb.append("=");
                sb.append(URLEncoder.encode(value.get(i),type));
                if(i!=key.size()-1)
                    sb.append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }



}
