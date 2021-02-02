package com.example.Simulator.TCPServer.Callback;

import io.netty.channel.ChannelHandlerContext;

/**
 * 클라이언트 요청 메시지 처리 인터페이스
 *
 * @author jhpark
 */
public interface CallBackInterface {
    /**
     * 클라이언트 요청 수신
     *
     * @param requestBytes 클라이언트 요청 메시지
     */
    public byte[] read(byte[] requestBytes);

    /**
     * 클라이언트와 통신 종료시 동작
     * 
     * @param ctx
     */
    public void afterClose(ChannelHandlerContext ctx);
}
