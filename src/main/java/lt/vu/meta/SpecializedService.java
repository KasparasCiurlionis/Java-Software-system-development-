package lt.vu.meta;

import javax.enterprise.inject.Specializes;

@Specializes
public class SpecializedService extends AlternativeServiceImpl {
    public void doSomething() {
        System.out.println("specializes");
    }
}
