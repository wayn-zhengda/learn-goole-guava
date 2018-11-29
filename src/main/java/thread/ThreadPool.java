package thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPool {
    /**
     * 使用Executors来创建线程池(《阿里巴巴Java开发手册》中不推荐)
     */
    @Test
    public void executorsDemo() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        final List<String> list = new ArrayList<String>();
        final List<String> list2 = new CopyOnWriteArrayList<String>();
        CountDownLatch countDownLatch = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        list.add("111");
                        list2.add("222");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            countDownLatch.await();
            //有时候不是200
            System.out.println(list.size());
            System.out.println(list);
            System.out.println(list2.size());
            System.out.println(list2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用guava中的工具包来创建线程池
     * guava中的工具包ThreadFactoryBuilder
     * 自定义线程名称，避免OOM
     */
    @Test
    public void guavaThreadPoolDemo() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Test-Thread-%d")
                .build();
        ExecutorService executorService = new ThreadPoolExecutor(10,
                                                            20,
                                                                5,
                                                                TimeUnit.MINUTES,
                                        new LinkedBlockingDeque<>(1024),
                                                                    threadFactory);
        for (int i = 0; i < 1020; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000L);
                        System.out.println("thread end");
                        Thread.currentThread().getName();
                    } catch (InterruptedException e) {

                    }
                }
            });
        }
    }
}
