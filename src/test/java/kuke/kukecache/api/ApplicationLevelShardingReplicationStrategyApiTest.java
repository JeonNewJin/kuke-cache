package kuke.kukecache.api;

import kuke.kukecache.common.cache.CacheStrategy;
import kuke.kukecache.model.ItemCreateRequest;
import kuke.kukecache.model.ItemUpdateRequest;
import kuke.kukecache.service.response.ItemResponse;
import org.junit.jupiter.api.Test;

public class ApplicationLevelShardingReplicationStrategyApiTest {
    static final CacheStrategy CACHE_STRATEGY = CacheStrategy.APPLICATION_LEVEL_SHARDING_REPLICATION;

    @Test
    void test() {
        ItemResponse item = ItemApiTestUtils.create(CACHE_STRATEGY, new ItemCreateRequest("data"));
        for (int i = 0; i < 3; i++) {
            ItemResponse read = ItemApiTestUtils.read(CACHE_STRATEGY, item.itemId());
            System.out.println("read = " + read);
        }

        ItemApiTestUtils.update(CACHE_STRATEGY, item.itemId(), new ItemUpdateRequest("updated"));
        ItemResponse updated = ItemApiTestUtils.read(CACHE_STRATEGY, item.itemId());
        System.out.println("updated = " + updated);

        ItemApiTestUtils.delete(CACHE_STRATEGY, item.itemId());
        ItemResponse deleted = ItemApiTestUtils.read(CACHE_STRATEGY, item.itemId());
        System.out.println("deleted = " + deleted);
    }
}
