import java.util.Arrays;
import java.util.stream.IntStream;

public class Day9 extends Day {

    public static void main(String[] args) {
        Day9 day = new Day9();  // https://adventofcode.com/2023/day/9

        String sample = readFile("%s_sample.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(114, day.part1(sample));
        assertEquals(1980437560, day.part1(full));

        assertEquals(2, day.part2(sample));
        assertEquals(977, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }


    @Override
    public String part1(String input) {
        int result = input.lines()
            .map(l -> l.split(" "))
            .map(a -> Arrays.stream(a).mapToInt(Integer::parseInt).toArray())
            .mapToInt(this::predictNextValue)
            .sum();

        return String.valueOf(result);
    }

    @Override
    public String part2(String input) {
        int result = input.lines()
            .map(l -> l.split(" "))
            .map(a -> Arrays.stream(a).mapToInt(Integer::parseInt).toArray())
            .mapToInt(this::predictPreviousValue)
            .sum();

        return String.valueOf(result);
    }

    private int predictNextValue(int[] seq) {

        boolean allZeroes;
        int[] buf = Arrays.copyOf(seq, seq.length);
        int limit = seq.length - 1;
        do {
            allZeroes = true;
            for (int i = 0; i < limit; i++) {
                buf[i] = buf[i + 1] - buf[i];
                if (buf[i] != 0) {
                    allZeroes = false;
                }
            }
            limit -= 1;
        }
        while (!allZeroes);

        return IntStream.of(buf).sum();
    }

    private int predictPreviousValue(int[] seq) {

        boolean allZeroes;
        int[] buf = Arrays.copyOf(seq, seq.length);
        int limit = seq.length - 1;
        int keep;
        do {
            allZeroes = true;
            keep = buf[0];
            for (int i = 0; i < limit; i++) {
                buf[i] = buf[i + 1] - buf[i];
                if (buf[i] != 0) {
                    allZeroes = false;
                }
            }
            buf[limit] = keep;
            limit -= 1;
        }
        while (!allZeroes);

        return IntStream.of(buf).reduce(0, (l, r) -> r - l);
    }
}
