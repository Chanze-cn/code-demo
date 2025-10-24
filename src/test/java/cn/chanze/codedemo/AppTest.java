package cn.chanze.codedemo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * App类的单元测试
 */
public class AppTest {
    
    @Test
    public void testApp() {
        // 测试App类可以正常实例化
        // 由于main方法是静态的，我们主要测试类是否可以正常加载
        assertDoesNotThrow(() -> {
            Class.forName("cn.chanze.codedemo.App");
        });
    }
}