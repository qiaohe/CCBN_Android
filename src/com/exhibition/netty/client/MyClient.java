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
	//һ�������ͻ���ͨ���ͳ������ӵİ�����.
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
                
                result.addLast("encode", new StringEncoder());//���Ӵ�����
                result.addLast("decode", new StringDecoder());
                result.addLast("handler", new ClientHandler());
                return result;
            }
        });
        
        bootstrap.setOption("tcpNoDelay", true); //设置选项集
        bootstrap.setOption("keepAlive", true);
    }
	
	/**
	 * 建立socket连接
-	 * @param host 地址
-	 * @param poot 端口号
	 */
	
    private ChannelFuture getChannelFuture(final String host,final int poot) {
    	
    	try{
    		ChannelFuture channelFuture  = bootstrap.connect(new InetSocketAddress(host, poot));
    		channelFuture.awaitUninterruptibly();
    		if (!channelFuture.isSuccess()) {  
                channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
                return null;
            }
    		return channelFuture;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
        
        
        
    }

    public void send(final String jSonMessage,final String host,final int poot) {
       
    	
        ChannelFuture future = getChannelFuture(host,poot);		//һ��ͨ�����첽 I/O�������
        if (future != null) {
            future.getChannel().write(jSonMessage);
        }
    }
    
}
