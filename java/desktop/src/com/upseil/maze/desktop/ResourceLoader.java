package com.upseil.maze.desktop;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;

public class ResourceLoader {
    
    private static final Logger logger = Logger.getLogger(ResourceLoader.class.getName());
    
    public static URL getResource(String resourcePath) {
        URL resource = ResourceLoader.class.getResource(resourcePath);
        if (resource == null) {
            // No internal resource is available for the name.
            // Assuming, that the resource is external.
            try {
                resource = new URL("file:" + resourcePath);
                File resourceFile = new File(resource.getFile());
                if (!resourceFile.isFile()) {
                    logger.severe("Can't get file resource for '" + resourceFile.getAbsolutePath() + "'");
                    resource = null;
                }
            } catch (MalformedURLException e) {
                logger.log(Level.SEVERE, "Error building the resource URL", e);
            }
        }
        return resource;
    }

    private ResourceBundle bundle;
    
    public ResourceLoader(String bundleBaseName, Locale locale) {
        logger.info("Loading internal resource bundle [" + bundleBaseName + ", " + locale + "]");
        bundle = ResourceBundle.getBundle(bundleBaseName, locale);
    }
    
    public ResourceLoader(File resourceBundleDirectory, String bundleBaseName, Locale locale) throws IOException {
        if (!resourceBundleDirectory.isDirectory()) {
            throw new IllegalArgumentException("The given resource bundle directory isn't a directory: " + resourceBundleDirectory);
        }

        logger.info("Loading resource bundle [" + bundleBaseName + ", " + locale + "] located in " + resourceBundleDirectory.getAbsolutePath());
        URL[] resourceBundleUrls = { resourceBundleDirectory.toURI().toURL() };
        ClassLoader bundleLoader = new URLClassLoader(resourceBundleUrls);
        bundle = ResourceBundle.getBundle(bundleBaseName, locale, bundleLoader);
    }
    
    public ResourceBundle getResourceBundle() {
		return bundle;
	}
    
    public <T> T loadFXML(String fxmlPath) {
        return loadFXML(null, null, fxmlPath);
    }
    
    public <T> T loadFXML(Object controller, String fxmlPath) {
        return loadFXML(null, controller, fxmlPath);
    }

	public <T> T loadFXML(Object root, Object controller, String fxmlPath) {
		if (bundle == null) {
			logger.severe("The resource bundle isn't initialized.");
		}
		
		try {
            FXMLLoader loader = new FXMLLoader(getResource(fxmlPath), bundle);
            if (root != null) {
				loader.setRoot(root);
			}
            if (controller != null) {
                loader.setController(controller);
            }
            return loader.load();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading the FXML '" + fxmlPath + "'", e);
        }
        return null;
	}

}
