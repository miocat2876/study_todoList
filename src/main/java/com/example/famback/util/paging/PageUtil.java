package com.example.famback.util.paging;

public class PageUtil {
	//rest
	public static int getPage(int totalCount){
		return totalCount <= 0 ? 1 : (totalCount / PageContext.defaultViewRow) + ((totalCount % PageContext.defaultViewRow) > 0 ? 1 : 0);
	}
}
