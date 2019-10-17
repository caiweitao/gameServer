package game.core.example.task;

import game.core.task.AbstrackTask;

/**
* @author caiweitao
* @Date 2019年10月17日
* @Description 
*/
public class Test1Task extends AbstrackTask {

	@Override
	public void execute() throws Exception {
		System.out.println("执行一次性任务。");

	}

}
