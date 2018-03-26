package com.nirima.snowglobe.jenkins.step;

import com.nirima.snowglobe.jenkins.SnowGlobePluginConfiguration;
import com.nirima.snowglobe.jenkins.actions.SnowGlobeAction;
import com.nirima.snowglobe.jenkins.api.remote.ApplyCommand;
import com.nirima.snowglobe.jenkins.api.remote.CloneCommand;

import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousNonBlockingStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.inject.Inject;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.Run;
import hudson.model.TaskListener;

public final class ApplyStep extends AbstractStepImpl {

  String globeId;
  String settings;

  boolean createAction;

  @DataBoundConstructor
  public ApplyStep() {

  }

  public String getGlobeId() {
    return globeId;
  }

  @DataBoundSetter
  public void setGlobeId(String sourceId) {
    this.globeId = sourceId;
  }

  public String getSettings() {
    return settings;
  }
  
  @DataBoundSetter
  public void setSettings(String settings) {
    this.settings = settings;
  }

  public boolean isCreateAction() {
    return createAction;
  }
  @DataBoundSetter
  public void setCreateAction(boolean createAction) {
    this.createAction = createAction;
  }

  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl) super.getDescriptor();
  }


  @Extension
  public static final class DescriptorImpl extends AbstractStepDescriptorImpl {

    public DescriptorImpl() {
      super(Execution.class);
    }

    //
    @Override
    public String getFunctionName() {
      return "snowglobe_apply";
    }


  }

  public static final class Execution extends AbstractSynchronousNonBlockingStepExecution<String> {

    @Inject
    private transient ApplyStep step;

    @StepContextParameter
    private transient Run<?, ?> run;
    @StepContextParameter
    private transient TaskListener listener;

    @Override
    protected String run() throws Exception {

      String baseUrl = SnowGlobePluginConfiguration.get().getServerUrl();
      new ApplyCommand(baseUrl, step.globeId, step.settings).execute();

      if (step.isCreateAction()) {
        SnowGlobeAction action = new SnowGlobeAction(step.globeId);

        run.addAction(action);
      }
      return "OK";
    }

    private static final long serialVersionUID = 1L;


    public Item getProject() {
      return run.getParent();
    }
  }
}