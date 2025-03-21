package com.ai.blog.consumer;

/**
 * maxwell监听数据
 *
 * @author yezhiqiu
 * @date 2021/08/02
 */
//@Component
//@RabbitListener(queues = MAXWELL_QUEUE)
//public class MaxWellConsumer {
//    @Resource
//    private ElasticsearchDao elasticsearchDao;
//
//    @RabbitHandler
//    public void process(byte[] data) {
//        // 获取监听信息
//        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
//        // 获取文章数据
//        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class);
//        // 判断操作类型
//        switch (maxwellDataDTO.getType()) {
//            case "insert":
//            case "update":
//                // 更新es文章
//                elasticsearchDao.save(BeanCopyUtils.copyObject(article, ArticleSearchDTO.class));
//                break;
//            case "delete":
//                // 删除文章
//                elasticsearchDao.deleteById(article.getId());
//                break;
//            default:
//                break;
//        }
//    }
//
//}