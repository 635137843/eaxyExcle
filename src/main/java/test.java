/**
 * @program: eoms-workflow
 * @description:
 * @author: HeMingXin
 * @create: 2020/9/29 14:36
 **/
public class test {

    static class Parent{
        public static int A = 1;
        static {
            A = 2;
        }
    }
    static class Sub extends Parent{
        public static int B = A;
    }

    public static void main(String[] args) {
        System.out.println(Parent.A);
        System.out.println(Sub.B);
    }
}
