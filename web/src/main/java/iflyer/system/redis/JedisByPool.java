package iflyer.system.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisByPool {

    private static Logger logger = LoggerFactory.getLogger(RedisService.class);


    //jedis连接池
    private static JedisPool jedisPool = null;

    //在jedispool中最小的idle状态(空闲的)的jedis实例的个数
    private final static Integer MINIDLE = 20;

    //Redis服务器IP
    private final static String ADDRESS = "127.0.0.1";

    //Redis的端口号
    private final static int PORT = 6379;

    //访问密码
    private final static String AUTH = "admin";

    //可用连接实例的最大数目，默认值为8；如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private final static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private final static int MAX_IDLE = 20;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private final static int MAX_WAIT = 10000;

    private final static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private final static boolean TEST_ON_BORROW = true;

    //在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。
    private final static Boolean TEST_ON_RETURN = true;


    /**
     * 缓存生存时间
     */
    private final static int expire = 60000;
    /**
     * 操作Key的方法
     */
    public JedisByKeys KEYS;
    /**
     * 对存储结构为String类型的操作
     */
    public JedisByString STRINGS;
    /**
     * 对存储结构为List类型的操作
     */
    public JedisByLists LISTS;
    /**
     * 对存储结构为HashMap类型的操作
     */
    public JedisByHash HASH;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDRESS, PORT, TIMEOUT/*, AUTH*/);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_ACTIVE);
        config.setMinIdle(MAX_ACTIVE);

        config.setTestOnBorrow(TEST_ON_RETURN);
        config.setTestOnReturn(TEST_ON_RETURN);

        //连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
        config.setBlockWhenExhausted(true);

        jedisPool = new redis.clients.jedis.JedisPool(config, ADDRESS, PORT, 1000 * 2);
    }


    /**
     * 在finaally中回收jedis
     */
    public static void returnJedis(Jedis jedis) {
        if (null != jedis && null != jedisPool) {
            jedisPool.returnResource(jedis);
        }
    }

    public static void close(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error("return redis resource exception", e);
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }


    public static void main(String[] args) {
        Jedis jedis = jedisPool.getResource();
        jedis.set("lzh", "liuzhonghua");
        close(jedis);
        //临时调用，销毁连接池中的所有连接
        jedisPool.destroy();
        System.out.println("program is end");
    }

}