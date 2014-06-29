package com.ramblelinks.golfprocompanion.domain;

import java.util.List;

import org.springframework.validation.ObjectError;

public class JqGridData<T> {

  /** Total number of pages */
  private int total;
  /** The current page number */
  private int page;
  /** Total number of records */
  private int records;
  /** The actual data */
  private List<T> rows;
  
  private List<ObjectError> br;

  public JqGridData(int total, int page, int records, List<T> rows) {
    this.total = total;
    this.page = page;
    this.records = records;
    this.rows = rows;
  }
  
  public JqGridData(int total, int page, int records, List<T> rows, List<ObjectError> br) {
	    this.total = total;
	    this.page = page;
	    this.records = records;
	    this.rows = rows;
	    this.br = br;
	  }

  public int getTotal() {
    return total;
  }

  public int getPage() {
    return page;
  }

  public int getRecords() {
    return records;
  }

  public List<T> getRows() {
    return rows;
  }

public void setBr(List<ObjectError> br) {
	this.br = br;
}

public List<ObjectError> getBr() {
	return br;
}
}
