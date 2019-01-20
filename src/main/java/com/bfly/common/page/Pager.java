package com.bfly.common.page;

import java.io.Serializable;

/**
 * 简单分页类
 *
 * @author 胡礼波-Andy
 * @2014年11月10日上午9:28:09
 */
public class Pager implements Paginable, Serializable {

    private static final long serialVersionUID = 9084846050836791950L;

    public static final int DEF_COUNT = 10;

    /**
     * 构造器
     *
     * @param pageNo     页码
     * @param pageSize   每页几条数据
     * @param totalCount 总共几条数据
     * @author 胡礼波
     * @2014年11月10日上午9:28:31
     */
    public Pager(int pageNo, int pageSize, long totalCount) {
        setTotalCount(totalCount);
        setPageSize(pageSize);
        setPageNo(pageNo);
        getTotalPage();
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getTotalCount() {
        return totalCount;
    }

    @Override
    public int getTotalPage() {
        Long totalPage = (totalCount + pageSize - 1) / pageSize;
        this.totalPage = totalPage.intValue();
        return this.totalPage;
    }

    private boolean isFirstPage() {
        return pageNo <= 1;
    }

    private boolean isLastPage() {
        return pageNo >= getTotalPage();
    }

    @Override
    public int getNextPage() {
        if (isLastPage()) {
            return pageNo;
        } else {
            return pageNo + 1;
        }
    }

    @Override
    public int getPrePage() {
        if (isFirstPage()) {
            return pageNo;
        } else {
            return pageNo - 1;
        }
    }

    private long totalCount = 0;
    private int pageNo = 1;
    private int totalPage = 0;
    protected int pageSize = DEF_COUNT;

    protected Object data;


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * if totalCount<0 then totalCount=0
     *
     * @param totalCount
     * @author 胡礼波
     * @2014年11月10日上午9:28:31
     */
    public void setTotalCount(long totalCount) {
        if (totalCount < 0) {
            this.totalCount = 0;
        } else {
            this.totalCount = totalCount;
        }
    }

    /**
     * if pageSize< 1 then pageSize=DEF_COUNT
     *
     * @param pageSize
     * @author 胡礼波
     * @2014年11月10日上午9:28:31
     */
    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            this.pageSize = DEF_COUNT;
        } else {
            this.pageSize = pageSize;
        }
    }

    /**
     * if pageNo < 1 then pageNo=1
     *
     * @param pageNo
     * @author 胡礼波
     * @2014年11月10日上午9:28:31
     */
    public void setPageNo(int pageNo) {
        if (pageNo < 1) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
    }
}
