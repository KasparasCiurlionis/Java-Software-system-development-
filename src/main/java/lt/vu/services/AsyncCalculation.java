package lt.vu.services;

import javax.ejb.Asynchronous;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.Random;


@ApplicationScoped
public class AsyncCalculation implements Serializable {
    public Integer calculate() {
        try {
            Thread.sleep(3000); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        Integer generatedNumber = new Random().nextInt(100);
        return generatedNumber;
    }
}
