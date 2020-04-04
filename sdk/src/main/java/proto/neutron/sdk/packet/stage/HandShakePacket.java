package proto.neutron.sdk.packet.stage;

import lombok.Getter;
import lombok.ToString;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.network.PacketBuf;

@ToString
@Getter
public final class HandShakePacket extends ProtoPacket {

    private Integer protocol, port, stage;
    private String address;

    @Override
    public void read(PacketBuf buf) {
        protocol = buf.readVarInt();
        address = buf.readString(255);
        port = buf.readUnsignedShort();
        stage = buf.readVarInt();
    }
}
