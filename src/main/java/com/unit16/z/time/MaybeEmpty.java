package com.unit16.z.time;

import java.io.IOException;
import java.util.function.Consumer;

import com.unit16.z.io.Source;

/**
 * A possibly empty sequence of time-stamped events.
 */
public interface MaybeEmpty extends UTCMicros
{
    public boolean hasNext();
    
    public void advance();
    
    public static final class FromSource<E>
    implements MaybeEmpty
    {
        private final Source<TimedI<? extends E>> src_;
        private final Consumer<? super E> dst_;
        
        private long micros_ = 0l;
        private TimedI<? extends E> current_ = null;
        
        public FromSource(Source<TimedI<? extends E>> src, Consumer<? super E> dst)
        {
            src_ = src;
            dst_ = dst;
            readNext();
        }

        @Override
        public long utcMicros() { return micros_; }

        @Override
        public boolean hasNext() {  return current_ != null; }

        @Override
        public void advance() {
            final E e = current_.get();
            dst_.accept(e);
            readNext();
        }
        
        private void readNext()
        {
            try {
                if (src_.hasNext())
                {
                    final TimedI<? extends E> c1 = src_.next();
                    assert c1.utcMicros() >= micros_;
                    current_ = c1;
                    micros_ = current_.utcMicros();
                }
                else
                {
                    micros_ = 0l;
                    current_ = null;
                }
            } catch (IOException e) {
                micros_ = 0l;
                current_ = null;
                throw new RuntimeException(e);
            }
        }
        
        @Override
        public String toString()
        {
            return super.toString() + "{" + src_ + "->" + dst_ + "@" + (current_ != null ? utcMicros() : 0l) + "}";
        }
    }
    
    /**
     * Synchronize two streams.
     */
    public static final class Synchronized implements MaybeEmpty
    {
        private final MaybeEmpty _fst;
        private final MaybeEmpty _snd;
        
        private long micros_ = 0l;
        private MaybeEmpty _next = null;
        
        public Synchronized(MaybeEmpty a, MaybeEmpty b)
        {
            _fst = a;
            _snd = b;
            recomputeNext();
        }

        @Override public long utcMicros() { return micros_; }

        @Override public boolean hasNext() { return _next != null; }

        @Override public void advance() { _next.advance(); recomputeNext(); }
        
        private void recomputeNext()
        {
            _next = null;
            final boolean f = _fst.hasNext();
            final boolean s = _snd.hasNext();
            
            if (!f && !s)
            {
                return;
            }
            else if (f && s)
            {
                if (_fst.utcMicros() <= _snd.utcMicros())
                {
                    _next = _fst;
                }
                else
                {
                    _next = _snd;
                }
            }
            else if (f)
            {
                _next = _fst;
            }
            else // s
            {
                _next = _snd;
            }
            
            final long m2 = _next.utcMicros();

            assert m2 >= micros_ : _fst + " " + _snd + " -> " + _next;
            micros_ = m2;
        }
    }
}