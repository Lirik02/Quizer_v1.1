package by.Kirill_Chigiryov.quizer.task_generators;

import by.Kirill_Chigiryov.quizer.Main;
import by.Kirill_Chigiryov.quizer.tasks.Tasks;

import java.util.*;

public class TaskGenerators {

    public static class ExpressionTaskGenerator implements Main.TaskGenerator {
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param generateSum            разрешить генерацию с оператором +
         * @param generateDifference     разрешить генерацию с оператором -
         * @param generateMultiplication разрешить генерацию с оператором *
         * @param generateDivision       разрешить генерацию с оператором /
         */

        public ExpressionTaskGenerator(int minNumber,
                                       int maxNumber,
                                       boolean generateSum,
                                       boolean generateDifference,
                                       boolean generateMultiplication,
                                       boolean generateDivision) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.generateDifference = generateDifference;
            this.generateDivision = generateDivision;
            this.generateSum = generateSum;
            this.generateMultiplication = generateMultiplication;
            generator();
        }

        private void generator() {
            if (!generateDifference &&
                    !generateDivision &&
                    !generateMultiplication &&
                    !generateSum || (maxNumber < minNumber)) {
                System.out.println(Main.Result.INCORRECT_INPUT);
            }

            int signCount = 0;
            int firstNumber = 0;
            int secondNumber = 0;
            String sign;
            ArrayList<String> signs = new ArrayList<>();
            while (firstNumber == 0) {
                firstNumber = generateRandomNumber(minNumber, maxNumber);
            }
            while (secondNumber == 0) {
                secondNumber = generateRandomNumber(minNumber, maxNumber);
            }
            if (generateDifference) {
                signCount++;
                signs.add("-");
            }
            if (generateDivision) {
                signCount++;
                signs.add("/");
            }
            if (generateMultiplication) {
                signCount++;
                signs.add("*");
            }
            if (generateSum) {
                signCount++;
                signs.add("+");
            }

            sign = signs.get((int) (Math.random() * (signCount)));

            if (sign.equals("/") &&
                    Math.abs(firstNumber) <= Math.abs(secondNumber)) {
                int temp = firstNumber;
                firstNumber = secondNumber;
                secondNumber = temp;
            }

            switch (sign) {
                case "+" -> rightAnswer =
                        String.valueOf(firstNumber + secondNumber);
                case "-" -> rightAnswer =
                        String.valueOf(firstNumber - secondNumber);
                case "*" -> rightAnswer =
                        String.valueOf(firstNumber * secondNumber);
                case "/" -> {
                    int reminder = firstNumber % secondNumber;
                    if (reminder != 0) {
                        if ((firstNumber +
                                (secondNumber - reminder)) <= maxNumber) {
                            firstNumber = firstNumber +
                                    (secondNumber - reminder);
                        } else {
                            firstNumber -= reminder;
                        }
                    }
                    rightAnswer = String.valueOf(firstNumber / secondNumber);
                }
            }

            if (firstNumber < 0 && secondNumber > 0) {
                taskQuestion =
                        "(" + firstNumber + ")" + sign + secondNumber + "=?";
            } else if (firstNumber > 0 && secondNumber < 0) {
                taskQuestion =
                        firstNumber + sign + "(" + secondNumber + ")" + "=?";
            } else if (firstNumber < 0) {
                taskQuestion = "(" + firstNumber + ")" +
                        sign + "(" + secondNumber + ")" + "=?";
            } else {
                taskQuestion = firstNumber + sign + secondNumber + "=?";
            }

        }

        int generateRandomNumber(int minNumber, int maxNumber) {
            return minNumber +
                    (int) (Math.random() * (maxNumber - minNumber + 1));
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        public Tasks.ExpressionTask generate() {
            String thisTaskQuestion = taskQuestion;
            String thisRightAnswer = rightAnswer;
            generator();
            return new Tasks.ExpressionTask(thisTaskQuestion, thisRightAnswer);
        }

        private String rightAnswer;
        private String taskQuestion;
        private final int minNumber;
        private final int maxNumber;
        private final boolean generateSum;
        private final boolean generateDifference;
        private final boolean generateMultiplication;
        private final boolean generateDivision;

    }

    public static class EquationTaskGenerator implements Main.TaskGenerator {
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param generateSum            разрешить генерацию с оператором +
         * @param generateDifference     разрешить генерацию с оператором -
         * @param generateMultiplication разрешить генерацию с оператором *
         * @param generateDivision       разрешить генерацию с оператором /
         */
        public EquationTaskGenerator(int minNumber,
                                     int maxNumber,
                                     boolean generateSum,
                                     boolean generateDifference,
                                     boolean generateMultiplication,
                                     boolean generateDivision) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.generateDifference = generateDifference;
            this.generateDivision = generateDivision;
            this.generateSum = generateSum;
            this.generateMultiplication = generateMultiplication;
            generator();
        }

