package proto.neutron.sdk.network.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import proto.neutron.api.network.Session;
import proto.neutron.api.network.info.Stage;
import proto.neutron.sdk.NeutronServer;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.packet.stage.HandShakePacket;

import java.net.InetSocketAddress;

public final class ChannelHandler extends SimpleChannelInboundHandler<ProtoPacket> {

    @Override
    public void channelActive(
            ChannelHandlerContext ctx
    ) throws Exception {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        NeutronServer.getSessionHandler().createSession(address);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(
            ChannelHandlerContext ctx
    ) throws Exception {
        System.out.println("Killed connection");
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(
            ChannelHandlerContext ctx,
            ProtoPacket protoPacket
    ) throws Exception {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        Session session = NeutronServer.getSessionHandler().findSession(address);

        if(protoPacket instanceof HandShakePacket) {
            int id = ((HandShakePacket) protoPacket).getStage();

            switch (id) {
                case 1:
                    session.setSessionStage(Stage.STATUS);
                    break;
                case 2:
                    session.setSessionStage(Stage.LOGIN);
                    break;
                default:
                    ctx.channel().close();
                    break;
            }
            return;
        }

        System.out.println(protoPacket);
        ctx.channel().writeAndFlush(protoPacket);
    }
}
