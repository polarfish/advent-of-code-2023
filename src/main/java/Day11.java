import java.util.ArrayList;
import java.util.List;

public class Day11 extends Day {

    public static void main(String[] args) {
        Day11 day = new Day11();  // https://adventofcode.com/2023/day/11

        String sample = readFile("%s_sample.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(374, day.part1(sample));
        assertEquals(10289334, day.part1(full));

        assertEquals(82000210, day.part2(sample));
        assertEquals(649862989626L, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }


    @Override
    public String part1(String input) {
        char[][] map = buildMap(input);
        long result = calculateExpandedDistances(map, 2);
        return String.valueOf(result);
    }


    @Override
    public String part2(String input) {
        char[][] map = buildMap(input);
        long result = calculateExpandedDistances(map, 1000000);
        return String.valueOf(result);
    }

    private static long calculateExpandedDistances(char[][] map, long expansionFactor) {
        int[] columnsGalaxyCount = new int[map[0].length];
        int[] rowsGalaxyCount = new int[map.length];
        List<Galaxy> galaxies = new ArrayList<>();
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] == '#') {
                    galaxies.add(new Galaxy(x, y));
                    columnsGalaxyCount[x] += 1;
                    rowsGalaxyCount[y] += 1;
                }
            }
        }

        long shift;
        long[] columnsGalaxyShift = new long[columnsGalaxyCount.length];
        shift = 0;
        for (int i = 0; i < columnsGalaxyCount.length; i++) {
            if (columnsGalaxyCount[i] == 0) {
                shift += (expansionFactor - 1L);
            }
            columnsGalaxyShift[i] = shift;
        }

        shift = 0;
        long[] rowsGalaxyShift = new long[rowsGalaxyCount.length];
        for (int i = 0; i < rowsGalaxyCount.length; i++) {
            if (rowsGalaxyCount[i] == 0) {
                shift += (expansionFactor - 1L);
            }
            rowsGalaxyShift[i] = shift;
        }

        long result = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                Galaxy g1 = galaxies.get(i);
                Galaxy g2 = galaxies.get(j);
                long distance = Math.abs((g1.x() + columnsGalaxyShift[g1.x()]) - (g2.x() + columnsGalaxyShift[g2.x()]))
                                + Math.abs((g1.y() + rowsGalaxyShift[g1.y()]) - (g2.y() + rowsGalaxyShift[g2.y()]));
                result += distance;
            }
        }

        return result;
    }

    private static char[][] buildMap(String input) {
        return input.lines()
            .map(String::toCharArray)
            .toArray(char[][]::new);
    }

    record Galaxy(int x, int y) {

    }
}
