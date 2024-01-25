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

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		System.out.println("HelloWorld");
		
		// 获取当前插件的 Bundle
        Bundle bundle = FrameworkUtil.getBundle(SampleHandler.class);

        if (bundle != null) {
            // 获取插件的标识符
            String pluginId = bundle.getSymbolicName();
            System.out.println("Plugin ID: " + pluginId);

            // 获取插件的安装路径
            String pluginPath = Platform.getStateLocation(bundle).toOSString();
            System.out.println("Plugin Path: " + pluginPath);
        } else {
            System.out.println("Bundle not found.");
        }
        
		try {
      ProcessBuilder processBuilder = new ProcessBuilder("E:\\eclipse-workspace\\HelloWorld\\exe\\example_script.exe");
      Process process = processBuilder.start();

      // 获取 MATLAB 输出
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      // 创建一个线程负责实时读取 MATLAB 输出并打印到控制台
      Thread outputThread = new Thread(() -> {
          try {
              String line;
              while ((line = reader.readLine()) != null) {
                  System.out.println(line);  // 实时打印每行输出到 Eclipse 控制台
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      });
      outputThread.start();

      // 等待 MATLAB 进程结束
      int exitCode = process.waitFor();
      System.out.println("MATLAB Process exited with code " + exitCode);

      // 等待输出线程完成
      outputThread.join();
  } catch (IOException | InterruptedException e) {
      e.printStackTrace();
  }
		
		
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"HelloWorld",
				"Hello, Eclipse world");
		return null;
	}
}
