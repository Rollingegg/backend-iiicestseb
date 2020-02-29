package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl {
    @Resource
    UserMapper userMapper;
    public void test(){

    }
}
