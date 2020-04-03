package proto.neutron.api.network;

import proto.neutron.api.network.info.Stage;

import java.net.InetSocketAddress;

public interface Session {

    InetSocketAddress getAddress();
    long getTimeConnection();

    Stage getSessionStage();
    void setSessionStage(Stage stage);
}
