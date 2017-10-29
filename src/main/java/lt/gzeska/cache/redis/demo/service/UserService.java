package lt.gzeska.cache.redis.demo.service;

import lt.gzeska.cache.redis.demo.service.user.entities.User;

import java.util.Optional;

/**
 * Created by gjurgo@gmail.com on 28/10/2017.
 */
public interface UserService {
    Optional<User> findById(long id);
    void delete(User user);
}