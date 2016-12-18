package rocks.appconcept.testing.perf;

/**
 * Created by imeta on 05-Dec-16.
 */
public interface PerfTest {

    //public TestReport getTestReport();

    /**
     * The setUp() method is called ONCE ONLY for each instance of the PerfTest. The setUp() method can
     * be used to initialise resources needed by the test. The cost of the setUp() call is not included
     * in the timings collected by JPerf.
     *
     * @throws Exception
     */
    public void setUp() throws Exception;

    /**
     * Implement the code you want to test for performance and scalability in this method. JPerf will
     * repeatedly call this method for the testDuration.
     *
     * @throws Exception
     */
    public void test(long id) throws Exception;

    /**
     * The tearDown() method is called ONCE ONLY for each instance of the PerfTest at the end of testing.
     *
     * @throws Exception
     */
    public void tearDown() throws Exception;

}
