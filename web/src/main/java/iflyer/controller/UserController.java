package iflyer.controller;

import com.alibaba.fastjson.JSONObject;
import iflyer.model.User;
import iflyer.service.UserService;
import iflyer.system.mqtt.RabbitConfig;
import iflyer.system.redis.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Value("${spring.rabbitmq.default.address}")
    String address;

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private RedisService redisTemplate;

    @Autowired
    @Qualifier("messageTemplate")
    private AmqpTemplate messageTemplat;

    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "Hello" + "\n" + address + "\n" + rabbitConfig.address;
    }

//    @ResponseBody
//    @RequestMapping(value = "{name}/user", method = RequestMethod.GET)
//    public String getUserInfo(@PathVariable(name = "name") String name, ModelMap modelMap) {
//        User user = userService.getUser(name);
//        modelMap.put("user", user);
//        return name + "user";
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/save/{name}/{age}", method = RequestMethod.GET)
//    public String saveUserInfo(@PathVariable(name = "name") String name, @PathVariable(name = "age") String age, ModelMap modelMap) {
//        User user = userService.saveUser(name, age);
//        modelMap.put("user", user);
//        return user.toString() + "save success";
//    }

    @ResponseBody
    @RequestMapping(value = "/getRedisKey", method = RequestMethod.GET)
    public Object getRedisKey() {

        String key = "list1";
        List<String> list = new ArrayList<>(4);
        list.add("1");
        list.add("2");
        redisTemplate.set(key, list, RedisService.HALF_DAY);
        return redisTemplate.get(key);
    }

    @ResponseBody
    @RequestMapping(value = "/mqDemo1", method = RequestMethod.GET)
    public Object rabbitMqDemo() {
        JSONObject json = new JSONObject(5);
        for (int i = 1; i <= 10; i++) {
            json.put("key" + i, "var" + i);
            json.put("key" + i, "var" + i);
            String msg = JSONObject.toJSONString(json);
            //这里的队列要一致
            messageTemplat.convertAndSend("test_queue", msg);
        }
        return "hello";
    }
}