package com.github.liuyedeqi1.octopus.core.server;


import com.github.liuyedeqi1.octopus.core.OctopusRequest;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus的链接处理handler,用于处理RPC服务获取到的各个请求 ，并且根据rpcRequest中的信息反射以及动态代理到对应的对象实例化方法
 * @date 2020/4/2014:03
 */
public class OctopusProcessHandler implements Runnable {

    private Socket socket;

    private Map<String, Object> handlerMap;

    public OctopusProcessHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            OctopusRequest octopusRequest = (OctopusRequest) objectInputStream.readObject();

            if (octopusRequest == null) {
                throw new NullPointerException("OctopusRequest is null");
            }

            Object result = invoke(octopusRequest);
            if (result != null) {
                objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
                objectOutputStream.writeObject(result);
                objectOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(OctopusRequest octopusRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String name = octopusRequest.getBeanName();
        String methodName = octopusRequest.getMethod();
        String version = octopusRequest.getVersion();
        Object[] args = octopusRequest.getArgs();

        if (!StringUtils.isEmpty(version)) {
            name += "_" + version;
        }

        Object bean = handlerMap.get(name);

        if (bean == null) {
            System.out.println(String.format("name:%s, methodName:%s, version:%s",name,methodName,version));
            throw new RuntimeException("OctopusService not found:" + name);
        }

        Class classes = Class.forName(octopusRequest.getBeanName());
        Method method = null;
        if (args != null) {
            Class<?>[] types = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                types[i] = arg.getClass();
            }
            method = classes.getMethod(methodName, types);
        } else {
            method = classes.getMethod(methodName);
        }

        Object result = method.invoke(bean, args);

        return result;
    }
}
