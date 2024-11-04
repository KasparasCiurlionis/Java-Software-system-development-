package lt.vu.meta;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public abstract class  LargeDecoration implements Decoration{
    @Inject @Delegate @Any Decoration decoration;

    @Override
    public void doDecoration() {
        System.out.println("large decoration");
    }
}
