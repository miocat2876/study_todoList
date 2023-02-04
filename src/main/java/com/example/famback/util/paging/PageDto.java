package com.example.famback.util.paging;

public interface PageDto {

	public final int defaultViewData = 20;
	public int getParamCurrentPage();
	public int getParamViewData();
	public String getParamSearchCondition();
	public String getParamSearchValue();
	public void setParamCurrentPage(int getCurrentPage);
	public void setParamSearchCondition(String searchCondition);
	public void setParamSearchValue(String searchValue);

	default public int CheckCurrentPage() {
		return getParamCurrentPage() == 0 ? 1 : getParamCurrentPage();
	}
	default public int CheckParamViewData() {
		return getParamViewData() == 0 ? defaultViewData : getParamViewData();
	}
	default public int getStartData() {
		return (CheckCurrentPage() - 1) * CheckParamViewData();
	}
	default public int getEndData() {
		return CheckCurrentPage() * CheckParamViewData();
	}
	default public int getViewData() {
		return CheckParamViewData();
	}
	default public String getSearchCondition() {
		return getParamSearchCondition();
	}
	default public String getSearchValue() {
		return getParamSearchValue();
	}
}
