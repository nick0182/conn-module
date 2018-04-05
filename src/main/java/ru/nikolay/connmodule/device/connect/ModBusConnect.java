package ru.nikolay.connmodule.device.connect;

import net.wimpi.modbus.net.TCPMasterConnection;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ModBusConnect {

    private ExecutorService executorService =Executors.newSingleThreadExecutor();
    private ConnectCallable connectCallable;
    private Future<Boolean> connectStatus;
    private String id;
    private String port;


    public boolean connect(String ip, String port) {

        connectCallable = new ConnectCallable(ip, port);

        connectStatus = executorService.submit(connectCallable);

        try {

            return connectStatus.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

            return false;
        }
    }





}
