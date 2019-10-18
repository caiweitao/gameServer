package game.core.example.task.quartz;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobKey;

import game.core.example.task.quartz.job.TestJob1;
import game.core.example.task.quartz.job.TestJob2;
import game.core.task.quartz.SchedulerJobUtils;

/**
* @author caiweitao
* @Date 2019年10月18日
* @Description 
*/
public class QuartzMain {

	public static void main(String[] args) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("hehe", 123);
		map.put("xx", "xxx");
		
//		SchedulerUtils.scheduleJob(TestJob1.class, map, null, null,
//				new Date(System.currentTimeMillis() + 5000), new Date(System.currentTimeMillis() + 15000), "0/3 * * * * ?", true);
	
//		Scheduler schedule = SchedulerUtils.scheduleJob(TestJob1.class,map, "0/1 * * * * ?");
		SchedulerJobUtils.scheduleJob(TestJob1.class,map, "testJob1","test","0/10 * * * * ?");
		SchedulerJobUtils.scheduleJob(TestJob2.class,map, "testJob2","test","0/5 * * * * ?");
		
		Thread.sleep(10*1000);
		
		SchedulerJobUtils.pauseJob("testJob2","test");
		System.out.println("--------暂停 testJob2------");
		
		Thread.sleep(10*1000);
//		
		System.out.println("--------重启 testJob2------");
		SchedulerJobUtils.resumeJob("testJob2","test");
//		
		Thread.sleep(10*1000);
//		SchedulerUtils.scheduler.shutdown();
		System.out.println("--------结束 testJob2------");
		SchedulerJobUtils.deleteJob("testJob2","test");
		
	}
}
