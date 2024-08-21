package com.app;

import com.holder.Holder;
import com.model.Mapping;
import com.model.Request;
import com.utility.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5001);
        Holder holder = Holder.getInstance();
        holder.scan();

        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Request request = null;
            try {
                request = Utils.getRequest(br);

//				System.out.println(request.getBody());
            } catch (Exception e) {
                System.out.println("Utility Exception...."+e);
            }

            System.out.println(request.getPath());
            Mapping m = holder.getMapping().get(request.getPath());
            String actualResult = "";
            try {
                actualResult = (String)m.getMethod().invoke(m.getObject(), request.getBody());
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            PrintWriter out = new PrintWriter(socket.getOutputStream());

            // we send HTTP Headers with data to client
            Utils.writeResponse(out, actualResult);

            socket.close();
        }
    }
}
