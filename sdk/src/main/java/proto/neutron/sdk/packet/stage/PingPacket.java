package proto.neutron.sdk.packet.stage;

import lombok.Getter;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.network.PacketBuf;

@Getter
public final class PingPacket extends ProtoPacket {

    private long ping;

    @Override
    public void read(PacketBuf buf) {
        ping = buf.readVarLong();
    }
}
