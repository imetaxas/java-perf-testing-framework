package rocks.appconcept.testing.perf;

/**
 * Created by ioannis.metaxas on 2015-11-10.
 * Each test set has the data needed for running a test and represents a scenario
 * Each scenario can be run in a number of different threads
 */
public class TestSet implements PerfTest {
    //private TestReport testReport;



    @Override
    public void setUp() throws Exception {

    }

    @Override
    public void test(long id) throws Exception {
        // Starts reporting
        //this.testReport = new TestReport(id, this.getClass().getName());
        // Calls the test method
        //sampleTest(this.getTestSet());
        try {
            Thread.sleep(100);
            //System.out.println(this.getClass().getName() + "#" + id + ": Test body run");
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        // Stops reporting
        //testReport.stopTimers();
        // Prints all the info
        //testReport.printInfo();
    }

    @Override
    public void tearDown() throws Exception {

    }

    /*@Override
    public TestReport getTestReport() {
        return testReport;
    }*/
}
