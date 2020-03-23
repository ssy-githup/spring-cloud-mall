import ai.ssy.ProductApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyTestHystrix
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/22
 * @Version V1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= ProductApp.class)
public class MyTestHystrix {

    private RestTemplate template = new RestTemplate();

    private final int THREAD_NUM=20;
    CountDownLatch cdl = new CountDownLatch(THREAD_NUM);

   // private static int i = 0;
    @Test
    public void testThread() throws Exception {
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://127.0.0.1:8801/testThread";
                    String str = template.getForObject(url, String.class);

                    System.out.println(str);
                }
            }).start();
            cdl.countDown();
        }
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void testSemaphore() throws Exception {
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://127.0.0.1:8801/testSemaphore";
                    String str = template.getForObject(url, String.class);
                    System.out.println(str);
                }
            }).start();
            cdl.countDown();
        }
        TimeUnit.SECONDS.sleep(5);
    }
    @Test
    public void testCircuitBreaker() throws Exception {
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://127.0.0.1:8801/testCircuitBreaker";
                    String str = template.getForObject(url, String.class);
                    System.out.println("name:"+Thread.currentThread().getName()+"  ID"+Thread.currentThread().getId() +str);
                }
            }).start();
            cdl.countDown();
        }
        TimeUnit.SECONDS.sleep(5);
    }

}
