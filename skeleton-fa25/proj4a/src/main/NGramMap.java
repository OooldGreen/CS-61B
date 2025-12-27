package main;

import edu.princeton.cs.algs4.In;

import java.sql.Time;
import java.util.*;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    List<WordMap> wordList = new ArrayList<>();
    TimeSeries yearList = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In wordHistoryFile = new In(wordHistoryFilename);
        In yearHistoryFile = new In(yearHistoryFilename);

        splitWordFile(wordHistoryFile);
        splitYearFile(yearHistoryFile);
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        WordMap fw = findWord(word);
        if (fw != null) {
            TimeSeries tempTs = fw.ts;
            return new TimeSeries(tempTs, startYear, endYear);
        }
        return null;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        WordMap fw = findWord(word);

        if (fw != null) {
            return fw.ts;
        }

        return null;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return yearList;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    /**
     * relative frequency = number of this word in that year / total number of words in that year
     * **/
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries resultTs = new TimeSeries();
        WordMap fw = findWord(word);
        if (fw != null) {
            TimeSeries wordTs = new TimeSeries(fw.ts, startYear, endYear);
            TimeSeries yearTs = new TimeSeries(yearList, startYear, endYear);
            resultTs = wordTs.dividedBy(yearTs);
        }
        return resultTs;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries resultTs = new TimeSeries();
        WordMap fw = findWord(word);
        if (fw != null) {
            TimeSeries wordTs = fw.ts;
            TimeSeries yearTs = yearList;
            resultTs = wordTs.dividedBy(yearTs);
        }
        return resultTs;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    /**
     * summed relative frequency = number of all words / totalCountHistory
     * **/
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries wordTs = new TimeSeries();
        TimeSeries yearTs = new TimeSeries(yearList, startYear, endYear);

        for (WordMap wordMap : wordList) {
           if(words.contains(wordMap.word)) {
               wordTs = wordTs.plus(wordMap.ts);
           }
        }
        wordTs = new TimeSeries(wordTs, startYear, endYear);

        return wordTs.dividedBy(yearTs);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries wordTs = new TimeSeries();

        for (WordMap wordMap : wordList) {
            if(words.contains(wordMap.word)) {
                wordTs = wordTs.plus(wordMap.ts);
            }
        }

        return wordTs.dividedBy(yearList);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
    /**
     * 处理 yearFile，返回一个 TimeSeries
     * 第一列是 year，第二列是 对应年份 total number of words recorded
     * 时间复杂度：O(n)
     * **/
    private void splitYearFile(In file) {
        while(!file.isEmpty()) {
            String[] line = file.readLine().split(",");
            yearList.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
        }
    }

    private static class WordMap {
        String word;
        TimeSeries ts;

        WordMap(String word, TimeSeries ts) {
            this.word = word;
            this.ts = ts;
        }
    }

     private static class Data {
        String word;
        Integer year;
        Double data;

        Data (String word, Integer year, Double data) {
            this.word = word;
            this.year = year;
            this.data = data;
        }
     }

    /**
     * 处理 wordFile
     * 包含一个 string word，一个 TimeSeries 用于存储对应 word 的年份以及出现的次数
     * 时间复杂度：O(n^2)
     * result: 3min20s +, too slow
     *
     * 优化：后来添加了 Data，先给数据进行了排序再创建 wordMap 添加到 wordList，时间复杂度提升到 O(nlogn)
     * result: 5s + , much better
     * **/
    private void splitWordFile(In file) {
        List<Data> list = new ArrayList<>();
        while(!file.isEmpty()) {
            String[] line = file.readLine().split("\t");
            Data data = new Data(line[0], Integer.parseInt(line[1]), Double.parseDouble(line[2]));
            list.add(data);
        }
        list.sort(Comparator.comparing((Data d) -> d.word)
                .thenComparingInt(d -> d.year));
        addDataToWordList(list);
    }

    private void addDataToWordList(List<Data> list) {
        String currentWord = "";
        TimeSeries currentTs = null;

        for (Data d : list) {
            if(!d.word.equals(currentWord)) {
                currentWord = d.word;
                currentTs = new TimeSeries();
                currentTs.put(d.year, d.data);
                wordList.add(new WordMap(currentWord, currentTs));
            }

            if (currentTs != null) {
                currentTs.put(d.year, d.data);
            }
        }
    }

//    private void addDataToWordList(String[] line) {
//        String word = line[0];
//        Integer year = Integer.parseInt(line[1]);
//        Double data = Double.parseDouble(line[2]);
//        WordMap exsitingWord = null;
//        for (WordMap wm : wordList) {
//            if (wm.word.equals(word)) {
//                exsitingWord = wm;
//                break;
//            }
//        }
//
//        if (exsitingWord == null) {
//            TimeSeries ts = new TimeSeries();
//            ts.put(year, data);
//            wordList.add(new WordMap(word, ts));
//        } else {
//            exsitingWord.ts.put(year, data);
//        }
//    }

    // 检索 word 并返回相应的 wordMap
    private WordMap findWord(String word) {
        for (WordMap wordMap : wordList) {
            if (Objects.equals(wordMap.word, word)) {
                return wordMap;
            }
        }
        return null;
    }
}
