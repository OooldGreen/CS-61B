package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        /**
         * 1. 处理输入，分离出单词 cat，dog
         * 2. 调用 NGramMap 中的函数找出数据
         * 3. 将返回的 TS 处理成 String
         * 4. 返回结果
         * **/
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = "";

        for (String word : words) {
            response += word + ": {";
            TimeSeries ts = map.weightHistory(word, startYear, endYear);
            response += ts.toString() + "}\n";
        }
        return response;
    }
}
