package lt.gzeska.cache.redis.demo.service.user.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

/**
 * Created by gjurgo@gmail.com on 28/10/2017.
 */
public class User {
    private final long id;
    private final String name;
    private DateTime dateTime;

    @JsonCreator
    public User(@JsonProperty("id") long id,
                @JsonProperty("name") String name,
                @JsonProperty("dateTime") DateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DateTime getDateTime() {
        return dateTime;
    }
}
