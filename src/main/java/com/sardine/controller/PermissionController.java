package com.sardine.controller;

import com.sardine.bean.Response;
import com.sardine.bean.User;
import com.sardine.common.CustomException;
import com.sardine.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v0")
public class PermissionController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录获取token
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/session", method = RequestMethod.POST)
    public Response checkToken(@RequestBody User user) throws CustomException {
        Response response = Response.success(loginService.createToken(user));
        return response;
    }


    @RequestMapping(value = "/session", method = RequestMethod.DELETE)
    public Response deleteToken(@RequestBody User user) {
        loginService.deleteToken(user.getName());
        return Response.success();
    }
}
