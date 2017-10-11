package com.hoon.smart_home.serial;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ThreadWorker implements Runnable {
	private final Object _target;
	private Method _method=null;
	
	public ThreadWorker(Object p_target, String p_methodName) throws NoSuchMethodException, SecurityException {
		if (p_target != null){		
			this._target = p_target;
			this._method = p_target.getClass().getMethod(p_methodName);	
		} else {
			this._target = null;
		}
	}
	
	@Override
	public void run() {
		boolean l_isBreak = true;
		while(true) {
			l_isBreak = true;
			try {
				if (_method !=null) {
					_method.invoke(_target);
				}
			} catch (InvocationTargetException e) {	
				l_isBreak = false;					
			} catch (Exception e) {				
			}
			if (l_isBreak) {
				break;
			}
		}	
	}

}
