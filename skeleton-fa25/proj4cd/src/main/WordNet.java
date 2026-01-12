package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    // parameters
    HashMap<Integer, List<String>> wordMap = new HashMap<>();
    HashMap<String, LinkedList<Integer>> idMap = new HashMap<>();
    HashMap<Integer, Set<Integer>> wordNetMap = new HashMap<>();

    // constructors
    public WordNet(String synsetsFileName, String hyponymsFileName) {
        In synsetsFile = new In(synsetsFileName);
        In hyponymsFile = new In(hyponymsFileName);

        getIdWordMap(synsetsFile);
        getWordNetMap(hyponymsFile);
    }

    // methods
    public Set<Integer> getHyponymIds(int id) {
        if (wordNetMap.containsKey(id)) {
            return wordNetMap.get(id);
        }
        return null;
    }

    public List<String> idToWord(int id) {
        if (wordMap.containsKey(id)) {
            return wordMap.get(id);
        }
        return null;
    }

    public List<Integer> wordToId(String word) {
        if (idMap.containsKey(word)) {
            return idMap.get(word);
        }
        return null;
    }

    private void getIdWordMap(In file) {
        while (!file.isEmpty()) {
            String[] line = file.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            String[] words = line[1].split(" ");

            wordMap.put(id, List.of(words));
            for (String word : words) {
                if (!idMap.containsKey(word)) {
                    idMap.put(word, new LinkedList<>());
                }
                idMap.get(word).add(id);
            }
        }
    }

    private void getWordNetMap(In file) {
        while(!file.isEmpty()) {
            String[] line = file.readLine().split(",");
            int id = Integer.parseInt(line[0]);

            Set<Integer> hyponymIds = new HashSet<>();
            for (int i = 1; i < line.length; i++) {
                hyponymIds.add(Integer.parseInt(line[i]));
            }

            if (!wordNetMap.containsKey(id)) {
                wordNetMap.put(id, hyponymIds);
            } else {
                wordNetMap.get(id).addAll(hyponymIds);
            }
        }
    }
}
