import java.util.stream.Stream;

public class AllDaysRunner {

    public static void main(String[] args) {
        System.out.println("Running Advent Of Code 2023");
        long totalTime =
            Stream.of(
                new Day1(),
                new Day2(),
                new Day3(),
                new Day4(),

                new Day6(),


                new Day9(),
                new Day10(),
                new Day11(),



                new Day15()
            ).mapToLong(day -> {
                System.out.println();
                return day.run();
            }).sum();

        System.out.println();
        System.out.printf("Total time: %d ms%n", totalTime);
    }

}
