package iflyer;

import iflyer.system.mqtt.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class IflyerApplication implements CommandLineRunner {

    @Autowired
    public RabbitConfig rabbitConfig;

    public static void main(String[] args) {
        SpringApplication.run(IflyerApplication.class, args);
    }


    @Override
    public void run(String... strings) throws Exception {

        test1();
    }

    void test1() {
        System.out.println(rabbitConfig.address);
    }
}
