package com.rcd.fiber.websocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rcd.fiber.security.jwt.TokenProvider;
import com.rcd.fiber.service.dto.EventInfoDTO;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * websocket地址 ws://localhost:8888/event-websocket 单线程异常不会抛错，而断开连接
 *
 * @author 55044
 */
@ServerEndpoint(value = "/event-websocket")
@Component
public class EventPublisher {
    // 站点名 - 用户名列表
    static ConcurrentHashMap<String, ConcurrentLinkedDeque<String>> siteUsersDict;

    // 用户名 -- session
    private static ConcurrentHashMap<String, ConcurrentLinkedDeque<Session>> allUserSessions = new ConcurrentHashMap<>();

    static JSONObject ILLEGAL_USER = new JSONObject() {{
        put("type", "msg");
        put("status", "error");
        put("msg", "非法用户");
    }};

    static JSONObject CONNECTED_SUCCESS = new JSONObject() {{
        put("type", "msg");
        put("status", "success");
        put("msg", "连接成功");
    }};

    static JSONObject WELCOME = new JSONObject() {{
        put("type", "msg");
        put("status", "success");
        put("msg", "欢迎使用");
    }};


    @OnOpen
    public void open(Session session, EndpointConfig config) throws InterruptedException {
        session.getAsyncRemote().sendText(JSONObject.toJSONString(WELCOME));
    }

    @OnMessage
    public void getMessage(String data, Session session) throws Exception {
        JSONObject params = JSONObject.parseObject(data);
        if (params.containsKey("token") && params.containsKey("sites")) {
            String token = params.getString("token");
            try {
                String username = TokenProvider.getClaims(token).getSubject();
                session.getAsyncRemote().sendText(CONNECTED_SUCCESS.toJSONString());
                session.getAsyncRemote().sendText("{\"siteName\":\"西安\",\"deviceName\":\"Keithley_2000型多用万用表\",\"dataName\":\"透射峰电压\",\"eventType\":\"exception\",\"eventLevel\":\"alarm\",\"value\":111}");
                addNewUser(username, session);
                JSONArray arr = params.getJSONArray("sites");
                for (int i = 0; i < arr.size(); i++) {
                    addNewSiteUserItem(arr.getString(i), username);
                }
            } catch (Exception e) {
                e.printStackTrace();
                handleIllegalUser(session);
            }
        } else {
            handleIllegalUser(session);
        }
    }

    @OnClose
    public void close(Session session) {
        System.out.println("用户退出");
    }

    @OnError
    public void error(Throwable t) {
        t.printStackTrace();
    }

    private void handleIllegalUser(Session session) throws IOException {
        session.getAsyncRemote().sendText(ILLEGAL_USER.toJSONString());
        session.close();
    }

    private static void addNewSiteUserItem(String siteName, String username) {
        ConcurrentLinkedDeque allUsersOfSite = siteUsersDict.get(siteName);
        if (allUsersOfSite == null) {
            allUsersOfSite = new ConcurrentLinkedDeque<String>();
            siteUsersDict.put(siteName, allUsersOfSite);
        }
        allUsersOfSite.add(username);
    }

    private static void addNewUser(String username, Session session) {
        ConcurrentLinkedDeque<Session> sessions = allUserSessions.get(username);
        if (sessions == null) {
            sessions = new ConcurrentLinkedDeque<Session>();
            allUserSessions.put(username, sessions);
        }
        sessions.add(session);
    }

    public static void sendEvent(EventInfoDTO event) {
        String siteName = event.getSiteName();
        // 获取订阅该站点时间的所有用户名
        ConcurrentLinkedDeque<String> allUserNamesOfSite = siteUsersDict.get(siteName);
        if (allUserNamesOfSite != null) {
            for (String userName : allUserNamesOfSite) {
                // 获取用户名旗下的所有session
                ConcurrentLinkedDeque<Session> allSessionsOfUserName = allUserSessions.get(userName);
                if (allSessionsOfUserName != null) {
                    for (Session session : allSessionsOfUserName) {
                        if(session.isOpen()){
                            session.getAsyncRemote().sendText(JSONObject.toJSONString(event));
                        }
                    }
                }
            }
        }
    }
}
