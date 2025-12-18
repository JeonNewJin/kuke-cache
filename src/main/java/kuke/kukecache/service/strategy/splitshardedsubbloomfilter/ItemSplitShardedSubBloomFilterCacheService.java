package kuke.kukecache.service.strategy.splitshardedsubbloomfilter;

import kuke.kukecache.common.cache.CacheStrategy;
import kuke.kukecache.model.ItemCreateRequest;
import kuke.kukecache.model.ItemUpdateRequest;
import kuke.kukecache.service.ItemCacheService;
import kuke.kukecache.service.ItemService;
import kuke.kukecache.service.response.ItemPageResponse;
import kuke.kukecache.service.response.ItemResponse;
import kuke.kukecache.service.strategy.splitshardedbloomfilter.SplitShardedBloomFilterRedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemSplitShardedSubBloomFilterCacheService implements ItemCacheService {
    private final ItemService itemService;
    private final SplitShardedSubBloomFilterRedisHandler splitShardedSubBloomFilterRedisHandler;

    private static final SplitShardedSubBloomFilter bloomFilter = SplitShardedSubBloomFilter.create(
            "item-bloom-filter",
            1000,
            0.01,
            4
    );

    @Override
    public ItemResponse read(Long itemId) {
        boolean result = splitShardedSubBloomFilterRedisHandler.mightContain(bloomFilter, String.valueOf(itemId));
        if (!result) {
            return null;
        }
        return itemService.read(itemId);
    }

    @Override
    public ItemPageResponse readAll(Long page, Long pageSize) {
        return itemService.readAll(page, pageSize);
    }

    @Override
    public ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        return itemService.readAllInfiniteScroll(lastItemId, pageSize);
    }

    @Override
    public ItemResponse create(ItemCreateRequest request) {
        ItemResponse itemResponse = itemService.create(request);
        splitShardedSubBloomFilterRedisHandler.add(bloomFilter, String.valueOf(itemResponse.itemId()));
        return itemResponse;
    }

    @Override
    public ItemResponse update(Long itemId, ItemUpdateRequest request) {
        return itemService.update(itemId, request);
    }

    @Override
    public void delete(Long itemId) {
        itemService.delete(itemId);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return CacheStrategy.SPLIT_SHARDED_SUB_BLOOM_FILTER == cacheStrategy;
    }
}
