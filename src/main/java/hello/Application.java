package hello;

import hello.service.CabBookingService;
import hello.service.CabBookingServiceImpl;
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



    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "/booking")
    RemoteExporter bookingService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService( new CabBookingServiceImpl() );
        exporter.setServiceInterface( CabBookingService.class );
        return exporter;
    }
}