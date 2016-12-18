package rocks.appconcept.testing.perf;

/**
 * Created by ioannis.metaxas on 2015-11-10.
 * It represents a user, as a thread, who calls makes calles in the client
 * Each thread has a test set and a report which keeps different time metrics
 */
public interface TestThread {

    public long getId();

    public String getName();

    public void setName(String name);

    public TestSet getTestSet();

    public void setTestSet(TestSet testSet);

    public TestReport getTestReport();

    public void setTestReport(TestReport report);

}
