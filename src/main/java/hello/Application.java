package hello;

import hello.service.CabBookingService;
import hello.service.CabBookingServiceImpl;
import hello.service.ChatService;
import hello.service.ChatServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    @Bean
    CabBookingService bookingService() {
        return new CabBookingServiceImpl();
    }

    @Bean
    ChatService chatService() {
        return new ChatServiceImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "/booking")
    RemoteExporter hessianBookingService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(bookingService());
        exporter.setServiceInterface(CabBookingService.class);
        return exporter;
    }

    @Bean(name = "/chat")
    RemoteExporter hessianChatService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(chatService());
        exporter.setServiceInterface(ChatService.class);
        return exporter;
    }
}