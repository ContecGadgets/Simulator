package com.example.Simulator.TCPServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * 네티 서버 구동
 *
 * @author jhpark
 */
@Sharable
public class NettyServer {
    /**
     * 네티 서버 초기화
     *
     * @param serverList 장비 별 메시지 처리 핸들러, 접속 포트
     */
    public void init(HashMap<ChannelInboundHandlerAdapter, Integer> serverList) {
        //네트워크 소켓에서 들어오는 새로운 이벤트(데이터)를 계속 찾는 루프 - 스레드 기반임
        //그룹에서 동작할 스레드 수를 결정하지 않을 경우 하드웨어가 가지고 있는 CPU*2 배의 스레드를 사용한다.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        Collection<ChannelFuture> channels = new ArrayList<>(serverList.size());
        try {
            for (HashMap.Entry<ChannelInboundHandlerAdapter, Integer> entry : serverList.entrySet()) {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                //그룹이 연결되어야 서버가 실행이 되고 클라이언트 요청을 받을 수 있음
                serverBootstrap.group(bossGroup, workerGroup)
                        //소켓의 입출력 모드 설정
                        .channel(NioServerSocketChannel.class)
                        //연결된 클라이언트를 핸들링 해줄 설정
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(entry.getKey());
                            }
                        })
                        //클라이언트에서 보낸 데이터를 읽을 수 있는 최대 크기
                        .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(4096))
                        .option(ChannelOption.SO_BACKLOG, 128)  //클라이언트가 동시에 Send해서 데이터를 요청 할 수 있는 최대치
                        .option(ChannelOption.SO_KEEPALIVE, true);  //패킷여부

                //포트 바인드 수행
                ChannelFuture channelFuture = serverBootstrap.bind(entry.getValue()).sync();
                channels.add(channelFuture);
            }
            //모든 작업은 비동기로 동작함
            //서버가 종료될 때까지 대기
            for (ChannelFuture ch : channels) {
                ch.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bossGroup.shutdownGracefully().sync();
                workerGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
