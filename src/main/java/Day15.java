import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class Day15 extends Day {

    public static void main(String[] args) {
        Day15 day = new Day15();  // https://adventofcode.com/2023/day/15

        String sample = readFile("%s_sample.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(1320, day.part1(sample));
        assertEquals(513158, day.part1(full));

        assertEquals(145, day.part2(sample));
        assertEquals(200277, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }


    @Override
    public String part1(String input) {
        int result = Arrays.stream(input.split(","))
            .mapToInt(Day15::calculateHash)
            .sum();

        return String.valueOf(result);
    }

    @Override
    public String part2(String input) {
        List<LinkedHashMap<String, Integer>> boxes =
            Stream.generate(() -> new LinkedHashMap<String, Integer>())
                .limit(256)
                .toList();
        Arrays.stream(input.split(","))
            .forEach(step -> {
                if (step.charAt(step.length() - 2) == '=') {
                    String lens = step.substring(0, step.length() - 2);
                    LinkedHashMap<String, Integer> box = boxes.get(calculateHash(lens));
                    box.put(lens, Character.getNumericValue(step.charAt(step.length() - 1)));
                } else {
                    String lens = step.substring(0, step.length() - 1);
                    LinkedHashMap<String, Integer> box = boxes.get(calculateHash(lens));
                    box.remove(lens);
                }
            });

        int totalFocusingPower = 0;
        for (int i = 0; i < boxes.size(); i++) {
            LinkedHashMap<String, Integer> box = boxes.get(i);
            int boxNumber = i + 1;
            int lensNumber = 0;
            for (Entry<String, Integer> lens : box.entrySet()) {
                lensNumber++;
                totalFocusingPower += boxNumber * lensNumber * lens.getValue();
            }
        }

        return String.valueOf(totalFocusingPower);
    }

    private static int calculateHash(String input) {
        int hashValue = 0;
        for (int i = 0; i < input.length(); i++) {
            hashValue += input.charAt(i);
            hashValue *= 17;
            hashValue %= 256;
        }
        return hashValue;
    }
}
