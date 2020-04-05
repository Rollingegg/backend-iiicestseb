
package group.iiicestseb.backend;

import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.utils.DateUtil;
import group.iiicestseb.backend.utils.NumberUtil;
import group.iiicestseb.backend.vo.affiliation.AffiliationActiveInTerm;
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
        public void func() {
        }
    }

    public static class Son extends Super {
    }


    public static void main(String[] args) throws Exception {
        Collection<Affiliation> affiliations = new LinkedList<>();
        affiliations.add(new Affiliation(1, "1"));
        affiliations.add(new Affiliation(2, "2"));
        affiliations.add(new Affiliation(3, "3"));
        Map<Integer, Double> id_scores = new HashMap<>() {
            {
                put(1, 0.5);
                put(2, 3.0);
                put(3, 2.0);
            }
        };
        ArrayList<AffiliationActiveInTerm> results = new ArrayList<>(3);
//        for (Affiliation a : affiliations) {
//            results.add(new AffiliationActiveInTerm(a.getId(), a.getName(), id_scores.get(a.getId())));
//        }
//        results.sort((o1, o2) ->
//                o1.getScore() < o2.getScore() ? 1 : o1.getScore().equals(o2.getScore()) ? 0 : -1);
//        System.out.println(results);
    }

}

