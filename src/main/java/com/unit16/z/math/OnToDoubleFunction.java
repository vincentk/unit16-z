package com.unit16.z.math;

import java.util.function.ToDoubleFunction;

import com.unit16.z.indexed.Indexed;

public final class OnToDoubleFunction<B>
{
    private final ToDoubleFunction<B> td;
    public OnToDoubleFunction(ToDoubleFunction<B> td_) { td = td_; }
    
    public final double[] from(Indexed<? extends B> idx)
    {
        final double[] r = new double[idx.size()];
        into(idx, r);
        return r;
    }
    
    public void into(Indexed<? extends B> idx, double[] dst)
    {
        final int n = idx.size();
        for (int i = 0; i < n; i++) { dst[i] = td.applyAsDouble(idx.get(i)); }
    }
    
    public double min(Iterable<? extends B> src)
    {
        double m = Double.MAX_VALUE;
        for (B b : src) { m = Math.min(m, td.applyAsDouble(b)); }
        return m;
    }
    
    public double max(Iterable<? extends B> src)
    {
        double m = Double.MIN_VALUE;
        for (B b : src) { m = Math.max(m, td.applyAsDouble(b)); }
        return m;
    }
}
