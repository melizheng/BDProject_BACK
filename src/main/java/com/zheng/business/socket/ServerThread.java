package com.zheng.business.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 单个线程对应单个用户操作
 * Date:2022/3/1710:13
 **/
public class ServerThread extends Thread{
    //连接上套接字的用户列表
    private static ArrayList<SocketUser> list;
    //对应的用户
    private SocketUser user;
    //是否是第一次读取信息，是的话需要得到id和name
    private boolean firstTime = true;
    //是否一直读取
    private boolean Reading=true;
    //未发送成功的消息队列
    private static HashMap<String,String> waitSendMessages;

    public ServerThread(SocketUser user, ArrayList<SocketUser> list,HashMap<String,String> waitSendMessages) {
        this.user = user;
        this.list = list;
        this.waitSendMessages=waitSendMessages;
    }


    public void run() {
        try {
            // 获取该客户端的printwriter用来给该客户端发连接成功的消息
            PrintWriter pw = user.getPw();
            pw.write("已连接服务器" + "\n");
            pw.flush();
            while (Reading) {
                // 信息的格式：
                // login,id,name
                // say,接收者的id,消息对应的记录的id,提示的内容,时间long类型,type
                // rename,name
                // exit
                // heart
                // 不断地读取客户端发过来的信息
            String msg = user.getBr().readLine();
            if(msg==null){
                System.out.println("非法信息:" + msg);
                remove(user);
                break;
            }
            // System.out.println(msg);
                String[] str = msg.split(",");
                if (firstTime) {
                    if (str[0].equals("login")) {
                        judgeCanLogin(str);
                        user.setId(str[1]);
                        user.setName(str[2]);
                        System.out.println(list);
                        firstTime = false;
                        recMessages();
                    } else {
                        System.out.println("非法用户请求套接字——移除用户");
                        remove(user);
                        break;
                    }
                }
                switch (str[0]) {
                    case "login":
                    case "heart":
                        break;
                    case "rename":
                        user.setName(str[1]);
                        break;
                    case "exit":
                        remove(user);// 移除用户
                        break;
                    case "say":
                        judgeCanSend(str);
                        break;
                    default:
                        System.out.println("非法信息:" + msg);
                        remove(user);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("接受指令异常,异常原因："+e.getMessage());
            remove(user);
        }
    }

    /**
     * 判断是否允许登录
     * @param str
     */
    private void judgeCanLogin(String[] str) {
        for (int i=0;i<list.size();i++) {
            //得到对应的id  可能出现1个ip地址同个账号对应多个套接字的情况
            if (list.get(i).getId().equals(str[1])) {
                //同一个ip只能建立一个套接字,移除先前的套接字
                remove(list.get(i));
               // if( list.get(i).getSocket().getInetAddress().equals(user.getSocket().getInetAddress())){
               //     System.out.println("同一个ip同一个用户只能建立一个套接字,移除先前的套接字");
               //     remove(list.get(i));
               // }
            }
        }
    }

    /**
     * 接受离线消息
     */
    private void recMessages(){
        String instructions = waitSendMessages.get(user.getId());
        if(null!=instructions){
            String[] split = instructions.split(";");
            PrintWriter pw = user.getPw();
            for (int i=0;i<split.length;i++){
                pw.write(split[i] + "\n");
                pw.flush();
            }
            waitSendMessages.remove(user.getId());
        }
    }
    /**
     * 得到用户想发送的信息时，判断信息发送对象/格式是否能够发送
     * 消息格式：行为，接受对象id，消息跳转的id,标题,时间,类型
     * @param str
     */
    private void judgeCanSend(String[] str) {
        if(str[1].equals(user.getId()))
            System.out.println("信息接收者异常——拒绝执行");
        else if(str.length!=6)
            System.out.println("信息格式异常——拒绝执行");
        else
            sendToClients(str[1], str[2]+","+str[3]+","+user.getName()+","+str[4]+","+str[5]); // 转发信息给特定的用户
    }

    /**
     * 发送信息调用的方法
     * @param userId 想发送的对象的id
     * @param instruction  发送的内容
     */
    public synchronized static  boolean sendToClients(String userId, String instruction) {
        if(list==null) {
            System.out.println(userId+"没有查找到~消息"+instruction+"发送失败");
            //存到待发送队列
            String instructions = waitSendMessages.get(userId);
            if(null!=instructions)
                waitSendMessages.put(userId,instructions+";"+instruction);
            else
                waitSendMessages.put(userId,instruction);
            return false;
        }
        //是否查找到该用户标识
         boolean findOther = false;
        // 从头查起list
        int number=0;
        for (SocketUser userFind : list) {
            //得到对应的id  可能出现1个账号对应多个套接字的情况
            if (userFind.getId().equals(userId)) {
                findOther=true;
                try {
                    // System.out.println(user.getName()+"发送给用户：" + userFind.getId() + "--" + userFind.getName() + ":" + instruction);
                    PrintWriter pw = userFind.getPw();
                    pw.write(instruction + "\n");
                    pw.flush();
                    number++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(findOther) {
            System.out.println("总共发送"+number+"个客户端");
            return true;
        }
        else {
            System.out.println(userId+"没有查找到~消息"+instruction+"发送失败");
            //存到待发送队列
            String instructions = waitSendMessages.get(userId);
            if(null!=instructions)
                waitSendMessages.put(userId,instructions+";"+instruction);
            else
                waitSendMessages.put(userId,instruction);
            return false;
        }
    }



    /**
     * 用户退出/其他原因关闭套接字
     * @param userLogOut  用户
     */
    private void remove(SocketUser userLogOut) {
        try {
            System.out.println("用户"+userLogOut.getId()+"--"+userLogOut.getName()+"退出");
            System.out.println(userLogOut.getSocket()+"out");
            userLogOut.getBr().close();
            userLogOut.getSocket().close();
            list.remove(userLogOut);
            if(userLogOut.getSocket().equals(user))
                Reading=false;
        } catch (IOException e) {
            System.out.println("remove异常原因：");
            e.printStackTrace();
        }

    }

}
