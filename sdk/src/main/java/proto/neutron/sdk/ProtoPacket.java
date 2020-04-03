package proto.neutron.sdk;

import proto.neutron.api.network.Packet;
import proto.neutron.sdk.network.PacketBuf;

public abstract class ProtoPacket implements Packet {

    public void read(PacketBuf buf) {}
    public void write(PacketBuf buf){}
}
