package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.form.UserForm;
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
@ResponseBody
@CrossOrigin
@RequestMapping("/user")
public class UserController {
//    @Resource(name = "Regedit")
//    private UserService userService;
//    public static String USER_EXIST = "用户已注册";
//    public static String WRONG_LOGIN_INFO = "用户名或密码错误";
//    /**
//     * 用户登录/注册界面 用户登录
//     * @param userForm 用户表单
//     * @return 用户个人信息
//     */
//    @PostMapping("/login")
//    public Response signIn(@RequestBody UserForm userForm){
//        try {
//            return Response.buildSuccess(userService.signIn(userForm));
//        }catch (WrongLoginInfoException e){
//            return Response.buildFailure(WRONG_LOGIN_INFO);
//
//        }
//    }
//
//    /**
//     * 用户登录/注册界面 用户注册
//     * @param userForm 用户表单
//     * @return 无
//     */
//    @PostMapping("/register")
//    public Response register(@RequestBody UserForm userForm){
//        try {
//            userService.register(userForm);
//            return Response.buildSuccess();
//        }catch (UserAlreadyRegisterException e){
//            return Response.buildFailure(USER_EXIST);
//        }
//    }
//
//    @GetMapping("/judge/{username}")
//    public Response judgeUsername(@PathVariable String username){
//        if( !userService.judgeUsername(username)){
//            //不存在
//            return Response.buildSuccess();
//        }else {
//            //用户存在
//            return Response.buildFailure(USER_EXIST);
//        }
//    }
}
