import org.apache.log4j.Logger;
import org.junit.Test;
import rocks.appconcept.testing.perf.*;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by ioannis.metaxas on 2015-11-10.
 * Its a performance test for the soap client
 * Different test data sets running in multiple threads can be used for running performance tests.
 *
 * create config
 * PerfTestConfig config = JPerfTest.newConfigBuilder()
 * .minThreads(1)
 * .maxThreads(10)
 * .duration(100)
 * .testFactory(() -> new EmptyTest())
 * .build();
 *
 * // run test
 * JPerfTest.run(config);
 *
 */
public class SoapClientTest {
    final static Logger LOGGER = Logger.getLogger(SoapClientTest.class);

    @Test
    public void testNewSampleTest() throws Exception {

        long now = System.currentTimeMillis();

        CpuProfiler profiler = new CpuProfiler(100);// get update each 1 sec
        profiler.setUpdateHandler(new CpuProfiler.UpdateHandler()
        {
            public void onUpdate(double cpuLoad)
            {
                DecimalFormat cpuLoadFormat = new DecimalFormat("#.##");
                String cpuLoadFormatted = cpuLoadFormat.format(cpuLoad);
                System.out.println("CPU Load: " + cpuLoadFormatted + "%");
            }
        });
        profiler.start();

        PerfTestConfig config = JPerfTest.newConfigBuilder()
                .minThreads(1)
                .maxThreads(10)
                .duration(1)
                .threadIncrement(1)
                .testFactory(() -> new TestSet())
                .build();
        JPerfTest.run(config);

        System.out.println("Full run: " + (System.currentTimeMillis() - now) + " ms");
    }

    /*@Test
    public void testSampleTest() throws Exception {
        TestSet testSet1 = new TestSet();
        /*testSet1.setNumberOfThreads(5);
        testSet1.setMarketId(521);
        testSet1.setDesiredDealerDate("20120401");
        testSet1.setVehicleTypeId(2);
        testSet1.setRunMethodClass(SampleTestThread.class);*/

        TestSet testSet2 = new TestSet();
        /*testSet2.setNumberOfThreads(5);
        testSet2.setMarketId(521);
        testSet2.setDesiredDealerDate("20141001");
        testSet2.setVehicleTypeId(2);
        testSet2.setRunMethodClass(SampleTestThread.class);

        ArrayList<TestSet> testSets = new ArrayList<TestSet>();
        testSets.add(testSet1);
        testSets.add(testSet2);

        // Run the method and get the results
        TestSetResult testSetResult = runTestSets(testSets);
        // Print all the gathered information
        testSetResult.printInfo();

        // Do tests on the retrieved data
        assertThat("Linear time, 2 second per iteration", testSetResult.getThreadManager().getSumWallClockTime(), lessThanOrEqualTo(Long.valueOf(testSetResult.getThreadManager().getThreads().size() * 2000)));
        assertEquals(testSetResult.getThreadManager().getThreads().size(), testSetResult.getThreadManager().getThreads().size());
    }*/

    /**
     * Runs the given test sets
     *
     * @param testSets
     * @return
     * @throws Exception
     */
    /*public TestSetResult runTestSets(ArrayList<TestSet> testSets) throws Exception, InterruptedException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // Initializes the thread manager
        TestThreadManager testThreadManager = new TestThreadManager();
        // Adds the threads in the manager
        /*for (TestSet testSet : testSets) {
            for (int i = 0; i < testSet.getNumberOfThreads(); i++) {
                if (testSet.getRunMethodClass().equals(SampleTestThread.class)) {
                    SampleTestThread sampleTestThread = new SampleTestThread(testSet) {
                        @Override
                        public void run() {
                            try {
                                // Starts reporting
                                this.testReport = new TestReport(this.getId(), this.getName());
                                // Calls the test method
                                sampleTest(this.getTestSet());
                                // Stops reporting
                                testReport.stopTimers();
                                // Prints all the info
                                testReport.printInfo();
                            } catch (RemoteException re) {
                                //LOGGER.error("Remote exception in sampleTest(): ", re);
                            }
                        }
                    };
                    sampleTestThread.setName("Thread_" + i);
                    testThreadManager.addThread(sampleTestThread);
                }
            }
        }*/

        // Initialize the memory manager
        /*TestMemoryManager memoryManager = new TestMemoryManager();
        // Starts monitoring the thread block
        memoryManager.startMonitoring();
        for (TestThread testThread : testThreadManager.getThreads()) {
            ((TestThreadImpl) testThread).start();
            ((TestThreadImpl) testThread).join();
        }
        // Stop monitoring the thread block
        memoryManager.stopMonitoring();

        // Return the results
        //return new TestSetResult(testThreadManager, memoryManager);
    }*/

    /**
     * A sample test method which tests a basic scenario
     *
     * @param testSet
     * @throws RemoteException
     */
    private void sampleTest(TestSet testSet) throws RemoteException {

    }

}