package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import api.service.MinesweeperService;
import server.service.MinesweeperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
    Logger logger = LoggerFactory.getLogger(Application.class);

    @Bean
    MinesweeperService minesweeperService() {
        return new MinesweeperImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



    @Bean(name = "/burlap_minesweeper")
    RemoteExporter burlapMinesweeperService() {
        logger.info("Starting burlap service");
        BurlapServiceExporter exporter = new BurlapServiceExporter();
        exporter.setService(minesweeperService());
        exporter.setServiceInterface(MinesweeperService.class);
        return exporter;
    }
}