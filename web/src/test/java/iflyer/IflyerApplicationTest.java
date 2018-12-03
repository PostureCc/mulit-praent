package iflyer;

import iflyer.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IflyerApplication.class)
public class IflyerApplicationTest {
    @Autowired
    UserDao userDao;
    @Test
    public void getUser() throws Exception {
//        System.out.println(userDao.getUser("liuixn").getName());
    }

    @Test
    public void mapInitTest() {
        Map<String, Object> map = new HashMap<>(5);
        for (int i = 0; i < 20; i++) {
            map.put("key" + i, "value" + i);
        }
        System.out.println("map.size:" + map.size());
    }
}