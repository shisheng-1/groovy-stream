package groovy.stream.iterators ;

import groovy.lang.Closure ;
import java.util.Iterator ;
import java.util.NoSuchElementException ;

public class TransformingIterator<T,U> implements Iterator<U> {
    private final Iterator<T> iterator ;
    private final Closure<U> mapping ;
    private U current ;
    private boolean exhausted ;
    private boolean initialised ;

    public TransformingIterator( Iterator<T> iterator, Closure<U> mapping ) {
        this.iterator = iterator ;
        this.mapping = mapping ;
        this.exhausted = false ;
        this.initialised = false ;
    }

    public void remove() {
        iterator.remove() ;
    }

    private void loadNext() {
        if( iterator.hasNext() ) {
            T next = iterator.next() ;
            mapping.setDelegate( next ) ;
            current = mapping.call( next ) ;
        }
        else {
            exhausted = true ;
        }
    }

    public boolean hasNext() {
        if( !initialised ) {
            loadNext() ;
            initialised = true ;
        }
        return !exhausted ;
    }

    public U next() {
        if( !initialised ) {
            hasNext() ;
        }
        if( exhausted ) {
            throw new NoSuchElementException( "TransformingIterator has been exhausted and contains no more elements" ) ;
        }
        U ret = current ;
        loadNext() ;
        return ret ;
    }
}