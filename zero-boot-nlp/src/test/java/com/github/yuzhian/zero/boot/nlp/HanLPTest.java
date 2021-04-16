package com.github.yuzhian.zero.boot.nlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;
import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.tokenizer.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author yuzhian
 */
public class HanLPTest {
    /**
     * 分词
     */
    @Test
    public void participle() {
        String simplifiedChineseText = "商品和服务";
        String traditionalChineseText = "商品和服務";

        // 标准分词 HanLP.segment 即 StandardTokenizer.segment 的封装
        HanLP.segment(simplifiedChineseText).forEach(System.out::println);
        StandardTokenizer.segment(simplifiedChineseText).forEach(System.out::println);

        // NLP分词, 会执行全部命名实体识别和词性标注, 所以速度比标准分词慢, 并且有误识别的情况
        NLPTokenizer.segment(simplifiedChineseText).forEach(System.out::println);

        // 索引分词, 面向搜索引擎的分词器，能够对长词全切分，另外通过 term.offset 可以获取单词在文本中的偏移量
        IndexTokenizer.segment("主副食品").forEach(term -> System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]"));

        // 繁体分词
        TraditionalChineseTokenizer.segment(traditionalChineseText).forEach(System.out::println);

        // 极速分词, 词典最长分词，速度极其快，精度一般。调用方法如下:
        SpeedTokenizer.segment(simplifiedChineseText).forEach(System.out::println);


        // 最短路分词器, 一般场景下最短路分词的精度已经足够，而且速度比N-最短路分词器快几倍
        new ViterbiSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true).seg(simplifiedChineseText).forEach(System.out::println);

