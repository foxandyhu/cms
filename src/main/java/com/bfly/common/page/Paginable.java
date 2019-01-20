package com.bfly.common.page;

/**
 * 分页接口
 *
 * @author 胡礼波
 * 2012-4-26 下午09:21:36
 */
public interface Paginable {

    /**
     * 总记录数
     *
     * @return
     * @author 胡礼波
     * 2012-4-26 下午09:22:00
     */
    long getTotalCount();

    /**
     * 总页数
     *
     * @return
     * @author 胡礼波
     * 2012-4-26 下午09:22:06
     */
    int getTotalPage();

    /**
     * 每页记录数
     *
     * @return
     * @author 胡礼波
     * 2012-4-26 下午09:22:12
     */
    int getPageSize();

    /**
     * 当前页号
     *
     * @return
     * @author 胡礼波
     * 2012-4-26 下午09:22:18
     */
    int getPageNo();

    /**
     * 返回下页的页号
     *
     * @return
     * @author 胡礼波
     * 2012-4-26 下午09:22:37
     */
    int getNextPage();

    /**
     * 返回上页的页号
     *
     * @return
     * @author 胡礼波
     * 2012-4-26 下午09:22:43
     */
    int getPrePage();
}
