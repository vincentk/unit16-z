package com.unit16.z.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FluentConsumer<T> implements Consumer<T> {

    private final Consumer<? super T> wrapped;

    private FluentConsumer(Consumer<? super T> w) { wrapped = w; }

    public static <P> FluentConsumer<P> on(Consumer<? super P> w) { return new FluentConsumer<P>(w); }

    @Override
    public final void accept(T t) {
        wrapped.accept(t);
    }

    public final FluentConsumer<T> filter(final Predicate<? super T> pred) {
        return new Filters<>(pred, wrapped);
    }

    public final <S> FluentConsumer<S> map(final Function<? super S, ? extends T> func)
    {
        return new MapsTo<>(func, wrapped);
    }
    
    private static final class Filters<S> extends FluentConsumer<S>
    {
        private Filters(Predicate<? super S> pred, Consumer<? super S> ct)
        {
            super(new Consumer<S>(){
                @Override
                public void accept(S t) {
                    if (pred.test(t)) ct.accept(t);
                }});
        }
    }
    
    private static final class MapsTo<S, T> extends FluentConsumer<S>
    {
        private MapsTo(Function<? super S, ? extends T> map, Consumer<? super T> ct)
        {
            super(new Consumer<S>(){

                @Override
                public void accept(S t) {
                    ct.accept(map.apply(t));
                }});
        }
    }
}
