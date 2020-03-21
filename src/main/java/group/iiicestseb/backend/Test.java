
package group.iiicestseb.backend;

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
        String s1 = "16-24 May 2015";
        System.out.println(DateUtil.parseChron(s1));
        String s2 = "25-31 May 2019";
        System.out.println(DateUtil.parseChron(s2));
        String s3 = "20-28 May 2017";
        System.out.println(DateUtil.parseChron(s3));
        String s4 = "May 27 2018-June 3 2018";
        System.out.println(DateUtil.parseChron(s4));
        System.out.println();
    }

}

