package net.jayce.examples.collections;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtilTest{

    @Test
    public void testListOfString() {
        Assert.assertEquals(2, List.of("Hello", "World").size());
    }

    @Test
    public void testListOfInteger() {
        Assert.assertEquals(4, List.of(1,2,3,4).size());
    }

    @Test
    public void testDropWhile() throws Exception {
        final long count = Stream.of(1, 2, 3, 4, 5)
                .dropWhile(i -> i % 2 != 0)
                .count();
        Assert.assertEquals(4, count);
    }

    @Test
    public void testTakeWhile() throws Exception {
        final long count = Stream.of(1, 2, 3, 4, 5)
                .takeWhile(i -> i % 2 != 0)
                .count();
        Assert.assertEquals(1, count);
    }

    @Test
    public void testIterate() throws Exception {
        final Stream<Integer> stream =
                Stream.iterate(2, i -> i <= 10, i -> i + 2);
        Assert.assertEquals(5, stream.count());
    }

    @Test
    public void testSimpleFiltering() throws Exception {
        final Set<String> result = Stream.of("a", "bc", "def")
                .collect(Collectors.filtering(
                        v -> v.length() > 1,
                        Collectors.toSet()));

        Assert.assertEquals(Stream.of("a", "bc", "def").filter(v->v.length() > 1).collect(Collectors.toSet()), result);
    }

    //Word count example
    @Test
    public void testComplicatedFiltering() throws Exception {
        final List<String> users = List.of(
                "Text","messaging,","or","texting,","is","the","act","of","composing","and","sending","electronic","messages,","typically","consisting","of","alphabetic","and","numeric","characters,","between","two","or","more","users","of","mobile","devices,","desktops/laptops,","or","other","type","of","compatible","computer.","Text","messages","may","be","sent","over","a","cellular","network,","or","may","also","be","sent","via","an","Internet","connection"
        );
        Map<String, Integer> result = users
                .stream()
                .flatMap(e -> Arrays.stream(e.split("")).map(String::toLowerCase).map(String::trim))
                .filter(r -> r.charAt(0) > 'a' && r.charAt(0) <'z')
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet().stream().map(l -> Map.entry(l.getKey(), l.getValue().size()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
    }
}