package ru.nikolay.connmodule.device.connect;

import net.wimpi.modbus.net.TCPMasterConnection;

import java.net.InetAddress;
import java.util.concurrent.Callable;

public class ConnectCallable implements Callable<Boolean> {

    private String ip;
    private String port;

    private TCPMasterConnection tcpMasterConnection;

    public ConnectCallable(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }


    @Override
    public Boolean call() {
        try {
            InetAddress serverAddr = InetAddress.getByName(ip);
            tcpMasterConnection = new TCPMasterConnection(serverAddr);
            if (!serverAddr.isReachable(1000)) {
                return false;
            }

            if(tcpMasterConnection != null && tcpMasterConnection.isConnected()) {
                tcpMasterConnection.close();
            }

            tcpMasterConnection.setPort(Integer.parseInt(port));
            tcpMasterConnection.setTimeout(1000);

            tcpMasterConnection.connect();

            return tcpMasterConnection.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        if(tcpMasterConnection == null)
            return false;
        return tcpMasterConnection.isConnected();
    }

    public TCPMasterConnection getConnection() {
        return tcpMasterConnection;
    }
}
