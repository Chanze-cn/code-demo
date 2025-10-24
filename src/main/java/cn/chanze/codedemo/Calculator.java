package cn.chanze.codedemo;

/**
 * 简单的计算器类
 * 提供基本的数学运算功能
 */
public class Calculator {
    
    /**
     * 加法运算
     * @param a 第一个数
     * @param b 第二个数
     * @return 两数之和
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * 减法运算
     * @param a 被减数
     * @param b 减数
     * @return 两数之差
     */
    public int subtract(int a, int b) {
        return a - b;
    }
    
    /**
     * 乘法运算
     * @param a 第一个数
     * @param b 第二个数
     * @return 两数之积
     */
    public int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * 除法运算
     * @param a 被除数
     * @param b 除数
     * @return 两数之商
     * @throws IllegalArgumentException 当除数为0时抛出异常
     */
    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return (double) a / b;
    }
}