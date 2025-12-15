package kuke.kukecache.api;

import kuke.kukecache.common.cache.CacheStrategy;
import kuke.kukecache.service.response.ItemResponse;
import org.junit.jupiter.api.Test;

public class NullObjectPatternStrategyApiTest {
    static final CacheStrategy CACHE_STRATEGY = CacheStrategy.NULL_OBJECT_PATTERN;

    @Test
    void read() {
        // given
        for (int i = 0; i < 3; i++) {
            ItemResponse item = ItemApiTestUtils.read(CACHE_STRATEGY, 99999L);
            System.out.println("item = " + item);
        }

        // when

        // then
    }
}
