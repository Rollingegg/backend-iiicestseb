package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.entity.User;
import group.iiicestseb.backend.exception.user.UserAlreadyRegisterException;
import group.iiicestseb.backend.exception.user.WrongLoginInfoException;
import group.iiicestseb.backend.mapper.UserMapper;
import group.iiicestseb.backend.service.UserService;
import group.iiicestseb.backend.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/2/22
 */
@Service("User")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserVO signIn(UserForm userForm) {
        User user;
        //检查用户是否存在
        try {
            user = userMapper.selectByUsername(userForm.getUsername());
        }catch (Exception e){
            throw new WrongLoginInfoException();
        }
        //检验用户密码是否正确
        if (user.getPassword().equals(userForm.getPassword())) {
            return new UserVO(user);
        } else {
            throw new WrongLoginInfoException();
        }
    }

    @Override
    public void register(UserForm userForm) {
        User newUser = new User();
        //检查用户是否已注册
        try {

            newUser.setUsername(userForm.getUsername());
            newUser.setPassword(userForm.getPassword());
            newUser.setRecordId(userForm.getRecordId());
            newUser.setPrivilegeLevel("用户");
            userMapper.insert(newUser);
            //若注册成功，也该更新历史记录
        }catch (Exception e){
            throw new UserAlreadyRegisterException();
        }
    }

    @Override
    public void judgeUsername(String username) {
        try{
            userMapper.selectByUsername(username);
        }catch (Exception e){
        }

    }
}
