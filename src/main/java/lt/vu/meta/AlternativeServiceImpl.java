package lt.vu.meta;

import javax.enterprise.inject.Alternative;
import javax.inject.Named;

@Alternative
@Named("alternativeService")
public class AlternativeServiceImpl implements MyService {
    public void doSomething() {
        System.out.println("alternative");
    }
}
