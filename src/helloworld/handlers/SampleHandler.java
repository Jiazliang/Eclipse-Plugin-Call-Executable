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
		
		
//        // 查找名为 "Plugin" 的控制台
//        MessageConsole pluginConsole = findConsole("Plugin");
//        // 获取 "Plugin" 控制台的输出流
//        MessageConsoleStream out = pluginConsole.newMessageStream();
//
//        // 输出信息到控制台
//        out.println("Hello, this is a message from your Eclipse plugin!");
//        
//        String exePath = "E:\\eclipse-workspace\\HelloWorld\\exe\\example_script.exe";
//		if (isFile(exePath)) {
//			out.println("File Path: " + exePath);
//			try {
//				ProcessBuilder processBuilder = new ProcessBuilder(exePath);
//				Process process = processBuilder.start();
//
//				// 获取 MATLAB 输出
//				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//				// 创建一个线程负责实时读取 MATLAB 输出并打印到控制台
//				Thread outputThread = new Thread(() -> {
//					try {
//						String line;
//						while ((line = reader.readLine()) != null) {
//							System.out.println(line);
//							out.println(line); // 实时打印每行输出到 Eclipse 控制台
//						}
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				});
//				outputThread.start();
//
//				// 等待 MATLAB 进程结束
//				int exitCode = process.waitFor();
//				out.println("MATLAB Process exited with code " + exitCode);
//
//				// 等待输出线程完成
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
//		// 获取当前插件的 Bundle
//		Bundle bundle = FrameworkUtil.getBundle(SampleHandler.class);
//        
//
//		if (bundle != null) {
//			// 获取插件的标识符
//			String pluginId = bundle.getSymbolicName();
//			out.println("Plugin ID: " + pluginId);
//
//			// 获取插件的安装路径
//			String pluginPath = Platform.getStateLocation(bundle).toOSString();
//			out.println("Plugin Path: " + pluginPath);
//
//		} else {
//			out.println("Bundle not found.");
//		}
//		
//		
//		// 关闭输出流
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
	
	// 辅助方法：根据名称查找控制台
    private static MessageConsole findConsole(String name) {
    	// 获取所有控制台
        IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
        IConsole[] consoles = consoleManager.getConsoles();
        // 查到控制台
        for (IConsole console : consoles) {
            if (console.getName().equals(name) && console instanceof MessageConsole) {
            	consoleManager.showConsoleView(console);
                return (MessageConsole) console;
            }
        }
        // 如果不存在，则创建新的控制台
        MessageConsole console = new MessageConsole(name, null);
        consoleManager.addConsoles(new IConsole[]{console});
        consoleManager.showConsoleView(console);
        return console;
    }
    // 判断文件是否存在
	private static boolean isFile(String name) {
		// 将文件路径转换为 Path 对象
		Path path = Paths.get(name);

		// 使用 Files.exists() 方法判断文件是否存在
		return Files.exists(path);
	}
}
