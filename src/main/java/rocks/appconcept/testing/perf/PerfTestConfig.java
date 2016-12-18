package rocks.appconcept.testing.perf;

/**
 * Created by imeta on 05-Dec-16.
 */
public class PerfTestConfig {
    protected int minThreads = 1;
    protected int maxThreads = 10;
    protected int threadIncrement = 1;
    protected int durationPerThread = 0;
    protected int durationTotal = 0;
    //protected ResultWriter resultWriter = new ResultWriterStdout();
    protected PerfTestFactory testFactory;

    public int getMinThreads() {
        return minThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public int getThreadIncrement() {
        return threadIncrement;
    }

    public int getDurationPerThread() {
        return durationPerThread;
    }

    public int getDurationTotal() {
        return durationTotal;
    }

    public PerfTestFactory getTestFactory() {
        return testFactory;
    }

    @Override
    public String toString() {
        return "PerfTestConfig{" +
                "minThreads=" + minThreads +
                ", maxThreads=" + maxThreads +
                ", threadIncrement=" + threadIncrement +
                ", durationPerThread=" + durationPerThread +
                ", durationTotal=" + durationTotal +
                '}';
    }
}
