package top.webra.constants;

/**
 * 响应码常量类
 * @author webra
 */
public class ResponseStateConstant {
    /**
     * 访问成功
     */
    public static final Integer RESPONSE_SUCCESS = 200;
    /**
     * 访问失败或者后端异常等情况
     */
    public static final Integer RESPONSE_FAILURE = 404;
    /**
     * 不影响任何状态代码
     */
    public static final Integer RESPONSE_NULL = null;

    /**
     * 数据异常--数据库
     */
    public static final Integer DATA_EXCEPTION = 601;
    /**
     * 数据库搜索为空
     */
    public static final Integer DATA_SEARCH_NULL = 602;
    /**
     * 已有数据充足，禁止再添加
     */
    public static final Integer DATA_EXCESSIVE = 603;

    /**
     * 请求异常--请求参数
     */
    public static final Integer RESPONSE_EXCEPTION = 611;
    /**
     * 请求参数中的邮箱格式错误
     */
    public static final Integer RESPONSE_EMAIL_ERROR = 612;

    /**
     * 登录用户异常（包含游客）
     */
    public static final Integer USER_EXCEPTION = 621;
    /**
     * 非管理员不可与管理员使用相同邮箱
     */
    public static final Integer USER_EMAIL_WEBRA = 622;




}
