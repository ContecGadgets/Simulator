package com.example.Simulator.TCPServer;

import com.example.Simulator.TCPServer.Callback.CallBackInterface;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final CallBackInterface handler;

    /**
     * 장비 별 메시지 핸들러 설정
     *
     * @param handler 메시지 핸들러
     */
    public NettyServerHandler(CallBackInterface handler) {
        this.handler = handler;
    }

    /**
     * 클라이언트 요청 메시지 수신
     *
     * @param ctx 이벤트 처리 객체
     * @param msg 요청 메시지
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf inBuffer = (ByteBuf) msg;
        byte[] requestBytes = new byte[inBuffer.readableBytes()];
        inBuffer.readBytes(requestBytes);

        byte[] responseBytes = handler.read(requestBytes);

        if (responseBytes != null) {
            ctx.write(Unpooled.wrappedBuffer(responseBytes));
        }
    }

    /**
     * 클라이언트 요청 메시지 수신 완료
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    /**
     * 서버 오류 시 자동 처리
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