        // N-最短路分词器 NShortSegment 比最短路分词器慢，但是效果稍微好一些，对命名实体识别能力更强
        new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true).seg(simplifiedChineseText).forEach(System.out::println);
    }

    /**
     * 提取频次最高的名称和动名称
     */
    @Test
    public void extractKeyword() {
        String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。";
        HanLP.extractKeyword(content, 5).forEach(System.out::println);
    }

    /**
     * 提取摘要
     */
    @Test
    public void extractSummary() {
        String document = """
                算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。
                算法可以宽泛的分为三类，
                一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。
                二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。
                三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。""";
        HanLP.extractSummary(document, 3).forEach(System.out::println);
        System.out.println(HanLP.getSummary(document, 50));
    }

    /**
     * 提取短语
     */
    @Test
    public void extractPhrase() {
        String text = """
                算法工程师
                算法（Algorithm）是一系列解决问题的清晰指令，也就是说，能够对一定规范的输入，在有限时间内获得所要求的输出。如果一个算法有缺陷，或不适合于某个问题，执行这个算法将不会解决这个问题。不同的算法可能用不同的时间、空间或效率来完成同样的任务。一个算法的优劣可以用空间复杂度与时间复杂度来衡量。算法工程师就是利用算法处理事物的人。

                1职位简介
                算法工程师是一个非常高端的职位；
                专业要求：计算机、电子、通信、数学等相关专业；
                学历要求：本科及其以上的学历，大多数是硕士学历及其以上；
                语言要求：英语要求是熟练，基本上能阅读国外专业书刊；
                必须掌握计算机相关知识，熟练使用仿真工具MATLAB等，必须会一门编程语言。

                2研究方向
                视频算法工程师、图像处理算法工程师、音频算法工程师 通信基带算法工程师

                3目前国内外状况
                目前国内从事算法研究的工程师不少，但是高级算法工程师却很少，是一个非常紧缺的专业工程师。算法工程师根据研究领域来分主要有音频/视频算法处理、图像技术方面的二维信息算法处理和通信物理层、雷达信号处理、生物医学信号处理等领域的一维信息算法处理。
                在计算机音视频和图形图像技术等二维信息算法处理方面目前比较先进的视频处理算法：机器视觉成为此类算法研究的核心；另外还有2D转3D算法(2D-to-3D conversion)，去隔行算法(de-interlacing)，运动估计运动补偿算法(Motion estimation/Motion Compensation)，去噪算法(Noise Reduction)，缩放算法(scaling)，锐化处理算法(Sharpness)，超分辨率算法(Super Resolution),手势识别(gesture recognition),人脸识别(face recognition)。
                在通信物理层等一维信息领域目前常用的算法：无线领域的RRM、RTT，传送领域的调制解调、信道均衡、信号检测、网络优化、信号分解等。
                另外数据挖掘、互联网搜索算法也成为当今的热门方向。
                算法工程师逐渐往人工智能方向发展。""";
        System.out.println(HanLP.extractPhrase(text, 10));
    }

    /**
     * 智能提示
     */
    @Test
    public void suggester() {
        Suggester suggester = new Suggester();
        Arrays.asList(
                "威廉王子发表演说 呼吁保护野生动物",
                "《时代》年度人物最终入围名单出炉 普京马云入选",
                "“黑格比”横扫菲：菲吸取“海燕”经验及早疏散",
                "日本保密法将正式生效 日媒指其损害国民知情权",
                "英报告说空气污染带来“公共健康危机”")
                .forEach(suggester::addSentence);

        // 语义
        System.out.println(suggester.suggest("选中", 1));
        // 字符
        System.out.println(suggester.suggest("污染空气", 1));
        // 拼音
        System.out.println(suggester.suggest("heigebi", 1));
    }

    /**
     * 名称识别
     */
    @Test
    public void nameRecognize() {
        // 中文人名识别
        String textNR = "年轻有为的广告设计师卢家耀（梁家辉 饰）在一次阻止不良分子寻衅滋事中失手伤人，被判服刑三年，在狱中，他因个性憨直得罪了黑帮老大，受尽欺辱，老囚犯钟天正（周润发 饰）看不过仗义相助，两人结为患难之交。钟天正原本有完好家庭，只因嗜赌成性误杀妻子，身陷囹圄的他追忆往事时后悔不已， 决心走出高墙后重新做人。然而，因为替卢家耀出头，他得罪了狐假虎威心狠手辣的典狱长（张耀扬 饰），自此卷入狱中恶势力的漩涡，走投无路之际，他放弃了“能忍则忍”的处世哲学，决意以暴施暴。";
        System.out.println(HanLP.segment(textNR).stream().filter(term -> term.nature == Nature.nr).collect(Collectors.toList()));
        // 音译人名识别
        String textNRF = "1933年，纽约流氓Noodles（罗伯特·德·尼罗 饰）因向哈洛伦警司（布鲁斯·巴伦堡 饰）通风报信害死了三名同伙而被追杀。逃亡之前，他打开了存放帮派基金的手提箱，里面却只有报纸。 1968年，已改名换姓的Noodles收到一封犹太会堂通知迁葬亲友的信。Noodles联系拉比，得知信是八个月前寄出的，他的三名同伙已被迁葬至一所豪华公墓。Noodles回到纽约，找老友莫（拉里·拉普 饰）了解情况，却一无所获。闲谈中，Noodles问起莫的妹妹黛博拉（伊丽莎白·麦戈文 饰）的情况，得知其已成名角。 原来，两人少时互有好感，但黛博拉（詹妮弗·康纳利 饰）志向高远脚踏实地，理智地将街头混混Noodles（斯科特·提勒 饰）拒于门外。 Noodles在公墓发现了一把钥匙，并用钥匙打开了当年存放手提箱的储物柜。这一次，箱子里有满满的百元钞，还有写着“下一份工作的预付款”的纸条……";
        System.out.println(HanLP.segment(textNRF).stream().filter(term -> term.nature == Nature.nrf).collect(Collectors.toList()));
        // 地名识别, 需要手动开启
        String textNS = "蓝翔给宁夏固原市彭阳县红河镇黑牛沟村捐赠了挖掘机";
        System.out.println(HanLP.newSegment().enablePlaceRecognize(true).seg(textNS).stream().filter(term -> term.nature == Nature.ns).collect(Collectors.toList()));
        // 机构名识别, 需要手动开启
        String textNS2 = "我在上海林原科技有限公司兼职工作，同时在上海外国语大学日本文化经济学院学习经济与外语。我经常在台川喜宴餐厅吃饭，偶尔去地中海影城看电影。";
        System.out.println(HanLP.newSegment().enableOrganizationRecognize(true).seg(textNS2).stream().filter(term -> term.nature == Nature.nt).collect(Collectors.toList()));
    }

    /**
     * 拼音
     */
    @Test
    public void pinyin() {
        String text = "萨哈夫说，伊拉克将同联合国销毁伊拉克大规模杀伤性武器特别委员会继续保持合作。";
        HanLP.convertToPinyinList(text).forEach(pinyin -> System.out.printf(
                "拼音(声调): %s,\t带音调的拼音: %s,\t纯字母: %s,\t声调: %s,\t声母: %s,\t韵母: %s,\t输入法头: %s%n",
                pinyin,
                pinyin.getPinyinWithToneMark(),
                pinyin.getPinyinWithoutTone(),
                pinyin.getTone(),
                pinyin.getShengmu(),
                pinyin.getYunmu(),
                pinyin.getHead()
        ));
    }

    /**
     * 简繁转换
     */
    @Test
    public void convert() {
        System.out.println(HanLP.convertToTraditionalChinese("“以后等你当上皇后，就能买草莓庆祝了”"));
        System.out.println(HanLP.convertToSimplifiedChinese("用筆記簿型電腦寫程式HelloWorld"));
    }
}
