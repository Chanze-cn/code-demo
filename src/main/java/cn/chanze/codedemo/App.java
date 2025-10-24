package cn.chanze.codedemo;

/**
 * 主应用程序类
 * 这是一个Java学习项目，包含各种编程概念和框架的演示
 */
public class App {
    
    /**
     * 主方法 - 程序入口点
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("=== Java学习代码库 ===");
        System.out.println("欢迎使用Maven项目！");
        System.out.println("这是一个用于学习Java编程的演示项目。\n");
        
        // 演示基础计算器功能
        demonstrateCalculator();
        
        // 演示高并发编程
        demonstrateConcurrency();
        
        System.out.println("\n=== 演示完成 ===");
    }
    
    /**
     * 演示计算器功能
     */
    private static void demonstrateCalculator() {
        System.out.println("--- 基础Java语法演示 ---");
        
        Calculator calculator = new Calculator();
        int a = 10;
        int b = 5;
        
        System.out.println("计算演示：");
        System.out.println(a + " + " + b + " = " + calculator.add(a, b));
        System.out.println(a + " - " + b + " = " + calculator.subtract(a, b));
        System.out.println(a + " * " + b + " = " + calculator.multiply(a, b));
        System.out.println(a + " / " + b + " = " + calculator.divide(a, b));
        
        // 演示异常处理
        try {
            System.out.println("10 / 0 = " + calculator.divide(10, 0));
        } catch (IllegalArgumentException e) {
            System.out.println("异常处理演示: " + e.getMessage());
        }
    }
    
    /**
     * 演示高并发编程
     */
    private static void demonstrateConcurrency() {
        System.out.println("\n--- Java高并发编程演示 ---");
        
        ConcurrencyDemo concurrencyDemo = new ConcurrencyDemo();
        
        try {
            // 运行所有并发演示
            concurrencyDemo.runAllDemonstrations();
        } finally {
            // 确保资源被正确关闭
            concurrencyDemo.shutdown();
        }
    }
}