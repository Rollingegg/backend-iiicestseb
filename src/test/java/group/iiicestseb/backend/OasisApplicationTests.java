package group.iiicestseb.backend;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

//如果测试回滚出问题可以试试加上下面的注解
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
//        TransactionalTestExecutionListener.class})
@SpringBootTest
//使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉；这个应该注在测试根目录下，应该是一个测试包只能有一个，否则会出现无法回滚的情况
//我测试了一下，整个测试包只需要这里一个web环境即可
@WebAppConfiguration
public class OasisApplicationTests {

	@Test
	public void contextLoads() {
	}

}
