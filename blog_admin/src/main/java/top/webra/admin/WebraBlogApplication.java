package top.webra.admin;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动入口
 * @author webra
 */
@EnableWebSecurity
@SpringBootApplication(scanBasePackages = "top.webra")
@MapperScan("top.webra.mapper")
//@EnableTransactionManagement
//@EnableScheduling
public class WebraBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebraBlogApplication.class,args);
        System.out.println(" __          __    _                          ____   _\n" +
                " \\ \\        / /   | |                        |  _ \\ | |\n" +
                "  \\ \\  /\\  / /___ | |__   _ __  __ _  ______ | |_) || |  ___    __ _\n" +
                "   \\ \\/  \\/ // _ \\| '_ \\ | '__|/ _` ||______||  _ < | | / _ \\  / _` |\n" +
                "    \\  /\\  /|  __/| |_) || |  | (_| |        | |_) || || (_) || (_| |\n" +
                "     \\/  \\/  \\___||_.__/ |_|   \\__,_|        |____/ |_| \\___/  \\__, |\n" +
                "   _____  _                _            _____                   __/ |\n" +
                "  / ____|| |              | |          / ____|                 |___/\n" +
                " | (___  | |_  __ _  _ __ | |_  ______| (___   _   _   ___  ___  ___  ___  ___\n" +
                "  \\___ \\ | __|/ _` || '__|| __||______|\\___ \\ | | | | / __|/ __|/ _ \\/ __|/ __|\n" +
                "  ____) || |_| (_| || |   | |_         ____) || |_| || (__| (__|  __/\\__ \\\\__ \\\n" +
                " |_____/  \\__|\\__,_||_|    \\__|       |_____/  \\__,_| \\___|\\___|\\___||___/|___/");
    }
}
