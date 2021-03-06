package group.iiicestseb.backend.service;


import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.vo.user.UserVO;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface UserService {

    /**
     * 用户登录
     * @author wph
     * @param userForm 用户账户密码表单
     * @return 用户信息vo
     */
    UserVO signIn(UserForm userForm);

    /**
     * 用户注册
     * @author wph
     * @param userForm 用户账户密码表单
     */
    void register(UserForm userForm);

    /**
     * 判断用户名存在
     * @param username 用户名
     */
    void isExist(String username);
}
