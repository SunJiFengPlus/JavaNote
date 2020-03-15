package exception;

/**
 * @author 孙继峰
 * @date 2019/05/02
 */
public class ClassCastTest {
    public static void main(String[] args) {
        Object s = "123";
        int a = (int) s;
    }
}
