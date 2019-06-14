package de.tarent.nca.testutil

import groovy.util.logging.Log
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.SpecInfo

@Log
class CouchDbExtension extends AbstractAnnotationDrivenExtension<NeedsCouchDb> implements IGlobalExtension {


    CouchDbExtension () {
    }

    @Override
    void visitSpecAnnotation(NeedsCouchDb annotation, SpecInfo spec) {
        CouchDb.instance.start()
    }

    // IGlobalExtension

    @Override
    void start() {

    }

    @Override
    void visitSpec(SpecInfo spec) {
    }

    @Override
    void stop() {
        CouchDb.instance.stop()
    }

}
