package concurrent;

import java.util.concurrent.TimeUnit;

/*
 * 一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 * 其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
 *
 * synchronized 锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
 * static synchronized 锁的是对象class
 *
 * 也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁，
 * 可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，
 * 所以无须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁。
 *
 * 所有的静态同步方法用的也是同一把锁——类对象本身，
 * 这两把锁是两个不同的对象，所以静态同步方法与非静态同步方法之间是不会有竞争的。
 * 但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁，而不管是同一个实例对象的静态同步方法之间，
 * 还是不同的实例对象的静态同步方法之间
 *
 * 个人理解:
 * 所有的 static synchronized 方法竞争的是类的锁, 一个线程得到了类的锁, 所有要调用该类的 static synchronized
 * 方法的线程只能等待;
 * 所有的 synchronized 方法竞争的是对象的锁, 一个线程得到了对象的锁, 所有要调用该对象的 synchronized 方法的线程只能等待;
 * static synchronized、 synchronized、普通方法不共同竞争
 */


/*
 * 1    标准访问，请问先打印短信还是邮件？   - 短信 邮件
 * 2    停4秒钟在短信方法内 ，请问先打印短信还是邮件？    - 4s 短信 邮件
 * 3    新增普通的hello方法，请问先打印短信还是hello？ - hello 4s 短信
 * 4    现在有两部手机，请问先打印短信还是邮件？ - 邮件 4s 短信
 * 5    两个静态同步方法，1部手机，请问先打印短信还是邮件？ - 4s 短信 邮件
 * 6    两个静态同步方法，2部手机，请问先打印短信还是邮件？ - 4s 短信 邮件
 * 7    1个普通同步方法，1个静态同步发发，1部手机，请问先打印短信还是邮件？ - 邮件 4s 短信
 * 8    1个普通同步方法，1个静态同步发发，2部手机，请问先打印短信还是邮件？ - 邮件 4s 短信
 */
public class Lock8 {
    public static void main(String[] args) throws Exception {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                Phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AA").start();

        Thread.sleep(100);

        new Thread(() -> {
            try {
                // phone.sendEmail();
                // phone.getHello();
                phone2.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BB").start();
    }

    static class Phone {
        public static synchronized void sendSMS() throws Exception {
            TimeUnit.SECONDS.sleep(4);
            System.out.println("-----sendSMS");
        }

        public synchronized void sendEmail() throws Exception {
            System.out.println("-----sendEmail");
        }

        public void getHello() {
            System.out.println("-----getHello");
        }

    }
}