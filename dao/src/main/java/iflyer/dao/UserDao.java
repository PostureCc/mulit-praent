package iflyer.dao;

import iflyer.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    public User getUser(String name) {
//        User user = mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), User.class);
//        if (user == null) {
//            user = new User("不存在该用户", "xx");
//        }
//        return user;
//    }
//
//    public User saveUser(String name, String age) {
//        mongoTemplate.save(new User(name, age));
//        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), User.class);
//    }

}
