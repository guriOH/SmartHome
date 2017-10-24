package com.hoon.smart_home.service.interfaces;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hoon.smart_home.utility.APIMETHOD;

public abstract class AbstractService {
	private Logger _logger = LogManager.getLogger(AbstractService.class.getName());
	private String _rootInstanceID;
	private String _parentInstanceID;
	private String _instanceID;
	
	public AbstractService() {
		
	}
	
	public void initialize() {
		
	}

	public String get_rootInstanceID() {
		return _rootInstanceID;
	}

	public void set_rootInstanceID(String _rootInstanceID) {
		this._rootInstanceID = _rootInstanceID;
	}

	public String get_parentInstanceID() {
		return _parentInstanceID;
	}

	public void set_parentInstanceID(String _parentInstanceID) {
		this._parentInstanceID = _parentInstanceID;
	}

	public String get_instanceID() {
		return _instanceID;
	}

	public void set_instanceID(String _instanceID) {
		this._instanceID = _instanceID;
	}
	
	public final void execute(String task) {
		try {
			switch (APIMETHOD.valueOf(task)) {
			case CREATE:
				break;
			case INSERTDATA:
				break;
			case GETDATA:
				break;
			}
		} catch (Exception e) {

		}
	}
	
	protected abstract void create();
	
	protected abstract String getData();
	
	protected abstract void insertData();
}
