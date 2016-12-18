package rocks.appconcept.testing.perf;

/**
 * Created by ioannis.metaxas on 2015-11-18.
 * A top abstract implementation of the TestThread interface.
 * Each test thread should extend this class and use its custom defined run method
 */
public abstract class TestThreadImpl extends Thread implements TestThread {

    private TestSet testSet;
    protected TestReport testReport;

    public TestThreadImpl(TestSet testSet) {
        this.testSet = testSet;
    }

    public TestSet getTestSet() {
        return testSet;
    }

    public void setTestSet(TestSet testSet) {
        this.testSet = testSet;
    }

    public TestReport getTestReport() {
        return testReport;
    }

    public void setTestReport(TestReport report) {
        this.testReport = testReport;
    }


}
