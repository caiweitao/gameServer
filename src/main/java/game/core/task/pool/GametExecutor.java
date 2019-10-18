package game.core.task.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** 游戏任务调度器
* @author caiweitao
* @Date 2019年10月17日
* @Description 
*/
public class GametExecutor {
	
	/**
	 * 执行一次性任务线程池，通过实现AbstrackTask，传递给executeDisposableTask(AbstrackTask task)方法
	 */
	public static ExecutorService disposablePool = Executors.newCachedThreadPool();
	private static ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	/**
	 * 执行一次性任务
	 * @param task
	 */
	public static void executeDisposableTask (final AbstrackTask task) {
		if (task == null) {
			System.err.println("!!!任务为null");
			return;
		}
		disposablePool.execute(task);
	};
	
	/**
	 *  创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；
	 *  也就是将在 initialDelay 后开始执行，然后在 initialDelay+period 后执行，接着在 initialDelay + 2 * period 后执行，依此类推。
	 *  时间单位：毫秒
	 * @param task
	 * @param initialDelay
	 * @param period
	 */
	public static void executeScheduledTask (
			final AbstrackTask task,
			long initialDelay,
            long period) {
		if (task == null) {
			System.err.println("!!!任务为null");
			return;
		}
		scheduledPool.scheduleAtFixedRate(task,
				initialDelay, period, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 关闭线程池
	 */
	public static void shutdown (ExecutorService...pools) {
		for (ExecutorService pool:pools) {
			if (pool != null) {
				pool.shutdown();
			}
		}
	}
}
