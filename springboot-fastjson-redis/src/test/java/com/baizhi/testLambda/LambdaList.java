package com.baizhi.testLambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class LambdaList {
    @Test
    public void test01(){
        List<String> list = Arrays.asList("a", "b", "c");
        list.forEach(n->System.out.println(n));
    }
}
