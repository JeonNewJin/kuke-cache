package kuke.kukecache.service.strategy.requestcollapsing;

import kuke.kukecache.common.cache.CacheStrategy;
import kuke.kukecache.common.cache.KukeCacheEvict;
import kuke.kukecache.common.cache.KukeCachePut;
import kuke.kukecache.common.cache.KukeCacheable;
import kuke.kukecache.model.ItemCreateRequest;
import kuke.kukecache.model.ItemUpdateRequest;
import kuke.kukecache.service.ItemCacheService;
import kuke.kukecache.service.ItemService;
import kuke.kukecache.service.response.ItemPageResponse;
import kuke.kukecache.service.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemRequestCollapsingCacheService implements ItemCacheService {
    private final ItemService itemService;

    @Override
    @KukeCacheable(
            cacheStrategy = CacheStrategy.REQUEST_COLLAPSING,
            cacheName = "item",
            key = "#itemId",
            ttlSeconds = 1
    )
    public ItemResponse read(Long itemId) {
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
        return itemService.create(request);
    }

    @Override
    @KukeCachePut(
            cacheStrategy = CacheStrategy.REQUEST_COLLAPSING,
            cacheName = "item",
            key = "#itemId",
            ttlSeconds = 1
    )
    public ItemResponse update(Long itemId, ItemUpdateRequest request) {
        return itemService.update(itemId, request);
    }

    @Override
    @KukeCacheEvict(
            cacheStrategy = CacheStrategy.REQUEST_COLLAPSING,
            cacheName = "item",
            key = "#itemId"
    )
    public void delete(Long itemId) {
        itemService.delete(itemId);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return CacheStrategy.REQUEST_COLLAPSING == cacheStrategy;
    }
}
