package lt.gzeska.cache.redis.demo.config;

import lt.gzeska.cache.redis.demo.service.RestClient;
import lt.gzeska.cache.redis.demo.service.UserService;
import lt.gzeska.cache.redis.demo.service.rest.RestClientImpl;
import lt.gzeska.cache.redis.demo.service.user.ExternalUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gjurgo@gmail.com on 28/10/2017.
 */
@Configuration
public class ExternalServiceConfiguration {

    @Bean
    UserService getUserService(RestClient restClient){
        return new ExternalUserService(restClient);
    }

    @Bean
    RestClient getRestClient() {
        return new RestClientImpl();
    }

}
