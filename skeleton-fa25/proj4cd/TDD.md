# Data
What data will you be using?
What does the data consist of and how will you parse it for your program?
How will your program use the data?

## Hyponyms (Basic Case)
- wordNet graph
- one single word input or a list of word

## WordNet Graph
- result of readfile method

### IdToWord method
- wordMap:
    - Int id
    - LinkedList words

### WordToId method
- idMap:
    - String word
    - LinkedList ids

## Read file method
NOTE: columns are separated by ","

- Synsets File: 
  - first column (numbers) - id
  - second column (strings) - words, words are separated by spaces
- Hyponyms File: 
  - first column (numbers) - synsets
  - others (numbers) - hyponyms

# Data Structures
What data structures will you be using?
How will your data structures use the data?
How will your program use the data structures?

## Hyponyms (Basic Case)
- Set<String>: TreeSet -- because the list shouldn't have repeated words, alphabetical order

## Graph (Wordnet)
- three maps:
    HashMap<Integer, List<String>> wordMap
        key: word id
        value: corresponding words
    HashMap<String, LinkedList<Integer>> idMap
        key: word
        value: corresponding ids
    HashMap<Integer, Set<Integer>> wordNetMap
        key: vertexId
        value: edgeTo
NOTE: one word can have several ids and vice versa.

## Read file method
NOTE: if I use a tree, it is sorted at first but it is more slow to search; if I use a hashMap, it is faster to search
- synset:
    - wordMap: hash, built as a dictionary, because we need to search for word many times,
        Map<Integer, String> wordMap = new HashMap<>()
    - idMap
        Map<String, List<Integer>> idMap = new HashMap<>()

# Algorithms
What algorithms will you be using?
How will your algorithms use the data structures and data?
How will your program use the algorithms?

NOTE: 
    - 只做id组成的graph，再写一个单独的方法对应 word
    - each id can correspond several words, it means I can't use a one-to-one map storing id to word, I need to use a list to store words
    - WordToId map is the same situation, there can be several ids for each word

## Wordnet
### Read file method
    create a wordMap
    create a idMap
    parsing files (`In file = new In(filename)`)
    splitSynsetFile(In synsetFile)
        parsing lines (`String[] line = file.readline().split(",")`)
        int id = line[0]
        List<String> word = line[1].split(" ")
        add (id, word) to wordMap
        if word is not in idMap:
            add (word, [id]) to idMap
        else:
            add id to idMap.word
    splitHyponymsFile(In hyponymsFile)
        read lines
        word = line[0]

### Graph WordNet
    Create an empty graph wordNet
    for each word id
        if id does not exists:
            add id to graph
            size += 1
        for each hyponym of id
            add h to hyponymSet

### idToWord method
    idToWord(int id)
        if id not exists (wordMap.containsKey(id) == false)
            return null
        else
            return wordMap.get(id)

### wordToId method
    wordToId(String word)
        if word not exists (idMap.containsKey(word) == false)
            return null
        else
            return idMap.get(word)

## HyponymsHandle

### Handle several words input:

    Handle word inputs
    if no input:
        return 'please input a word'
    Initialize a result id HashSet
    Initialize a result word TreeSet
    for each word:
        find hyponyms by traversal
        if hyponyms is not null:
            if resultIdSet is empty:
                add hyponyms in resultIdSet
            else:
               resultIdSet.restianAll(returned results) (find intersection)
    Find corresponding word of each set and add to resultWordSet

### Handle k != 0:
NOTES: result is sorted by count, count can not be 0, this means this word have not appeared during startYear and endYear. (结果数组按照count排序，count不能为0)
Continuing from the code above, finding corresponding word resultWordSet：

    if k > 0:
        initialize a HashMap wordCount
        for each word in resultWordSet:
            count = ngramMap.countHistory(word, startYear, endYear)
            if count > 0:
                add result (word, count) in wordCount
        initialize an ArrayList wordList
        sort wordList by counts:
            find count of first word count1 according to wordCount
            find count of second word count2 according to wordCount
            compare count1 and count2
        return fisrt k number of wordList

### Traversal
NOTE: Commence from the vertex of target number in stead of from the starting vertex will be faster

    List targetIds = wordToId(word)
    if targetIds is empty:
        return null
    Initialize a HashSet marked
    Initialize an empty queue
    Initialize a result ids HashSet resultSet
    for each targetId:
        Add targetId to queue as starting vertex
        mark vertex
        add v to resultIdSet
    while queue is not empty:
        remove vertex v from the queue
        if v has hyponyms:
            for each hyponym h of vertex v:
                if h is not marked:
                    add h to queue
                    mark h
                    add h to resultIdSet
    return resultSet

# Complexity
What is the input?
How does the time and space complexity change with the input?
What are the bounds?

## Read file
time: O(N)
space: O(N)

## Graph
time: 
    find id O(1)
    add vertex O(V)
    add hyponyms O(E)
    total: O(V+E)
space: O(V+E)

## HyponymsHandler

### Traversal
time:
    BFS: O(V+E)
    find corresponding word: O(KlogK)
    total: O(V+E)
space: O(N)

#### IdToWord method
time: O(1)
space: O(1)

#### WordToId method
NOTE: If I create one map, this method will be too slow (O(N)). But if I create two maps at first, I can find this one in a faster way
time: O(1)
space: O(K) 
// K is the number of words found

# Questions
## Open Questions
### General Questions
- Poke around the /browser and /main folders and observe what code exists.
    How do the examples in the /demo folder use existing classes/methods?

- How are a user’s inputs (i.e. hyponyms of “cat”, k > 0) communicated to us from the ngordnet website?
    How do we communicate results back to the website?

- You aren’t expected to know how the website works. However, it’s important to understand the boundaries of your implementation.

- Where do tasks overlap? Are there opportunities to reuse methods across different cases?
    Think about helper methods or opportunities to overload methods.

- Can you find groups of data structures and methods that serve a similar, clearly defined purpose?
    If so, consider moving them to a separate class. Building abstraction barriers may be helpful for managing code complexity.

### Considering Edge Cases
- How will your implementation handle words that exist within multiple hyponyms?

- What output do we expect when a list of words are inputted? Under what circumstances is a synset a hyponym of two words?
    What about multiple words?
    How will your implementation determine the correct output?

- What output do we expect when the user provides a k > 0?
    Does this relate to anything we’ve already built?

## Closed Questions

## Test
- when input no word, when input a word doesn't exist in the database
- polysemy and intersection: 多词 Synset 验证，确保查询同义词集中的任意一个词，都能得到完整的结果。
- tie breaking for top k: K 值排序验证，看输出是否真的是降序排列后的前 K 个，且最后按字母序输出。
- zero frequency exclusion: 出现次数为 0 的单词不予显示在结果中