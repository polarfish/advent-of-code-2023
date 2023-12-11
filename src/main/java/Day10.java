import java.util.HashSet;
import java.util.Set;

public class Day10 extends Day {

    public static void main(String[] args) {
        Day10 day = new Day10();  // https://adventofcode.com/2023/day/10

        String sample1 = readFile("%s_sample1.txt".formatted(day.name()));
        String sample2 = readFile("%s_sample2.txt".formatted(day.name()));
        String sample3 = readFile("%s_sample3.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(4, day.part1(sample1));
        assertEquals(8, day.part1(sample2));
        assertEquals(80, day.part1(sample3));
        assertEquals(6842, day.part1(full));

        assertEquals(1, day.part2(sample1));
        assertEquals(1, day.part2(sample2));
        assertEquals(10, day.part2(sample3));
        assertEquals(393, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }


    @Override
    public String part1(String input) {
        char[][] map = buildMap(input);
        Set<Point> pipe = buildPipe(map);
        return String.valueOf(pipe.size() / 2);
    }

    @Override
    public String part2(String input) {
        char[][] map = buildMap(input);

        Set<Point> pipe = buildPipe(map);

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (!pipe.contains(new Point(x, y))) {
                    map[y][x] = '.';
                }
            }
        }

        int result = 0;
        for (int y = 0; y < map.length; y++) {
            boolean isInside = false;
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '.' && isInside) {
                    result += 1;
                } else if (map[y][x] == '|' || map[y][x] == 'J' || map[y][x] == 'L') {
                    isInside = !isInside;
                }
            }
        }

        return String.valueOf(result);
    }

    private Set<Point> buildPipe(char[][] map) {
        Point sPoint = null;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if ('S' == map[y][x]) {
                    sPoint = new Point(x, y);
                    map[y][x] = determinePipe(x, y, map);
                }
            }
        }

        Set<Point> pipe = new HashSet<>();

        Point next;
        Point curr = sPoint;
        Point prev = null;
        while (!pipe.contains(curr)) {
            pipe.add(curr);
            next = determineNextStepPoint(curr, prev, map);
            prev = curr;
            curr = next;
        }
        return pipe;
    }

    private static char[][] buildMap(String input) {
        return input.lines()
            .map(String::toCharArray)
            .toArray(char[][]::new);
    }

    private Point determineNextStepPoint(Point curr, Point prev, char[][] map) {
        return switch (map[curr.y()][curr.x()]) {
            case '|' -> prev == null || prev.y() == curr.y() - 1
                ? new Point(curr.x(), curr.y() + 1)
                : new Point(curr.x(), curr.y() - 1);
            case '-' -> prev == null || prev.x() == curr.x() - 1
                ? new Point(curr.x() + 1, curr.y())
                : new Point(curr.x() - 1, curr.y());
            case 'L' -> prev == null || prev.y() == curr.y() - 1
                ? new Point(curr.x() + 1, curr.y())
                : new Point(curr.x(), curr.y() - 1);
            case 'F' -> prev == null || prev.y() == curr.y() + 1
                ? new Point(curr.x() + 1, curr.y())
                : new Point(curr.x(), curr.y() + 1);
            case 'J' -> prev == null || prev.y() == curr.y() - 1
                ? new Point(curr.x() - 1, curr.y())
                : new Point(curr.x(), curr.y() - 1);
            case '7' -> prev == null || prev.y() == curr.y() + 1
                ? new Point(curr.x() - 1, curr.y())
                : new Point(curr.x(), curr.y() + 1);
            default -> null;
        };

    }

    private char determinePipe(int x, int y, char[][] map) {
        boolean n = y > 0 && (map[y - 1][x] == '|' || map[y - 1][x] == '7' || map[y - 1][x] == 'F');
        boolean s = y < map.length && (map[y + 1][x] == '|' || map[y + 1][x] == 'L' || map[y + 1][x] == 'J');
        boolean w = x > 0 && (map[y][x - 1] == '-' || map[y][x - 1] == 'L' || map[y][x - 1] == 'F');
        boolean e = x < map[0].length && (map[y][x + 1] == '-' || map[y][x + 1] == 'J' || map[y][x + 1] == '7');

        if (n && s) {
            return '|';
        }

        if (n && e) {
            return 'L';
        }

        if (n && w) {
            return 'J';
        }

        if (s && e) {
            return 'F';
        }

        if (s && w) {
            return '7';
        }

        if (e && w) {
            return '-';
        }

        return '?';
    }

    record Point(int x, int y) {

    }

}
