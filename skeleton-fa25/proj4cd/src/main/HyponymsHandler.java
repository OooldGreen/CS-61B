package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import com.google.common.annotations.VisibleForTesting;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wn;
    private final NGramMap ngm;

    public HyponymsHandler(WordNet wordNet, NGramMap ngMap) {
        this.wn = wordNet;
        this.ngm = ngMap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        if (words == null) {
            return "please input a word";
        }
        Set<String> hyponymsSet = findHyponyms(words);
        if (k > 0) {
            hyponymsSet = findFirstKHyponymsByCount(hyponymsSet, startYear, endYear, k);
        }

        return hyponymsSet.toString();
    }

    private Set<String> findFirstKHyponymsByCount(Set<String> hyponymsSet, int startYear, int endYear, int k) {
        Map<String, Double> wordCount = new HashMap<>();

        for (String hyponym : hyponymsSet) {
            double count = 0;
            if (!ngm.countHistory(hyponym, startYear, endYear).isEmpty()) {
                Map<Integer, Double> map = ngm.countHistory(hyponym, startYear, endYear);
                for (double value : map.values()) {
                    count += value;
                }
            }
            wordCount.put(hyponym, count);
        }

        List<String> hyponymsList = new ArrayList<>(wordCount.keySet());
        hyponymsList.sort((a, b) -> wordCount.get(b).compareTo(wordCount.get(a)));

        int limit = Math.min(k, hyponymsList.size());
        return new TreeSet<>(hyponymsList.subList(0, limit));
    }

    private Set<String> findHyponyms(List<String> words) {
        Set<Integer> hyponymIds = new HashSet<>();
        Set<String> hyponymWords = new TreeSet<>();

        for (String word : words) {
            Set<Integer> res = traversalBFS(word);

            if (res != null) {
                if (hyponymIds.isEmpty()) {
                    hyponymIds.addAll(res);
                } else {
                    hyponymIds.retainAll(res);
                }
            }
        }

        for (int id : hyponymIds) {
            if (wn.idToWord(id) != null) {
                hyponymWords.addAll(wn.idToWord(id));
            }
        }

        return hyponymWords;
    }

    private Set<Integer> traversalBFS(String word) {
        List<Integer> targetIds = wn.wordToId(word);
        if (targetIds == null) {
            return null;
        }

        Set<Integer> marked = new HashSet<>();
        List<Integer> queue = new LinkedList<>();
        Set<Integer> resultSet = new HashSet<>();

        for (int targetId : targetIds) {
            queue.add(targetId);
            marked.add(targetId);
            resultSet.add(targetId);
        }

        while (!queue.isEmpty()) {
            int id = queue.removeFirst();
            Set<Integer> hyponyms = wn.getHyponymIds(id);

            if (hyponyms != null) {
                for (int hyponym : hyponyms) {
                    if (!marked.contains(hyponym)) {
                        queue.add(hyponym);
                        marked.add(hyponym);
                        resultSet.add(hyponym);
                    }
                }
            }
        }

        return resultSet;
    }

}
