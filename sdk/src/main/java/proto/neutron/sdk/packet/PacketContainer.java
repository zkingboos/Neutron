package proto.neutron.sdk.packet;

import proto.neutron.sdk.ProtoPacket;
import proto.neutron.api.network.info.Direction;
import proto.neutron.api.network.info.Stage;
import proto.neutron.sdk.packet.stage.*;

import java.util.ArrayList;
import java.util.List;

public final class PacketContainer {

    private final static List<PacketInfo> PACKET_INFO = new ArrayList<>();

    static {
        PACKET_INFO.add(PacketInfo.builder()
                .name("HandshakePacket")
                .inboundId(0x00)
                .direction(Direction.INBOUND)
                .stage(Stage.HANDSHAKE)
                .clazz(HandShakePacket.class)
                .supplier(HandShakePacket::new)
                .build());

        PACKET_INFO.add(PacketInfo.builder()
                .name("QueryPacket")
                .inboundId(0x00)
                .direction(Direction.INBOUND)
                .stage(Stage.STATUS)
                .clazz(QueryPacket.class)
                .supplier(QueryPacket::new)
                .build());

        PACKET_INFO.add(PacketInfo.builder()
                .name("PingPacket")
                .inboundId(0x01)
                .direction(Direction.INBOUND)
                .stage(Stage.STATUS)
                .clazz(PingPacket.class)
                .supplier(PingPacket::new)
                .build());

        PACKET_INFO.add(PacketInfo.builder()
                .name("LoginPacket")
                .inboundId(0x00)
                .outboundId(0x02)
                .direction(Direction.BOTH)
                .stage(Stage.LOGIN)
                .clazz(LoginPacket.class)
                .supplier(LoginPacket::new)
                .build());
    }

    public static PacketInfo findByInId(
            Stage stage,
            int id
    ){
        for (PacketInfo packetInfo : PACKET_INFO) {
            if(packetInfo.getInboundId() == id && packetInfo.getStage() == stage) {
                return packetInfo;
            }
        }
        return null;
    }

    public static ProtoPacket doByInId(
            Stage stage,
            int id
    ){
        PacketInfo info = findByInId(stage, id);
        if(info != null) return info.initialize();
        return null;
    }
}