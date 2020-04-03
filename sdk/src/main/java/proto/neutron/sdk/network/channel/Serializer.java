package proto.neutron.sdk.network.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.network.PacketBuf;

public final class Serializer extends MessageToByteEncoder<ProtoPacket> {

    @Override
    protected void encode(
            ChannelHandlerContext channelHandlerContext,
            ProtoPacket protoPacket,
            ByteBuf byteBuf
    ) throws Exception {
        PacketBuf buf = new PacketBuf(byteBuf);

        buf.writeVarInt(0x02);
        protoPacket.write(buf);
    }
}
