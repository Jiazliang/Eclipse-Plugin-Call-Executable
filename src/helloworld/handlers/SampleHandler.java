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
		
		// ��ȡ��ǰ����� Bundle
        Bundle bundle = FrameworkUtil.getBundle(SampleHandler.class);

        if (bundle != null) {
            // ��ȡ����ı�ʶ��
            String pluginId = bundle.getSymbolicName();
            System.out.println("Plugin ID: " + pluginId);

            // ��ȡ����İ�װ·��
            String pluginPath = Platform.getStateLocation(bundle).toOSString();
            System.out.println("Plugin Path: " + pluginPath);
        } else {
            System.out.println("Bundle not found.");
        }
        
		try {
      ProcessBuilder processBuilder = new ProcessBuilder("E:\\eclipse-workspace\\HelloWorld\\exe\\example_script.exe");
      Process process = processBuilder.start();

      // ��ȡ MATLAB ���
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      // ����һ���̸߳���ʵʱ��ȡ MATLAB �������ӡ������̨
      Thread outputThread = new Thread(() -> {
          try {
              String line;
              while ((line = reader.readLine()) != null) {
                  System.out.println(line);  // ʵʱ��ӡÿ������� Eclipse ����̨
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      });
      outputThread.start();

      // �ȴ� MATLAB ���̽���
      int exitCode = process.waitFor();
      System.out.println("MATLAB Process exited with code " + exitCode);

      // �ȴ�����߳����
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
