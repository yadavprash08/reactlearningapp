package com.prashant.java.web.react.resources;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Uninterruptibles;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Author: @yprasha
 * Created on: 6/2/17
 */
public class MyResourceTest {

    private ForkJoinPool pool = ForkJoinPool.commonPool();
    private final FutureTask<String> generatTask = new FutureTask<String>(this::generateMessageTask);


    public String generateMessageTask() {
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        return LocalDateTime.now().toString() + " :: Hello";
    }

    @Test
    public void testMyResourceFailure(){
        IntStream.range(0, 10).mapToObj(i->generatTask).map(this::generateTask).forEach(System.out::println);
    }

    private String generateTask(final FutureTask<String> stringFutureTask) {
        try{
            return generatTask.get(2, TimeUnit.SECONDS);
        } catch (Exception e){
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
    }

}