package com.company.pokespeare.pokemon.cache;

import com.company.pokespeare.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * A cache to store every response from Shakespeare API.
 * This is an improvement to the limitation of 5 calls per hour to Shakespeare free API.
  */
@Service
public class VisitedCache extends CacheManager<String, String> {
}
