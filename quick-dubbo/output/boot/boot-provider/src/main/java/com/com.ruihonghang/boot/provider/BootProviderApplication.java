packagecom.com.ruihonghang.boot.provider;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Hello world!
 *
 */
@EnableDubbo
@SpringBootApplication
public class BootProviderApplication
{
    public static void main(String[] args) {
        SpringApplication.run(BootProviderApplication.class, args);
    }
}

