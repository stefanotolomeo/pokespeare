package com.company.pokespeare.cache;

import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager<K, V> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<K, V> cache;

	protected CacheManager() {
		cache = new ConcurrentHashMap<>();
	}

	/**
	 * @param key
	 * @return An optional containing the value currently associated to the given
	 *         key, or an empty optional if no such value exists.
	 *
	 * @throws IllegalArgumentException
	 *             if the key is null
	 */
	public Optional<V> get(@NotNull K key) {
		Assert.notNull(key, "Null keys are not supported");

		logger.trace("Fetching the value for: {}", key);

		return Optional.ofNullable(cache.get(key));
	}

	/**
	 * @return An optional containing the previous value associated to the key, or
	 *         an empty optional if the cache didn't contain the key
	 *
	 * @throws IllegalArgumentException
	 *             if either the key or the value is null
	 */
	public Optional<V> put(@NotNull K key, @NotNull V value) {
		Assert.notNull(key, "Null keys are not supported");
		Assert.notNull(key, "Null values are not supported");

		logger.trace("Attempting to set the value for: {} to: {}", key, value);

		V oldValue = cache.put(key, value);

		return Optional.ofNullable(oldValue);
	}

	/**
	 * @throws NullPointerException
	 *             if the key is null
	 */
	public void remove(@NotNull K key) {
		Assert.notNull(key, "Null keys are not supported");

		logger.trace("Removing the value for: {}", key);

		cache.remove(key);
	}

	/**
	 * Clears all values from the cache
	 */
	public void clear() {
		logger.trace("Removing all values");
		cache.clear();
	}

	/**
	 *
	 * @return the cache's keySet wrapped with an unmodifiable set
	 */
	public Set<K> keys() {
		logger.trace("Retrieving keySet");
		return Collections.unmodifiableSet(cache.keySet());
	}

	/**
	 *
	 * @return the cache's keySet wrapped with an unmodifiable set
	 */
	public Set<Map.Entry<K, V>> entries() {
		logger.trace("Retrieving entries");
		return Collections.unmodifiableSet(cache.entrySet());
	}

	public boolean containsKey(K key) {
		return cache.containsKey(key);
	}

}