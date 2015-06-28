/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package groovy.stream.iterators.groovy ;

import groovy.lang.Closure ;
import groovy.stream.iterators.AbstractIterator ;
import groovy.stream.iterators.CloseableIterator;

import java.util.Iterator ;
import java.util.NoSuchElementException ;

public class TapIterator<T> extends AbstractIterator<T> {
    protected final int           every ;
    private   final boolean       withIndex ;
    private   final Closure<Void> output ;

    protected int     index ;

    public TapIterator( CloseableIterator<T> parent, int every, boolean withIndex, Closure<Void> output ) {
        super( parent ) ;
        this.every = every ;
        this.index = 0 ;
        this.withIndex = withIndex ;
        this.output = output ;
    }

    @Override
    protected void loadNext() {
        if( iterator.hasNext() ) {
            current = iterator.next() ;
        }
        else {
            exhausted = true ;
        }
    }

    @Override
    public T next() {
        hasNext() ;
        if( exhausted ) {
            throw new NoSuchElementException( "TapIterator has been exhausted and contains no more elements" ) ;
        }
        T ret = current ;
        performTap( ret );
        loaded = false ;
        return ret ;
    }

    protected void performTap( T ret ) {
        if( ++index % every == 0 ) {
            if( withIndex ) {
                output.call( ret, index - 1 ) ;
            }
            else {
                output.call( ret ) ;
            }
        }
    }
}