package group.iiicestseb.backend.service;


import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.vo.UserVO;

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
    public UserVO signIn(UserForm userForm);

    /**
     * 用户注册
     * @author wph
     * @param userForm 用户账户密码表单
     */
    public void register(UserForm userForm);
}
