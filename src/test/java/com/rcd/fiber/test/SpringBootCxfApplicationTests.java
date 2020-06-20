package com.rcd.fiber.test;

import com.rcd.fiber.base.soap.SendWSNCommand;
import com.rcd.fiber.base.soap.wsn.UserNotificationProcessImpl;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Endpoint;
import java.util.List;

/**
 * @Author: HUHU
 * @Date: 2019/6/27 17:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootCxfApplicationTests {


    //王伟：拿取注入的Mongo相关Bean
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    //王伟：从配置文件获取bucketName
    @Value("${spring.data.mongodb.bucket-name}")
    private String bucketName;

    @Test
    public void test_ww()
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("site_name").is("zhengzhou"))
            .addCriteria(Criteria.where("site_level").is("3"));
        System.out.println("123*****");
        List<Document> all = mongoTemplate.findAll(Document.class, bucketName + ".files");
        System.out.println(all);


    }


    @Test
    public void test1() throws Exception {


        //本机地址
        String wsnAddr = "http://127.0.0.1:9011/wsn-core";
        String receiveAddr = "http://127.0.0.1:9008/wsn-subscribe";
        SendWSNCommand receive = new SendWSNCommand(receiveAddr, wsnAddr);

        //设置用户id
        String id = "Fiber";
        // 消息处理逻辑
        UserNotificationProcessImpl implementor = new UserNotificationProcessImpl();
        // 启接收服
        Endpoint endpint = Endpoint.publish(receiveAddr, implementor);
        receive.subscribe(id, "test1");




    }

}
