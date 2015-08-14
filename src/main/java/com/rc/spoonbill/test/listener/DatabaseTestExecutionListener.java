package com.rc.spoonbill.test.listener;

import java.lang.annotation.Annotation;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import com.rc.spoonbill.test.annotation.CleanUp;
import com.rc.spoonbill.test.annotation.DataSet;

public class DatabaseTestExecutionListener extends AbstractTestExecutionListener {

	private IDatabaseTester databaseTester;

	private IDatabaseConnection connection;

	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {

		DataSet dataSetAnnotation = getAnnotation(DataSet.class, testContext);

		if (dataSetAnnotation != null) {
			FlatXmlDataFileLoader flatXmlDataFileLoader = (FlatXmlDataFileLoader) testContext.getApplicationContext()
					.getBean("flatXmlDataFileLoader");
			databaseTester = (IDatabaseTester) testContext.getApplicationContext().getBean("databaseTester");
			databaseTester.setDataSet(flatXmlDataFileLoader.load(dataSetAnnotation.value()));
			databaseTester.onSetup();
		}

		this.connection = databaseTester.getConnection();
	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {

		// Clear up testing data if exists
		if (databaseTester != null) {

			CleanUp cleanUpAnnotation = getAnnotation(CleanUp.class, testContext);
			if (cleanUpAnnotation != null) {
				IDataSet dataSet = connection.createDataSet(cleanUpAnnotation.value());
				DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
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
