import browser.NgordnetQuery;
import main.HyponymsHandler;
import main.NGramMap;
import main.WordNet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestNonzeroKHyponyms {
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
    public static final String SYNSETS_TEST_FILE = PREFIX + "synsets_test.txt";
    public static final String HYPONYMS_TEST_FILE = PREFIX + "hyponyms_test.txt";

    @Test
    public void testEECS() {
        WordNet wn = new WordNet(SYNSETS_EECS_FILE, HYPONYMS_EECS_FILE);
        NGramMap ngm = new NGramMap(WORD_HISTORY_EECS_FILE, YEAR_HISTORY_FILE);
        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
        List<String> words = new ArrayList<>();
        words.add("CS61A");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 4);
        String actual = handler.handle(nq);
        String expected = "[CS170, CS61A, CS61B, CS61C]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testK() {
        WordNet wn = new WordNet(SYNSETS_TEST_FILE, HYPONYMS_TEST_FILE);
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE4_FILE, YEAR_HISTORY_FILE);
        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
        List<String> words = new ArrayList<>();
        words.add("academic");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2010, 2);
        String actual = handler.handle(nq);
        String expected = "[academic, below]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testNonExistWord() {
        WordNet wn = new WordNet(SYNSETS_TEST_FILE, HYPONYMS_TEST_FILE);
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE4_FILE, YEAR_HISTORY_FILE);
        HyponymsHandler handler = new HyponymsHandler(wn, ngm);
        List<String> words = new ArrayList<>();
        words.add("academic");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2010, 6);
        String actual = handler.handle(nq);
        String expected = "[academic, beach, below, economically]";
        assertThat(actual).isEqualTo(expected);
    }
}
