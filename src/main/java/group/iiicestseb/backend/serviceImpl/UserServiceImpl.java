package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Record;
import group.iiicestseb.backend.entity.User;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.exception.user.UserAlreadyRegisterException;
import group.iiicestseb.backend.exception.user.WrongLoginInfoException;
import group.iiicestseb.backend.mapper.RecordMapper;
import group.iiicestseb.backend.mapper.UserMapper;
import group.iiicestseb.backend.service.UserService;
import group.iiicestseb.backend.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */
@Service("User")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RecordMapper recordMapper;

    @Override
    public UserVO signIn(UserForm userForm) {
        User user;
        user = userMapper.findByUsername(userForm.getUsername());
        //检验用户密码是否正确
        if (user != null && user.getPassword().equals(userForm.getPassword())) {
            List<Record> recordList = recordMapper.findByUserId(user.getId());
            return new UserVO(user,recordList);
        } else {
            throw new WrongLoginInfoException();
        }
    }

    @Override
    public void register(UserForm userForm) {
        User user = userMapper.findByUsername(userForm.getUsername());
        if (user != null){
            throw new UserAlreadyRegisterException();
        }
        user = new User();
        //检查用户是否已注册
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setPrivilegeLevel("用户");
        userMapper.save(user);
    }

    @Override
    public void isExist(String username) {
            if(null !=userMapper.findByUsername(username)) {
                throw new UserAlreadyRegisterException();
            }
    }
}
