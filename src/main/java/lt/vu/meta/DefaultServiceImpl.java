package lt.vu.meta;

import javax.enterprise.inject.Alternative;
import javax.inject.Named;

@Named("defaultService")
public class DefaultServiceImpl implements MyService {
    public void doSomething() {
        System.out.println("default");
    }
}


