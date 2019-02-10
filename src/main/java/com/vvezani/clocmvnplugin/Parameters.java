package com.vvezani.clocmvnplugin;

import java.util.HashMap;
import java.util.Map;

public class Parameters {

  private String PARAM_FORMAT = "--%s=%s";

  private final Map<ParamType, String> params = new HashMap<>();

  public void addParam(ParamType param, String value) {
    params.put(param, value);
  }

  public String getFormattedStringFor(ParamType param) {
    return String.format(PARAM_FORMAT, param.value, params.get(param));
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (ParamType key : params.keySet()) {
      result.append(getFormattedStringFor(key));
    }
    return result.toString();
  }

  public enum ParamType {
    EXCLUDE_DIR("exclude-dir");

    private final String value;

    ParamType(String value) {
      this.value = value;
    }
  }


  public static void main(String[] args){
    final Parameters parameters = new Parameters();
    parameters.addParam(ParamType.EXCLUDE_DIR, "value");
    System.out.println(parameters.getFormattedStringFor(ParamType.EXCLUDE_DIR));
  }
}
