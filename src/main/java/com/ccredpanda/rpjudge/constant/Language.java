package com.ccredpanda.rpjudge.constant;

import lombok.Getter;

@Getter
public enum Language implements BaseEnum {

    // 这里的 code 就是你数据库里要存的数字
    Cpp(1, "Cpp (GCC 9.2.0)", "cpp", 54),
    JAVA17(0, "Java 17", "java", 91),
    PYTHON3(2, "Python 3.8.1", "py", 71);

    private final Integer code;
    private final String desc;
    private final String suffix; // 源代码文件后缀名（关键：用于判题机生成临时文件）
    private final Integer judgeId; // 判题机（比如 Judge0）对应的语言 ID（关键：用于调用判题接口）

    Language(Integer code, String desc, String suffix, Integer judgeId) {
        this.code = code;
        this.desc = desc;
        this.suffix = suffix;
        this.judgeId = judgeId;
    }
}