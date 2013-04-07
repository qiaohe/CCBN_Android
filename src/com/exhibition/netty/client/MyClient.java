package com.exhibition.netty.client;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import com.google.gson.Gson;

public class MyClient {
	//一个创建客户端通道和尝试连接的帮助类.
	private ClientBootstrap bootstrap;
	
	public interface MessageListener {
        public void onMessageReceived(MessageEvent e);
    }
	
	public MyClient() {
        bootstrap = new ClientBootstrap((ChannelFactory) new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors
                .newCachedThreadPool()));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline result = new DefaultChannelPipeline();
                
                result.addLast("encode", new StringEncoder());//增加处理器
                result.addLast("decode", new StringDecoder());
                result.addLast("handler", new ClientHandler());
                return result;
            }
        });
        
        bootstrap.setOption("tcpNoDelay", true);  //设置选项集
        bootstrap.setOption("keepAlive", true);
    }   
	/**
	 * 建立socket连接
	 * @param host 地址
	 * @param poot 端口号
	 * @return
	 */
    private ChannelFuture getChannelFuture(final String host,final int poot) {
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(host, poot));
        
        channelFuture.awaitUninterruptibly();//等待该future完成.该方法会捕捉InterruptedException并安静丢弃
        if (!channelFuture.isSuccess()) {
            channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
            return null;
        }
        return channelFuture;
    }

    public void send(final String jSonMessage,final String host,final int poot) {
       
    	
        ChannelFuture future = getChannelFuture(host,poot);		//一个通道的异步 I/O操作结果
        if (future != null) {
            future.getChannel().write(jSonMessage);
        }
    }
    
}
