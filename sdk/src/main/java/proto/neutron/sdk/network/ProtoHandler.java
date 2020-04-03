package proto.neutron.sdk.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import proto.neutron.sdk.network.channel.ChannelBound;
import proto.neutron.sdk.network.channel.Deserializer;
import proto.neutron.sdk.network.channel.Serializer;

public final class ProtoHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addFirst("deserializer", new Deserializer())
                .addAfter("deserializer", "general_controller", new ChannelBound())
                .addAfter("general_controller", "serializer", new Serializer());
    }
}
