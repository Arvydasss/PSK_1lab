package src.services;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.Random;
@ApplicationScoped
public class NameGenerator implements Serializable {
    public String generateName() {
        try {
            Thread.sleep(3000); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        String[] names = {"Alice", "Bob", "Charlie", "David", "Emily", "Frank", "Grace", "Henry", "Isabella", "John"};
        String randomName = names[new Random().nextInt(names.length)];
        return randomName;
    }
}
