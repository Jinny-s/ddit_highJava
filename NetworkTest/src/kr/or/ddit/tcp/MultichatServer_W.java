package kr.or.ddit.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MultichatServer_W {
	// 대화명, 클라이언트의 Socket을 저장하기 위한 Map변수 선언
	Map<String, Socket> clients;
	
	// 생성자
	public MultichatServer_W() {
		// 동기화 처리가 가능하도록 Map객체 생성
		clients = Collections.synchronizedMap(new HashMap<String, Socket>());
	}
	
	// 서버 시작
	public void startServer() {
		
		Socket socket = null;
		
		try(ServerSocket serverSocket = new ServerSocket(7777)){
			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				// 클라이언트의 접속을 대기한다.
				socket = serverSocket.accept();
				
				System.out.println("[" + socket.getInetAddress() 
				             + " : " + socket.getPort() + "] 에서 접속하였습니다.");
				
				// 메시지를 전송 처리하는 스레드 생성 및 실행
				ServerReceiver receiver = new ServerReceiver(socket);
				receiver.start();
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 대화방 즉, Map에 저장된 전체 유저에게 안내메시지를 전송하는 메서드
	 * @param msg
	 */
	public void sendMessage(String msg) {
		// Map에 저장된 유저의 대화명 리스트 추출(key값 구하기)
		Iterator<String> it = clients.keySet().iterator();
		while(it.hasNext()) {
			try {
				String name = it.next(); // 대화명
				
				// 대화명에 해당하는 Socket의 OutputStream 구하기
				DataOutputStream dos = new DataOutputStream(clients.get(name).getOutputStream());
				dos.writeUTF(msg); // 메시지 보내기
				
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 대화방 즉, Map에 저장된 전체 유저에게 안내메시지를 전송하는 메서드
	 * @param msg
	 * @param from
	 */
	public void sendMessage(String msg, String from) {
		// Map에 저장된 유저의 대화명 리스트 추출(key값 구하기)
		Iterator<String> it = clients.keySet().iterator();
		while(it.hasNext()) {
			try {
				String name = it.next(); // 대화명
				
				// 대화명에 해당하는 Socket의 OutputStream 구하기
				DataOutputStream dos = new DataOutputStream(clients.get(name).getOutputStream());
				dos.writeUTF("[" + from + "]" + msg); // 메시지 보내기
				
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 특정인에게 귓속말을 전송하는 메서드
	 * @param msg 메시지
	 * @param from 보내는 사람
	 * @param to 받는 사람
	 */
	public void sendMessage(String msg, String from, String to) {
		try {
			Socket client = clients.get(to);
			DataOutputStream fromDos = new DataOutputStream(clients.get(from).getOutputStream());
			if (client == null) {
				fromDos.writeUTF("'" + to + "'님은 없는 이용자입니다.");
			} else { 
				DataOutputStream dos = new DataOutputStream(client.getOutputStream());
				dos.writeUTF("[" + from + "님의 귓속말]" + msg);
				fromDos.writeUTF("[" + to + "님에게 귓속말]" + msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 개인 공지 메시지
	 * @param msg 메시지 내용
	 * @param to 받는 사람(나 자신)
	 */
	public void myMessage(String msg, String to) {
		try {
			DataOutputStream dos = new DataOutputStream(clients.get(to).getOutputStream());
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// 서버에서 클라이언트로 메시지를 전송할 스레드를 Inner클래스로 정의
	// => Inner클래스에서는 부모 클래스의 멤버들을 직접 사용할 수 있다.
	class ServerReceiver extends Thread {
		private Socket socket;
		private DataInputStream dis;
		private String name;
		
		public ServerReceiver(Socket socket) {
			this.socket = socket;
			try {
				// 수신용
				dis = new DataInputStream(socket.getInputStream());
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				// 서버에서는 클라이언트가 보내는 최초의 메시지 즉, 대화명을
				// 수신해야 한다.
				name = dis.readUTF();
				
				// 대화명을 받아서 다른 모든 클라이언트에게 대화방 참여
				// 메시지를 보낸다.
				sendMessage("#" + name + "님이 입장했습니다.");
				
				// 대화명과 소켓정보를 Map에 저장한다.
				clients.put(name, socket);
				System.out.println("현재 서버 접속자 수는 " + clients.size() + "명 입니다.");
				
				// 이 이후의 메시지 처리는 반복문으로 처리한다.
				// 한 클라이언트가 보낸 메시지를 다른 모든 클라이언트에게
				// 보내준다.
				while(dis != null) {
					// 1. 메시지 담기
					String msg = dis.readUTF();
					
					// 2. 귓속말 여부 확인 ( /w 닉네임 메시지 )
					if(msg.startsWith("/w")) {
						String[] msgs = msg.split(" ", 3);
						if(msgs.length < 3) {
							myMessage("귓속말은 '/w 닉네임 메시지'의 형태로 적어주세요.", name); // 닉네임 뒤에 메시지를 안 적었을 때 안내
						} else {
							sendMessage(msgs[2], name, msgs[1]);
						}
					} else {
						sendMessage(msg, name);
					}
				}
			}catch(IOException ex) {
				ex.printStackTrace();
			}finally {
				// 이 finally영역이 실행된다는 것은 클라이언트의 접속이
				// 종료되었다는 의미이다.
				sendMessage(name + "님이 나가셨습니다.");
				
				// Map에서 해당 대화명을 삭제한다.
				clients.remove(name);
				
				System.out.println("[" + socket.getInetAddress()
						+ " : " + socket.getPort() + "] 에서 접속을 종료했습니다.");
				System.out.println("현재 접속자 수는 " + clients.size() + "명 입니다.");
			}
		}
	}
	
	public static void main(String[] args) {
		new MultichatServer_W().startServer();
	}
}
