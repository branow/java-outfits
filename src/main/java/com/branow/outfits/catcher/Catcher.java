package com.branow.outfits.catcher;

public class Catcher {

    public static void intercept(Functions.Void func) {
        try {
            func.run();
        } catch (Exception e) {

        }
    }

    public static <R> R intercept(Functions.Supplier<R> func) {
        try {
            return func.run();
        } catch (Exception e) {
            return null;
        }
    }

    public static <R> R intercept(Functions.Supplier<R> func, R other) {
        try {
            return func.run();
        } catch (Exception e) {
            return other;
        }
    }


    public static void intercept(Functions.Void func,
                                 Functions.ConsumerException catching) {
        try {
            func.run();
        } catch (Exception e) {
            catching.intercept(e);
        }
    }

    public static<P> void intercept(P p, Functions.Consumer<P> func,
                                    Functions.ConsumerException catching) {
        try {
            func.run(p);
        } catch (Exception e) {
            catching.intercept(e);
        }
    }

    public static <P> boolean intercept(P p, Functions.Predicate<P> func,
                                        Functions.PredicateException catching) {
        try {
            return func.run(p);
        } catch (Exception e) {
            return catching.transform(e);
        }
    }

    public static <P, R> R intercept(P p, Functions.Function<P, R> func,
                                     Functions.FunctionException<R> catching) {
        try {
            return func.run(p);
        } catch (Exception e) {
            return catching.transform(e);
        }
    }

    public static <R> R intercept(Functions.Supplier<R> func,
                                  Functions.FunctionException<R> catching) {
        try {
            return func.run();
        } catch (Exception e) {
            return catching.transform(e);
        }
    }

    public static <P1, P2> void intercept(P1 p1, P2 p2, Functions.BiConsumer<P1, P2> func,
                                          Functions.ConsumerException catching) {
        try {
            func.run(p1, p2);
        } catch (Exception e) {
            catching.intercept(e);
        }
    }

    public static <P1, P2> boolean intercept(P1 p1, P2 p2, Functions.BiPredicate<P1, P2> func,
                                             Functions.PredicateException catching) {
        try {
            return func.run(p1, p2);
        } catch (Exception e) {
            return catching.transform(e);
        }
    }

    public static <P1, P2, R> R intercept(P1 p1, P2 p2, Functions.BiFunction<P1, P2, R> func,
                                          Functions.FunctionException<R> catching) {
        try {
            return (R) func.run(p1, p2);
        } catch (Exception e) {
            return catching.transform(e);
        }
    }

    public static <P1, P2, P3, R> R intercept(P1 p1, P2 p2, P3 p3, Functions.TrioFunction<P1, P2, P3, R> func,
                                              Functions.FunctionException<R> catching) {
        try {
            return func.run(p1, p2, p3);
        } catch (Exception e) {
            return catching.transform(e);
        }
    }




    public static <E extends Exception> void interceptAndThrow(Functions.Void func, Class<E> throwing) {
        try {
            func.run();
        } catch (Exception e) {
            try {
                throw throwing.getConstructor(Throwable.class).newInstance(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static <E extends Exception, P> void interceptAndThrow(P p, Functions.Consumer<P> func, Class<E> throwing) {
        try {
            func.run(p);
        } catch (Exception e) {
            try {
                throw throwing.getConstructor(Throwable.class).newInstance(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static <E extends Exception, P> boolean interceptAndThrow(P p,
                                                          Functions.Predicate<P> func, Class<E> throwing) {
        try {
            return func.run(p);
        } catch (Exception e) {
            try {
                throw throwing.getConstructor(Throwable.class).newInstance(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static <E extends Exception, P, R> R interceptAndThrow(P p,
                                                         Functions.Function<P, R> func, Class<E> throwing) {
        try {
            return func.run(p);
        } catch (Exception e) {
            try {
                throw throwing.getConstructor(Throwable.class).newInstance(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static <E extends Exception, R> R interceptAndThrow(Functions.Supplier<R> func, Class<E> throwing) {
        try {
            return func.run();
        } catch (Exception e) {
            try {
                throw throwing.getConstructor(Throwable.class).newInstance(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static <E extends Exception, P1, P2> void interceptAndThrow(P1 p1, P2 p2,
                                                       Functions.BiConsumer<P1, P2> func, Class<E> throwing) {
        try {
            func.run(p1, p2);
        } catch (Exception e) {
            try {
                throw throwing.getConstructor(Throwable.class).newInstance(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static <E extends Exception, P1, P2> boolean interceptAndThrow(P1 p1, P2 p2,
                                                          Functions.BiPredicate<P1, P2> func, Class<E> throwing) {
        try {
            return func.run(p1, p2);
        } catch (Exception e) {
            try {
                throw throwing.getConstructor(Throwable.class).newInstance(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static <E extends Exception, P1, P2, R> R interceptAndThrow(P1 p1, P2 p2,
                                                         Functions.BiFunction<P1, P2, R> func, Class<E> throwing) {
        try {
            return func.run(p1, p2);
        } catch (Exception e) {
            try {
                throw throwing.getConstructor(Throwable.class).newInstance(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static <E extends Exception, P1, P2, P3, R> R interceptAndThrow(P1 p1, P2 p2, P3 p3,
                                                         Functions.TrioFunction<P1, P2, P3, R> func, Class<E> throwing) {
        try {
            return func.run(p1, p2, p3);
        } catch (Exception e) {
            try {
                throw throwing.getConstructor(Throwable.class).newInstance(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
