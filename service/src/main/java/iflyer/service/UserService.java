package iflyer.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import iflyer.dao.UserDao;
import iflyer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

//    public User getUser(String name) {
//        return userDao.getUser(name);
//    }
//
//    public User saveUser(String name, String age) {
//        return userDao.saveUser(name, age);
//    }


}
