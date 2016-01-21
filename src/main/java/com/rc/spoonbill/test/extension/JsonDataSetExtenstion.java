package com.rc.spoonbill.test.extension;

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;

import com.rc.spoonbill.test.annotation.JsonDataSet;
import com.rc.spoonbill.test.interceptor.JsonDataSetInterceptor;

public class JsonDataSetExtenstion extends AbstractAnnotationDrivenExtension<JsonDataSet> {

    private JsonDataSetInterceptor interceptor;

    @Override
    public void visitFeatureAnnotation(JsonDataSet annotation, FeatureInfo feature) {
        this.interceptor = new JsonDataSetInterceptor(annotation);
    }

    @Override
    public void visitSpec(SpecInfo spec) {
        interceptor.register(spec);
    }

}
