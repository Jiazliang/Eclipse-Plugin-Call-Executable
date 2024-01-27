package helloworld.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		System.out.println("HelloWorld");
		
		// The console used for displaying output
	    MessageConsole myConsole = findConsole("Plugin");
	    
		// Start the background job
        ProcessOutputJob job = new ProcessOutputJob("My Long Running Job", myConsole);
        job.schedule();
		
		
//        // ������Ϊ "Plugin" �Ŀ���̨
//        MessageConsole pluginConsole = findConsole("Plugin");
//        // ��ȡ "Plugin" ����̨�������
//        MessageConsoleStream out = pluginConsole.newMessageStream();
//
//        // �����Ϣ������̨
//        out.println("Hello, this is a message from your Eclipse plugin!");
//        
//        String exePath = "E:\\eclipse-workspace\\HelloWorld\\exe\\example_script.exe";
//		if (isFile(exePath)) {
//			out.println("File Path: " + exePath);
//			try {
//				ProcessBuilder processBuilder = new ProcessBuilder(exePath);
//				Process process = processBuilder.start();
//
//				// ��ȡ MATLAB ���
//				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//				// ����һ���̸߳���ʵʱ��ȡ MATLAB �������ӡ������̨
//				Thread outputThread = new Thread(() -> {
//					try {
//						String line;
//						while ((line = reader.readLine()) != null) {
//							System.out.println(line);
//							out.println(line); // ʵʱ��ӡÿ������� Eclipse ����̨
//						}
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				});
//				outputThread.start();
//
//				// �ȴ� MATLAB ���̽���
//				int exitCode = process.waitFor();
//				out.println("MATLAB Process exited with code " + exitCode);
//
//				// �ȴ�����߳����
//				outputThread.join();
//			} catch (IOException | InterruptedException e) {
//				e.printStackTrace();
//			}
//		} else {
//			out.println("File does not exist.");
//		}
//        
//        
//
//		// ��ȡ��ǰ����� Bundle
//		Bundle bundle = FrameworkUtil.getBundle(SampleHandler.class);
//        
//
//		if (bundle != null) {
//			// ��ȡ����ı�ʶ��
//			String pluginId = bundle.getSymbolicName();
//			out.println("Plugin ID: " + pluginId);
//
//			// ��ȡ����İ�װ·��
//			String pluginPath = Platform.getStateLocation(bundle).toOSString();
//			out.println("Plugin Path: " + pluginPath);
//
//		} else {
//			out.println("Bundle not found.");
//		}
//		
//		
//		// �ر������
//        try {
//			out.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	

//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		MessageDialog.openInformation(window.getShell(), "HelloWorld", "Hello, Eclipse world");
		return null;
	}
	
	// �����������������Ʋ��ҿ���̨
    private static MessageConsole findConsole(String name) {
    	// ��ȡ���п���̨
        IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
        IConsole[] consoles = consoleManager.getConsoles();
        // �鵽����̨
        for (IConsole console : consoles) {
            if (console.getName().equals(name) && console instanceof MessageConsole) {
            	consoleManager.showConsoleView(console);
                return (MessageConsole) console;
            }
        }
        // ��������ڣ��򴴽��µĿ���̨
        MessageConsole console = new MessageConsole(name, null);
        consoleManager.addConsoles(new IConsole[]{console});
        consoleManager.showConsoleView(console);
        return console;
    }
    // �ж��ļ��Ƿ����
	private static boolean isFile(String name) {
		// ���ļ�·��ת��Ϊ Path ����
		Path path = Paths.get(name);

		// ʹ�� Files.exists() �����ж��ļ��Ƿ����
		return Files.exists(path);
	}
}
