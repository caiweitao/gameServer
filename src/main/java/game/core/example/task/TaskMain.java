package game.core.example.task;

import game.core.task.GametExecutor;

/**
* @author caiweitao
* @Date 2019年10月17日
* @Description 
*/
public class TaskMain {

	public static void main(String[] args) {
		Test1Task task1 = new Test1Task();
		GametExecutor.executeDisposableTask(task1);
		
		Test2Task task2 = new Test2Task();
		GametExecutor.executeScheduledTask(task2, 5000, 2000);
	}
}
