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
