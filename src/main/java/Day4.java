import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 extends Day {

    public static void main(String[] args) {
        Day4 day = new Day4();  // https://adventofcode.com/2023/day/4

        String sample = readFile("%s_sample.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(13, day.part1(sample));
        assertEquals(25231, day.part1(full));

        assertEquals(30, day.part2(sample));
        assertEquals(9721255, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }


    @Override
    public String part1(String input) {
        int result = input.lines()
            .mapToInt(line -> {
                String[] split = line.split(": ")[1].split(" \\| ");
                Set<Integer> winningNumbers = Arrays.stream(split[0].trim().split("\\s+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
                Set<Integer> yourNumbers = Arrays.stream(split[1].trim().split("\\s+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
                yourNumbers.retainAll(winningNumbers);
                return yourNumbers.isEmpty()
                    ? 0
                    : 1 << (yourNumbers.size() - 1);
            })
            .sum();

        return String.valueOf(result);
    }

    @Override
    public String part2(String input) {
        int[] arrWinningCards = input.lines()
            .mapToInt(line -> {
                String[] split = line.split(": ")[1].split(" \\| ");
                Set<Integer> winningNumbers = Arrays.stream(split[0].trim().split("\\s+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
                Set<Integer> yourNumbers = Arrays.stream(split[1].trim().split("\\s+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
                yourNumbers.retainAll(winningNumbers);

                return yourNumbers.size();
            })
            .toArray();

        int[] arrCardsCount = new int[arrWinningCards.length];
        Arrays.fill(arrCardsCount, 1);

        for (int i = 0; i < arrWinningCards.length; i++) {
            for (int j = 0; j < arrWinningCards[i]; j++) {
                arrCardsCount[i + j + 1] += arrCardsCount[i];
            }
        }

        int totalCards = Arrays.stream(arrCardsCount).sum();

        return String.valueOf(totalCards);
    }
}
