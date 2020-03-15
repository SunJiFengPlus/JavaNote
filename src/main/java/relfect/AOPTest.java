package relfect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AOPTest {
    public static void main(String[] args) {
        // 创建被代理类的对象
        SuperMan superman = new SuperMan();
        // 得到代理类的对象
        Human human = (Human) MyProxy.getProxyInstance(superman);
        // 通过代理类对象调用重写的抽象方法
        human.info();
        human.fly();
    }
}

interface Human {
    void info();

    void fly();
}

class SuperMan implements Human {

    public void info() {
        System.out.println("im superman");
    }

    public void fly() {
        System.out.println("i can fly");
    }
}

class MyInvocationHandler1 implements InvocationHandler {

    Object obj; // 被代理对象的声明

    public MyInvocationHandler1() {
        System.out.println("Constructor");
    }

    public void setObject(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue = method.invoke(obj, args);
        return returnValue;
    }
}

class MyProxy {
    // 动态创建代理类对象
    public static Object getProxyInstance(Object obj) {
        MyInvocationHandler1 handler = new MyInvocationHandler1();
        handler.setObject(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }
}