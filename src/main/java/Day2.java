import java.util.Arrays;

public class Day2 extends Day {

    public static void main(String[] args) {
        Day2 day = new Day2();  // https://adventofcode.com/2023/day/2

        String sample = readFile("%s_sample.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(8, day.part1(sample));
        assertEquals(2617, day.part1(full));

        assertEquals(2286, day.part2(sample));
        assertEquals(59795, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }


    @Override
    public String part1(String input) {
        int result = input
            .lines()
            .mapToInt(line -> {
                String[] game = line.split(":");
                int gameId = Integer.parseInt(game[0].split(" ")[1]);
                String[] gameSets = game[1].split(";");

                for (String gameSet : gameSets) {
                    String[] cubes = gameSet.trim().split(", ");
                    for (String cube : cubes) {
                        String[] countAndColor = cube.split(" ");
                        int cubeCount = Integer.parseInt(countAndColor[0]);
                        String cubeColor = countAndColor[1];
                        int cubeLimit = switch (cubeColor) {
                            case "red" -> 12;
                            case "green" -> 13;
                            case "blue" -> 14;
                            default -> 0;
                        };

                        if (cubeCount > cubeLimit) {
                            return 0;
                        }
                    }
                }

                return gameId;
            }).sum();

        return String.valueOf(result);
    }

    @Override
    public String part2(String input) {
        final int RED = 0;
        final int GREEN = 1;
        final int BLUE = 2;
        int[] fewestPossible = new int[3];
        int result = input
            .lines()
            .mapToInt(line -> {
                String[] gameSets = line.split(":")[1].split(";");
                Arrays.fill(fewestPossible, 0);
                for (String gameSet : gameSets) {
                    String[] cubes = gameSet.trim().split(", ");
                    for (String cube : cubes) {
                        String[] countAndColor = cube.split(" ");
                        int cubeCount = Integer.parseInt(countAndColor[0]);
                        String cubeColor = countAndColor[1];
                        switch (cubeColor) {
                            case "red":
                                fewestPossible[RED] = Math.max(fewestPossible[RED], cubeCount);
                                break;
                            case "green":
                                fewestPossible[GREEN] = Math.max(fewestPossible[GREEN], cubeCount);
                                break;
                            case "blue":
                                fewestPossible[BLUE] = Math.max(fewestPossible[BLUE], cubeCount);
                                break;
                        }
                    }
                }

                return fewestPossible[RED] * fewestPossible[GREEN] * fewestPossible[BLUE];
            }).sum();

        return String.valueOf(result);
    }

}