        private void generator() {
            int signCount = 0;
            int xPosition;
            int firstNumber = generateRandomNumber(minNumber, maxNumber);
            int secondNumber = generateRandomNumber(minNumber, maxNumber);

            if (firstNumber == 0) {
                firstNumber++;
            }
            if (secondNumber == 0) {
                secondNumber++;
            }

            String sign;
            ArrayList<String> signs = new ArrayList<String>();
            if (generateDifference) {
                signCount++;
                signs.add("-");
            }
            if (generateDivision) {
                signCount++;
                signs.add("/");
            }
            if (generateMultiplication) {
                signCount++;
                signs.add("*");
            }
            if (generateSum) {
                signCount++;
                signs.add("+");
            }

            int randomSign = (int) (Math.random() * (signCount));

            sign = signs.get(randomSign);

            xPosition = (int) (Math.random() * 2);

            if (sign.equals("+")) {
                rightAnswer = String.valueOf(secondNumber - firstNumber);
            }

            if (sign.equals("-") && xPosition == 1) {
                rightAnswer = String.valueOf(firstNumber - secondNumber);
            } else if (sign.equals("-") && xPosition == 0) {
                rightAnswer = String.valueOf(firstNumber + secondNumber);
            }

            if (sign.equals("*")) {
                if (firstNumber < secondNumber) {
                    int temp = firstNumber;
                    firstNumber = secondNumber;
                    secondNumber = temp;
                }
                int reminder = secondNumber % firstNumber;
                if (reminder != 0) {
                    if ((secondNumber +
                            (firstNumber - reminder)) <= maxNumber) {
                        secondNumber = secondNumber + (firstNumber - reminder);
                    } else {
                        secondNumber -= reminder;
                    }
                }
                rightAnswer = String.valueOf(secondNumber / firstNumber);
//                if (secondNumber % firstNumber != 0) {
//                    while (secondNumber % firstNumber != 0) {
//                        firstNumber++;
//                    }
//                }
//                rightAnswer = String.valueOf(secondNumber / firstNumber);
            }

            if (sign.equals("/") && xPosition == 1) {
                if (secondNumber < firstNumber) {
                    int temp = secondNumber;
                    secondNumber = firstNumber;
                    firstNumber = temp;
                }
                int reminder = firstNumber % secondNumber;
                if (reminder != 0) {
                    if ((firstNumber +
                            (secondNumber - reminder)) <= maxNumber) {
                        firstNumber = firstNumber + (secondNumber - reminder);
                    } else {
                        firstNumber -= reminder;
                    }
                }
                rightAnswer = String.valueOf(firstNumber / secondNumber);
//                if (firstNumber % secondNumber != 0) {
//                    while (firstNumber % secondNumber != 0) {
//                        secondNumber++;
//                    }
//                }
//                rightAnswer = String.valueOf(firstNumber / secondNumber);
            } else if (sign.equals("/") && xPosition == 0) {
                rightAnswer = String.valueOf(firstNumber * secondNumber);
            }

            if (firstNumber < 0 && secondNumber > 0 && xPosition == 0) {
                taskQuestion =
                        "x" + sign + "(" + firstNumber + ")=" + secondNumber;
            } else if (firstNumber < 0 && secondNumber < 0 && xPosition == 0) {
                taskQuestion = "x" + sign +
                        "(" + firstNumber + ")=(" + secondNumber + ")";
            } else if (firstNumber > 0 && secondNumber < 0 && xPosition == 0) {
                taskQuestion =
                        "x" + sign + firstNumber + "=(" + secondNumber + ")";
            } else if (firstNumber > 0 && secondNumber > 0 && xPosition == 0) {
                taskQuestion = "x" + sign + firstNumber + "=" + secondNumber;
            }

            if (firstNumber < 0 && secondNumber > 0 && xPosition == 1) {
                taskQuestion =
                        "(" + firstNumber + ")" + sign + "x=" + secondNumber;
            } else if (firstNumber < 0 && secondNumber < 0 && xPosition == 1) {
                taskQuestion = "(" + firstNumber + ")" +
                        sign + "x=(" + secondNumber + ")";
            } else if (firstNumber > 0 && secondNumber < 0 && xPosition == 1) {
                taskQuestion = firstNumber + sign + "x=(" + secondNumber + ")";
            } else if (firstNumber > 0 && secondNumber > 0 && xPosition == 1) {
                taskQuestion = firstNumber + sign + "x=" + secondNumber;
            }
        }

        int generateRandomNumber(int minNumber, int maxNumber) {
            return minNumber +
                    (int) (Math.random() * (maxNumber - minNumber + 1));
        }

        /**
         * return задание типа {@link EquationTask}
         */
        public Tasks.EquationTask generate() {
            String thisTaskQuestion = taskQuestion;
            String thisRightAnswer = rightAnswer;
            generator();
            return new Tasks.EquationTask(thisTaskQuestion, thisRightAnswer);
        }

