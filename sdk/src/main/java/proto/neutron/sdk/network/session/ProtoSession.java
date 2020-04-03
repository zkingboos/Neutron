package proto.neutron.sdk.network.session;

import lombok.*;
import proto.neutron.api.network.Session;
import proto.neutron.api.network.info.Stage;

import java.net.InetSocketAddress;

@Getter
@ToString
@AllArgsConstructor
public final class ProtoSession implements Session {

    private InetSocketAddress address;
    private long timeConnection;

    @Setter
    private Stage sessionStage;
}
