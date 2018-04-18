package com.nirima.snowglobe.jenkins.api.remote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = {"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD",
                             "NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"}, justification = "JSON Deserialization")

public class Globe implements Serializable {


  public String id;
  public String type;

  public String name;
  public String description;


  public Date lastUpdate;
  public Date created;

  public List<String> tags = new ArrayList<>();

  public List<String> configFiles = new ArrayList<>();

}
