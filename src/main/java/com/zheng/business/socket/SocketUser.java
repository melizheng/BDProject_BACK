package com.zheng.business.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 每个用户的套接字,成员：用户id,name,socket，输入流，输出流
 * Date:2022/3/179:56
 **/
public class SocketUser {
    private String id;
    private String name;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }

    public SocketUser(String id, String name, final Socket socket) throws IOException {
        this.id = id;
        this.name = name;
        this.socket = socket;
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
    }

    @Override
    public String toString() {
        return "SocketUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", socket=" + socket +
                '}';
    }
}
