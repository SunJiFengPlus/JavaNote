package relfect;

public class StaticProxyTest1 {

    public static void main(String[] args) {
        NikeClothesFactory nike = new NikeClothesFactory();
        //创造代理类对象时传入被代理类的对象
        ProxyFactory pf = new ProxyFactory(nike);
        pf.productClothes();
    }
}

//接口
interface ClothesFactory {
    public void productClothes();
}

//被代理类
class NikeClothesFactory implements ClothesFactory {
    public void productClothes() {
        System.out.println("Nike生产");
    }
}

//代理类
class ProxyFactory implements ClothesFactory {
    ClothesFactory cf;

    //创造代理类对象时传入被代理类的对象
    public ProxyFactory(ClothesFactory cf) {
        this.cf = cf;
    }

    public void productClothes() {
        System.out.println("代理生产");
        cf.productClothes();
    }
}