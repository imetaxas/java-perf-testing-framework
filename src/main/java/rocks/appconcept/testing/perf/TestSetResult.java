package rocks.appconcept.testing.perf;

import java.text.DecimalFormat;

/**
 * Created by ioannis.metaxas on 2015-11-11.
 * The result information retrieved after running a test
 */
public class TestSetResult {

    private static DecimalFormat dec2 = new DecimalFormat("#.##");

    private TestThreadManager threadManager;
    private TestMemoryManager memoryManager;

    public TestSetResult(TestThreadManager threadManager, TestMemoryManager memoryManager) {
        this.threadManager = threadManager;
        this.memoryManager = memoryManager;
    }

    /**
     * Prints all the current gathered information
     */
    public void printInfo() {
        System.out.println("\nSummary:");
        System.out.println("-----------------------------------------------------");
        System.out.println("Median Wall clock time: " + threadManager.getSumWallClockTime() + " ms ~ " + TestReport.convertFromMillisToSec(threadManager.getSumWallClockTime()) + " sec" + " (is the real-world elapsed time experienced by a user waiting for a task to complete)");
        System.out.println("Median User time: " + threadManager.getSumUserTimeNano() + " ns ~ " + TestReport.convertFromNanoToSec(threadManager.getSumUserTimeNano()) + " sec" + " (is the time spent running the application's own code)");
        System.out.println("Median System time: " + threadManager.getSumSystemTimeNano() + " ns ~ " + TestReport.convertFromNanoToSec(threadManager.getSumSystemTimeNano()) + " sec" + " (is the time spent running OS code on behalf of the application (such as for I/O))");
        System.out.println("Median CPU time: " + (threadManager.getSumUserTimeNano() + threadManager.getSumSystemTimeNano()) + " ns ~ " + TestReport.convertFromNanoToSec(threadManager.getSumUserTimeNano() + threadManager.getSumSystemTimeNano()) + " sec" + " (is user time plus system time. It's the total time spent using a CPU for the application)\n");

        System.out.println("\nMemory usage every " + memoryManager.getTimePeriod() + " ms" + " (Max Heap Memory: " + memoryManager.getMaxHeapMemory() + " MB)");
        System.out.println("-----------------------------------------------------");
        for (int i = 0; i < memoryManager.getMemoryUsage().size(); i++) {
            long timePeriodSlot = (memoryManager.getTimePeriod() * i);
            String timeStr = "Time: " + timePeriodSlot;
            String usageStr = "Usage: " + memoryManager.getMemoryUsage().get(i) + " MB";
            String percStr = "Perc: " + dec2.format(memoryManager.getMemoryUsagePerc(memoryManager.getMemoryUsage().get(i))) + "%";
            String printStr = timeStr + "\t" + usageStr + "\t" + percStr;
            SingleTestRunner testThread = null; //threadManager.getTestThreadFromTimeRun(timePeriodSlot);
            String runningThreadStr = "";
            if (testThread != null) {
                runningThreadStr = "Running thread: " + testThread.test.getClass().getName() + "#" + testThread.getId();
            }
            System.out.println(printStr + "\t" + runningThreadStr);
        }
    }
}
