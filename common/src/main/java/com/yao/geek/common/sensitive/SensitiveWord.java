package com.yao.geek.common.sensitive;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.deny.WordDenys;

public class SensitiveWord {
    private SensitiveWord(){

    }

    static class SensitiveWordHolder{
        private static final SensitiveWordBs INSTANCE = SensitiveWordBs.newInstance()
                .wordDeny(WordDenys.defaults())
                .ignoreCase(true)
                .ignoreChineseStyle(true)
                .ignoreWidth(true)
                .ignoreRepeat(true)
                .wordFailFast(true)
                .init();
    }

    public static SensitiveWordBs getInstance(){
        return SensitiveWordHolder.INSTANCE;
    }

}
