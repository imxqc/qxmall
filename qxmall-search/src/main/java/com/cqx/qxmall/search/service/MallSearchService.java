package com.cqx.qxmall.search.service;

import com.cqx.qxmall.search.vo.SearchParam;
import com.cqx.qxmall.search.vo.SearchResult;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/25 21:12
 */
public interface MallSearchService {
    /**
     * @param searchParam 根据检索条件
     * @return 返回查询信息
     */
    SearchResult search(SearchParam searchParam);
}
