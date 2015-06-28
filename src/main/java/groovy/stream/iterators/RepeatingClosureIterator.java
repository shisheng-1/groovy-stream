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

package groovy.stream.iterators ;

import groovy.lang.Closure ;
import java.util.Iterator ;

public class RepeatingClosureIterator<T> implements CloseableIterator<T> {
    private final Closure<T> value ;

    public RepeatingClosureIterator( Closure<T> value ) {
        this.value = value ;
    }

    @Override
    public boolean hasNext() {
        return true ;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException() ;
    }

    @Override
    public T next() {
        return value.call() ;
    }

    @Override
    public void close() throws Exception {
    }
}