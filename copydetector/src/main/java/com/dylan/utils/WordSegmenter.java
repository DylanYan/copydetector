package com.dylan.utils;

import com.google.common.base.Splitter;
import org.ansj.recognition.impl.StopRecognition;
import org.apdplat.word.segmentation.Word;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.List;

public class WordSegmenter {

    public static String plainSeg (String input) {
        input = input.replaceAll("[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]",",");  //用"@"代替一切标点符号和制表符，删除回车符和换行符
        input = input.replaceAll("[\\s*|\t|\r|\n]","");
        //input = input.replaceAll("[\\s*|\r|\n]","");
        Iterable<String> output = Splitter.on(',').trimResults().omitEmptyStrings().split(input);
        System.out.println(output);
        return input;
    }

    //使用Word分词器分词，速度较慢,可支持从.doc,.docx和.txt文档读取的文本
    public static String wordSeg (String input) {
        List<Word> words = org.apdplat.word.WordSegmenter.seg(input);
        String output = words.toString();
        return output;

    }
    //使用Ansj分词器分词，速度较快，目前只支持从.docx文档读取的文本
    public static String ansjSeg (String input) {
        StopRecognition filter = new StopRecognition();
        //去除常用符号
        filter.insertStopWords(" "); //去除文本中的空格
        filter.insertStopWords(" ");//另一种空格
        filter.insertStopWords("\n");
        filter.insertStopWords("\t");
        filter.insertStopWords("[");
        filter.insertStopWords("]");
        filter.insertStopWords("【");
        filter.insertStopWords("】");
        filter.insertStopWords(",");
        filter.insertStopWords("，");
        filter.insertStopWords(".");
        filter.insertStopWords("。");
        filter.insertStopWords("?");
        filter.insertStopWords("？");
        filter.insertStopWords(";");
        filter.insertStopWords("；");
        filter.insertStopWords("!");
        filter.insertStopWords("！");
        filter.insertStopWords(":");
        filter.insertStopWords("：");
        Result modifiedResult = ToAnalysis.parse(input).recognition(filter);
        String output = modifiedResult.toStringWithOutNature();
        return output;
    }
}
