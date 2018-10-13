package com.liveramp.java_support.util;

import java.io.File;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JarUtils {
  private static final Logger LOG = LoggerFactory.getLogger(com.liveramp.java_support.util.JarUtils.class);

  public static Class getMainClass() throws ClassNotFoundException {

    for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
      StackTraceElement[] value = entry.getValue();

      ArrayList<StackTraceElement> traceList = Lists.newArrayList(value);

      for (StackTraceElement element : traceList) {
        String methodName = element.getMethodName();
        if (methodName.equals("main")) {
          return Class.forName(element.getClassName());
        }
      }
    }

    return null;
  }

  public static URL getMainJarURL() throws ClassNotFoundException {
    Class mainClass = getMainClass();
    LOG.info("Found main class: " + mainClass);

    if (mainClass != null) {
      CodeSource src = mainClass.getProtectionDomain().getCodeSource();
      if (src != null) {
        LOG.info("Found code source: " + src);

        return src.getLocation();
      }
    }

    return null;
  }


  public static File getMainJar() throws ClassNotFoundException {

    return new File(getMainJarURL().getFile());

  }

  @SuppressWarnings("PMD.BlacklistedMethods")
  public static String getMainJarName() {
    try {
      File mainJar = getMainJar();
      if (mainJar != null) {
        return FilenameUtils.getName(mainJar.getName());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


}