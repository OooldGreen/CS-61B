import main.WordNet;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class TestWordNet {
    @Test
    public void testIdToWord(){
        final String PREFIX = "./data/";
        WordNet wn=new WordNet("./data/synsets_size16.txt","./data/hyponyms_size16.txt");

        List<String> result = List.of("happening", "occurrence", "occurrent", "natural_event");
        assertThat(wn.idToWord(1)).isEqualTo(result);
        assertThat(wn.idToWord(17)).isEqualTo(null);
    }

    @Test
    public void testWordToId() {
        final String PREFIX = "./data/";
        WordNet wn=new WordNet("./data/synsets_size16.txt","./data/hyponyms_size16.txt");

        assertThat(wn.wordToId("aimer")).isEqualTo(null);

        List<Integer> ids1 = new LinkedList<>();
        ids1.add(1);
        assertThat(wn.wordToId("happening")).isEqualTo(ids1);

        List<Integer> ids2 = new LinkedList<>();
        ids2.add(3);
        ids2.add(14);
        assertThat(wn.wordToId("transition")).isEqualTo(ids2);
    }

    @Test
    public void testGetHyponymIds() {
        final String PREFIX = "./data/";
        WordNet wn=new WordNet("./data/synsets_size16.txt","./data/hyponyms_size16.txt");

        assertThat(wn.getHyponymIds(17)).isEqualTo(null);

        Set<Integer> res = new HashSet<>();
        res.add(9);
        res.add(10);
        assertThat(wn.getHyponymIds(8)).isEqualTo(res);
    }
}
