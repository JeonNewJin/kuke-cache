package kuke.kukecache.service.strategy.per;

import kuke.kukecache.RedisTestContainerSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProbabilisticEarlyRecomputationCacheHandlerTest extends RedisTestContainerSupport {

    @Autowired
    private ProbabilisticEarlyRecomputationCacheHandler cacheHandler;

    @Test
    void put() {
        // given, when
        cacheHandler.put("testKey", Duration.ofSeconds(10), "data");

        // then
        String result = redisTemplate.opsForValue().get("testKey");
        assertThat(result).isNotNull();
        System.out.println("result = " + result);
    }

    @Test
    void evict() {
        // given
        cacheHandler.put("testKey", Duration.ofSeconds(10), "data");

        // when
        cacheHandler.evict("testKey");

        // then
        String result = redisTemplate.opsForValue().get("testKey");
        assertThat(result).isNull();
    }

    @Test
    void fetch() {
        String result1 = fetchData();
        String result2 = fetchData();
        String result3 = fetchData();

        assertThat(result1).isEqualTo("sourceData");
        assertThat(result2).isEqualTo("sourceData");
        assertThat(result3).isEqualTo("sourceData");
    }

    private String fetchData() {
        return cacheHandler.fetch(
                "testKey",
                Duration.ofSeconds(10),
                () -> {
                    System.out.println("fetch source data");
                    return "sourceData";
                },
                String.class
        );
    }
}