package com.study.base.common;

public enum ResultStatusCode {
    OK(200, "OK"),
    NO_DATA(402, "无数据"),
    INVALID_PARAM(400, "参数不合法"),
    INVALID_AUTH(401, "用户权限不够"),
    SYSTEM_ERR(500, "系统错误"),
    API_VERSION_ERROR(4001, "APP调用接口版本错误"),
    INVALID_PASSWORD(10001, "用户名或密码错误"),
    INVALID_TOKEN(10002, "无效的TOKEN"),
    INVALID_HEADER_PARAM(10003, "参数头标识不合法"),
    EXPIRED_TOKEN(10004, "TOKEN过期"),
    FORGED_TOKEN(10005, "伪造的TOKEN"),
    UNAUTHORIZED_APP(20001, "未授权的应用"),
    UNAUTHORIZED_SERVICE(20001, "未授权的服务"),
    NOPERMISSION_USER(20003, "未授权的用户"),
    FLOP(501, "操作失败"),
    BASE_DEPLOY_HYSTRIX_COMMAND(15001, "基础服务-Deploy-系统服务异常，启动断路由！！！"),
    INVENTORY_REGION_NODATA(14000, "查询无数据"),
    INVENTORY_NUMBER_FAILED(14001, "输入数量超过库存"),
    INVENTORY_REGIONNAME_REPETITION(14002, "库位已存在！"),
    PURCHASE_ORDER_NO_DETAIL(14003, "该采购订单没有详情!"),
    CODE_NOT_BIND_BOX(14004, "此二维码没有绑定箱单"),
    NO_QUALITY_INSPECTION_ORDER_INFORMATION(14005, "无质检订单信息"),
    INVENTORY_CHECK_ISEXIST(14006, "该条数据已经存在"),
    FILE_NAME_MISMATCH(14007, "请上传正确的excel报表"),
    APS_ISEXISTS(801, "该数据已经存在"),
    APS_PLANISEXISTS(802, "已生成该生产计划，无法删除该订单"),
    APS_STATUSNOTISZERO(803, "该订单下的生产计划已计算，无法修改该订单"),
    APS_PROCESSISUSE(804, "该条工艺已经在使用中，无法进行编辑与删除！"),
    APS_PROCESSISEXIST(805, "该工序已存在，请重新输入！"),
    APS_NOORDER(806, "该生产计划为无订单，暂不支持无订单备料！！"),
    APS_NOPRODUCTINFO(807, "该产品下没有物料信息，无法添加！"),
    APS_TECHNOLOGYISEXIST(808, "该工艺已经存在，无法添加！"),
    APS_PRODUCTISCOMLETE(809, "该产品已生产完成！"),
    APS_EXCEEDINGCOUNT(810, "生产数量超出剩余生产数量！"),
    APS_COUNTNOTTOTAL(811, "生产数量必须为该产品下生产数量！"),
    APS_STATUSISEXIST(812, "还存在未领料的工单！"),
    APS_WORKORDERNUMISEXIST(813, "还存在未领料的工单！"),
    FACTORY_IS_NULL(900, "没有找到该工厂信息"),
    APP_USR_LIMIT(901, "APP用户限制达到最大值"),
    NOT_REGISTER(902, "您的号码未在工厂中注册"),
    NOT_AUTH_FACTORY(903, "未认证的工厂企业"),
    REPAET_PHONE(904, "重复的手机号码"),
    EHR_HYSTRIX_COMMAND(11001, "人事系统服务异常，启动断路由！！！"),
    UPDATE_FAILED(1309, "系统异常，更新失败"),
    SELECT_DELETED(1310, "请先选择要删除的数据"),
    MATERIAL_NOMATERIALRECORDS(1300, "没有采购的物料记录"),
    NOPROJECT(1301, "您还没有参与专案"),
    XPROJECT_COMMAND(13000, "X专案系统服务异常，启动断路由！！！"),
    SPECIFIED_FAILED(1302, "指定失败"),
    CANNOTTOPURCHASE(1303, "时间过久不能转换为采购状态"),
    RECORD_HAS_BEEN_DELETED(1304, "记录已经删除"),
    NO_PUSH_RECORD(1305, "没有推送记录"),
    CAN_NOT_WATCH(1306, "权限不够您还不能查看"),
    LESS_CURRENT_TIME(1307, "交期应该大与当前日期"),
    HAS_BEEN_RECEIVED(1308, "已经被领取,不能转换为采购状态"),
    FAILED_DELETE(1309, "删除失败"),
    SAVE_FAILED(1320, "保存失败"),
    THE_SAME_CODE(1321, "已存在同样的供应商编码，请你重新输入"),
    THE_SAME_NAME(1322, "已经存在同样的供应商名称，请您重新输入"),
    MATERIAL_COMMAND(13001, "物料录入系统服务异常，启动断路由！！！"),
    NO_SELECT_DATA(1323, "被删除的数据不能为空"),
    CAN_NOT_CHANGE(1324, "该申请单在审批中，不能对其进行修改"),
    ALREADY_SUBMITTED(1325, "审批中，不能进行修改或者删除"),
    CAN_NOT_WITHDRAW(1326, "审批已经开始或已经完成不能进行撤回"),
    CRM_CUST_NOTFOUND(12002, "您暂时没有任何用户"),
    CRM_CUST_NODATA(12005, "无数据"),
    CRM_CUST_ISEXTST(12006, "联系人已存在"),
    CRM_CUST_TIMN(12008, "提醒时间已超过当前时间"),
    CRM_EVENT_ERROR(12007, "操作失败"),
    PROJECT_NULL(900, "您还未有进行中的专案"),
    PROJECT_NOTFIND(912, "未找到专案"),
    PROJECT_NAME_REPEAT(908, "专案名重复"),
    PROJECT_CODE_REPEAT(909, "专案代码重复"),
    PROJECT_TAG_NULL(901, "标签库为空"),
    PROJECT_TAG_INVALID(902, "标签不合法"),
    PROJECT_DELETE(910, "专案删除成功"),
    PROJECT_REVISE(911, "专案修改成功"),
    PROJECT_TAG_NAME_INVALID(906, "自定义标签不合法"),
    PROJECT_TAG_NAME_REPEAT(905, "自定义标签重复"),
    PROJECT_DOCUMENT_INVALID(903, "文件不合法"),
    BUCKETNAME_NULL(920, "文件桶不能为NULL"),
    FILE_NULL(921, "文件为NULL"),
    DEL_FILE_SUCC(930, "删除文件成功"),
    DEL_FILE_ERROR(930, "删除文件失败"),
    MINIO_NOT_FILE(922, "MINIO存储系统中没有当前文件"),
    PROJECT_KEYWORKDS(907, "没有符合该关键字的专案"),
    PROJECT_TEAMMEMBER_INVALID(904, "专案参与人插入失败"),
    STAGE_ERR(600, "阶段参数不合法"),
    STAGE_NULL(602, "该专案下还没有阶段"),
    STAGE_DELETE(605, "阶段删除成功"),
    STAGE_REVISE(606, "阶段修改成功"),
    STAGE_NOTFIND(607, "找不到该阶段"),
    STAGE_DOCUMENT_NULL(603, "该阶段还没有上传文件"),
    STAGE_MEMBER_INVALID(601, "阶段成员插入失败"),
    STAGE_ESTIMATEDDATE(604, "预计完成时间不可小于当前时间"),
    REPLY_ERR(800, "回复参数错误"),
    DYNAMIC_NOT(701, "该页没有动态信息"),
    DYNAMIC_LIKE(702, "您已经点赞过该动态"),
    DYNAMIC_NULL(703, "该用户之前没有申请"),
    DYNAMIC_ERR(700, "动态表参数不合法"),
    EFFECT_USER_NULL(2000, "您还未参与任何专案"),
    EFFECT_USER_NOT(2001, "您不是工程部人员"),
    EFFECT_TIME_NOT(2002, "请投入正确的时间"),
    EFFECT_TIME_EXCESS(2003, "每天投入的时间不可大于24小时"),
    NOT_TYPE(5001, "请传入相应的评价人员"),
    NOT_NULL(5002, "该人员不存在"),
    NOT_SCORE(5003, "该员工分数没有全部统计完毕"),
    NOT_NULLSCORE(5004, "该员工还没有进行评测"),
    NOT_PARAMETER(5005, "请传入正确参数"),
    NOT_SYSID(5006, "该部门不存在,请传入正确部门ID"),
    PASSWORDERROR(3101, "密码错误"),
    NO_LOGIN_RIGHTS(900, "没有登录权限"),
    EXIST_LOGIN_NAME(5007, "工号已存在"),
    ERROR_PHONE(5008, "手机号错误"),
    EXIST_PHONE(5009, "手机号已存在"),
    QUERY_NOT_DATA(6001, "查询无数据"),
    NOT_LOWER_CATEGORY(6002, "该节点没有子节点"),
    CONFERENCE_CANCEL_FAIL(50001, "取消失败，您只能取消您本人申请记录"),
    CONFERENCE_APPLICANT_FAIL(50002, "申请失败，该会议室已被申请");

    private int code;
    private String msg;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ResultStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