        private String rightAnswer;
        private String taskQuestion;
        private final int minNumber;
        private final int maxNumber;
        private final boolean generateSum;
        private final boolean generateDifference;
        private final boolean generateMultiplication;
        private final boolean generateDivision;

    }


    public static class GroupTaskGenerator implements Main.TaskGenerator {
        /**
         * Конструктор с переменным числом аргументов
         *
         * @param generators генераторы, которые в конструктор передаются через запятую
         */
        public GroupTaskGenerator(Main.TaskGenerator... generators) {
            tasks = new ArrayList<>();
            for (int i = 0; i < generators.length; i++) {
                tasks.add(generators[i].generate());
                taskIDs.add(i);
            }
            taskID = (int) (Math.random() * generators.length);
        }

        /**
         * Конструктор, который принимает коллекцию генераторов
         *
         * @param generators генераторы, которые передаются в конструктор в Collection (например, {@link ArrayList})
         */
        public GroupTaskGenerator(Collection<Main.TaskGenerator> generators) {
            taskGenerators = new ArrayList<>(generators.stream().toList());
            tasks = new ArrayList<>();
            for (int i = 0; i < generators.size(); ++i) {
                taskIDs.add(i);
                tasks.add(taskGenerators.get(i).generate());
            }
            taskID = (int) (Math.random() * taskIDs.size());
        }

        /**
         * @return результат метода generate() случайного генератора из списка.
         * Если этот генератор выбросил исключение в методе generate(), выбирается другой.
         * Если все генераторы выбрасывают исключение, то и тут выбрасывается исключение.
         */
        public Main.Task generate() {
            if (taskIDs.size() == 1) {
                int ID = taskIDs.get(0);
                taskIDs.remove(0);
                return tasks.get(ID);
            }
            int thisTaskID = taskID;
            if (thisTaskID == taskIDs.size()) {
                thisTaskID--;
            }
            Main.Task task = tasks.get(taskIDs.get(thisTaskID));
            taskIDs.remove(thisTaskID);
            taskID = (int) (Math.random() * taskIDs.size());
            return task;
        }

        private List<Main.Task> tasks;
        private List<Main.TaskGenerator> taskGenerators;
        private int taskID;
        private List<Integer> taskIDs = new ArrayList<>();

    }


    public static class PoolTaskGenerator implements Main.TaskGenerator {
        /**
         * Конструктор с переменным числом аргументов
         *
         * @param allowDuplicate разрешить повторения
         * @param tasks          задания, которые в конструктор передаются через запятую
         */
        public PoolTaskGenerator(boolean allowDuplicate, Main.Task... tasks) {
            this.tasks = new Main.Task[tasks.length];
            if (allowDuplicate) {
                System.arraycopy(tasks, 0, this.tasks, 0, tasks.length);
            } else {
                List<Main.Task> temp = new ArrayList<>(Arrays.asList(tasks));
                this.tasks = (Main.Task[]) temp.stream().distinct().toArray();
            }
        }

        /**
         * Конструктор, который принимает коллекцию заданий
         *
         * @param allowDuplicate разрешить повторения
         * @param tasks          задания, которые передаются в конструктор в Collection (например, {@link LinkedList})
         */
        public PoolTaskGenerator(boolean allowDuplicate,
                                 Collection<Main.Task> tasks) {
            if (allowDuplicate) {
                this.tasks = (Main.Task[]) tasks.toArray();
            } else {
                this.tasks = (Main.Task[]) tasks.stream().distinct().toArray();
            }
            taskID = (int) (Math.random() * tasks.size());
        }

        /**
         * @return случайная задача из списка
         */
        public Main.Task generate() {
            return tasks[taskID];
        }

        private Main.Task[] tasks;
        private int taskID;

    }

//    public static void main(String[] args) throws InterruptedException {
//        GroupTaskGenerator taskGenerator = new GroupTaskGenerator(
//                        new TaskGenerators.ExpressionTaskGenerator(-50, -5,
//                                false, false, false, true),
//                        new TaskGenerators.EquationTaskGenerator(-50, -5,
//                                false, false, false, true),
//                        new TaskGenerators.ExpressionTaskGenerator(-50, -5,
//                        false, false, false, true),
//                        new TaskGenerators.EquationTaskGenerator(-50, -5,
//                        false, false, false, true));
//        Collection<Main.TaskGenerator> taskCollection = new ArrayList<>();
//        taskCollection.add(new TaskGenerators.ExpressionTaskGenerator(-50, -5,
//                false, false, false, true));
//        taskCollection.add(new TaskGenerators.EquationTaskGenerator(-50, -5,
//                false, false, false, true));
//        taskCollection.add(new TaskGenerators.ExpressionTaskGenerator(-50, -5,
//                false, false, false, true));
//        taskCollection.add(new TaskGenerators.EquationTaskGenerator(-50, -5,
//                false, false, false, true));
//        GroupTaskGenerator taskGenerator1 = new TaskGenerators.GroupTaskGenerator(taskCollection);
//        Main.Task[] arr = new Main.Task[4];
//        for (int i = 0; i < 4; i++) {
//            arr[i] = taskGenerator1.generate();
//        }
//    }
}
