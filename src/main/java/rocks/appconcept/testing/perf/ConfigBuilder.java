package rocks.appconcept.testing.perf;

/**
 * Created by imeta on 05-Dec-16.
 */
public interface ConfigBuilder {
    ConfigBuilder minThreads(int minThreads);
    ConfigBuilder maxThreads(int maxThreads);
    ConfigBuilder threadIncrement(int threadIncrement);
    @Deprecated ConfigBuilder duration(int duration);
    ConfigBuilder durationPerThread(int duration);
    ConfigBuilder durationTotal(int duration);
    ConfigBuilder testFactory(PerfTestFactory testFactory);
    //ConfigBuilder resultWriter(ResultWriter resultWriter);
    void run() throws Exception;

    @Deprecated
    PerfTestConfig build();
}
