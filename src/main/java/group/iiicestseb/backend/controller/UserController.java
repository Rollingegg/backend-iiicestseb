package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.Form.UserForm;
import group.iiicestseb.backend.exception.user.UserAlreadyRegisterException;
import group.iiicestseb.backend.exception.user.WrongLoginInfoException;
import group.iiicestseb.backend.service.UserService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户登录/注册界面 用户登录
     * @param userForm
     * @return 用户个人信息
     */
    @PostMapping("/login")
    public Response signIn(@RequestBody UserForm userForm){
        try {
            return Response.buildSuccess(userService.signIn(userForm));
        }catch (WrongLoginInfoException e){
            return Response.buildFailure(e.getCode()+":"+e.getMessage());
        }
    }

    /**
     * 用户登录/注册界面 用户注册
     * @param userForm
     * @return
     */
    @PostMapping("/register")
    public Response register(@RequestBody UserForm userForm){
        try {
            userService.register(userForm);
            return Response.buildSuccess();
        }catch (UserAlreadyRegisterException e){
            return Response.buildFailure(e.getCode()+":"+e.getMessage());
        }
    }
}
