package proto.neutron.sdk.network.session;

import proto.neutron.api.network.Session;
import proto.neutron.api.network.info.Stage;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public final class SessionHandler {

    private final List<Session> sessions = new ArrayList<>();

    public Session createSession(InetSocketAddress address){
        Session cachedSession = findSession(address);
        if(cachedSession != null) sessions.remove(cachedSession);

        cachedSession = new ProtoSession(
                address,
                System.currentTimeMillis(),
                Stage.HANDSHAKE
        );

        sessions.add(cachedSession);
        return cachedSession;
    }

    public Session findSession(InetSocketAddress address){
        for (Session session : sessions) {
            if(session.getAddress() == address) return session;
        }
        return null;
    }
}
