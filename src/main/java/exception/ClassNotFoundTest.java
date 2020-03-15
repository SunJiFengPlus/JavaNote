package exception;

/**
 * @author 孙继峰
 * @date 2019/05/02
 */
public class ClassNotFoundTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("bucunzai");
    }
}
