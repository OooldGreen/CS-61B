import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import edu.princeton.cs.algs4.In;
import main.HyponymsHandler;
import main.NGramMap;
import main.WordNet;
import org.junit.jupiter.api.Test;
import main.AutograderBuddy;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.
 * The word history and year history files do not matter for the k==0 case, but are provided
 * as input for the constructor of the HyponymsHandler.
 */
public class TestOneWordK0Hyponyms {
    private static final String PREFIX = "./data/";

    /** NGrams Files */
    public static final String WORD_HISTORY_EECS_FILE = PREFIX + "word_history_eecs.csv";
    public static final String WORD_HISTORY_SIZE3_FILE = PREFIX + "word_history_size3.csv";
    public static final String WORD_HISTORY_SIZE4_FILE = PREFIX + "word_history_size4.csv";
    public static final String WORD_HISTORY_SIZE1291_FILE = PREFIX + "word_history_size1291.csv";
    public static final String WORD_HISTORY_SIZE14377_FILE = PREFIX + "word_history_size14377.csv";
    public static final String YEAR_HISTORY_FILE = PREFIX + "year_history.csv";

    /** Wordnet Files */
    public static final String SYNSETS_EECS_FILE = PREFIX + "synsets_eecs.txt";
    public static final String HYPONYMS_EECS_FILE = PREFIX + "hyponyms_eecs.txt";
    public static final String SYNSET_SIZE16_FILE = PREFIX + "synsets_size16.txt";
    public static final String HYPONYM_SIZE16_FILE = PREFIX + "hyponyms_size16.txt";
    public static final String SYNSET_SIZE1000_FILE = PREFIX + "synsets_size1000.txt";
    public static final String HYPONYM_SIZE1000_FILE = PREFIX +  "hyponyms_size1000.txt";

    @Test
    public void testActK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE, SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
        List<String> words = new ArrayList<>();
        words.add("act");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[act, action, change, demotion, human_action, human_activity, variation]";
        assertThat(actual).isEqualTo(expected);
    }

    // TODO: Add more unit tests (including edge case tests) here.
    @Test
    public void testEmptyInput() {
        WordNet wn = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
        List<String> words = new ArrayList<>();

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = handler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testNonExistInput() {
        WordNet wn = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
        List<String> words = new ArrayList<>();
        words.add("aimer");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = handler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPolysemousWord() {
        WordNet wn = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
        List<String> words = new ArrayList<>();
        words.add("change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = handler.handle(nq);
        String expected = "[alteration, change, demotion, increase, jump, leap, modification, saltation, transition, variation]";
        assertThat(actual).isEqualTo(expected);
    }

//    @Test
//    public void testReversal() {
//        WordNet wn = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
//        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
//        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
//
//        assertThat(handler.traversalBFS("aimer")).isEqualTo(null);
//
//        Set<Integer> ids = handler.traversalBFS("change");
//        Set<Integer> result = Set.of(2, 3, 4, 5, 8, 9, 10);
//        assertThat(ids).isEqualTo(result);
//    }

//    @Test
//    public void testFindHyponyms() {
//        WordNet wn = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
//        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
//        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
//
//        Set<String> hyponyms = handler.findHyponyms(Collections.singletonList("change"));
//        Set<String> results = Set.of("change", "alteration", "modification", "transition", "increase", "jump", "leap", "saltation", "demotion", "variation");
//        assertThat(hyponyms).isEqualTo(results);
//    }

//    @Test
//    public void testReversal2() {
//        WordNet wn = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
//        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
//        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
//
//        Set<Integer> ids = handler.traversalBFS("act");
//        Set<Integer> result = Set.of(6, 7, 8, 9, 10);
//        assertThat(ids).isEqualTo(result);
//    }

//    @Test
//    public void testFindHyponyms2() {
//        WordNet wn = new WordNet(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE);
//        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
//        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
//
//        Set<String> hyponyms = handler.findHyponyms(Collections.singletonList("act"));
//        Set<String> results = Set.of("act", "action", "change", "demotion", "human_action", "human_activity", "variation");
//        assertThat(hyponyms).isEqualTo(results);
//    }
}
