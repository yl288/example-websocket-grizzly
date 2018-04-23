package yl288.example.websocket.grizzly;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.glassfish.grizzly.websockets.Broadcaster;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class ChatApplication extends WebSocketApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatApplication.class);
	
	private List<WebSocket> clients = Lists.newArrayList();

	public ChatApplication() {
		logger.info("Created chat application");
	}
	
	@Override
	public void onConnect(WebSocket socket) {
		logger.info("Connected: {}", socket);
		super.onConnect(socket);
	}
	
	@Override
	public void onPing(WebSocket socket, byte[] bytes) {
		logger.info("Ping received: {}", bytes.length);
	}
	
	@Override
	public void onPong(WebSocket socket, byte[] bytes) {
		logger.info("Pong received: {}", bytes.length);
	}
	
	@Override
	public void onMessage(WebSocket socket, String text) {
		logger.info("String message received: [{}]", text);
		socket.broadcast(getWebSockets(), text);
	}
	
	@Override
	public void onMessage(WebSocket socket, byte[] bytes) {
		logger.info("Byte message received: length={}", bytes.length);
		socket.broadcast(getWebSockets(), bytes);
	}
	
	@Override
	public void onFragment(WebSocket socket, String fragment, boolean last) {
		logger.info("String fragment received: [{}], last={}", fragment, last);
	}
	
	@Override
	public void onFragment(WebSocket socket, byte[] fragment, boolean last) {
		logger.info("String fragment received: {}, last={}", fragment.length, last);
	}
	
}
