package com.example.Simulator.TCPServer.Callback.impl;

import com.example.Simulator.Status.OrbitalStatus;
import com.example.Simulator.TCPServer.Callback.CallBackInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.netty.channel.ChannelHandlerContext;

/**
 * Orbital ACU 메시지 핸들러
 *
 * @author jhpark
 */
public class OrbitalACUCallBack implements CallBackInterface {

    @Override
    public byte[] read(byte[] requestBytes) {
        //Json 파싱 처리
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //클라이언트에서 보낸 데이터 중 STX, ETX 분리
        byte[] bytes = new byte[requestBytes.length - 2];
        System.arraycopy(requestBytes, 1, bytes, 0, bytes.length);
        String msg = new String(bytes);

        try {
            OrbitalStatus.Request request = mapper.readValue(msg, OrbitalStatus.Request.class);

            if (request.getEntity().contains("antenna_positioner")) {

            } else if (request.getEntity().contains("FTSS-00-CB-99-01")) {

            } else if (request.getEntity().contains("PA-01")) {

            }

            System.out.println(request.toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("요청들어온 메시지 입니다 : " + msg);

        msg = "{\"entity\":\"FTSS-00-CB-99-01\",\"reason\":\"command_response\",\"elements\":{\"purge_valve\":\"Closed\",\"feed_temperature\":14.3,\"feed_humidity\":19.1,\"tx_polarity\":\"RHCP\"}}";

        return msg.getBytes();
    }

    @Override
    public void afterClose(ChannelHandlerContext ctx) {
        System.out.println("커넥션이 끊기면 동작하는 메소드 입니다.");
    }
}
