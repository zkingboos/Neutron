package proto.neutron.sdk;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.SneakyThrows;
import proto.neutron.api.Server;
import proto.neutron.api.plugin.Plugin;
import proto.neutron.sdk.network.ProtoHandler;
import proto.neutron.sdk.network.session.SessionHandler;

import java.util.Collections;
import java.util.Set;

public class NeutronServer extends Thread implements Server {

    @Getter(lazy = true)
    private final static SessionHandler sessionHandler = new SessionHandler();

    @Getter
    private final Set<Plugin> plugins = Collections.emptySet();

    @SneakyThrows
    @Override
    public void run() {
        protonServer();
    }

    @Override
    public void protonServer() throws InterruptedException {
        EventLoopGroup general = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap strap = new ServerBootstrap()
                .group(general, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ProtoHandler());

        ChannelFuture future = strap
                .bind("0.0.0.0", 25565)
                .sync();

        if(future.isSuccess()) {
            System.out.println("Listening connections");
        }

        future.channel().closeFuture().sync();

        general.shutdownGracefully();
        worker.shutdownGracefully();
    }
}
