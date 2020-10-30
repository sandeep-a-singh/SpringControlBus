package com.sunny.spring.control.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
 	private ApplicationContext applicationContext;
	
	@GetMapping("start")
	public String startService()
	{
		//MessageChannel controlChannel = applicationContext.getBean("fromFileChannel", MessageChannel.class);
		//controlChannel.send(new GenericMessage<>("@inputFileAdapter.start()"));
		
		SourcePollingChannelAdapter msgChannelAdapterOutput = applicationContext.getBean("inputFileAdapter", SourcePollingChannelAdapter.class);
		msgChannelAdapterOutput.start();
		
		return "Started ";
	}
	
	@GetMapping("status")
	public String serviceStatus()
	{
		//MessageChannel controlChannel = applicationContext.getBean("fromFileChannel", MessageChannel.class);
		//controlChannel.send(new GenericMessage<>("@inputFileAdapter.start()"));
		
		SourcePollingChannelAdapter msgChannelAdapterOutput = applicationContext.getBean("inputFileAdapter", SourcePollingChannelAdapter.class);
		boolean status  = msgChannelAdapterOutput.isRunning();
		
		return "Adapter is Running  "+status;
	}
	
	
	@GetMapping("stop")
	public String startMyService()
	{
		SourcePollingChannelAdapter msgChannelAdapterOutput = applicationContext.getBean("inputFileAdapter", SourcePollingChannelAdapter.class);
		msgChannelAdapterOutput.stop();
		
		return "Stoped ";
	}
    
}
