package helloworld.handlers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessOutputJob extends Job {

    private final MessageConsole console;

    public ProcessOutputJob(String name, MessageConsole console) {
        super(name);
        this.console = console;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        try {
            // ��������
        	String exePath = "E:\\eclipse-workspace\\HelloWorld\\exe\\example_script.exe";
            ProcessBuilder processBuilder = new ProcessBuilder(exePath);
            Process process = processBuilder.start();

            // ������̵����
            handleProcessOutput(process.getInputStream());

            // �ȴ����̽���
            process.waitFor();

        } catch (Exception e) {
            return new Status(IStatus.ERROR, "YourPluginID", "Error executing the executable", e);
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
        consoleManager.addConsoles(new IConsole[]{console});
        consoleManager.showConsoleView(console);
        return console;
    }
}