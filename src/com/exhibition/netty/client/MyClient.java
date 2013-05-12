package com.exhibition.netty.client;

import android.content.Context;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;


public class MyClient {

    private ClientBootstrap bootstrap;

    public MyClient() {
    	this(null);
        bootstrap = new ClientBootstrap((ChannelFactory) new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors
                .newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline result = new DefaultChannelPipeline();

                result.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
                result.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
                result.addLast("handler", new ClientHandler());
                return result;
            }
        });

        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
    }

    public MyClient(final Context context) {
        bootstrap = new ClientBootstrap((ChannelFactory) new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors
                .newCachedThreadPool()));   
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline result = new DefaultChannelPipeline();  
                result.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
                result.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
                result.addLast("handler", new ClientHandler(context));
                return result;
            }
        });
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
    }

    /**
     * 建立socket连接
     * -	 * @param host 地址
     * -	 * @param poot 端口号
     */
    private ChannelFuture getChannelFuture(final String host, final int poot) {
        try {
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(host, poot));
            channelFuture.awaitUninterruptibly();
            if (!channelFuture.isSuccess()) {
                channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
                return null;
            }
            return channelFuture;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }  
    }

    public void send(final String jSonMessage, final String host, final int poot) {  
        ChannelFuture future = getChannelFuture(host, poot);
        if (future != null) {
            future.getChannel().write(jSonMessage);
        }
    }

    //private Context context;
    public interface MessageListener {
        public void onMessageReceived(MessageEvent e);
    }

}
