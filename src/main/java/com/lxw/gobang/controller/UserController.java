package com.lxw.gobang.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxw.gobang.common.socket.ImportDictValueSocket;
import com.lxw.gobang.common.utils.MyResult;
import com.lxw.gobang.entity.CoralUser;
import com.lxw.gobang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2021年12月03日 09:40:00
 */

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImportDictValueSocket socket;

    @PostMapping("addUser")
    public MyResult addUser(@RequestBody CoralUser user){
        userService.addUser(user);
        return MyResult.success();
    }

    @PostMapping("queryuser")
    public MyResult queryuser(){
        return MyResult.success(userService.selectUser());
    }

    @PostMapping("sendInfo")
    public MyResult sendInfo(){
        try {
            socket.sendInfo("msg","","传输成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MyResult.success();
    }

    @PostMapping("sendRoomIdInfo")
    public MyResult sendInfo(@RequestBody Map map){
        try {
            socket.sendInfo("msg",map.get("roomId").toString(),"传输成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MyResult.success();
    }

}
