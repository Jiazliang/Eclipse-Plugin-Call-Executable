package helloworld.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

import java.io.File;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.handlers.HandlerUtil;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		System.out.println("HelloWorld");

		String relativePath = "lib/example_script.exe";
		File file = PluginResourceUtil.getPluginFile(relativePath);

		if (file != null && file.exists()) {
			System.out.println("File exists: " + file.getAbsolutePath());

			// Executable path
			String path = file.getAbsolutePath();
			// The console used for displaying output
			MessageConsole pluginConsole = findConsole("Plugin");

			// Start the background job
			ProcessExecutableJob job = new ProcessExecutableJob("My Excutable Job", path, pluginConsole);
			job.schedule();

		} else {
			System.out.println("File not found.");
		}

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(), "HelloWorld", "Hello, Eclipse world");
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
		consoleManager.addConsoles(new IConsole[] { console });
		consoleManager.showConsoleView(console);
		return console;
	}
}
