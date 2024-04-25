package com.cqx.qxmall.search.controller;

import com.cqx.qxmall.search.service.MallSearchService;
import com.cqx.qxmall.search.vo.SearchParam;
import com.cqx.qxmall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/25 19:35
 */
@Controller
public class SearchController {
    @Autowired
    MallSearchService mallSearchService;


    @GetMapping({"/list.html"})
    public String listPage(SearchParam searchParam,Model model) {
        SearchResult result = mallSearchService.search(searchParam);
        model.addAttribute("result", result);

        return "list";
    }

}
