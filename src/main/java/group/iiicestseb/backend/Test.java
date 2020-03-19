
package group.iiicestseb.backend;

import group.iiicestseb.backend.config.OASISExceptionHandler;
import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.utils.DateUtil;
import group.iiicestseb.backend.utils.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.*;


/**
 * @author jh
 */
public class Test {

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    public @interface A {
    }
    @Retention(RetentionPolicy.RUNTIME)
    public @interface B {
    }
    @A
    @B
    public static class Super {
        @A
        public int a;
        @B
        public int b;
        @A
        @B
        public void func(){}
    }

    public static class Son extends Super {
    }


    public static void main(String[] args) throws Exception {
        Super su = new Super();
        Son son = new Son();
        Annotation[] superAnn = Super.class.getAnnotations();
        Annotation[] sonAnn = Son.class.getAnnotations();
        Annotation[] superFunc = Super.class.getMethod("func").getAnnotations();
        Annotation[] sonFunc = Son.class.getMethod("func").getAnnotations();
        System.out.println();

    }

}

