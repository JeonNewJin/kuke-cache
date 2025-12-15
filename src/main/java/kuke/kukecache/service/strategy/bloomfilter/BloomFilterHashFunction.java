package kuke.kukecache.service.strategy.bloomfilter;

@FunctionalInterface
public interface BloomFilterHashFunction {
    long hash(String value);
}
