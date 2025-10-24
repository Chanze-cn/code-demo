package cn.chanze.codedemo;

/**
 * 主应用程序类
 * 这是一个简单的Maven项目演示
 */
public class App {
    
    /**
     * 主方法 - 程序入口点
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("欢迎使用Maven项目！");
        System.out.println("这是一个简单的演示项目。");
        
        // 创建一个Calculator实例并演示其功能
        Calculator calculator = new Calculator();
        int a = 10;
        int b = 5;
        
        System.out.println("计算演示：");
        System.out.println(a + " + " + b + " = " + calculator.add(a, b));
        System.out.println(a + " - " + b + " = " + calculator.subtract(a, b));
        System.out.println(a + " * " + b + " = " + calculator.multiply(a, b));
        System.out.println(a + " / " + b + " = " + calculator.divide(a, b));
    }
}