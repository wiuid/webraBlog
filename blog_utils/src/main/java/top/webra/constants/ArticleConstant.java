package top.webra.constants;

/**
 * 文章常量类
 * @author webra
 */
public class ArticleConstant {
    /**
     * 文章状态--发表
     */
    public  static final String ARTICLE_STATE_PUBLISH = "发表";
    /**
     * 文章状态--草稿
     */
    public  static final String ARTICLE_STATE_DRAFT = "草稿";
    /**
     * 文章状态--回收站
     */
    public  static final String ARTICLE_STATE_RECYCLING_STATIONS = "回收站";

    /**
     * 文章id 0
     */
    public  static final Integer ARTICLE_ID_NULL = 0;
    /**
     * 获取到了0页的文章数据
     */
    public  static final Integer ARTICLE_QUERY_COUNT = 0;
    /**
     * 当没有数据时的默认当前页
     */
    public  static final Integer ARTICLE_DEFAULT_CURR = 1;
    /**
     * 当没有数据时的默认总页数
     */
    public  static final Integer ARTICLE_DEFAULT_COUNT = 1;



}
