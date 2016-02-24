package com.rc.spoonbill.test.listener;

import java.util.Map;

import org.spockframework.runtime.AbstractRunListener;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;

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
        addDataSetSourcesToContainer(dataSetSources);
    }

    @Override
    public void beforeSpec(SpecInfo spec) {

        String[] dataSetSources = dataSetMap.get(spec.getName());
        addDataSetSourcesToContainer(dataSetSources);
    }

    private void addDataSetSourcesToContainer(String[] dataSetSources) {

        if (dataSetSources != null) {
            container.addAllDataSetSources(dataSetSources);
        }
    }

}
