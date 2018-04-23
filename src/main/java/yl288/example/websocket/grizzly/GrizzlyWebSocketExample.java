package yl288.example.websocket.grizzly;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GrizzlyWebSocketExample {
	private static final Logger logger = LoggerFactory.getLogger(GrizzlyWebSocketExample.class);
	
	public static void main(String[] args) throws Exception {
		logger.info("Starting server");
		HttpServer server = HttpServer.createSimpleServer("src/main/webapp", 20860);
		WebSocketAddOn addon = new WebSocketAddOn();
		
		server.getListeners().forEach(x -> {
			x.registerAddOn(addon);
		});
		
		WebSocketApplication websocketApp = new ChatApplication();
		
		// ws://localhost:20860/api/chat
		WebSocketEngine.getEngine().register("/api", "/echo", websocketApp);
		
		// register shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("Stopping server..");
				server.shutdownNow();
				logger.info("Stopped server");
			}
		}, "shutdownHook"));
		
		server.start();
		logger.info("Started server");

		Thread.currentThread().join();
	}
}
