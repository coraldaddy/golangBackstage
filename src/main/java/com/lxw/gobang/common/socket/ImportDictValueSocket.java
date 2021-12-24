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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private static Map<String, Integer> onlineCount = new ConcurrentHashMap<>();
    private static int onlineCountAll = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<ImportDictValueSocket> webSocketSet = new CopyOnWriteArraySet<ImportDictValueSocket>();
    private static Map<String ,CopyOnWriteArraySet<ImportDictValueSocket>> webSocketSetRoomMap = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String userId;

    private String roomId;
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        closeSession();
        System.out.println(session);
        this.session = session;
        this.userId = userId;
        this.roomId = "goBangHall";
        webSocketSet.add(this);     //加入set中
        addOnlineCount("goBangHall"); // 大厅在线加1
        addOnlineCountAll(); // 总人数加1
        System.out.println("有新连接加入！当前房间在线人数为" + getOnlineCount(roomId));
        System.out.println("有新连接加入！当前总在线人数为" + getOnlineCountAll());
        //群发大厅消息
        addWebSocketSetRoomMap("goBangHall");
        sendGoBangHallInfo();
    }
    /**
     * @description 群发五子棋大厅信息消息
     * @author  lxw
     * @updateTime 2021/12/10 15:34
     */
    public void sendGoBangHallInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","roomList");
        Map map = new HashMap();
        for (String roomId: webSocketSetRoomMap.keySet()) {
            map.put(roomId,onlineCount.get(roomId));
        }
        jsonObject.put("data",map);
        sendAll(jsonObject.toJSONString());
    }

    /**
     * @description 群发五子棋房间信息消息
     * @author  lxw
     * @updateTime 2021/12/10 15:37
     */
    public void sendGoBangHomeInfo(String roomId){
        for (ImportDictValueSocket item : webSocketSetRoomMap.get(roomId)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type","userNum");
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("num",ImportDictValueSocket.getOnlineCount(roomId));
                jsonObject1.put("roomId",roomId);
                jsonObject.put("data",jsonObject1);
                item.sendMessage(jsonObject.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        closeSession();
        if(!"goBangHall".equals(this.roomId)){
            sendGoBangHomeInfo(this.roomId);
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("color","");
        try {
            this.sendInfo("changeColor",this.roomId,jsonObject1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        subOnlineCount(this.roomId);           //在线数减1
        webSocketSet.remove(this);  //从set中删除
        System.out.println("关闭连接");
        webSocketSetRoomMap.get(this.roomId).remove(this);
        subOnlineCountAll();
        sumMapPeople();
        System.out.println("有一连接关闭！当前房间在线人数为" + getOnlineCount(roomId));
        System.out.println("有一连接关闭！当前总在线人数为" + getOnlineCountAll());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        closeSession();
        System.out.println("来自客户端的消息:" + message);
        System.out.println("来自用户："+userId);
        //群发消息
        JSONObject jsonObject = JSONObject.parseObject(message);
        String type = jsonObject.getString("type");
        switch (type){
            case "gotoRoom":
                String roomId = jsonObject.getString("roomId");
                this.roomId = roomId;
                addWebSocketSetRoomMap(roomId);
                webSocketSetRoomMap.get("goBangHall").remove(this);
                addOnlineCount(roomId); // 房间在线数加1
                subOnlineCount("goBangHall"); // 大厅人数减1
                sendGoBangHomeInfo(roomId); // 下发当前房间人数消息
                break;
            case "outRoom":
                String outRoomId = jsonObject.getString("roomId");
                this.roomId = "goBangHall";
                webSocketSetRoomMap.get(outRoomId).remove(this);
                addOnlineCount("goBangHall"); // 大厅人数加1
                subOnlineCount(outRoomId); // 房间在线数减1
                sendGoBangHomeInfo(outRoomId); // 下发当前房间人数消息
                sendGoBangHallInfo(); // 下发大厅人数消息
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("color","");
                this.sendInfo("changeColor",outRoomId,jsonObject1);
                break;
            case "changeColor":
                String roomIdc = jsonObject.getString("roomId");
                this.sendInfo("changeColor",roomIdc,jsonObject.getJSONObject("data"));
                break;
            default:
                break;
        }
//        for (ImportDictValueSocket item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 发生错误时调用
     @OnError
     public void onError(Session session, Throwable error) {
     System.out.println("发生错误");
     error.printStackTrace();
     }  **/

    public void closeSession(){
        for (ImportDictValueSocket socket:webSocketSet) {
            if(!socket.session.isOpen()){
                webSocketSet.remove(socket);
            }
        }
        for (String roomId :webSocketSetRoomMap.keySet()) {
            for (ImportDictValueSocket socket:webSocketSetRoomMap.get(roomId)) {
                if(!socket.session.isOpen()){
                    webSocketSetRoomMap.get(roomId).remove(socket);
                }
            }
        }
    }
    public void sumMapPeople(){
        this.onlineCountAll = this.webSocketSet.size();
        for (String roomId :webSocketSetRoomMap.keySet()) {
            onlineCount.put(roomId,webSocketSetRoomMap.get(roomId).size());
        }
    }


    public void sendMessage(String message) throws IOException {
        System.out.println("发送消息："+message);
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public void addWebSocketSetRoomMap(String roomId){
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
    }

    /**
     * @description 发送全部人消息
     * @author  lxw
     * @updateTime 2021/12/10 15:40
     */
    public void sendAll(String msg){
        for (ImportDictValueSocket item : webSocketSet) {
            try {
                item.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 群发自定义消息
     * */
    public void sendInfo(String type,String roomId,Object message) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",type);
        jsonObject.put("data",message);
        if(StringUtils.isEmpty(roomId)){
            for (ImportDictValueSocket item : webSocketSet) {
                try {
                    System.out.println(item.session.getId());
                    item.sendMessage(jsonObject.toJSONString());
                } catch (IOException e) {
                    continue;
                }
            }
        }else{
            for (ImportDictValueSocket item : webSocketSetRoomMap.get(roomId)) {
                try {
                    System.out.println(item.session.getId());
                    item.sendMessage(jsonObject.toJSONString());
                } catch (IOException e) {
                    continue;
                }
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

    public static synchronized int getOnlineCount(String roomId) {
        return onlineCount.get(roomId);
    }

    public static synchronized void addOnlineCount(String roomId) {
        Integer num = StringUtils.isEmpty(ImportDictValueSocket.onlineCount.get(roomId))?0:ImportDictValueSocket.onlineCount.get(roomId);
        ImportDictValueSocket.onlineCount.put(roomId,num+1);
    }

    public static synchronized void subOnlineCount(String roomId) {
        Integer num = StringUtils.isEmpty(ImportDictValueSocket.onlineCount.get(roomId))?0:ImportDictValueSocket.onlineCount.get(roomId);
        ImportDictValueSocket.onlineCount.put(roomId,num-1);
    }

    public static synchronized int getOnlineCountAll() {
        return onlineCountAll;
    }

    public static synchronized void addOnlineCountAll() {
        ImportDictValueSocket.onlineCountAll++;
    }

    public static synchronized void subOnlineCountAll() {
        ImportDictValueSocket.onlineCountAll--;
    }
}
