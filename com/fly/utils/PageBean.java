package com.fly.utils;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 1.起始索引(计算)
    private int startIndex;

    // 2.每页显示记录数(指定写死)
    private int pageSize;

    // 3.当前页面(用户要查看的页码)（前台获取）
    private int pageNumber;

    // 4.总记录数(查询得到)
    private int totalRecord;

    // 5.总页码(计算)
    private int totalPage;

    // 6.存放查询出来所有商品信息的结果对象
    private List<T> result;

    public int getStartIndex() {
        return (this.getPageNumber() - 1) * this.getPageSize();
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return this.getTotalRecord() % this.getPageSize() == 0 ? (this.getTotalRecord() / this.getPageSize())
                : (this.getTotalRecord() / this.getPageSize() + 1);
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public PageBean() {

    }
    
    public int getSize() {
    	return this.result.size();
    }

	@Override
	public String toString() {
		return "PageBean [startIndex=" + startIndex + ", pageSize=" + pageSize + ", pageNumber=" + pageNumber
				+ ", totalRecord=" + totalRecord + ", totalPage=" + totalPage + ", result=" + result + "]";
	}
    
    
}
