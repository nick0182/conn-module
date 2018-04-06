package ru.nikolay.connmodule.device.connect;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ModBusConnect {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ConnectCallable connectCallable;
    private Future<Boolean> connectStatus;
    private ModbusTCPTransaction modbusTCPTransaction;
    private TCPMasterConnection conn;

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


    public boolean isConnected() {
        return connectStatus.isDone() && connectCallable.isConnected();
    }

    public ModbusTCPTransaction getTransaction() {
        if (modbusTCPTransaction == null) {
            modbusTCPTransaction = new ModbusTCPTransaction(connectCallable.getConnection());
            modbusTCPTransaction.setRetries(3);
            return modbusTCPTransaction;
        } else
            return modbusTCPTransaction;
    }

    public ModbusResponse request(ModbusRequest request) throws ModbusException {
        getTransaction().setRequest(request);
        getTransaction().execute();
        return getTransaction().getResponse();
    }

    public void disconnect() {
        if (executorService != null)
            executorService.shutdown();
        if (connectCallable != null && connectCallable.isConnected()) {
            conn = connectCallable.getConnection();
            if (conn != null)
                conn.close();
        }
    }
}