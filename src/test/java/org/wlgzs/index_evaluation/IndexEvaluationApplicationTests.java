package org.wlgzs.index_evaluation;

import org.apache.xmlbeans.impl.store.Saaj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexEvaluationApplicationTests {

    @Test
    public void contextLoads() {
        Scanner scanner  = new Scanner(System.in);
        while (scanner.hasNext()){
            System.out.println("asdfawf");
            String str = scanner.next();
            System.out.println(str);

        }
    }

}

