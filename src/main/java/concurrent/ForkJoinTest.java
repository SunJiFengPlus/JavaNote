package concurrent;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
    public static void main(String[] args) {
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new Sum(0L, 100000000L);
        System.out.println(pool.invoke(task));
        Instant end = Instant.now();
        System.out.println("时间:" + Duration.between(start, end).toMillis()); // 13
    }

    @Test
    public void testNoForkJoinNoAlgorithm() {
        Instant start = Instant.now();
        long sum = 0L;
        for (long i = 0L; i <= 100000000L; i++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("时间:" + Duration.between(start, end).toMillis()); // 931
    }

    @Test
    public void testAlgorithm() {
        Instant start = Instant.now();
        Long sum = (100000000L) * (100000000L + 1L) / 2;
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("时间:" + Duration.between(start, end).toMillis()); // 0
    }

    /**
     * 带有返回值的 ForkJoin
     */
    static class Sum extends RecursiveTask<Long> {

        private Long start;
        private Long end;
        // 阈值
        private static final Long THRESHOLD = 10000L;

        @Override
        protected Long compute() {
            if ((end - start) <= THRESHOLD) {
                return gaussAlgorithm(start, end);
            }

            long middle = (start + end) / 2;
            Sum left = new Sum(start, middle);
            Sum right = new Sum(middle + 1, end);
            // 进行拆分, 同时压入线程队列
            left.fork();
            right.fork();
            // 合并
            return left.join() + right.join();
        }

        public Sum(Long start, Long end) {
            this.start = start;
            this.end = end;
        }

        public Long gaussAlgorithm(Long start, Long end) {
            return (start + end) * (end - start + 1) / 2;
        }
    }
}