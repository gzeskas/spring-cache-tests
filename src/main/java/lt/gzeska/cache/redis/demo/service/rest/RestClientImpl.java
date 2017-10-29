package lt.gzeska.cache.redis.demo.service.rest;

import lt.gzeska.cache.redis.demo.service.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gjurgo@gmail.com on 28/10/2017.
 */
public class RestClientImpl implements RestClient {
    private static final Logger logger = LoggerFactory.getLogger(RestClientImpl.class);

    @Override
    public String doRequest() {
        logger.info("Do request");
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            logger.error("Failed to suspend thread", e);
        }
        return "Simulated request";
    }
}
