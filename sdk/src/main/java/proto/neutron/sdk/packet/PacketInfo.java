package proto.neutron.sdk.packet;

import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.api.network.info.Direction;
import proto.neutron.api.network.info.Stage;

import java.util.function.Supplier;

@Getter
@Builder
public class PacketInfo {

    private String name;
    private int inboundId, outboundId;

    private Direction direction;
    private Stage stage;

    private Class<?> clazz;
    private Supplier<ProtoPacket> supplier;

    @SneakyThrows
    public ProtoPacket initialize() {
        return supplier.get();
    }
}
