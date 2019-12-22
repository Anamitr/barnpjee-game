package server;

import api.service.CabBookingService;
import com.caucho.burlap.client.BurlapProxyFactory;
import org.apache.xmlrpc.XmlRpcConfig;
import org.apache.xmlrpc.XmlRpcConfigImpl;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.metadata.XmlRpcSystemImpl;
import org.apache.xmlrpc.server.XmlRpcServerConfig;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;
import server.service.CabBookingServiceImpl;
import api.service.ChatService;
import server.service.ChatServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

import java.util.TimeZone;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
    Logger logger = LoggerFactory.getLogger(Application.class);

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

    @Bean(name = "/hessian_chat")
    RemoteExporter hessianChatService() {
        logger.info("Starting hessian service");
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(chatService());
        exporter.setServiceInterface(ChatService.class);
        return exporter;
    }

    @Bean(name = "/burlap_chat")
    RemoteExporter burlapChatService() {
        logger.info("Starting burlap service");
        BurlapServiceExporter exporter = new BurlapServiceExporter();
        exporter.setService(chatService());
        exporter.setServiceInterface(ChatService.class);
        return exporter;
    }

    @Bean(name = "/xmlrpc_chat")
    HttpRequestHandler xmlRpcService() throws XmlRpcException {
        logger.info("Starting xmlrpc service");
        ChatService chatService = chatService();
        PropertyHandlerMapping handlerMapping = new PropertyHandlerMapping();
        handlerMapping.setRequestProcessorFactoryFactory(pClass -> pRequest -> chatService);
        handlerMapping.addHandler(ChatService.class.getSimpleName(), ChatService.class);
        XmlRpcSystemImpl.addSystemHandler(handlerMapping);
        XmlRpcServletServer server = new XmlRpcServletServer();
        server.setHandlerMapping(handlerMapping);

        XmlRpcServerConfigImpl config = new XmlRpcServerConfigImpl();
        config.setEnabledForExtensions(true);
        server.setConfig(config);

        logger.info("isEnabledForExtensions = " + server.getConfig().isEnabledForExtensions());
        return server::execute;
    }
}