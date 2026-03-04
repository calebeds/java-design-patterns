package designpatterns.singleton;

public class InnerStaticSingleton {
    private InnerStaticSingleton() {
    }

    private static class Impl {
        private static final InnerStaticSingleton INTANCE = new InnerStaticSingleton();
    }

    public InnerStaticSingleton getIntance() {
        return Impl.INTANCE;
    }

}
