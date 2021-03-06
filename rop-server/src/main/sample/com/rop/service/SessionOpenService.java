package com.rop.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.params.request.LoginRequest;
import com.rop.params.request.SessionRequest;
import com.rop.params.response.LoginResponse;
import com.rop.params.response.SessionResponse;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-27 15:24
 * To change this template use File | Settings | File Templates.
 */
@ServiceMethodBean(version = "1.0")
public class SessionOpenService {

    @ServiceMethod(method = "user.getSession1", version = "1.0", needInSession = NeedInSessionType.NO,title = "无参获取session")
    SessionResponse getSession() {
        SessionResponse response=new SessionResponse();
        response.setResult(true);
        response.setCode("0");
        response.setMsg("success");
        response.setSessionId(UUID.randomUUID().toString());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    @ServiceMethod(method = "user.getSession", version = "1.0", needInSession = NeedInSessionType.NO,title ="获取session")
    SessionResponse getSession(SessionRequest request) {
        SessionResponse response=new SessionResponse();
        response.setResult(true);
        response.setCode("0");
        response.setMsg("success");
        response.setSessionId(UUID.randomUUID().toString());

        return response;
    }

    @ServiceMethod(method = "user.login", version = "1.0", needInSession = NeedInSessionType.YES,title = "登陆方法")
    LoginResponse login(LoginRequest request) {
        LoginResponse response=new LoginResponse();
        response.setResult(true);
        response.setCode("0");
        response.setMsg("success");
        response.setPwd(System.currentTimeMillis()+"");

        return response;
    }
}
