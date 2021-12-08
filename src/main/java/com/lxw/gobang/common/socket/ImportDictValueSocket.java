package com.lxw.gobang.common.socket;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @description
 * @author  lxw
 * @updateTime 2021/12/3 9:36
 */
@ServerEndpoint(value = "/websocket/dictSocket/{userId}")
@Component
public class ImportDictValueSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<ImportDictValueSocket> webSocketSet = new CopyOnWriteArraySet<ImportDictValueSocket>();
    private static Map<String ,CopyOnWriteArraySet<ImportDictValueSocket>> webSocketSetRoomMap = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String userId;
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam("userId") String userId,@PathParam("roomId") String roomId, Session session) {
        System.out.println(session);
        this.session = session;
        this.userId = userId;
        webSocketSet.add(this);     //加入set中
//        if(!StringUtils.isEmpty(roomId)){
//            webSocketSetRoomMap.put(roomId,this);
//        }
        if (!webSocketSetRoomMap.containsKey(roomId)) {
            // 创建房间不存在时，创建房间
            CopyOnWriteArraySet<ImportDictValueSocket> room = new CopyOnWriteArraySet<>();
            // 添加用户
            room.add(this);
            webSocketSetRoomMap.put(roomId, room);
        } else {
            // 房间已存在，直接添加用户到相应的房间
            webSocketSetRoomMap.get(roomId).add(this);
        }
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        //群发消息
        for (ImportDictValueSocket item : webSocketSet) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("data","当前在线人数"+getOnlineCount()+",连接用户："+userId);
                item.sendMessage(jsonObject.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data","当前在线人数"+getOnlineCount()+",当前sessionID:"+this.session.getId());
            sendMessage(jsonObject.toJSONString());
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("roomId") String roomId) {
        webSocketSet.remove(this);  //从set中删除
        webSocketSetRoomMap.get(roomId).remove(this);
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        System.out.println("来自客户端的消息:" + message);
        System.out.println("来自用户："+userId);
//        for(int i=0;i<5;i++){
//            // 给当前的session返回进度
//            session.getBasicRemote().sendText("当前进度为:"+i*20);
//            Thread.sleep(2000);
//        }


        //群发消息
        for (ImportDictValueSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     @OnError
     public void onError(Session session, Throwable error) {
     System.out.println("发生错误");
     error.printStackTrace();
     }  **/


    public void sendMessage(String message) throws IOException {
        System.out.println("发送消息："+message);
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String type,Object message) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",type);
        jsonObject.put("data",message);
        for (ImportDictValueSocket item : webSocketSet) {
            try {
                System.out.println(item.session.getId());
                item.sendMessage(jsonObject.toJSONString());
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 按房间群发自定义消息
     * */
    public static void sendInfoByRoomId(String type,Object message,String roomId) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",type);
        jsonObject.put("data",message);
        for (ImportDictValueSocket item : webSocketSetRoomMap.get(roomId)) {
            try {
                System.out.println(item.session.getId());
                item.sendMessage(jsonObject.toJSONString());
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ImportDictValueSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ImportDictValueSocket.onlineCount--;
    }
}
