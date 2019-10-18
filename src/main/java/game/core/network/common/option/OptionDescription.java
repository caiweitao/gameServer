package game.core.network.common.option;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelOption;

public class OptionDescription {

	private final ChannelOption<?> option;
	private final String title;
	private List<String[]> optionDescriptions;

	protected OptionDescription(ChannelOption<?> option, String title) {
		this.option = option;
		this.title = title;
	}

	public void addOptionDescription(String name, String desc) {
		if (this.optionDescriptions == null) {
			this.optionDescriptions = new ArrayList<>();
		}

		this.optionDescriptions.add(new String[]{name, desc});
	}

	public ChannelOption<?> getOption() {
		return this.option;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription(String name) {
		if (this.optionDescriptions != null) {
			int i = 0;

			for (int len = this.optionDescriptions.size(); i < len; ++i) {
				if (name.indexOf(((String[]) this.optionDescriptions.get(i))[0]) > -1) {
					return ((String[]) this.optionDescriptions.get(i))[1];
				}
			}
		}

		return null;
	}
}
