package org.example.Base;

public abstract class BaseObstacle {
    protected String typeOfObstacle;
    protected int length;
    protected int height;
    protected boolean flag;

    /**
     * Параметры претпятствия
     *
     * @param typeOfObstacle тип препятствия
     * @param length         длина препятствия
     * @param height         высота препятствия
     *                       <p>----------------------------------</p>
     *                       <p>При создании препятствий</p>
     *                       <p>flag = true;</p>
     */
    public BaseObstacle(String typeOfObstacle, int length, int height) {
        this.typeOfObstacle = typeOfObstacle;
        this.length = length;
        this.height = height;
        this.flag = true;
    }

    /**
     * Длина препядствия
     *
     * @return возвращает длину "int"
     */
    public int getObstacleLength() {
        return this.length;
    }

    /**
     * Высота препядствия
     *
     * @return возвращает высоту "int"
     */
    public int getObstacleHeight() {
        return this.height;
    }

    /**
     * Тип препядствия
     *
     * @return возвращает тип "String"
     */
    public String getObstacleType() {
        return this.typeOfObstacle;
    }

    /**
     * Строковое представление препятствия
     *
     * @return возвращает "String" представление препятствия
     */
    public String getObstacle() {
        String s;
        if (length != 0) {
            s = String.format("__(%d)__", this.length);
        } else {
            s = String.format("|(%d)|", this.height);
        }
        return s;
    }
}