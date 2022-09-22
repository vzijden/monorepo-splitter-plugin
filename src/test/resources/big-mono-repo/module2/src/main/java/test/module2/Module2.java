package test.module2;

import test.module3.Module3;

public class Module2 {
    public static void iMModule2() {
        System.out.println("I'm module 2");

        Module3.iMModule3();
    }
}