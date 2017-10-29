package lt.gzeska.cache.redis.demo.service.user;

import lt.gzeska.cache.redis.demo.service.RestClient;
import lt.gzeska.cache.redis.demo.service.UserService;
import lt.gzeska.cache.redis.demo.service.user.entities.User;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

/**
 * Created by gjurgo@gmail.com on 28/10/2017.
 */
public class ExternalUserService implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(ExternalUserService.class);

    private final RestClient restClient;

    public ExternalUserService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    @Cacheable(cacheNames = "users", key ="#id", cacheManager = "usersCacheManager")
    public Optional<User> findById(long id) {
        String response = restClient.doRequest();
        return Optional.of(new User(id, "User:" + id, DateTime.now()));
    }

    @Override
    @CacheEvict(cacheNames = "users", key = "#user.id", cacheManager = "usersCacheManager")
    public void delete(User user) {
        logger.info("Deleting user");
    }
}
