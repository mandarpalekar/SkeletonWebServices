package com.mandar.springboot.example.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncResponse<V> implements Future<V> {
	
	private V value;
	private boolean isDone = false;
	private boolean isCompletedWithException = false;

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean complete(V val){
		this.value = val;
		this.isDone = true;
		return true;
	}
	
	public boolean completeWithException(Throwable ex){
		this.value = null;
		this.isDone = true;
		this.completeWithException(ex);
		this.isCompletedWithException = true;
		return true;
	}
}
