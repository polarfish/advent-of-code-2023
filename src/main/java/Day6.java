import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day6 extends Day {

    public static void main(String[] args) {
        Day6 day = new Day6();  // https://adventofcode.com/2023/day/6

        String sample = readFile("%s_sample.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(288, day.part1(sample));
        assertEquals(781200, day.part1(full));

        assertEquals(71503, day.part2(sample));
        assertEquals(49240091, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }

    @Override
    public String part1(String input) {
        long[][] table = input.lines()
            .map(line -> Arrays.stream(line.trim().split("\\s+"))
                .skip(1L)
                .mapToLong(Long::parseLong)
                .toArray())
            .toArray(long[][]::new);
        long result = solvePart(table);
        return String.valueOf(result);
    }

    @Override
    public String part2(String input) {
        long[][] table = input.lines()
            .map(line -> Arrays.stream(line.replaceAll(" ", "").split(":"))
                .skip(1L)
                .mapToLong(Long::parseLong)
                .toArray())
            .toArray(long[][]::new);
        long result = solvePart(table);
        return String.valueOf(result);
    }

    private long solvePart(long[][] table) {
        return IntStream.range(0, table[0].length)
            .mapToLong(i ->
                LongStream.range(1, table[0][i] - 1)
                    .parallel()

                    .count()
            )
            .reduce(1, (i1, i2) -> i1 * i2);
    }
}
