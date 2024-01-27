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
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		System.out.println("HelloWorld");

		// Get or create the console of "Plugin"
		MessageConsole pluginConsole = findConsole("Plugin");
		// Get the console stream
		MessageConsoleStream stream = pluginConsole.newMessageStream();

		// TODO: Set the relative path of the executable
		final String relativePath = "lib/example_script.exe";

		// Get the file of the executable
		File file = PluginResourceUtil.getPluginFile(relativePath);
		// The file exists
		if (file != null && file.exists()) {
			// Executable path
			final String path = file.getAbsolutePath();
			System.out.println("File exists: " + path);
			// Start the background job
			ProcessExecutableJob job = new ProcessExecutableJob("My Excutable Job", path, pluginConsole);
			job.schedule();

		} else {
			// The file not exists
			stream.println(" The executable file not found.");
		}

		// Provide the example of MessageDialog
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(), "HelloWorld", "Hello, Eclipse world");
		return null;
	}

	// Find or create the console
	private static MessageConsole findConsole(String name) {
		// Get all consoles
		IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		IConsole[] consoles = consoleManager.getConsoles();
		// Look up the console
		for (IConsole console : consoles) {
			if (console.getName().equals(name) && console instanceof MessageConsole) {
				consoleManager.showConsoleView(console);
				return (MessageConsole) console;
			}
		}
		// If not exist, then create one
		MessageConsole console = new MessageConsole(name, null);
		consoleManager.addConsoles(new IConsole[] { console });
		consoleManager.showConsoleView(console);
		return console;
	}
}
