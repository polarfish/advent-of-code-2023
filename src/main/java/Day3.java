import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day3 extends Day {

    public static void main(String[] args) {
        Day3 day = new Day3();  // https://adventofcode.com/2023/day/3

        String sample = readFile("%s_sample.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(4361, day.part1(sample));
        assertEquals(535351, day.part1(full));

        assertEquals(467835, day.part2(sample));
        assertEquals(87287096, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }

    @Override
    public String part1(String input) {
        int partNumberSum = parseSchematic(input).engineParts()
            .stream()
            .mapToInt(EnginePart::number)
            .sum();
        return String.valueOf(partNumberSum);
    }

    @Override
    public String part2(String input) {
        int partNumberSum = parseSchematic(input).gears()
            .stream()
            .mapToInt(g -> g.part1().number() * g.part2().number())
            .sum();
        return String.valueOf(partNumberSum);
    }

    record EnginePart(int number, int x1, int x2, int y, List<Symbol> symbols) {

    }

    record Symbol(char symbol, int x, int y) {

    }

    record Gear(int x, int y, EnginePart part1, EnginePart part2) {

    }

    record Schematic(List<EnginePart> engineParts, List<Gear> gears) {

    }

    private Schematic parseSchematic(String input) {
        char[][] a = input.lines().map(String::toCharArray).toArray(char[][]::new);
        List<EnginePart> engineParts = new ArrayList<>();
        Map<Symbol, List<EnginePart>> potentialGears = new HashMap<>();
        for (int y = 0; y < a.length; y++) {
            boolean readingNumber = false;
            int number = 0;
            int x1 = 0;
            List<Symbol> symbols = new ArrayList<>();
            for (int x = 0; x < a[y].length; x++) {
                if (Character.isDigit(a[y][x])) {
                    number = number * 10 + Character.getNumericValue(a[y][x]);
                    if (!readingNumber) {
                        readingNumber = true;
                        x1 = x;
                        readNeighbor(a, x - 1, y - 1, symbols);
                        readNeighbor(a, x - 1, y, symbols);
                        readNeighbor(a, x - 1, y + 1, symbols);
                    }

                    readNeighbor(a, x, y - 1, symbols);
                    readNeighbor(a, x, y + 1, symbols);
                } else {
                    if (readingNumber) {
                        readingNumber = false;
                        readNeighbor(a, x, y - 1, symbols);
                        readNeighbor(a, x, y, symbols);
                        readNeighbor(a, x, y + 1, symbols);
                        if (!symbols.isEmpty()) {
                            EnginePart part = new EnginePart(number, x1, x - 1, y, symbols);
                            engineParts.add(part);
                            symbols.stream()
                                .filter(s -> s.symbol() == '*')
                                .forEach(s -> potentialGears.computeIfAbsent(s, k -> new ArrayList<>()).add(part));
                        }
                        number = 0;
                        symbols = new ArrayList<>();
                    }
                }
            }

            if (readingNumber && !symbols.isEmpty()) {
                EnginePart part = new EnginePart(number, x1, a[y].length - 1, y, symbols);
                engineParts.add(part);
                symbols.stream()
                    .filter(s -> s.symbol() == '*')
                    .forEach(s -> potentialGears.computeIfAbsent(s, k -> new ArrayList<>()).add(part));
            }
        }

        List<Gear> gears = potentialGears.entrySet().stream()
            .filter(e -> e.getValue().size() == 2)
            .map(e -> new Gear(e.getKey().x(), e.getKey().y(), e.getValue().get(0), e.getValue().get(1)))
            .toList();

        return new Schematic(engineParts, gears);
    }

    private void readNeighbor(char[][] a, int x, int y, List<Symbol> symbols) {
        if (y >= 0 && x >= 0 && y < a.length && x < a[y].length && !Character.isDigit(a[y][x]) && '.' != a[y][x]) {
            symbols.add(new Symbol(a[y][x], x, y));
        }
    }

}
