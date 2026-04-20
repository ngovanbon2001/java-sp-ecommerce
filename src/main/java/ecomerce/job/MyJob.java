package ecomerce.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyJob {
    @Scheduled(fixedRate = 5000)
    public void run() {
        System.out.println("Run every 5 seconds");
    }
}
