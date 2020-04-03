package proto.neutron.sdk.boot;

import lombok.Getter;
import proto.neutron.api.Server;
import proto.neutron.sdk.NeutronServer;

public final class Bootstrap {

    @Getter
    private static Server instanceServer;

    public static void main(String[] args) throws Exception {
        NeutronServer server = new NeutronServer();
        server.start();

        instanceServer = server;
    }
}
