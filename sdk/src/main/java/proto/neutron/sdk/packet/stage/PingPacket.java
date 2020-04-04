package proto.neutron.sdk.packet.stage;

import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.network.PacketBuf;

public final class PingPacket extends ProtoPacket {

    private long payload;

    @Override
    public void read(PacketBuf buf) {
        payload = buf.readLong();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeByte(0x01);
        buf.writeLong(payload);
    }
}
