import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LessonMaker {
    public static final String RED = "\033[0;31m";     // RED
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String RESET = "\033[0m";  // Text Reset

    private static final Scanner scanner = new Scanner(System.in);
    private static int exerciseCount = 1;

    public static void main(String[] args) {
        System.out.print("Please enter your name: ");
        String lecturerName = scanner.next();
        System.out.print("Please enter the lesson number: ");
        int lessonNumber = scanner.nextInt();
        File lessonDir = new File(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "_jb_" + lecturerName.toLowerCase() + "_lesson_" + stringPrettify(lessonNumber));
        if (!lessonDir.exists()){
            System.out.print("Making " + lecturerName + "'s lesson " + lessonNumber + " directory, ");
            System.out.println(PURPLE + "don't forget to start recording!" + RESET);
            lessonDir.mkdir();
            System.out.println("Making homework directory");
            new File(lessonDir + "/homework").mkdir();
        } else {
            System.out.println(lecturerName +"'s lesson directory number " + lessonNumber + " already exists");
        }

        while (true) {
            System.out.print("Please enter the next exercise directory name or 'over' to end lesson: ");
            String input = scanner.next();
            if ("over".equals(input.toLowerCase())) {
                System.out.print("Ending lesson, ");
                System.out.println(RED + "please remember to send all files to the students!" + RESET);
                scanner.close();
                return;
            }
            try {
                createExercise(input, lessonDir);
                exerciseCount++;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to create exercise");
            }
        }
    }

    private static void createExercise(String input, File lessonDir) throws IOException {
        System.out.println("Creating exercise '" + input + "'");
        String exerciseDir = stringPrettify(exerciseCount) + " - " + input;
        new File(lessonDir + "/" + exerciseDir).mkdir();
        File exerciseREADMEFile = new File(lessonDir + "/" + exerciseDir + "/README.txt");
        if (!exerciseREADMEFile.createNewFile()) {
            System.out.println("Failed to create a new README.txt file for exercise " + input);
        }
        System.out.print("Please enter an exercise description or press enter to skip: ");
        BufferedWriter writer = new BufferedWriter(new FileWriter(exerciseREADMEFile));
        scanner.nextLine(); // Clean scanner
        String description = scanner.nextLine();
        if (description.isEmpty()) {
            description = "This is exercise " + input + ", no exercise description was provided";
        }
        writer.write(description);
        writer.close();
    }

    public static String stringPrettify(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return String.valueOf(number);
    }
}
