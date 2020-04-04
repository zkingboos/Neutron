package proto.neutron.sdk.network.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.network.PacketBuf;
import proto.neutron.sdk.packet.stage.LoginPacket;
import proto.neutron.sdk.packet.stage.PingPacket;
import proto.neutron.sdk.packet.stage.QueryPacket;

public final class Serializer extends MessageToByteEncoder<ProtoPacket> {

    @Override
    protected void encode(
            ChannelHandlerContext ctx,
            ProtoPacket protoPacket,
            ByteBuf byteBuf
    ) throws Exception {
        PacketBuf buf = new PacketBuf(byteBuf);

        buf.writeVarInt(0);

        if(protoPacket instanceof QueryPacket) {
            QueryPacket packet = (QueryPacket) protoPacket;
            packet.write(buf);
        }

        if(protoPacket instanceof PingPacket) {
            PingPacket packet = (PingPacket) protoPacket;
            packet.write(buf);
        }

        if(protoPacket instanceof LoginPacket) {
            LoginPacket packet = (LoginPacket) protoPacket;
            packet.write(buf);
        }
    }
}
