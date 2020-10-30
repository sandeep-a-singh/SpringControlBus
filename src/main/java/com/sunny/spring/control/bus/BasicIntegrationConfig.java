package com.sunny.spring.control.bus;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.EndpointId;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
public class BasicIntegrationConfig{
    public String INPUT_DIR = "P:\\From";
    public String OUTPUT_DIR = "P:\\To";
    public String FILE_PATTERN = "*.*";
 

 /*
    @Bean
    @EndpointId("inputAdapter")
    @InboundChannelAdapter(value = "fileChannel", channel = "fileChannel", poller = @Poller(fixedDelay = "1000"), autoStartup ="false")
    public MessageSource<File> fileReadingMessageSource() {
        FileReadingMessageSource sourceReader= new FileReadingMessageSource();
        sourceReader.setDirectory(new File(INPUT_DIR));
        sourceReader.setFilter(new SimplePatternFileListFilter(FILE_PATTERN));
        return sourceReader;
    }
*/ 
    @Bean
    public IntegrationFlow archiveFile() {
        return IntegrationFlows
                .from(Files.inboundAdapter(new File(INPUT_DIR))
                                .patternFilter("*.sql")
                                .preventDuplicates(false),
                                e -> e.id("inputFileAdapter")
                              	.autoStartup(false)
                        		.poller(Pollers.fixedDelay(1000)
                                .maxMessagesPerPoll(20)))
                .channel("fromFileChannel")
                .handle(Files.outboundAdapter(new File(OUTPUT_DIR)).deleteSourceFiles(true).autoCreateDirectory(true))
                .get();
    }
/*    
    @Bean
    @ServiceActivator(inputChannel= "fileChannel")
    public MessageHandler fileWritingMessageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(OUTPUT_DIR));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        return handler;
    }
   */ 
    

}