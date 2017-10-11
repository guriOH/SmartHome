package com.hoon.smart_home.serial;

import java.nio.charset.Charset;

import com.hoon.smart_home.serial.socket.DataWorkerPool;

public abstract class AbstractDataWorker implements Runnable {
	
	public enum WorkerState {
		NON_ACTIVE, WORKING, WAITING
	}
	
	private String _workerName;
	private boolean _isAvailable;
	private boolean _requiredReturn;
	private boolean _isStopped;
	private WorkerState _workerState = WorkerState.NON_ACTIVE;
	private DataWorkerPool _workerPool;
	private Charset _encoding = null;
	private Charset _utf8Encoding = Charset.forName("utf-8");
	
	
	public AbstractDataWorker(String p_workerName, DataWorkerPool p_workerPool) {
		_workerName = p_workerName;
		_workerPool = p_workerPool;
	}
	
	public String getName() {
		return _workerName;
	}
	
	protected Charset getUTF8Encoding() {
		return _utf8Encoding;
	}

	protected void setUTF8Encoding(Charset _utf8Encoding) {
		this._utf8Encoding = _utf8Encoding;
	}

	protected Charset getEncoding() {
		return _encoding;
	}

	protected void setEncoding(Charset _encoding) {
		this._encoding = _encoding;
	}
	
	public final boolean isAvailable() {
		if (!_isAvailable){
			synchronized (this) {
				try {
					while(true){	
						if(_isAvailable){
							break;
						} else {
							this.wait(1000);
						}
					}
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	private void setAvailable() {
		if(!_isAvailable) {
			synchronized (this) {
				_isAvailable = true;
				this.notifyAll();
			}
		}
	}
	
	public final void setRequiredReturn(boolean p_requiredReturn) {
		this._requiredReturn = p_requiredReturn;
	}
	
	public WorkerState getState() {
		return _workerState;
	}
	
	public void stop() {
		_isStopped = true;
		synchronized (this) {
			this.notify();
		}
	}
	
	public final void run() {
		while(true) {
			long l_start = 0;
			long l_end = 0;
			try {
				
				synchronized (this) {
					if(_requiredReturn) {
						_workerPool.putAvailableWorker(this);
					}
					if(_isStopped) {
						break;
					}
					setAvailable();
					
					_workerState = WorkerState.WAITING;
					this.wait();
					_workerState = WorkerState.WORKING;
				}
				
				if(_isStopped) {
					break;
				}
			} catch(Exception e) {
			}
			finally {
				
			}
		}
		_workerState = WorkerState.NON_ACTIVE;
	}
	
	private void doTask(String p_task) {
		
		try {
			
			
		} catch(Exception e) {
		} finally {
		}
	}
	
	protected abstract void returnResult(String p_task);
	
}
