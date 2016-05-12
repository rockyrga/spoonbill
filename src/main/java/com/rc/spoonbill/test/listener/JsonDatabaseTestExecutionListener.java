package com.rc.spoonbill.test.listener;

import java.lang.annotation.Annotation;
import java.util.Stack;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import com.rc.spoonbill.test.annotation.JsonDataSet;

public class JsonDatabaseTestExecutionListener extends AbstractTestExecutionListener {

    private IDatabaseTester databaseTester;

    private IDatabaseConnection connection;

    private Stack<IDataSet> dataSets;

    public JsonDatabaseTestExecutionListener() {
        this.dataSets = new Stack<IDataSet>();
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {

        // Create a DatabaseTester instance
        databaseTester = (IDatabaseTester) testContext.getApplicationContext().getBean("databaseTester");

        JsonDataSet jsonDataBaseSetAnnotation = getAnnotation(JsonDataSet.class, testContext);
        if (jsonDataBaseSetAnnotation != null) {
            IDataSet dataSet = new com.rc.spoonbill.test.dataset.JsonDataSets(
                    testContext.getApplicationContext().getResource(jsonDataBaseSetAnnotation.value()[0]).getFile());
            databaseTester.setDataSet(dataSet);
            databaseTester.onSetup();
            dataSets.push(dataSet);
        }

        this.connection = databaseTester.getConnection();
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {

        // Clear up testing data if exists
        if (databaseTester != null) {

            int stackSize = dataSets.size();
            for (int seq = 0; seq < stackSize; seq++) {

                IDataSet dataSet = dataSets.pop();
                DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
                DatabaseOperation.DELETE_ALL.execute(databaseTester.getConnection(), dataSet);
            }

            this.connection.close();
            databaseTester.onTearDown();
        }
    }

    private <A extends Annotation> A getAnnotation(Class<A> annotationClass, TestContext testContext) {

        A annotation = testContext.getTestClass().getAnnotation(annotationClass);
        if (annotation == null) {
            annotation = testContext.getTestMethod().getAnnotation(annotationClass);
        }

        return annotation;
    }
}
