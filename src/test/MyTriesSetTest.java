package test;

import main.LowerCaseCharSet;
import main.MyTriesSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class MyTriesSetTest
{
  private MyTriesSet set;
  private static final String ALL_LOWER_CAPS = "abcdefghijklmnopqrstuvwxyz";


  private boolean compareStrings(Iterable<String> left, Iterable<String> right)
  {
    Set<String> leftSet = new TreeSet<>();
    left.forEach(leftSet::add);

    Set<String> rightSet = new TreeSet<>();
    right.forEach(rightSet::add);

    return rightSet.equals(leftSet);
  }

  @BeforeEach
  void setUp()
  {
    set = new MyTriesSet(new LowerCaseCharSet());
  }

  @AfterEach
  void tearDown()
  {
    set = null;
  }

  @Test
  void contains()
  {
    set.add("item");
    set.add("items");
    set.add(ALL_LOWER_CAPS);
    set.add("az");

    assertTrue(set.contains("item"));
    assertTrue(set.contains("items"));
    assertTrue(set.contains(ALL_LOWER_CAPS));
    assertTrue(set.contains("az"));

    assertFalse(set.contains("itemss"));
    assertFalse(set.contains("ite"));

    assertThrows(IllegalArgumentException.class, ()-> set.contains("TEST"));
    assertThrows(IllegalArgumentException.class, ()-> set.contains(""));
    assertThrows(IllegalArgumentException.class, ()-> set.contains("1"));
    assertThrows(IllegalArgumentException.class, ()-> set.contains("Items"));
  }

  @Test
  void add()
  {
    assertDoesNotThrow(()-> set.add("lowercase"));
    assertDoesNotThrow(()-> set.add("alllowercase"));
    assertDoesNotThrow(()-> set.add(ALL_LOWER_CAPS));
    assertDoesNotThrow(()-> set.add("az"));

    assertThrows(IllegalArgumentException.class, ()-> set.add("A"));
    assertThrows(IllegalArgumentException.class, ()-> set.add("AZ"));
    assertThrows(IllegalArgumentException.class, ()-> set.add("A"));
  }

  @Test
  void keysWithPrefix()
  {
    set.add("testa");
    set.add("testb");
    set.add("testc");
    set.add("testd");
    set.add("testab");
    set.add("testac");

    assertTrue(compareStrings(set.keysWithPrefix("t"), Arrays.asList("testa","testb", "testc", "testd", "testab", "testac")));
    assertTrue(compareStrings(set.keysWithPrefix("te"), Arrays.asList("testa","testb", "testc", "testd", "testab", "testac")));
    assertTrue(compareStrings(set.keysWithPrefix("tes"), Arrays.asList("testa","testb", "testc", "testd", "testab", "testac")));
    assertTrue(compareStrings(set.keysWithPrefix("test"), Arrays.asList("testa","testb", "testc", "testd", "testab", "testac")));
    assertTrue(compareStrings(set.keysWithPrefix("testa"), Arrays.asList("testa", "testab", "testac")));
    assertTrue(compareStrings(set.keysWithPrefix("testb"), Arrays.asList("testb")));

    assertThrows(IllegalArgumentException.class, ()-> set.keysWithPrefix("UPPER_CAPS"));
  }

  @Test
  void keysThatMatch()
  {
    set.add("test");
    set.add("testa");
    set.add("testb");
    set.add("testc");
    set.add("testd");
    set.add("testab");
    set.add("testac");

    assertFalse(set.keysThatMatch("t").iterator().hasNext());
    assertFalse(set.keysThatMatch("t.").iterator().hasNext());
    assertFalse(set.keysThatMatch("t..").iterator().hasNext());

    assertTrue(set.keysThatMatch("t...").iterator().hasNext());

    assertTrue(compareStrings(set.keysThatMatch("t..."), Arrays.asList("test")));
    assertTrue(compareStrings(set.keysThatMatch("te.."), Arrays.asList("test")));
    assertTrue(compareStrings(set.keysThatMatch("tes."), Arrays.asList("test")));
    assertTrue(compareStrings(set.keysThatMatch("test"), Arrays.asList("test")));
    assertTrue(compareStrings(set.keysThatMatch("test."), Arrays.asList("testa", "testb", "testc", "testd")));
    assertTrue(compareStrings(set.keysThatMatch("....."), Arrays.asList("testa", "testb", "testc", "testd")));
    assertTrue(compareStrings(set.keysThatMatch("test.."), Arrays.asList("testab", "testac")));
    assertTrue(compareStrings(set.keysThatMatch("......"), Arrays.asList("testab", "testac")));
  }

  @Test
  void getNode()
  {
  }
}