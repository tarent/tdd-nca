package de.tarent.nca.util

import spock.lang.Specification
import spock.lang.Unroll

class JsonConverterTest extends Specification {

    static class A {
        String a
        @Override
        boolean equals(Object other) { other != null && other instanceof A && a == other.a }
    }

    static class B {
        String b
        @Override
        boolean equals(Object other) { other != null && other instanceof B && b == other.b }
    }

    static class C {
        String c
        @Override
        boolean equals(Object other) { other != null && other instanceof C && c == other.c }
    }

    @Unroll
    def 'merge works expecting #expectedJson'() {
        given:
        def converter = new JsonConverter()

        when:
        def merge = converter.merge((Object[]) givenObjects)

        then:
        merge.similar(converter.jsonObjectOf(expectedJson))

        where:
        givenObjects                                  | expectedJson
        []                                            | '{}'
        [new A(a: 'A')]                               | '{"a": "A"}'
        [new A(a: 'A'), new B(b: 'B')]                | '{"a": "A", "b": "B"}'
        [new A(a: 'A'), new B(b: 'B'), new C(c: 'C')] | '{"a": "A", "b": "B", "c": "C"}'
    }

    @Unroll
    def 'split works for #givenJson'() {
        given:
        def converter = new JsonConverter()
        def json = converter.jsonObjectOf('{"a": "A"}')

        when:
        def split = converter.split(json, A)

        then:
        split[0] == new A(a: "A")

        where:
        givenJson                        | givenClasses | expectedObjects
        '{"a": "A"}'                     | []           | []
        '{"a": "A"}'                     | [A]          | [new A(a: "A")]
        '{"a": "A", "b": "B"}'           | [A, B]       | [new A(a: 'A'), new B(b: 'B')]
        '{"a": "A", "b": "B", "c": "C"}' | [A, B, C]    | [new A(a: 'A'), new B(b: 'B'), new C(c: 'C')]
    }
}
