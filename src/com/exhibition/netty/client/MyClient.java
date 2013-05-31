package com.exhibition.netty.client;

import android.content.Context;
import com.exhibition.domain.mobile.MessageObject;
import com.exhibition.utils.Resources;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;


public class MyClient {
    private ClientBootstrap bootstrap;

    public MyClient() {
        this(null);
    }

    public MyClient(final Context context) {
        bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline result = new DefaultChannelPipeline();
                result.addLast("encode", new ObjectEncoder());
                result.addLast("decode", new ObjectDecoder(
                        ClassResolvers.cacheDisabled(MessageObject.class.getClassLoader())));
                result.addLast("handler", new ClientHandler(context));
                return result;
            }
        });
    }

    /**
     * 建立socket连接
     * -	 * @param host 地址
     * -	 * @param port 端口号
     */
    private ChannelFuture getChannelFuture(final String host, final int port) {
        try {
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
            channelFuture.awaitUninterruptibly();
            if (!channelFuture.isSuccess()) {
                channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
                return null;
            }
            return channelFuture;
        } catch (Exception e) {
            return null;
        }
    }

    public void send(final String jSonMessage, final String host, final int port) {
        ChannelFuture future = getChannelFuture(host, port);
        if (future != null) {
            future.getChannel().write(jSonMessage);
            Resources.isSocketLinked = true;
        }
    }

    public void send(final String host, final int port, MessageObject messageObject) {
        ChannelFuture future = getChannelFuture(host, port);
        if (future != null) {
            future.getChannel().write(messageObject);
        }
    }
}
