package regex;

/**
 * @author 孙继峰
 * @date 2019/07/01
 */
public class Test {
    public static void main(String[] args) {
        String res = "123456789".replaceAll("/d{3,6}", "X");
        System.out.println(res);
    }
}