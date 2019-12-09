import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;

class t0606 {

	static int angle = 270; // ���F��ʪ�����

	private static boolean OutServer = false;
	private static ServerSocket server;
	private final static int ServerPort = 1234; // �n�ʱ���port

	public static void main(String[] args) {

		//���UESCAPE�����}�{��
		Button.ESCAPE.addKeyListener(new KeyListener() {
			public void keyReleased(Key k) {
				System.exit(0);
			}
			public void keyPressed(Key k) {}
		});

		print("Start");
		
		Thread socketServer_thread = new Thread(socketServer_runnable);
		socketServer_thread.start();

		while (true);
	}

	//�إ�SocketServer
	public static void SocketServer() {
		try {
			server = new ServerSocket(ServerPort);

		} catch (java.io.IOException e) {
			print("Socket Error!");
			print("IOException :" + e.toString());
		}
	}

	static Runnable socketServer_runnable = new Runnable() {
		public void run() {
			SocketServer();

			Socket socket;

			print("Socket Server OK!");

			while (!OutServer) {
				socket = null;
				try {
					synchronized (server) {
						socket = server.accept();
					}
					print(socket.getInetAddress().toString()); //�L�X�ثe�s�u�]�ƪ�ip

					//Socket����
					BufferedReader inbound = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));

					//Socket�o�e
					PrintWriter outbound = new PrintWriter(
							socket.getOutputStream(), true);

					while (true) {
						String data = "";

						outbound.println("Success");

						while ((data = inbound.readLine()) == null)
							;

						print("getData:" + data);

						if (data.equals("FWD")) {
							go();
							outbound.print(data + " OK");
						} else if (data.equals("BACK")) {
							back();
							outbound.print(data + " OK");
						} else if (data.equals("LEFT")) {
							left(angle);
							outbound.print(data + " OK");
						} else if (data.equals("RIGHT")) {
							right(angle);
							outbound.print(data + " OK");
						} else if (data.equals("STOP")) {
							stop();
							outbound.print(data + " OK");
						}else if (data.equals("CA")) {
							ca();
							outbound.print(data + " OK");
						}else if (data.equals("CL")) {
							cl();
							outbound.print(data + " OK");
						}
					}

				} catch (java.io.IOException e) {
					print("Socket Error");
					print("IOException :" + e.toString());
					Thread socketServer_thread = new Thread(socketServer_runnable);
					socketServer_thread.start();
				}
			}
		}
	};
	
	//�e�i
	private static void go() {
		Motor.A.forward();
		Motor.B.forward();
	}

	//��h
	private static void back() {
		Motor.A.backward();
		Motor.B.backward();
	}

	//����
	private static void left(int angle) {		
		Motor.A.rotate(-angle, true);
		Motor.B.rotate(angle, true);
	}

	//�k��
	private static void right(int angle) {
		Motor.A.rotate(angle, true);
		Motor.B.rotate(-angle, true);
	}
	//����
	private static void stop() {
		Motor.A.stop();
		Motor.B.stop();
	}
	private static void ca() {
		Motor.C.rotate(-100, true);
	}
	private static void cl() {
		Motor.C.rotate(150, true);	
	}
	
	//�bEV3�ù��L�X�r��
	private static void print(String str) {
		LCD.clear();
		LCD.drawString(str, 0, 3);
	}
}