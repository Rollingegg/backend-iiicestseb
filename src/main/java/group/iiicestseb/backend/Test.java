
package group.iiicestseb.backend;

import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.utils.DateUtil;
import group.iiicestseb.backend.utils.NumberUtil;
import group.iiicestseb.backend.vo.affiliation.AffiliationActiveInTerm;
import group.iiicestseb.backend.vo.graph.Vertex;
import org.apache.commons.collections.list.TreeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.*;
import java.util.function.*;


/**
 * @author jh
 */
public class Test {

    static class C {
        public void print(String s){
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Exception {
        BiFunction<C, Collection<C>, Boolean> c = (o1, o2) -> o2.contains(o1);
        BiPredicate<C, Collection<C>> p = (o, co) -> co.contains(o);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println(list.subList(0,3));
        Collection<Vertex> vertices = new LinkedList<>();
        Vertex v1 = new Vertex("1", Vertex.TYPE.Paper);
        v1.setSize(0.5);
        Vertex v2 = new Vertex("2", Vertex.TYPE.Paper);
        v2.setSize(0.7);
        vertices.add(v1);
        vertices.add(v2);
        vertices.removeIf(v -> v.getId().equals(v1.getId()));
        System.out.println(vertices);
    }

}

