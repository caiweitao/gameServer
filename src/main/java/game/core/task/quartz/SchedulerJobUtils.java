package game.core.task.quartz;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author caiweitao
 * @Date 2019年10月18日
 * @Description 
 */
public class SchedulerJobUtils {

	private static final Scheduler scheduler = createScheduler();
	
	/**
	 * 创建调度器Scheduler
	 * @return
	 */
	public static Scheduler createScheduler () {
		try {
			return new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行一个job
	 * @param jobClass job
	 * @param jobParams 参数
	 * @param name job 名
	 * @param group job分组
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param cron cron表达式
	 * @param startNow 是否现在开始
	 */
	public static void scheduleJob (
			Class<? extends BaseJob> jobClass,
			Map<String,Object> jobParams,
			String name, 
			String group,
			Date startDate,
			Date endDate,
			String cron,
			boolean startNow) {
		
		if (jobClass == null) {
			return ;
		}
		try {
			JobDetail jobDetail = buildJobDetail(jobClass, jobParams, name, group);
			CronTrigger cronTrigger = buildCronTrigger(startDate, endDate, cron, startNow);
			scheduler.scheduleJob(jobDetail, cronTrigger);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void scheduleJob (
			Class<? extends BaseJob> jobClass,
			Map<String,Object> jobParams,
			String name, 
			String group,
			String cron) {
		scheduleJob(jobClass, jobParams, name, group, null, null, cron, false);
	}
	
	public static void scheduleJob (
			Class<? extends BaseJob> jobClass,
			String name, 
			String group,
			String cron) {
		scheduleJob(jobClass, null, name, group, null, null, cron, false);
	}
	
	/**
	 * 暂停job
	 * @param name job 名
	 * @param group job 组
	 * @return
	 */
	public static boolean pauseJob (String name ,String group) {
		try {
			scheduler.pauseJob(new JobKey(name,group));
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 重启job
	 * @param name job 名
	 * @param group job 组
	 * @return
	 */
	public static boolean resumeJob (String name ,String group) {
		try {
			scheduler.resumeJob(new JobKey(name,group));
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 从调度程序和任何相关触发器中删除已标识的作业。
	 * @param name job 名
	 * @param group job 组
	 * @return
	 */
	public static boolean deleteJob (String name ,String group) {
		try {
			scheduler.deleteJob(new JobKey(name,group));
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 暂停排定程序的触发器的触发，并清除与排定程序关联的所有资源。无法重新开始调度程序。
	 * @param waitForJobsToComplete
	 * @return
	 */
	public static boolean shutdown (boolean waitForJobsToComplete) {
		try {
			scheduler.shutdown(waitForJobsToComplete);
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean shutdown () {
		try {
			scheduler.shutdown();
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 传递基本数据类型参数
	 * @param jobBuilder
	 * @param params
	 */
	private static void conversionType (JobBuilder jobBuilder,Map<String,Object> params) {
		if (params != null && params.size() > 0) {
			for (Entry<String,Object> en :params.entrySet()) {
				if (en.getValue() instanceof String) {
					jobBuilder.usingJobData(en.getKey(), en.getValue().toString());
					return;
				}
				
				if (en.getValue() instanceof Integer) {
					jobBuilder.usingJobData(en.getKey(), Integer.valueOf(en.getValue().toString()));
					return;
				}
				
				if (en.getValue() instanceof Long) {
					jobBuilder.usingJobData(en.getKey(), Long.valueOf(en.getValue().toString()));
					return;
				}
				
				if (en.getValue() instanceof Float) {
					jobBuilder.usingJobData(en.getKey(), Float.valueOf(en.getValue().toString()));
					return;
				}
				
				if (en.getValue() instanceof Double) {
					jobBuilder.usingJobData(en.getKey(), Double.valueOf(en.getValue().toString()));
					return;
				}
				
				if (en.getValue() instanceof Boolean) {
					jobBuilder.usingJobData(en.getKey(), Boolean.valueOf(en.getValue().toString()));
					return;
				}
			}
		}
	}
	
	/**
	 * 构建CronTrigger
	 * @param startDate 开始时间 可为空
	 * @param endDate 结束时间 可为空
	 * @param cron cron表达式，不能为空
	 * @param startNow 是否立即开始 true 立即开始
	 * @return
	 */
	public static CronTrigger buildCronTrigger (
			Date startDate,
			Date endDate,
			String cron,
			boolean startNow) {
		if (cron == null) {
			System.err.print("cron 表达式不能为空！！！");
			return null;
		}
		
		TriggerBuilder<Trigger> newTrigger = TriggerBuilder.newTrigger();
		if (startDate != null) newTrigger.startAt(startDate);
		if (endDate != null) newTrigger.endAt(endDate);
		if (startNow) newTrigger.startNow();//立即生效
		
		return newTrigger.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
	}
	
	public static JobDetail buildJobDetail (
			Class<? extends BaseJob> jobClass,
			Map<String,Object> jobParams,
			String name, 
			String group) {
		JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
		//传递参数给job
		conversionType(jobBuilder, jobParams);
		
		//设置 name 和 group
		if (name != null && group != null) {
			jobBuilder.withIdentity(name, group);
		}

		return jobBuilder.build();
	}
	
}
