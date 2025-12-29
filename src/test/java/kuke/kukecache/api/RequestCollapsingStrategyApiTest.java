package kuke.kukecache.api;

import kuke.kukecache.common.cache.CacheStrategy;
import kuke.kukecache.model.ItemCreateRequest;
import kuke.kukecache.model.ItemUpdateRequest;
import kuke.kukecache.service.response.ItemResponse;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RequestCollapsingStrategyApiTest {
    static final CacheStrategy CACHE_STRATEGY = CacheStrategy.REQUEST_COLLAPSING;

    @Test
    void test() throws InterruptedException {
        // given
        ItemResponse item = ItemApiTestUtils.create(CACHE_STRATEGY, new ItemCreateRequest("data"));

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        long start = System.nanoTime();
        while (System.nanoTime() - start < TimeUnit.SECONDS.toNanos(20)) {
            for (int i = 0; i < 3; i++) {
                executorService.execute(() -> ItemApiTestUtils.read(CACHE_STRATEGY, item.itemId()));
            }
            TimeUnit.MILLISECONDS.sleep(10);
        }

        ItemApiTestUtils.update(CACHE_STRATEGY, item.itemId(), new ItemUpdateRequest("updated"));
        ItemResponse updated = ItemApiTestUtils.read(CACHE_STRATEGY, item.itemId());
        System.out.println("updated = " + updated);

        ItemApiTestUtils.delete(CACHE_STRATEGY, item.itemId());
        ItemResponse deleted = ItemApiTestUtils.read(CACHE_STRATEGY, item.itemId());
        System.out.println("deleted = " + deleted);
    }
}
