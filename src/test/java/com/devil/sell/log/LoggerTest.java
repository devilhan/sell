package com.devil.sell.log;

import com.devil.sell.SellApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * @author Hanyanjiao
 * @date 2020/6/9
 */
@Slf4j
public class LoggerTest extends SellApplicationTests {

//    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
    String name = "test";
    String password = "123456";

    private RedisTemplate redisTemplate;

    @Test
    public void test1(){

        log.debug("debug.....");
        log.info("info....");
        log.error("error....");
        log.info("name:{} ,password :{}",name,password);
    }
}
