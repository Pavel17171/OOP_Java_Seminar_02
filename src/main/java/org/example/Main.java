package org.example;

import org.example.Base.BasePlayer;
import org.example.Obstacle.Track;
import org.example.Obstacle.Wall;
import org.example.Players.Bot;
import org.example.Players.Cat;
import org.example.Players.Human;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // Создание игроков
        Cat Barsik = new Cat("Barsik");
        Human Bob = new Human("Bob");
        Bot Android = new Bot("Android");

        // Информация об участниках
        printBanner("Список участников:", '*');
        Barsik.printInfo();
        Barsik.active();
        Bob.printInfo();
        Bob.active();
        Android.printInfo();
        Android.active();
        System.out.println("**********************************************");

        // Массив игроков
        ArrayList<BasePlayer> arrayPlayers = new ArrayList<>();
        arrayPlayers.add(Barsik);
        arrayPlayers.add(Bob);
        arrayPlayers.add(Android);

        // Создание полосы препятствий
        Object[][] obstacles = createObstacle(10);
        System.out.println("Полоса препядствий:");
        printObstacle(obstacles);

        // Проходжение полосы препятствий
        passingAWay(arrayPlayers, obstacles);

    }

    /**
     * Прохождение дистанции
     *
     * @param arrayPlayers массив участников
     * @param obstacles    массив препятствий
     */
    public static void passingAWay(ArrayList<BasePlayer> arrayPlayers, Object[][] obstacles) {

        // Прохождение полосы препядствий
        int countPlayers = 0; // Количество участников
        boolean flag = true;  // true, пока все участники не выбыли

        // Вывод информации об участниках на старте
        for (BasePlayer player : arrayPlayers) {
            player.printNameEnergy();
            countPlayers++;
        }
        System.out.println("""
                ------------------------------
                Условные обозначения трассы:
                __(10)__ - длина участка 10м
                 |(5)|   - высота преграды 5м""");

        // Участок дистанции от старта до текущего препятствия
        StringBuilder way = new StringBuilder();

        // Берем поочереди препятствия
        for (Object[] objects : obstacles) {
            if (flag) {
                way.append(objects[0]);
                System.out.println("..............................");
                System.out.println("Трасса: " + way);
                if (countPlayers > 0) {

                    // Каждый участник пытается пройти препятствие
                    for (BasePlayer player : arrayPlayers) {

                        // Если участник может участвовать
                        if (player.getFlag()) {
                            if (player.getEnergy() <= 0) {
                                player.tired();
                                player.setFlag(false);
                                countPlayers--;
                            } else {

                                // Если препятствие - дорожка
                                if (objects[2] == "track") {

                                    // Может ли участник пробежать
                                    if ((int) objects[1] <= player.getEnergy()) {
                                        player.setEnergy((Integer) objects[1], 0);
                                    } else {
                                        player.notRun();
                                        player.setFlag(false);
                                        countPlayers--;
                                    }

                                    // Если препятствие стена
                                } else if (objects[2] == "wall") {

                                    // Может ли участник перепрыгнуть
                                    if ((int) objects[1] <= player.getMaxHeight() &&
                                            (int) objects[1] * player.getCoefficient() <= player.getEnergy()) {
                                        player.setEnergy(0, (Integer) objects[1]);
                                    } else {
                                        player.notJump();
                                        player.setFlag(false);
                                        countPlayers--;
                                    }
                                }
                                if (player.getEnergy() > 0) {
                                    player.printNameEnergy();
                                }
                            }
                        }
                    }
                } else {
                    flag = false;
                }
            }
        }
        winner(arrayPlayers, flag);

    }

    /**
     * Рандом числа "int" от min до max включительно
     *
     * @param min минимальное значение (включительно)
     * @param max максимальное значение (включительно)
     * @return возвращает число "int"
     */
    public static int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt(max + 1 - min) + min;

    }

    /**
     * Создание полосы препятствий
     *
     * @param size количество элементов
     * @return возвращает массив препятствий, где:
     * <p>- [i][0] - формат "String" для визуализации,</p>
     * <p>- [i][1] - формат "int" для расчетов,</p>
     * <p>- [i][2] - "String" тип препятствия</p>
     */
    public static Object[][] createObstacle(int size) {
        Object[][] arrayObstacle = new Object[size][3];
        for (int i = 0; i < size; i++) {
            int option = randomNum(1, 2);
            if (option == 1) {
                Track tempTrack = new Track(randomNum(10, 30));
                arrayObstacle[i][0] = tempTrack.getObstacle();
                arrayObstacle[i][1] = tempTrack.getObstacleLength();
                arrayObstacle[i][2] = tempTrack.getObstacleType();
            } else {
                Wall tempWall = new Wall(randomNum(1, 6));
                arrayObstacle[i][0] = tempWall.getObstacle();
                arrayObstacle[i][1] = tempWall.getObstacleHeight();
                arrayObstacle[i][2] = tempWall.getObstacleType();
            }
        }
        return arrayObstacle;
    }

    /**
     * Вывод элементов массива препятствий
     *
     * @param obstacle массив препятствий
     */
    static void printObstacle(Object[][] obstacle) {
        for (Object[] objects : obstacle) {
            System.out.print(objects[0]);
        }
        System.out.println();
    }

    /**
     * Печать баннера (текст с обрамлением)
     *
     * @param s      текст баннера
     * @param symbol символ обрамления баннера
     */
    public static void printBanner(String s, Character symbol) {
        int count = s.length() + 4;
        System.out.println();
        System.out.println(String.format("%" + count + "s", "").replace(' ', symbol));
        System.out.println(symbol + " " + s + " " + symbol);
        System.out.println(String.format("%" + count + "s", "").replace(' ', symbol));
    }

    /**
     * Определение победителя
     *
     * @param array массив участников
     * @param flag  есть ли финишировавшие
     */
    public static void winner(ArrayList<BasePlayer> array, boolean flag) {
        String answer = "\nНикто не финишировал";
        if (flag) {
            // Находим игрока с максимальным уровнем энергии
            double max = 0;

            BasePlayer win = array.get(0);
            for (BasePlayer player : array) {
                if (player.getEnergy() > max) {
                    max = player.getEnergy();
                    win = player;
                }
            }
            if (win.getEnergy() > 0) {
                printBanner(("Победил " + win.getName()), '*');
            } else {
                System.out.println(answer);
            }
        } else {
            System.out.println(answer);
        }
    }
}