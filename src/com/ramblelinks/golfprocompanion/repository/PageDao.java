package com.ramblelinks.golfprocompanion.repository;

public interface PageDao {

	public int getPageHitCount(String pageName);
	
	public int updatePageHitCount(String pageName, int pageCount);
	
}
