package helloworld.handlers;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import helloworld.Activator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PluginResourceUtil {

	public static File getPluginFile(String relativePath) {
		Bundle bundle = Platform.getBundle(Activator.getDefault().getBundle().getSymbolicName());

		if (bundle != null) {
			URL fileURL = bundle.getEntry(relativePath);

			if (fileURL != null) {
				try {
					URL resolvedFileURL = FileLocator.toFileURL(fileURL);
					return new File(resolvedFileURL.getFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}