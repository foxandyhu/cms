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
    private static ThreadLocal<Pager> pagerContext = new ThreadLocal<>();

    /**
     * 把分页对象放到threadLocal中
     *
     * @param pager
     * @author 胡礼波-Andy
     * @2014年11月10日上午9:28:20
     */
    public static void setPager(Pager pager) {
        pagerContext.set(pager);
    }

    /**
     * 得到分页对象
     *
     * @return
     * @author 胡礼波-Andy
     * @ 2014年11月10日上午9:28:25
     */
    public static Pager getPager() {
        return pagerContext.get();
    }

    /**
     * 移除threadLocal中的分页对象
     *
     * @author 胡礼波-Andy
     * @ 2014年11月10日上午9:28:31
     */
    public static void remove() {
        pagerContext.remove();
    }

    /**
     * 检查页码 checkPageNo
     *
     * @param pageNo
     * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
     * @author 胡礼波
     * @ 2014年11月10日上午9:28:31
     */
    public static int cpn(Integer pageNo) {
        return (pageNo == null || pageNo < 1) ? 1 : pageNo;
    }

    public Pager() {
    }

    /**
     * 构造器
     *
     * @param pageNo     页码
     * @param pageSize   每页几条数据
     * @param totalCount 总共几条数据
     * @author 胡礼波
     * @2014年11月10日上午9:28:31
     */
    public Pager(int pageNo, int pageSize, int totalCount) {
        setTotalCount(totalCount);
        setPageSize(pageSize);
        setPageNo(pageNo);
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
    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public int getTotalPage() {
        int totalPage = (totalCount + pageSize - 1) / pageSize;
        return totalPage;
    }

    @Override
    public boolean isFirstPage() {
        return pageNo <= 1;
    }

    @Override
    public boolean isLastPage() {
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

    protected int totalCount = 0;
    protected int pageSize = DEF_COUNT;
    protected int pageNo = 1;
    protected Object datas;


    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    /**
     * if totalCount<0 then totalCount=0
     *
     * @param totalCount
     * @author 胡礼波
     * @2014年11月10日上午9:28:31
     */
    public void setTotalCount(int totalCount) {
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
