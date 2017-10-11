package com.hoon.smart_home.serial.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hoon.smart_home.serial.AbstractDataWorker;
import com.hoon.smart_home.serial.AbstractDataWorker.WorkerState;

public class DataWorkerPool {

	private final List<AbstractDataWorker> _created;
	private final List<AbstractDataWorker> _available;
	private int _availableSize = 0;
	private int _createdSize = 0;
	private final int _maxThreads;
	private final Object lockObject = new Object();
	private Class<?> _workerClass;
	private static final int DEFAULT_WAIT_TIME = 100;
	
	public DataWorkerPool(int p_maxThreads, Class<?> p_workerClass) {
		this._created = new ArrayList<AbstractDataWorker>();
		this._available = new ArrayList<AbstractDataWorker>();
		this._maxThreads = p_maxThreads;
		this._workerClass = p_workerClass;
	}
	
	public int getCreatedSize() {
		return this._createdSize;
	}
	
	public int getAvailableSize() {
		return this._availableSize;
	}
	
	public AbstractDataWorker getWorker() {
		AbstractDataWorker l_worker = null;
		
		synchronized (lockObject) {
			try {
				if(this._availableSize > 0) {
					l_worker = this._available.remove(0);
					this._availableSize = this._available.size();
					l_worker.isAvailable();
				} else if(this._maxThreads > _createdSize) {
					l_worker = (AbstractDataWorker) this._workerClass.getConstructor(String.class, DataWorkerPool.class).newInstance("eDataRealm", this);
					this._created.add(l_worker);
					this._createdSize = this._created.size();
					Thread l_thread = new Thread(l_worker);
					l_thread.start();
					l_worker.isAvailable();
					l_worker.setRequiredReturn(true);
				} else {
					while(true) {
						if(this._availableSize > 0) {
							l_worker = this._available.remove(0);
							this._availableSize = this._available.size();
							l_worker.isAvailable();
							break;
						}
					}
					lockObject.wait(DEFAULT_WAIT_TIME);
				}
			} catch(Exception e) {
			} 
		}
		
		return l_worker;
	}
	
	public void putAvailableWorker(AbstractDataWorker p_worker) {
		synchronized (lockObject) {
			if(this._created.contains(p_worker) && !this._available.contains(p_worker)) {
				this._available.add(p_worker);
				this._availableSize = this._available.size();
				lockObject.notifyAll();
			}
		}
	}
	
	public Map<String, WorkerState> getThreadState() {
		Map<String, WorkerState> l_state = new HashMap<String, WorkerState>();
		try {
			for(AbstractDataWorker l_worker : this._created) {
				l_state.put(l_worker.getName(), l_worker.getState());
			}
		} catch(Exception e) {
		}
		
		return l_state;
	}
}
