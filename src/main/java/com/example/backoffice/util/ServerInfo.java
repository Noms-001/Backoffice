package com.example.backoffice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerInfo {

    @Value("${server.port}")
    private String port;

    @Value("${server.address}")
    private String address;

    public String getAddress() {
        return address;
    }

    public String getPort() {
        return port;
    }
}