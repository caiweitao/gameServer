package game.core.example.task.quartz.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import game.core.task.quartz.BaseJob;

/**
* @author caiweitao
* @Date 2019年10月18日
* @Description 
*/
public class TestJob1 extends BaseJob {
	
	private String hehe;
	
	

	public String getHehe() {
		return hehe;
	}



	public void setHehe(String hehe) {
		this.hehe = hehe;
	}



	@Override
	public void executeTask(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println(new Date().toGMTString()+" Job test ......"+arg0.getJobDetail().getJobDataMap().get("xx")+hehe);
	}

}
