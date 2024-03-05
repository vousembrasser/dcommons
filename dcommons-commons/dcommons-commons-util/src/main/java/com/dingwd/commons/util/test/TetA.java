package com.dingwd.commons.util.test;

public class TetA {

    public static void main(String[] args) throws Throwable {

        B b = A.build().buildB().doSomething();

        A a = b.done().done();
        System.out.println(b.done());
        System.out.println("=====调用gc=====");
        System.gc();
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=====gc调用后=====");
        System.out.println(b.done());
    }

}
