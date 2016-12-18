package rocks.appconcept.testing.perf;

/**
 * Created by imeta on 18-Dec-16.
 */
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;

/**
    CPU usage monitor in pure Java
    Goal: get CPU usage of whole java application (in percents) using pure Java.

    Steps to achieve goal:

    Assumptions: Lets assume we are gathering CPU load each second on 4 cores computer.

    Step 1 : calculate total time available to CPU
    In our case: 1 sec * number of cores (4) = 4 sec. Well, if you can't accept that from one real world second we get four, what probably is a legitimate concern, then think that each second we have 4 "units" of CPU resources.

    Step 2:  calculate how much of CPU time your application (all application threads) used during last second. For example we've got 0.43 sec, in other words, 0.43 units of CPU resources.

    Step 3: try to remember what you've been taught at elementary school:
    4       =  100%
    0.43  =  X %

    So X = (0.43 * 100) / 4 ==  10.75 % of all CPU power during last second.

    Step 4: implement.
 */
public class CpuProfiler extends Thread
{
    private final long interval;
    private final double processorTimeAvailable;
    private boolean continueProfiling = true;
    private static final int MILLISEC_IN_SEC = 1000;
    private static final int NANOSEC_IN_SEC = 1000000000;
    private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    private Map<Long, Stat> threadStats = new HashMap<Long, Stat>();
    private UpdateHandler updateHandler;

    private static class Stat
    {
        long prevCpuTime;
        long currCpuTime;
    }

    /** CPU load percentage will be passed to onUpdate method */
    public interface UpdateHandler
    {
        /**
         * @param cpuLoad
         *            CPU load in percents.
         */
        void onUpdate(double cpuLoad);
    }

    /**
     * Constructor.
     *
     * @param interval
     *            how often update stats, in milliseconds.
     */
    public CpuProfiler(long interval)
    {
        super("Thread timing monitor");
        this.interval = interval;
        setDaemon(true);

        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        int processorsCount = osMXBean.getAvailableProcessors();

        processorTimeAvailable = (double) this.interval / MILLISEC_IN_SEC * processorsCount;
    }

    /** Thread meat. */
    @Override
    public void run()
    {
        while (continueProfiling)
        {
            updateStats();

            double usedCpu = (double) getUsedCpuTimeForLastPeriod() / NANOSEC_IN_SEC;
            double cpuLoad = (double) usedCpu * 100 / processorTimeAvailable;

            if (updateHandler != null)
            {
                updateHandler.onUpdate(cpuLoad);
            }

            try
            {
                Thread.sleep(interval);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update stats, current monitor thread is also included into stats.
     */
    private void updateStats()
    {
        long[] ids = threadMXBean.getAllThreadIds();
        for (long id : ids)
        {
            long cpuTime = threadMXBean.getThreadCpuTime(id);
            if (cpuTime == -1)// thread is not alive
            {
                threadStats.remove(id);
                continue;
            }

            Stat stat = threadStats.get(id);
            if (stat == null)// thread is not monitored yet
            {
                stat = new Stat();
                stat.prevCpuTime = cpuTime;
                stat.currCpuTime = cpuTime;
                threadStats.put(id, stat);
            }
            else
            // thread is already monitored
            {
                stat.prevCpuTime = stat.currCpuTime;
                stat.currCpuTime = cpuTime;
            }
        }
    }

    /** Get total used CPU time in nanoseconds for last interval. */
    private long getUsedCpuTimeForLastPeriod()
    {
        long time = 0;
        for (Stat threadStat : threadStats.values())
        {
            time += threadStat.currCpuTime - threadStat.prevCpuTime;
        }

        return time;
    }

    public void setUpdateHandler(UpdateHandler updateHandler)
    {
        this.updateHandler = updateHandler;
    }

    public void stopProfiling()
    {
        this.continueProfiling = false;
    }
}
