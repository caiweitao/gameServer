package game.core.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

public abstract class SessionFactory<K extends Serializable, S extends ChannelSession<K>> {

	private final ConcurrentHashMap<K, S> keySessionMap = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Channel, S> channelSessionMap = new ConcurrentHashMap<>();
	
	public abstract ChannelSession<K> newChannelSession(Channel channel);
	
	public void register (ChannelSession<? extends Serializable> cs) {
		this.channelSessionMap.put(cs.getChannel(), (S)cs);
	}
	
	public void bind(K owner, S session) {
		session.setOwner(owner);
		this.keySessionMap.put(owner, session);
	}
	
	public void unbind(Channel channel) {
		S s = channelSessionMap.get(channel);
		this.channelSessionMap.remove(channel);
		if (s != null && s.getOwner() != null) {
			this.keySessionMap.remove(s.getOwner());
		}
	}
	
	public S getSession(K key) {
		return (S) this.keySessionMap.get(key);
	}
	
	public S getSession(Channel channel) {
		return (S) this.channelSessionMap.get(channel);
	}

	public boolean online(K key) {
		return this.keySessionMap.containsKey(key);
	}

	public int getOnlineNumber() {
		return this.keySessionMap.size();
	}

	public List<K> getOnlines() {
		List<K> list = new ArrayList<>(this.keySessionMap.size());
		Iterator<?> var3 = this.keySessionMap.entrySet().iterator();

		while (var3.hasNext()) {
			Entry<K, S> e = (Entry) var3.next();
			list.add((K) e.getKey());
		}
		return list;
	}

	public List<S> getChannelSessions() {
		return new ArrayList<>(this.keySessionMap.values());
	}
}
