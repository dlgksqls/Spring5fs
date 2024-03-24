package main;

import config.Appctx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import spring.Client;
import spring.Client2;

public class Main {
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Appctx.class);

        Client client = ctx.getBean(Client.class);
        Client2 client2 = ctx.getBean(Client2.class);
        client.send();
        client2.send();
        ctx.close();
        System.out.println();
    }
}
