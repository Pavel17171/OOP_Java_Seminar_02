package org.example.Base;

public abstract class BasePlayer implements Action {
    protected String name;
    private final int maxDistance;
    private final int maxHeight;
    protected double coefficient;
    protected double maxEnergy;
    protected double tempEnergy;
    protected String type;
    protected boolean flag;

    /**
     * Параметры игрока
     *
     * @param name        Имя игрока
     * @param maxDistance максимальное расстояние, которое может пробежать
     * @param maxHeight   максимальная высота прыжка
     * @param coefficient коэффициент расхода энергии при прыжке
     * @param type        тип игрока
     *                    <p>----------------------------------</p>
     *                    <p>При создании персонажа:</p>
     *                    <p>maxEnergy = maxDistance;</p>
     *                    <p>tempEnergy = maxEnergy;</p>
     *                    <p>flag = true;</p>
     */
    public BasePlayer(String name, int maxDistance, int maxHeight, double coefficient, String type) {
        this.name = name;
        this.maxDistance = maxDistance;
        this.maxHeight = maxHeight;
        this.coefficient = coefficient;
        this.type = type;
        this.maxEnergy = maxDistance;
        this.tempEnergy = maxEnergy;
        this.flag = true;

    }

    /**
     * Возвращает имя игрока
     *
     * @return возвращает "String" имя игрока
     */
    public String getName() {
        return this.name;
    }

    /**
     * Полная информации об игроке
     *
     * @return возвращает строку "String" с информацией:
     * <p>- тип;</p>
     * <p>- имя;</p>
     * <p>- максимальное расстояние;</p>
     * <p>- максимальная высота прыжка;</p>
     * <p>- уровень энергии (%).</p>
     */
    public String getInfo() {
        return String.format("%s %s, max run distance = %dм, max jump height = %dм, energy - %.1f%%",
                this.type, this.name, this.maxDistance, this.maxHeight, powerPercent());
    }

    /**
     * Выводит всю информацию об игроке
     */
    public void printInfo() {
        System.out.println(getInfo());
    }

    /**
     * Информация о типе, имени и уровне энергии игрока
     *
     * @return возвращает строку "String" с информацией
     */
    public String getNameEnergy() {
        return String.format("%s %s преодолел препятствие, осталось энергии: %.1f%%",
                this.type, this.name, powerPercent());
    }

    /**
     * Выводит информацию об игроке (имя и энергия)
     */
    public void printNameEnergy() {
        System.out.println(getNameEnergy());
    }


    /**
     * Рассчет уровня энергии игрока
     *
     * @return возвращает "double" уровень энергии (%)
     */
    protected double powerPercent() {
        return ((this.tempEnergy / this.maxEnergy) * 100);
    }

    /**
     * Количество энергии
     *
     * @return возвращает "double" количество энергии
     */
    public double getEnergy() {
        return this.tempEnergy;
    }

    /**
     * Редактирует остаток энергии игрока
     *
     * @param a длина дорожки
     * @param b высота стены
     */
    public void setEnergy(int a, int b) {
        this.tempEnergy = this.tempEnergy - a - b * this.coefficient;
    }

    /**
     * Максимальная высота прыжка
     *
     * @return возвращает "double" коэффициент
     * <p>(требуется для расчета усталости при прыжке)</p>
     */
    public double getCoefficient() {
        return this.coefficient;
    }

    /**
     * Максимальная высота прыжка
     *
     * @return возвращает "int" максимальную высоту прыжка
     */
    public int getMaxHeight() {
        return this.maxHeight;
    }

    /**
     * Проверка на возможность пройти препятствие
     *
     * @return возвращает "boolean" состояние флага:
     * <p>- true - можно продолжать;</p>
     * <p>- false - участник сошел с дистанции</p>
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Редактирование флага (true/false)
     * <p>(переключение энергии: 100%/0%)</p>
     *
     * @param status ввести состояние флага:
     *               <p>true - игрок участвует, энергия 100%;</p>
     *               <p>false - игрок выбывает, энергия 0%</p>
     */
    public void setFlag(boolean status) {
        if (status) {
            this.tempEnergy = this.maxEnergy;
            flag = true;
        } else {
            this.tempEnergy = 0.0;
            flag = false;

        }
    }

    /**
     * Участник не может пробежать
     */
    @Override
    public void notRun() {
        System.out.println(this.type + " " + this.name + " не может столько пробежать и выбывает");
    }

    /**
     * Участник не может перепрыгнуть
     */
    @Override
    public void notJump() {
        System.out.println(this.type + " " + this.name + " не может так высоко прыгнуть и выбывает");
    }

    /**
     * Участник полон сил
     */
    @Override
    public void active() {
        System.out.println(this.type + " " + this.name + " полон сил");
    }

    /**
     * Участник устал
     */
    @Override
    public void tired() {
        System.out.println(this.type + " " + this.name + " устал и выбывает");
    }
}