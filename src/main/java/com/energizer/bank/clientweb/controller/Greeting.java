package com.energizer.bank.clientweb.controller;

import com.energizer.bank.clientweb.TestClassGreeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Greeting {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public TestClassGreeting greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new TestClassGreeting(counter.incrementAndGet(),
                String.format(template, name));
    }


}
