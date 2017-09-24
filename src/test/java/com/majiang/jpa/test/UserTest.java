package com.majiang.jpa.test;

import com.bradypod.Application;
import com.bradypod.web.model.Organ;
import com.bradypod.web.service.repository.jpa.OrganRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

/**
 * 测试类
 * Created by lanhaozhi on 2017/9/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserTest {

    @Autowired
    private OrganRepository organRepository ;

    @Test
    public void testService(){
        List<Organ> organList = organRepository.findByOrgi("ukewo") ;
        organRepository.setOrgiById("steven lan",organList.get(0).getId()) ;
        System.out.println(organList.size());
    }

    public void setOrganRepository(OrganRepository organRepository) {
        this.organRepository = organRepository;
    }
}
