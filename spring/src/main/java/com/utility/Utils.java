package com.utility;

import com.model.Request;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static Request getRequest(BufferedReader br) throws Exception{
        String methodString = "";
        String pathString = "";

        String firstLine = br.readLine();
        String[] firstLineParams = firstLine.split(" ");

        methodString = firstLineParams[0];
        pathString = firstLineParams[1];

        HashMap<String, String> headers = new HashMap<String, String>();

        String line = "";
        StringBuilder body = new StringBuilder();
        while((line = br.readLine()) != null) {
            if(line.equals("")) {
                break;
            }

            String[] tokens = line.split(" ");
//			System.out.println(Arrays.toString(tokens));
            headers.put(tokens[0].substring(0, tokens[0].indexOf(":")), tokens[1]);
        }

//		System.out.println(headers);
        int length = 0;

        length = Integer.parseInt(headers.get("Content-Length"));

        char[] b = new char[length + 1];

        br.read(b);

        String[] pathStringArray = pathString.split("\\?");

        String path = pathStringArray[0];
        Map<String, String> queryParams = new HashMap<>();
        for (int i = 1; i < pathStringArray.length; i++) {
            String[] query = pathStringArray[i].split("=");
            queryParams.put(query[0], query[1]);
        }

        Request request = new Request(methodString, path, headers, queryParams, new String(b));

        return request;
    }

    public static void writeResponse(PrintWriter out, String response) {
        out.println("HTTP/1.1 200 OK");
        out.println("Server: Java HTTP Server from Sandeep : 1.0");
        out.println("Date: " + new Date());
        out.println("Content-type: " + "text/html;charset=utf-8");
        out.println("Content-length: " + response.length());
        out.println(); // blank line between headers and content, very important !
        out.println(response);
        out.flush();

    }
}
