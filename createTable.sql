
use test;

CREATE TABLE user (
                        id             bigint       NOT NULL AUTO_INCREMENT,
                        username       varchar(32)  NOT NULL COMMENT '登录名，4-32 位字母数字',
                        password       varchar(128) NOT NULL COMMENT 'BCrypt 密文，禁止明文',
                        nickname       varchar(32)  NOT NULL COMMENT '昵称，默认=username',
                        avatar         varchar(256) NOT NULL DEFAULT '' COMMENT '头像 URL',
                        signature      varchar(128) NOT NULL DEFAULT '' COMMENT '个性签名',
                        role           tinyint      NOT NULL DEFAULT 0 COMMENT '0 普通 1 管理员',
                        solved_count   int          NOT NULL DEFAULT 0 COMMENT '去重后的 AC 题数',
                        submit_count   int          NOT NULL DEFAULT 0 COMMENT '总提交数',
                        solution_count int          NOT NULL DEFAULT 0 COMMENT '已发布题解数',
                        status         tinyint      NOT NULL DEFAULT 1 COMMENT '1 正常 0 封禁',
                        create_time    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        update_time    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (id),
                        UNIQUE KEY uk_username (username),
                        KEY idx_solved (solved_count DESC) COMMENT '排行榜 DB 回退用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

CREATE TABLE problem (
                         id             bigint       NOT NULL AUTO_INCREMENT,
                         title          varchar(128) NOT NULL,
                         content        text         NOT NULL COMMENT 'Markdown 题面',
                         difficulty     tinyint      NOT NULL DEFAULT 0 COMMENT '0 简单 1 中等 2 困难',
                         tag            varchar(255) NOT NULL DEFAULT '' COMMENT '逗号分隔，如 数组,哈希表',
                         time_limit     int          NOT NULL DEFAULT 1000 COMMENT '单位 ms',
                         memory_limit   int          NOT NULL DEFAULT 256  COMMENT '单位 MB',
                         template_code  json         NULL COMMENT '{"java":"...","cpp":"...","python3":"..."}',
                         accepted_count int          NOT NULL DEFAULT 0,
                         submit_count   int          NOT NULL DEFAULT 0,
                         solution_count int          NOT NULL DEFAULT 0,
                         status         tinyint      NOT NULL DEFAULT 1 COMMENT '1 上架 0 下架',
                         create_time    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         update_time    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (id),
                         KEY idx_status_diff (status, difficulty),
                         KEY idx_title (title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目';

CREATE TABLE problem_case (
                              id         bigint   NOT NULL AUTO_INCREMENT,
                              problem_id bigint   NOT NULL,
                              input      mediumtext NOT NULL,
                              output     mediumtext NOT NULL,
                              score      int      NOT NULL DEFAULT 0 COMMENT '预留，M3 部分分用',
                              sort       int      NOT NULL DEFAULT 0 COMMENT '用例顺序，1 开始',
                              PRIMARY KEY (id),
                              KEY idx_problem (problem_id, sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试用例';


CREATE TABLE submission (
                            id            bigint      NOT NULL AUTO_INCREMENT,
                            user_id       bigint      NOT NULL,
                            problem_id    bigint      NOT NULL,
                            language      tinyint     NOT NULL COMMENT '0 Java 1 Cpp 2 Python3',
                            code          mediumtext  NOT NULL COMMENT '源码，≤100KB',
                            status        tinyint     NOT NULL DEFAULT 0 COMMENT '见状态机',
                            time_used     int         NULL COMMENT '单位 ms',
                            memory_used   int         NULL COMMENT '单位 KB',
                            error_message varchar(2000) NULL COMMENT '编译错误/首个错误用例，截断 2000 字符',
                            fail_case     int         NULL COMMENT '首个失败用例序号，1 开始',
                            create_time   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            update_time   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (id),
                            KEY idx_user_time (user_id, create_time DESC),
                            KEY idx_problem (problem_id, status),
                            KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提交记录';


CREATE TABLE solution (
                          id            bigint       NOT NULL AUTO_INCREMENT,
                          user_id       bigint       NOT NULL COMMENT '作者',
                          problem_id    bigint       NOT NULL COMMENT '所属题目',
                          submission_id bigint       NULL COMMENT '关联的 AC 提交，溯源用',
                          title         varchar(128) NOT NULL,
                          content       text         NOT NULL COMMENT 'Markdown 正文',
                          status        tinyint      NOT NULL DEFAULT 1 COMMENT '0 待审核 1 已发布 2 已下架',
                          like_count    int          NOT NULL DEFAULT 0 COMMENT '[M3] 点赞数',
                          view_count    int          NOT NULL DEFAULT 0 COMMENT '浏览量',
                          create_time   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          update_time   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (id),
                          KEY idx_problem_status (problem_id, status, create_time DESC),
                          KEY idx_user (user_id, create_time DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题解';


CREATE TABLE user_problem_ac (
                                 id            bigint   NOT NULL AUTO_INCREMENT,
                                 user_id       bigint   NOT NULL,
                                 problem_id    bigint   NOT NULL,
                                 submission_id bigint   NOT NULL COMMENT '首次 AC 的提交',
                                 create_time   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 PRIMARY KEY (id),
                                 UNIQUE KEY uk_user_problem (user_id, problem_id) COMMENT '天然去重，判断资格 O(1)',
                                 KEY idx_problem (problem_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AC 记录（题解资格 + 已解决列表）';

CREATE TABLE judge_task (
                            id            bigint   NOT NULL AUTO_INCREMENT,
                            submission_id bigint   NOT NULL,
                            status        tinyint  NOT NULL DEFAULT 0 COMMENT '0 待处理 1 处理中 2 完成 3 死信',
                            retry_count   tinyint  NOT NULL DEFAULT 0,
                            error_message varchar(500) NULL,
                            create_time   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            update_time   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (id),
                            UNIQUE KEY uk_submission (submission_id) COMMENT '消费幂等'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='判题任务（MQ 幂等）';
