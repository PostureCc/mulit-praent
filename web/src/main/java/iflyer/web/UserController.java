//package iflyer.web;
//
//import iflyer.model.User;
//import iflyer.service.UserService;
//import iflyer.system.RabbitConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
///**
// * Created by liuxin on 17/1/20.
// */
//@Controller
//public class UserController {
//
//    @Autowired
//    UserService userService;
//
//    @Value("${spring.rabbitmq.default.address}")
//    String address;
//
//    @Autowired
//    private RabbitConfig rabbitConfig;
//
//    @ResponseBody
//    @RequestMapping(value = "/index", method = RequestMethod.GET)
//    public String index() {
//        return "Hello" + "\n" + address + "\n" + rabbitConfig.address;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "{name}/user", method = RequestMethod.GET)
//    public String getUserInfo(@PathVariable(name = "name") String name, ModelMap modelMap) {
//        User user = userService.getUser(name);
//        modelMap.put("user", user);
//        return name + "user";
////        return new ModelAndView("user", modelMap);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/save/{name}/{age}", method = RequestMethod.GET)
//    public String saveUserInfo(@PathVariable(name = "name") String name, @PathVariable(name = "age") String age, ModelMap modelMap) {
//        User user = userService.saveUser(name, age);
//        modelMap.put("user", user);
//        return user.toString() + "save success";
////        return new ModelAndView("user", modelMap);
//    }
//
//}