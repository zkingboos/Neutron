package proto.neutron.sdk.network.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import proto.neutron.api.network.Session;
import proto.neutron.api.network.info.Stage;
import proto.neutron.sdk.NeutronServer;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.network.PacketBuf;
import proto.neutron.sdk.packet.PacketContainer;

import java.net.InetSocketAddress;
import java.util.List;

public final class Deserializer extends ByteToMessageDecoder {

    @Override
    protected void decode(
            ChannelHandlerContext ctx,
            ByteBuf byteBuf,
            List<Object> out
    ) throws Exception {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();

        Session session = NeutronServer.getSessionHandler().findSession(address);
        Stage stage = session.getSessionStage();

        PacketBuf buf = new PacketBuf(byteBuf);

        int size = buf.readVarInt(), id = buf.readVarInt();

        System.out.println("Size: " + size + " id: " + id);

        ProtoPacket packet = PacketContainer.doByInId(stage, id);

        if(packet == null) return;

        packet.read(buf);

        System.out.println("Packet: " + packet);

        out.add(packet);
    }
}

