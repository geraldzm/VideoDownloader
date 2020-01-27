package com.gerald.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author gerald
 * */
public class Main {
	public static void main(String[] args) {
		//video samples:
		//https://standaloneinstaller.com/blog/big-list-of-sample-videos-for-testers-124.html
		int op = -1;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("******************************");
			System.out.println(" 1. Download video via URL");
			System.out.println(" 2. exit");
			System.out.println(" Type your option: ");
			op = getInputInt(1, 2, sc);
			if(op == 1) {
				try {
					System.out.println("******************************");
					System.out.println(" Download video!!!");
					System.out.println(" Insert URL: \n");
					String url = sc.next();
					downloadFromURL(url);
				} catch (MalformedURLException e) {
					System.out.println("\n Something is wrong with the URL \n");
				}
			}else if(op == 2) {
				System.out.println("****");
				System.out.println(" Bye");
				System.out.println("****");
			}
		}while(op != 2);
		
		if(sc != null) {
			sc.close();
		}
	}
	
	public static void downloadFromURL(String urlString) throws MalformedURLException {
		URL url = new URL(urlString);
		try(InputStream is = url.openStream()){
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			//if the connection exist
			if(huc != null) {
				System.out.println("connected");
				
				//selecting a path for the new mp4 file
				String fileName = "Video.mp4";
				JFileChooser filec = new JFileChooser();
				filec.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filec.setSelectedFile(new File(fileName));
				
				String storagePath = "";
					
				//JFileChooser answer
				switch(filec.showSaveDialog(null)) {
					case JFileChooser.APPROVE_OPTION:
						storagePath = filec.getSelectedFile().getParent();
						fileName = filec.getSelectedFile().getName();
						break;
					case JFileChooser.CANCEL_OPTION:
						System.out.println("\n Canceled \n");
						return;
				}
					
				if(filec.getSelectedFile().exists()) {
					if(JOptionPane.showConfirmDialog(null, "Overwrite file?", "Warning", JOptionPane.OK_CANCEL_OPTION)== JOptionPane.CANCEL_OPTION) {
						System.out.println("\n Canceled \n");
						return;
					}
				}
				
				File newf = new File(storagePath, fileName);
				
				//writing the file
				FileOutputStream fos = new FileOutputStream(newf);
				byte[] buffer = new byte[1024];
				int len1 = 0;
				if(is != null) {
					System.out.println("downloading...");
					while((len1 = is.read(buffer)) > 0) {
						fos.write(buffer, 0, len1);
					}
					System.out.println("downloaded!");
				}
				if(fos != null) {
					fos.close();
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * <p>Request an integer within the scope, min and max included</p>
	 * */
	public static int getInputInt(int min, int max, Scanner sc) {
		int op = -1;
		boolean tryAgain = false;
		do {
			try {
				op = sc.nextInt();
				tryAgain = false;
				if(op > max) {
					tryAgain = true;
					System.out.println("\n you should not exceed " + max + "\n try again: \n");
				}else if(op < min) {
					tryAgain = true;
					System.out.println("\n the number can not be lest than " + min + "\n try again: \n");
				}
			}catch(java.util.InputMismatchException e) {
				tryAgain = true;
				System.out.println("\n Input Mismatch, you are suppoused to type an integer, try again: ");
			}
		}while(tryAgain);
		return op;
	}
	
}
	


