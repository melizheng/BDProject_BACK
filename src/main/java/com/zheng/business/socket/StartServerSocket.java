package com.zheng.business.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动
 * Date:2022/3/1710:59
 **/

@Component
public class StartServerSocket implements CommandLineRunner {

    @Autowired
    BusinessServerSocket businessServerSocket;

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        businessServerSocket.ServerSocketWorking();
    }

}