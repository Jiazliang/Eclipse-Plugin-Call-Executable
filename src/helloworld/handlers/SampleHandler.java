package helloworld.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		System.out.println("HelloWorld");
		
		// Executable path
		String path = "E:\\eclipse-workspace\\HelloWorld\\lib\\example_script.exe";
		// The console used for displaying output
	    MessageConsole pluginConsole = findConsole("Plugin");
	    
		// Start the background job
	    ProcessExecutableJob job = new ProcessExecutableJob("My Excutable Job", path, pluginConsole);
        job.schedule();
	

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
