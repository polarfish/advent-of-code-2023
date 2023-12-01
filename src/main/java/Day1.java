import java.util.regex.Pattern;

public class Day1 extends Day {

    public static void main(String[] args) {
        Day1 day = new Day1();  // https://adventofcode.com/2023/day/1

        String sample1 = readFile("%s_sample_1.txt".formatted(day.name()));
        String sample2 = readFile("%s_sample_2.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(142, day.part1(sample1));
        assertEquals(56108, day.part1(full));

        assertEquals(281, day.part2(sample2));
        assertEquals(55652, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }


    @Override
    public String part1(String input) {
        int result = input
            .lines()
            .mapToInt(line -> {
                int first = 0;
                int last = 0;
                int pos = 0;
                for (int i = 0; i < line.length(); i++) {
                    char ch = line.charAt(i);
                    if (Character.isDigit(ch)) {
                        int digit = Character.getNumericValue(ch);
                        if (pos == 0) {
                            first = digit;
                        }
                        last = digit;
                        pos++;
                    }
                }
                return first * 10 + last;
            })
            .sum();

        return String.valueOf(result);
    }

    @Override
    public String part2(String input) {
        return String.valueOf(
            input.lines()
                .mapToInt(this::calculateCalibrationValue)
                .sum());
    }

    private static final String DIGIT_PATTERN_STRING = "(\\d|one|two|three|four|five|six|seven|eight|nine)";
    private static final Pattern PATTERN_FIRST = Pattern.compile(DIGIT_PATTERN_STRING);
    private static final Pattern PATTERN_LAST = Pattern.compile(".*" + DIGIT_PATTERN_STRING);

    private int calculateCalibrationValue(String line) {
        return extractDigit(PATTERN_FIRST, line) * 10 + extractDigit(PATTERN_LAST, line);
    }

    private int extractDigit(Pattern pattern, String line) {
        return pattern
            .matcher(line)
            .results()
            .findFirst()
            .map(m -> m.group(1))
            .map(this::convertToNumericValue)
            .orElse(0);
    }

    private int convertToNumericValue(String value) {
        return switch (value) {
            case "1", "2", "3", "4", "5", "6", "7", "8", "9" -> Character.getNumericValue(value.charAt(0));
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> 0;
        };
    }
}
