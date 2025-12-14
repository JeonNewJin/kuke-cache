package kuke.kukecache.service;

import kuke.kukecache.common.cache.CacheStrategy;
import kuke.kukecache.model.ItemCreateRequest;
import kuke.kukecache.model.ItemUpdateRequest;
import kuke.kukecache.service.response.ItemPageResponse;
import kuke.kukecache.service.response.ItemResponse;

public interface ItemCacheService {
    ItemResponse read(Long itemId);

    ItemPageResponse readAll(Long page, Long pageSize);

    ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize);

    ItemResponse create(ItemCreateRequest request);

    ItemResponse update(Long itemId, ItemUpdateRequest request);

    void delete(Long itemId);

    boolean supports(CacheStrategy cacheStrategy);
}
