package com.rc.spoonbill.test.extension;

import java.util.HashMap;
import java.util.Map;

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;

import com.rc.spoonbill.test.DataSetSourceContainer;
import com.rc.spoonbill.test.annotation.JsonDataSet;
import com.rc.spoonbill.test.interceptor.JsonDataSetInterceptor;
import com.rc.spoonbill.test.listener.JsonDataSetSelector;

public class JsonDataSetExtenstion extends AbstractAnnotationDrivenExtension<JsonDataSet> {

    // To keep each feature name with the data set source
    // Key: feature name, value: data source paths
    private Map<String, String[]> dataSetMap = new HashMap<String, String[]>();

    @Override
    public void visitFeatureAnnotation(JsonDataSet annotation, FeatureInfo feature) {

        // Collect all features
        dataSetMap.put(feature.getName(), annotation.value());
    }

    @Override
    public void visitSpec(SpecInfo spec) {

        DataSetSourceContainer container = new DataSetSourceContainer();
        new JsonDataSetInterceptor(spec, container);
        spec.addListener(new JsonDataSetSelector(container, dataSetMap));
    }

}
