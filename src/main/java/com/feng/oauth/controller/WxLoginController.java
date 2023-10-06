package com.feng.oauth.controller;

import cn.hutool.http.HttpUtil;
import com.feng.oauth.R.PcAccessToken;
import com.feng.oauth.service.AccessTokenService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: FengYang
 * @create: 2023-10-06 20:57
 **/
@RestController
public class WxLoginController {
    @Resource
    private AccessTokenService accessTokenService;
    @PostMapping(value = "/getQRCode")
    public Map<String,Object> getQRCode() {
        Map<String,Object> map = new HashMap<>();
        String code_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
        try{
            PcAccessToken token = accessTokenService.findPcToken();
            code_url = code_url.replace("TOKEN", token.getAccessToken());
            String scene_id = (System.currentTimeMillis()+"").substring(0,10);
            JSONObject json = new JSONObject();
            json.put("expire_seconds", "36000");
            json.put("action_name", "QR_SCENE");
            JSONObject info = new JSONObject();
            JSONObject scen = new JSONObject();
            scen.put("scene_id", scene_id);
            info.put("scene", scen);
            json.put("action_info", info);
            String data = HttpUtil.post(code_url,json.toString());
            JSONObject resp = JSONObject.fromObject(data);
            System.out.println("data=="+data);
            map.put("data", resp);
            map.put("scene_id", scene_id);
            map.put("succ", "1");
        } catch(Exception e){
            map.put("succ", "0");
            e.printStackTrace();
        }
        return map;
    }
}