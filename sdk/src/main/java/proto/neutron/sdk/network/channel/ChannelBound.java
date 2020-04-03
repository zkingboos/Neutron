package proto.neutron.sdk.network.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import proto.neutron.api.network.Session;
import proto.neutron.api.network.info.Stage;
import proto.neutron.sdk.NeutronServer;
import proto.neutron.sdk.ProtoPacket;
import proto.neutron.sdk.packet.stage.HandShakePacket;
import proto.neutron.sdk.packet.stage.LoginPacket;

import java.net.InetSocketAddress;

public final class ChannelBound extends SimpleChannelInboundHandler<ProtoPacket> {

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
                case 0:
                    session.setSessionStage(Stage.HANDSHAKE);
                    break;
                case 1:
                    session.setSessionStage(Stage.STATUS);
                    break;
                case 2:
                    session.setSessionStage(Stage.LOGIN);
                    break;
                default:
                    ctx.channel().close();
                    return;
            }
            return;

        }

        if(protoPacket instanceof LoginPacket) {
            LoginPacket loginPacket = (LoginPacket) protoPacket;

            ctx.channel().writeAndFlush(loginPacket);
        }
    }
}
