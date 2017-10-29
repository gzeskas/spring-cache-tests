package lt.gzeska.cache.redis.demo.service.user;

import junit.framework.TestCase;
import lt.gzeska.cache.redis.demo.service.RestClient;
import lt.gzeska.cache.redis.demo.service.UserService;
import lt.gzeska.cache.redis.demo.service.rest.RestClientImpl;
import lt.gzeska.cache.redis.demo.service.user.entities.User;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by gjurgo@gmail.com on 28/10/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
public class ExternalUserServiceTest {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        redisServer.stop();
    }

    private RedisServer redisServer;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private RestClient restClient;

    private final User testUser = new User(1L, "test", DateTime.now());

    @Before
    public void setupRedis() throws IOException {
        if (redisServer == null) {
            redisServer = new RedisServer(redisProperties.getPort());
            redisServer.start();
        }
        userService.delete(testUser);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> redisServer.stop()));
    }

    @After
    public void tearDown() throws Exception {
        redisServer.stop();
    }

    @Test
    public void shouldCallOnlyOnceExternalService() throws Exception {
        Mockito.reset(restClient);
        Optional<User> byId = userService.findById(1L);
        userService.findById(1L);
        userService.findById(1L);
        Mockito.verify(restClient, Mockito.times(1)).doRequest();
    }

    @Test
    public void shouldCallTwiceExternalServiceAfterEviction() throws Exception {
        Mockito.reset(restClient);
        Optional<User> byId = userService.findById(1L);
        userService.findById(1L);

        byId.ifPresent(userService::delete);// After delete original request should be made and cached.
        userService.findById(1L);
        userService.findById(1L);
        Mockito.verify(restClient, Mockito.times(2)).doRequest();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RestClient restClient() {
            return Mockito.spy(RestClientImpl.class);
        }

    }

}