package com.github.liuyedeqi1.octopus.core.client;

import com.github.liuyedeqi1.octopus.core.OctopusRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus客户端连接对象，用于连接服务端
 * @date 2020/4/2016:13
 */
public class OctopusClientConnector {

    private String host;
    private int port;


    public OctopusClientConnector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object call(OctopusRequest request) throws Exception{
        Object result = null;
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {
            socket = new Socket(host, port);

            outputStream =new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();

            inputStream=new ObjectInputStream(socket.getInputStream());
            result=inputStream.readObject();
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
