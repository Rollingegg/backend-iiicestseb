package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.service.UserService;
import group.iiicestseb.backend.vo.Response;
import org.junit.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@ResponseBody
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource(name = "Regedit")
    private UserService userService;

    public static final String SIGN_IN_ERROR = "登录出现未知错误";
    public static final String REGISTER_IN_ERROR = "注册出现未知错误";
    public static final String IS_EXIST_ERROR = "用户查询出现未知错误";
    public static final String USER_NAME_TOO_SHORT = "用户名长度至少6个字符";
    public static final String USER_NAME_TOO_LONG = "用户名长度不能超过20个字符";

    /**
     * 用户登录/注册界面 用户登录
     *
     * @param userForm 用户表单
     * @return 用户个人信息
     */
    @PostMapping("/login")
    public Response signIn(@RequestBody @Valid UserForm userForm) {
        try {
            return Response.buildSuccess(userService.signIn(userForm));
        }catch (Exception ex){
            return Response.buildFailure(SIGN_IN_ERROR);
        }
    }

    /**
     * 用户登录/注册界面 用户注册
     *
     * @param userForm 用户表单
     * @return 无
     */
    @PostMapping("/register")
    public Response register(@RequestBody @Valid UserForm userForm) {
        try {
            userService.register(userForm);
            return Response.buildSuccess();
        } catch (Exception e) {
            return Response.buildFailure(REGISTER_IN_ERROR);
        }

    }

    @GetMapping("/judge/{username}")
    public Response isExist(@PathVariable
                                @Size(min = 6,max = 20,message = "用户名长度要在6至20个字符之间")
                                        @Pattern(regexp = "\\S+")
                                        String username) {
        try {
            userService.isExist(username);
            return Response.buildSuccess();
        } catch (Exception e) {
            return Response.buildFailure(IS_EXIST_ERROR);
        }
    }
}
