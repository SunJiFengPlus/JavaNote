package exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙继峰
 * @date 2019/05/02
 */
public class ConcurrentModifyTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }

        for (String i : list) {
            list.remove(i);
        }
    }
}
