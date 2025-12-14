package kuke.kukecache.service.response;

import kuke.kukecache.model.Item;

public record ItemResponse(
        Long itemId, String data
) {
    public static ItemResponse from(Item item) {
        return new ItemResponse(item.getItemId(), item.getData());
    }
}
