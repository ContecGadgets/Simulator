package com.example.Simulator.TCPServer.Callback.impl;

import com.example.Simulator.TCPServer.Callback.CallBackInterface;
import io.netty.channel.ChannelHandlerContext;

import java.util.Base64;

/**
 * Topsys SSPA 메시지 핸들러
 *
 * @author jhpark
 */
public class TopsysSspaCallBack implements CallBackInterface {
    /**
     * SSPA Status
     */
    private String sspaStatus = "{600000860}";

    @Override
    public byte[] read(byte[] requestBytes) {
        String requestMsg = new String(requestBytes);
        String responseMsg = null;
        System.out.println("요청들어온 메시지 입니다 : " + requestMsg);

        if (requestMsg.contains("CA")) {
            responseMsg = sspaStatus;
        } else {
            if (requestMsg.contains("CU")) {
                sspaStatus = "{600000861}";
            } else {
                sspaStatus = "{600000860}";
            }

            responseMsg = requestMsg;
        }

        return responseMsg.getBytes();
    }

    @Override
    public void afterClose(ChannelHandlerContext ctx) {

    }
}
