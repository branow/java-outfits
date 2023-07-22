package com.branow.outfits.catcher;


public class Functions {

    public interface FunctionException<R> {
        R transform(Exception e);
    }

    public interface PredicateException {
        boolean transform(Exception e);
    }

    public interface ConsumerException {
        void intercept(Exception e);
    }

    public interface Void {
        void run() throws Exception;
    }


    public interface Consumer<P> {
        void run(P p) throws Exception;
    }

    public interface Predicate<P> {
        boolean run(P p) throws Exception;
    }

    public interface Function<P, R> {
        R run(P p) throws Exception;
    }

    public interface Supplier<R> {
        R run() throws Exception;
    }

    public interface BiConsumer<P1, P2>  {
        void run(P1 p1, P2 p2) throws Exception;
    }

    public interface BiPredicate<P1, P2> {
        boolean run(P1 p1, P2 p2) throws Exception;
    }

    public interface BiFunction<P1, P2, R> {
        R run(P1 p1, P2 p2) throws Exception;
    }

    public interface TrioFunction<P1, P2, P3, R>  {
        R run(P1 p1, P2 p2, P3 p3) throws Exception;
    }

}
