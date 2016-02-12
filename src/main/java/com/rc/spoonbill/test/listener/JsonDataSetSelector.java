package com.rc.spoonbill.test.listener;

import java.util.Map;

import org.spockframework.runtime.AbstractRunListener;
import org.spockframework.runtime.model.FeatureInfo;

import com.rc.spoonbill.test.DataSetSourceContainer;

public class JsonDataSetSelector extends AbstractRunListener {

    private Map<String, String[]> dataSetMap;
    private DataSetSourceContainer container;

    public JsonDataSetSelector(DataSetSourceContainer container, Map<String, String[]> dataSetMap) {

        this.container = container;
        this.dataSetMap = dataSetMap;
    }

    @Override
    public void beforeFeature(FeatureInfo feature) {

        // Select the data source of the feature
        String[] dataSetSources = dataSetMap.get(feature.getName());
        container.setDataSetSources(dataSetSources);
    }

}
