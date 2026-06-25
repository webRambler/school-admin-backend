package com.example.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ========== 通用操作 ==========

    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.warn("Redis set 操作失败, key={}: {}", key, e.getMessage());
        }
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        } catch (Exception e) {
            log.warn("Redis set(带超时) 操作失败, key={}: {}", key, e.getMessage());
        }
    }

    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.warn("Redis get 操作失败, key={}: {}", key, e.getMessage());
            return null;
        }
    }

    public Boolean delete(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.warn("Redis delete 操作失败, key={}: {}", key, e.getMessage());
            return false;
        }
    }

    public Long delete(Collection<String> keys) {
        try {
            return redisTemplate.delete(keys);
        } catch (Exception e) {
            log.warn("Redis delete(批量) 操作失败: {}", e.getMessage());
            return 0L;
        }
    }

    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.warn("Redis hasKey 操作失败, key={}: {}", key, e.getMessage());
            return false;
        }
    }

    public Boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            return redisTemplate.expire(key, timeout, unit);
        } catch (Exception e) {
            log.warn("Redis expire 操作失败, key={}: {}", key, e.getMessage());
            return false;
        }
    }

    public Long getExpire(String key) {
        try {
            return redisTemplate.getExpire(key);
        } catch (Exception e) {
            log.warn("Redis getExpire 操作失败, key={}: {}", key, e.getMessage());
            return -1L;
        }
    }

    // ========== String 操作 ==========

    public Long increment(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.warn("Redis increment 操作失败, key={}: {}", key, e.getMessage());
            return null;
        }
    }

    public Long decrement(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(key, -delta);
        } catch (Exception e) {
            log.warn("Redis decrement 操作失败, key={}: {}", key, e.getMessage());
            return null;
        }
    }

    // ========== Hash 操作 ==========

    public void hSet(String key, String hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
        } catch (Exception e) {
            log.warn("Redis hSet 操作失败, key={}: {}", key, e.getMessage());
        }
    }

    public void hSetAll(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
        } catch (Exception e) {
            log.warn("Redis hSetAll 操作失败, key={}: {}", key, e.getMessage());
        }
    }

    public Object hGet(String key, String hashKey) {
        try {
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            log.warn("Redis hGet 操作失败, key={}: {}", key, e.getMessage());
            return null;
        }
    }

    public Map<Object, Object> hGetAll(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.warn("Redis hGetAll 操作失败, key={}: {}", key, e.getMessage());
            return Collections.emptyMap();
        }
    }

    public Long hDelete(String key, Object... hashKeys) {
        try {
            return redisTemplate.opsForHash().delete(key, hashKeys);
        } catch (Exception e) {
            log.warn("Redis hDelete 操作失败, key={}: {}", key, e.getMessage());
            return 0L;
        }
    }

    public Boolean hHasKey(String key, String hashKey) {
        try {
            return redisTemplate.opsForHash().hasKey(key, hashKey);
        } catch (Exception e) {
            log.warn("Redis hHasKey 操作失败, key={}: {}", key, e.getMessage());
            return false;
        }
    }

    // ========== List 操作 ==========

    public Long lPush(String key, Object value) {
        try {
            return redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            log.warn("Redis lPush 操作失败, key={}: {}", key, e.getMessage());
            return null;
        }
    }

    public Long rPush(String key, Object value) {
        try {
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.warn("Redis rPush 操作失败, key={}: {}", key, e.getMessage());
            return null;
        }
    }

    public Object lPop(String key) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.warn("Redis lPop 操作失败, key={}: {}", key, e.getMessage());
            return null;
        }
    }

    public Object rPop(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            log.warn("Redis rPop 操作失败, key={}: {}", key, e.getMessage());
            return null;
        }
    }

    public List<Object> lRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.warn("Redis lRange 操作失败, key={}: {}", key, e.getMessage());
            return Collections.emptyList();
        }
    }

    public Long lSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.warn("Redis lSize 操作失败, key={}: {}", key, e.getMessage());
            return 0L;
        }
    }

    // ========== Set 操作 ==========

    public Long sAdd(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.warn("Redis sAdd 操作失败, key={}: {}", key, e.getMessage());
            return null;
        }
    }

    public Set<Object> sMembers(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.warn("Redis sMembers 操作失败, key={}: {}", key, e.getMessage());
            return Collections.emptySet();
        }
    }

    public Boolean sIsMember(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.warn("Redis sIsMember 操作失败, key={}: {}", key, e.getMessage());
            return false;
        }
    }

    public Long sSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.warn("Redis sSize 操作失败, key={}: {}", key, e.getMessage());
            return 0L;
        }
    }

    public Long sRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.warn("Redis sRemove 操作失败, key={}: {}", key, e.getMessage());
            return 0L;
        }
    }
}
