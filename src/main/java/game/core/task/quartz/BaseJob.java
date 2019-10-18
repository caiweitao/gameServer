package game.core.task.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/** 定时任务基类，任务通过executeTask方法实现
* @author caiweitao
* @Date 2019年10月18日
* @Description 
*/
public abstract class BaseJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) {
		try {
			executeTask(arg0);
		} catch (JobExecutionException e) {
			//TODO：执行出错处理
			e.printStackTrace();
		}

	}
	
	public abstract void executeTask (JobExecutionContext arg0) throws JobExecutionException;
}
