package relfect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


//当调用代理类实现的抽象方法时,就会发起对被代理类同样的方法的调用
public class DynamicProxyTest1 {

    public static void main(String[] args) {
        // 1 创建被代理类对象
        RealSubject real = new RealSubject();
        // 2 创建实现 InvocationHandler 接口类的对象
        MyInvocationHandler handler = new MyInvocationHandler();
        // 3 调用 blind 方法,动态的返回一个实现了 real 所在类实现的接口 (Subject) 的代理类的对象
        Subject sub = (Subject) handler.blind(real);//此时 sub 就是代理类的对象
        // 4 转到对 InvocationHandler 接口实现类的 invoke() 方法的调用
        sub.action();


        // For example
        NikeClothesFactory nike = new NikeClothesFactory();
        ClothesFactory proxyClothes = (ClothesFactory) handler.blind(nike);
        proxyClothes.productClothes();
    }
}

// 接口
interface Subject {
    void action();
}

// 被代理类
class RealSubject implements Subject {

    @Override
    public void action() {
        System.out.println("被代理类");
    }
}

class MyInvocationHandler implements InvocationHandler {

    Object obj;

    /**
     * 给被代理类的对象实例化,并返回一个实现了代理类接口的被代理类的对象
     *
     * @param obj 被代理类的对象
     * @return 返回代理类的对象
     */
    public Object blind(Object obj) {
        this.obj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
    }

    /**
     * 当代理类的对象调用重写的方法时,都会调用此方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue = method.invoke(obj, args);
        System.out.println("invoke方法调用");
        return returnValue;
    }
}