package com.vvezani.clocmvnplugin;

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
import java.util.List;

import static com.vvezani.clocmvnplugin.Parameters.ParamType.EXCLUDE_DIR;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Mojo(name = "analyze", defaultPhase = LifecyclePhase.NONE)
public class ClocMojo extends AbstractMojo {

  private static final String VERSION = "1.80";
  private static final String SCRIPT_NAME = "cloc-" + VERSION + ".pl";

  private final Parameters params;

  @Parameter(defaultValue = "${project.build.directory}", readonly = true, required = true)
  private File directory; // target/

  @Parameter
  private List<String> excludeDirs;

  public ClocMojo() {
    this.params = new Parameters();
  }

  public void execute() {
    final String tmpFileAbsolutePath = directory.getAbsolutePath() + "/" + SCRIPT_NAME;

    try (InputStream in = getClass().getResourceAsStream("/" + SCRIPT_NAME)) {
      final Path tmpFilePath = Paths.get(tmpFileAbsolutePath);

      if(Files.notExists(directory.toPath())) Files.createDirectory(directory.toPath());
      Files.copy(in, Files.createFile(tmpFilePath), REPLACE_EXISTING);

      processParams();

      ProcessBuilder pb = new ProcessBuilder("perl", tmpFileAbsolutePath, ".", params.getFormattedStringFor(EXCLUDE_DIR));
      pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
      pb.redirectError(ProcessBuilder.Redirect.INHERIT);
      pb.start().waitFor();
    } catch (IOException e) {
      getLog().error("Could not execute script", e);
    } catch (InterruptedException e) {
      getLog().error("Execution got interrupted", e);
    } finally {
      try {
        Files.delete(Paths.get(tmpFileAbsolutePath));
        getLog().debug("File deleted: " + tmpFileAbsolutePath);
      } catch (IOException e) {
        e.printStackTrace();
        getLog().error("Could not delete file: " + tmpFileAbsolutePath, e);
      }
    }
  }

  private void processParams() {
    String excludeDirsStr = excludeDirs != null && !excludeDirs.isEmpty() ? String.join(",", excludeDirs) : directory.getName();
    params.addParam(EXCLUDE_DIR, excludeDirsStr);
  }
}
