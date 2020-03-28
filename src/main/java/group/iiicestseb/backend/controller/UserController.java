package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.service.UserService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class UserController {
    @Resource(name = "Regedit")
    private UserService userService;

    public static final String SIGN_IN_ERROR = "登录出现未知错误";
    public static final String REGISTER_IN_ERROR = "注册出现未知错误";
    public static final String IS_EXIST_ERROR = "用户查询出现未知错误";
    public static final String USERNAME_INVALID_LENGTH = "用户名长度要在6至20个字符之间";
    public static final String USERNAME_INVALID_SPACE = "用户名不能包含空格";
    public static final String USERNAME_EMPTY = "用户名不能为空";

    /**
     * 用户登录/注册界面 用户登录
     *
     * @param userForm 用户表单
     * @return 用户个人信息
     */
    @PostMapping("/login")
    public Response signIn(@RequestBody @Valid UserForm userForm) {
        return Response.buildSuccess(userService.signIn(userForm));
    }

    /**
     * 用户登录/注册界面 用户注册
     *
     * @param userForm 用户表单
     * @return 无
     */
    @PostMapping("/register")
    public Response register(@RequestBody @Valid UserForm userForm) {

        userService.register(userForm);
        return Response.buildSuccess();
    }


    /**
     * 输入用户名时实时检测格式及用户存不存在
     * @param username 用户名
     * @return 无
     */
    @GetMapping("/judge")
    @Valid
    public Response isExist(@RequestParam(name = "username") @Size(min = 4,max = 20,message = UserForm.USERNAME_LENGTH_INVALID)
                                @Pattern(regexp = "\\S+",message = UserForm.USERNAME_CONTAIN_SPACE)
                                        String username) {

        userService.isExist(username);
        return Response.buildSuccess();
    }


}
