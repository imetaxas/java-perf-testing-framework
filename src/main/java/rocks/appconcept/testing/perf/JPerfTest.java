package rocks.appconcept.testing.perf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by imeta on 05-Dec-16.
 */
public class JPerfTest {



    public static void run(final PerfTestConfig config) throws Exception {

        //Ensure it will run when the system crashes
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("System terminates");
            }
        });

        List<ExecutorService> finishedExecutors = new ArrayList();

            // Initializes the thread manager
            TestThreadManager testThreadManager = new TestThreadManager(config.maxThreads);

            final ExecutorService exec[] = new ExecutorService[config.maxThreads];
            final SingleTestRunner testRunner[] = new SingleTestRunner[config.maxThreads];

        // Initialize the memory manager
        TestMemoryManager memoryManager = new TestMemoryManager();
        try {

            // Starts monitoring the thread block
            memoryManager.startMonitoring();
            for (int threadCount = config.minThreads; threadCount <= config.maxThreads; threadCount += config.threadIncrement) {
                long now = System.currentTimeMillis();
                for (int i = 0; i < threadCount; i++) {
                    if (exec[i] == null) {
                        // create the executor
                        exec[i] = Executors.newSingleThreadExecutor();
                        // create the test runner
                        testRunner[i] = new SingleTestRunner(i, config.testFactory.create());
                        // start the test runner
                        exec[i].execute(testRunner[i]);
                    }
                }

                // reset the counters
                for (int i = 0; i < threadCount; i++) {
                    testRunner[i].getCounter().set(0);
                }

                final long start = System.currentTimeMillis();
                Thread.sleep(config.durationPerThread);
                final long actualDuration = System.currentTimeMillis() - start;

                // collect the totals
                long samples = 0;
                for (int i = 0; i < threadCount; i++) {
                    samples += testRunner[i].getCounter().get();
                }
                //System.out.println(threadCount + " " + actualDuration + " " + samples);
            }

        } finally {
            // stop threads
            for (SingleTestRunner r : testRunner) {
                if (r != null) {
                    r.stop();
                }
            }
            for (ExecutorService e : exec) {
                if (e != null) {
                    e.shutdown();
                }
            }
            for (ExecutorService e : exec) {
                if (e != null) {
                    e.awaitTermination(5, TimeUnit.SECONDS);
                }
            }
            // Gather thread reports
            for (SingleTestRunner r : testRunner) {
                if (r != null) {
                    r.getTestReport();
                    testThreadManager.addThread(r);
                }
            }
            // Stop monitoring the thread block
            memoryManager.stopMonitoring();

            // Return the results
            // Print all the gathered information

            TestSetResult testSetResult = new TestSetResult(testThreadManager, memoryManager);
            testSetResult.printInfo();
        }
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
        // Print all the gathered information
        TestSetResult testSetResult = new TestSetResult(testThreadManager, memoryManager);
        testSetResult.printInfo();*/
    }

    public static ConfigBuilder newConfigBuilder() {
        return new DefaultConfigBuilder();
    }
}
