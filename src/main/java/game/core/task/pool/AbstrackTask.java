package game.core.task.pool;


public abstract class AbstrackTask implements Runnable, ITask {

	private String name;

	public AbstrackTask() {}
	
	public AbstrackTask(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		try {
			execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
