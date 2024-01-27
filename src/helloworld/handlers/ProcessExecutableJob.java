package helloworld.handlers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import helloworld.Activator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessExecutableJob extends Job {

	private final String path;
	private final MessageConsole console;

	public ProcessExecutableJob(String name, String path, MessageConsole console) {
		super(name);
		this.path = path;
		this.console = console;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			// ��������
			ProcessBuilder processBuilder = new ProcessBuilder(path);
			Process process = processBuilder.start();

			// ������̵����
			handleProcessOutput(process.getInputStream());

			// �ȴ����̽���
			process.waitFor();

		} catch (Exception e) {
			return new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(),
					"Error executing the executable", e);
		}

		return Status.OK_STATUS;
	}

	private void handleProcessOutput(InputStream inputStream) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// �� UI �߳���ʵʱ���������̨
				displayOutputInUI(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displayOutputInUI(String output) {
		getConsoleStream().println(output);
	}

	private MessageConsoleStream getConsoleStream() {
		return console.newMessageStream();
	}

	// �ڲ������ʱ����������ʾ����̨
	public static MessageConsole createConsole(String consoleName) {
		IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		MessageConsole console = new MessageConsole(consoleName, null);
		consoleManager.addConsoles(new IConsole[] { console });
		consoleManager.showConsoleView(console);
		return console;
	}
}