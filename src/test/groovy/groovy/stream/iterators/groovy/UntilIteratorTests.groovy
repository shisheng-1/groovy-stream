/*
 * Copyright 2013 the original author or authors.
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

package groovy.stream.iterators.groovy

import groovy.stream.iterators.DelegatingCloseableIterator

class UntilIteratorTests extends spock.lang.Specification {

    def list  = [ 1, 2, null, 4, 5 ]
    UntilIterator iter

    def setup() {
        iter = new UntilIterator( new DelegatingCloseableIterator(list.iterator()), { it -> it > 3 }, false )
    }

    def "collect should return values"() {
        when:
            def result = iter.collect()
        then:
            result == [ 1, 2, null ]
    }

    def "call to next with no hasNext should work"() {
        expect:
            iter.next() == 1
            iter.hasNext() == true
    }

    def "remove should throw UnsupportedOperationException"() {
        when:
            iter.remove()
        then:
            UnsupportedOperationException ex = thrown()
    }

    def "Test NoSuchElementException"() {
        when:
            def result = iter.collect()
            iter.next()

        then:
            NoSuchElementException ex = thrown()
    }

    def "Test UntilIterator which never passes predicate"() {
        when:
            def iter = new UntilIterator( new DelegatingCloseableIterator(list.iterator()), { it -> it == 'NOTFOUND' }, false )
            def result = iter.collect()
            iter.next()
        then:
            NoSuchElementException ex = thrown()
            result == [ 1, 2, null, 4, 5 ]
    }
}