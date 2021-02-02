package com.example.Simulator;

import com.example.Simulator.TCPServer.Callback.impl.TopsysSspaCallBack;
import com.example.Simulator.TCPServer.NettyServer;
import com.example.Simulator.TCPServer.Callback.impl.OrbitalACUCallBack;
import com.example.Simulator.TCPServer.NettyServerHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class SimulatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
        NettyServer handler = new NettyServer();
        HashMap<ChannelInboundHandlerAdapter, Integer> serverList = new HashMap<>();

        serverList.put(new NettyServerHandler(new OrbitalACUCallBack()),12000);
        serverList.put(new NettyServerHandler(new TopsysSspaCallBack()),12001);

        handler.init(serverList);
    }
}
