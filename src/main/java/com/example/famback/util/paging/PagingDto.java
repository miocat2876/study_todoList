package com.example.famback.util.paging;

import lombok.*;

@Getter
@Setter
public class PagingDto {

	private int currentPage;
	private int viewRow;
	private int viewPage;
	private String searchCondition;
	private String searchValue;
	private int totalRowCount;
	private int defaultViewRow  = PageContext.defaultViewRow;
	private int defaultViewPage = PageContext.defaultViewPage;
	//private int totalViewCount;

	public int getCurrentPage() {
		return currentPage <= 0 ? 1 : currentPage;
	}
	public int getViewRow() {
		return viewRow <= 0 ? PageContext.defaultViewRow : viewRow;
	}
	public int getViewPage() {
		return viewPage <= 0 ? PageContext.defaultViewPage : viewPage;
	}
	public int getRowStart() {
		return ((getCurrentPage() - 1) * getViewRow());
	}
	public int getRowEnd() {
		return getViewRow();
	}
	public int getPageStart() {
		return getCurrentPage();
	}
//	public int getTotalViewCount() {
//		return totalRowCount <= 0 ? 1 : (totalRowCount / defaultViewRow);
//	}
	public int getPageEnd() {
		if(isLastPage()){
			return getTotalRowCount() / getViewRow();
		}
		return ((getCurrentPage() / getViewPage()) + 1) * getViewPage();
	}
	public boolean isLastPage(){
		return (getCurrentPage() * getViewRow()) >= getTotalRowCount();
	}

}
