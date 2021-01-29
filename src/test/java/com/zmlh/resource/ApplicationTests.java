package com.zmlh.resource;

import com.zmlh.entity.StudentInfoTab;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
class ApplicationTests {


    @Test
    @SneakyThrows
    void contextLoads () {
        List<StudentInfoTab> list = new ArrayList<>();
//        list.add(1);
//        list.add("tyh");
        list.add(new StudentInfoTab());
        getList(list);


    }

    private void getList ( List<?> list ) {
        list.forEach(System.out::println);
    }

}
