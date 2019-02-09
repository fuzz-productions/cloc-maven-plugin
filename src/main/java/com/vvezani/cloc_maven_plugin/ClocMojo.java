package com.vvezani.cloc_maven_plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Mojo(name = "analyze", defaultPhase = LifecyclePhase.NONE)
public class ClocMojo extends AbstractMojo {

  private static final String VERSION = "1.80";
  private static final String SCRIPT_NAME = "cloc-" + VERSION + ".pl";

  @Parameter(defaultValue = "${project.build.directory}", readonly = true, required = true)
  private File directory;

  public void execute() {
    final String tmpFile = directory.getAbsolutePath() + "/" + SCRIPT_NAME;

    try (InputStream in = getClass().getResourceAsStream("/" + SCRIPT_NAME)) {
      final Path path = Paths.get(tmpFile);

      Files.copy(in, Files.createFile(path), REPLACE_EXISTING);

      ProcessBuilder pb = new ProcessBuilder("perl", tmpFile, ".");
      pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
      pb.redirectError(ProcessBuilder.Redirect.INHERIT);
      pb.start().waitFor();
    } catch (IOException e) {
      getLog().error("Could not execute script", e);
    } catch (InterruptedException e) {
      getLog().error("Execution got interrupted", e);
    } finally {
      try {
        Files.delete(Paths.get(tmpFile));
        getLog().debug("File deleted: " + tmpFile);
      } catch (IOException e) {
        e.printStackTrace();
        getLog().error("Could not delete file: " + tmpFile, e);
      }
    }
  }
}
