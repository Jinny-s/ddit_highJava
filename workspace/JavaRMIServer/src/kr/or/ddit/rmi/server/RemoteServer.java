package kr.or.ddit.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.rmi.inf.RemoteInterface;
import kr.or.ddit.rmi.vo.FileInfoVO;
import kr.or.ddit.rmi.vo.TestVO;

/**
 * RMI용 서비스를 제공하는 객체는 RMI용 인터페이스를 구현하고
 * UnicastRemoteObject객체를 상속해서 작성한다.
 */
public class RemoteServer extends UnicastRemoteObject implements RemoteInterface{

	protected RemoteServer() throws RemoteException {
		super();
	}
	
	@Override
	public int doRemotePrint(String str) throws RemoteException {
		
		int length = str.length();
		System.out.println("클라이언트에서 보내온 메시지 : " + str);
		System.out.println("출력 끝.");
		
		return length;		
	}
	
	@Override
	public void doPrintList(List<String> list) throws RemoteException {
		System.out.println("클라이언트에서 보낸 List값들...");
		for(int i = 0; i < list.size(); i++) {
			System.out.println((i+1) + "번째 : " + list.get(i));
		}
		System.out.println("List 출력 끝...");
	}
	
	@Override
	public void doPrintVO(TestVO vo) throws RemoteException {
		System.out.println("클라이언트에서 보내온 TestVO객체 값 출력");
		System.out.println("testId : " + vo.getTestId());
		System.out.println("testNum : " + vo.getTestNum());
		System.out.println("TestVO 객체 출력 끝...");
		
	}
	
	@Override
	public void serFiles(FileInfoVO[] fInfo) throws RemoteException {
		
	}
}
