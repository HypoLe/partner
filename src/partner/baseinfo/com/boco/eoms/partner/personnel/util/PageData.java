package com.boco.eoms.partner.personnel.util;
/**
 * <p>
 * Title: hql查询 封装
 * </p>
 * <p>
 * Description: hql查询 封装
 * </p>
 * <p>
 *    2012-8-1  下午05:07:42
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */


import java.util.List;

public class PageData<T> {
	/**
	 * 默认 每页记录数
	 */
	public static final int DEF_COUNT = 15;  

	/**
	 * 总记录数
	 */
	protected int totalCount = 0;
	protected int pageSize = 15;
	/**
	 * 页号
	 */
	protected int pageIndex = 1;
	/**
	 * 当前页  数据
	 */
	private List<T> list;
	public PageData() {
	}

	public PageData(int pageIndex, int pageSize, int totalCount) {
		if (totalCount <= 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
		if (pageSize <= 0) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
		if (pageIndex <= 0) {
			this.pageIndex = 1;
		} else {
			this.pageIndex = pageIndex+1;
		}
		if ((this.pageIndex - 1) * this.pageSize >= totalCount) {
			this.pageIndex = 1;
		}
	}

	/**
	 * 调整分页参数，使合理化
	 */
	public void adjustPage() {
		if (totalCount <= 0) {
			totalCount = 0;
		}
		if (pageSize <= 0) {
			pageSize = DEF_COUNT;
		}
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		if ((pageIndex - 1) * pageSize >= totalCount) {
			pageIndex = 1;
		}
	}

	public int getpageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalCount % pageSize != 0 || totalPage == 0) {
			totalPage++;
		}
		return totalPage;
	}

	public boolean isFirstPage() {
		return pageIndex <= 1;
	}

	public boolean isLastPage() {
		return pageIndex >= getTotalPage();
	}

	public int getNextPage() {
		if (isLastPage()) {
			return pageIndex;
		} else {
			return pageIndex + 1;
		}
	}

	public int getPrePage() {
		if (isFirstPage()) {
			return pageIndex;
		} else {
			return pageIndex - 1;
		}
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getFirstResult() {
		return (pageIndex - 1) * pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
	
}
