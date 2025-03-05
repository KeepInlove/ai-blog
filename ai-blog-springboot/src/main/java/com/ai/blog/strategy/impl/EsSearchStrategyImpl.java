package com.ai.blog.strategy.impl;

import com.ai.blog.dto.ArticleSearchDTO;
import com.ai.blog.strategy.SearchStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ai.blog.constant.CommonConst.*;
import static com.ai.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * es搜索策略实现
 *
 * @author yezhiqiu
 * @date 2021/07/27
 */
@Log4j2
@Service("esSearchStrategyImpl")
public class EsSearchStrategyImpl implements SearchStrategy {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<ArticleSearchDTO> searchArticle(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        return search(buildQuery(keywords));
    }

    /**
     * 搜索文章构造
     *
     * @param keywords 关键字
     * @return es条件构造器
     */

    private Query buildQuery(String keywords) {
        Criteria criteria = new Criteria("articleTitle").contains(keywords)
                .or("articleContent").contains(keywords)
                .and("isDelete").is(FALSE)
                .and("status").is(PUBLIC.getStatus());

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);

        // 高亮参数
        HighlightParameters highlightParameters = HighlightParameters.builder()
                .withPreTags(PRE_TAG)
                .withPostTags(POST_TAG)
                .build();

        // 高亮字段
        Highlight highlight = new Highlight(highlightParameters, List.of(
                new HighlightField("articleTitle"),
                new HighlightField("articleContent")
        ));

        // 设置高亮查询
        criteriaQuery.setHighlightQuery(new HighlightQuery(highlight, ArticleSearchDTO.class));

        return criteriaQuery;
    }



    /**
     * 文章搜索结果高亮
     *
     * @param query es条件构造器
     * @return 搜索结果
     */
    private List<ArticleSearchDTO> search(Query query) {
        try {
            SearchHits<ArticleSearchDTO> search = elasticsearchOperations.search(query, ArticleSearchDTO.class);
            return search.getSearchHits().stream().map(hit -> {
                ArticleSearchDTO article = hit.getContent();
                // 获取文章标题高亮数据
                List<String> titleHighLightList = hit.getHighlightFields().get("articleTitle");
                if (CollectionUtils.isNotEmpty(titleHighLightList)) {
                    // 替换标题数据
                    article.setArticleTitle(titleHighLightList.get(0));
                }
                // 获取文章内容高亮数据
                List<String> contentHighLightList = hit.getHighlightFields().get("articleContent");
                if (CollectionUtils.isNotEmpty(contentHighLightList)) {
                    // 替换内容数据
                    article.setArticleContent(contentHighLightList.get(contentHighLightList.size() - 1));
                }
                return article;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }
}
