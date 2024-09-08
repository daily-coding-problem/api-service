package com.dcp.api_service.service;

@FunctionalInterface
public interface BatchProcessor<T> {
	void process(T item);
}
