package com.rc.spoonbill.test.interceptor;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.spockframework.runtime.extension.AbstractMethodInterceptor;
import org.spockframework.runtime.extension.IMethodInvocation;
import org.spockframework.runtime.model.FieldInfo;
import org.spockframework.runtime.model.SpecInfo;
import org.springframework.context.ApplicationContext;

import com.rc.spoonbill.test.annotation.JsonDataSet;
import com.rc.spoonbill.test.exception.ApplicationContextNotFoundException;

public class JsonDataSetInterceptor extends AbstractMethodInterceptor {

    private JsonDataSet annotation;
    private SpecInfo spec;
    private IDatabaseTester databaseTester;
    private IDataSet dataSet;

    public JsonDataSetInterceptor(JsonDataSet annotation) {
        this.annotation = annotation;
    }

    public void register(SpecInfo spec) {

        this.spec = spec;
        spec.addSetupInterceptor(this);
        spec.addCleanupInterceptor(this);
    }

    @Override
    public void interceptSetupMethod(IMethodInvocation invocation) throws Throwable {

        ApplicationContext applicationContext = getContext(invocation);
        this.databaseTester = (IDatabaseTester) applicationContext.getBean("databaseTester");
        for (String dataSource : annotation.value()) {

            this.dataSet = new com.rc.spoonbill.test.dataset.JsonDataSet(applicationContext.getResource(dataSource).getFile());
            databaseTester.setDataSet(dataSet);
            databaseTester.onSetup();
        }

        invocation.proceed();
    }

    @Override
    public void interceptCleanupMethod(IMethodInvocation invocation) throws Throwable {

        if (dataSet != null) {

            DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
            DatabaseOperation.DELETE_ALL.execute(databaseTester.getConnection(), dataSet);
            databaseTester.getConnection().close();
            databaseTester.onTearDown();
        }
    }

    private ApplicationContext getContext(IMethodInvocation invocation) {

        FieldInfo field = spec.getAllFields().stream().filter(f -> f.getName().equals("applicationContext")).findFirst()
                .orElseThrow(() -> new ApplicationContextNotFoundException());

        return (ApplicationContext) field.readValue(invocation.getInstance());
    }
}
