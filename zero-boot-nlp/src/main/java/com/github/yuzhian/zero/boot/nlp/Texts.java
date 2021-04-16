package com.github.yuzhian.zero.boot.nlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuzhian
 */
public class Texts {
    /**
     * 抽取关键字
     *
     * @param text   待分析文本
     * @param nature 词性
     * @return 词组
     * @see com.hankcs.hanlp.corpus.tag.Nature
     */
    private static List<String> segment(String text, Nature... nature) {
        String[] natures = Arrays.stream(nature).map(Nature::toString).toArray(String[]::new);
        return HanLP.segment(text).stream()
                .filter(term -> Arrays.binarySearch(natures, term.nature.toString()) > -1)
                .map(term -> term.word)
                .collect(Collectors.toList());
    }

    /**
     * 抽取关键字
     *
     * @param text 待分析文本
     * @return 词组
     */
    private static List<String> segment(String text) {
        return segment(text, Nature.n, Nature.vn);
    }

    public static void main(String[] args) {
        System.out.println(Texts.segment("张三、李四与王五去吃麻辣烫", Nature.nr));
    }
}
