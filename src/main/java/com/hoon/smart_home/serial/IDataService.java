package com.hoon.smart_home.serial;

import com.hoon.smart_home.common.exeception.AnalysisException;

public interface IDataService {
	public void start() throws AnalysisException;
	public void initialize() throws AnalysisException;
}
