package group.iiicestseb.backend.regedit;

import group.iiicestseb.backend.entity.User;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegeditTest {

    @Resource
    Regedit regedit;

    @Test
    public void register() {
        UserForm u= new UserForm();
        u.setPassword("a");
        u.setUsername("n");
        regedit.register(u);
    }
}