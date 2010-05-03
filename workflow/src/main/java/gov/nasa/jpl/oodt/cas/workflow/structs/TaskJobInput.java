//Copyright (c) 2006, California Institute of Technology.
//ALL RIGHTS RESERVED. U.S. Government sponsorship acknowledged.
//
//$Id$

package gov.nasa.jpl.oodt.cas.workflow.structs;

//JDK imports
import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;

//OODT imports
import gov.nasa.jpl.oodt.cas.metadata.Metadata;
import gov.nasa.jpl.oodt.cas.resource.structs.JobInput;
import gov.nasa.jpl.oodt.cas.workflow.util.XmlRpcStructFactory;

/**
 * @author mattmann
 * @version $Revision$
 * 
 * <p>
 * {@link JobInput} for a {@link WorkflowTask}. This class will be instantiated
 * by the Resource Manager when it receives a {@link TaskJob}. The class
 * consists of three important pieces of information:
 * 
 * <ul>
 * <li>Static {@link WorkflowTaskConfiguration}</li>
 * <li>Dynamic Task {@link Metadata}</li>
 * <li>The name of the {@link WorkflowTaskInstance} class</li>
 * </ul>
 * </p>.
 */
public class TaskJobInput implements JobInput {

  /* static task config */
  private WorkflowTaskConfiguration taskConfig = null;

  /* dynamic task metadata */
  private Metadata dynMetadata = null;

  /* our job id */
  private String id = null;
  
  /* the name of the workflow task instance to instantiate
   * against this input
   */
  private String workflowTaskInstanceClassName = null;
  

  /**
   * Default Constructor.
   */
  public TaskJobInput() {
    taskConfig = new WorkflowTaskConfiguration();
    dynMetadata = new Metadata();
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.nasa.jpl.oodt.cas.resource.structs.JobInput#getId()
   */
  public String getId() {
    return id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.nasa.jpl.oodt.cas.resource.util.XmlRpcWriteable#read(java.lang.Object)
   */
  public void read(Object in) {
    if (!(in instanceof Hashtable)) {
      return;
    }

    Hashtable inHash = (Hashtable) in;

    this.taskConfig = XmlRpcStructFactory
        .getWorkflowTaskConfigurationFromXmlRpc((Hashtable) inHash
            .get("task.config"));
    this.dynMetadata.addMetadata((Hashtable) inHash.get("task.metadata"));
    this.workflowTaskInstanceClassName = (String)inHash.get("task.instance.class");

  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.nasa.jpl.oodt.cas.resource.util.XmlRpcWriteable#write()
   */
  public Object write() {
    // need to create a hashtable with the task metadata and the task config
    Hashtable outHash = new Hashtable();
    outHash.put("task.config", XmlRpcStructFactory
        .getXmlRpcWorkflowTaskConfiguration(this.taskConfig));
    outHash.put("task.metadata", this.dynMetadata.getHashtable());
    outHash.put("task.instance.class", this.workflowTaskInstanceClassName);
    return outHash;
  }

  public Metadata getDynMetadata() {
    return dynMetadata;
  }

  public void setDynMetadata(Metadata dynMetadata) {
    this.dynMetadata = dynMetadata;
  }

  public WorkflowTaskConfiguration getTaskConfig() {
    return taskConfig;
  }

  public void setTaskConfig(WorkflowTaskConfiguration taskConfig) {
    this.taskConfig = taskConfig;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getWorkflowTaskInstanceClassName() {
    return workflowTaskInstanceClassName;
  }

  public void setWorkflowTaskInstanceClassName(
      String workflowTaskInstanceClassName) {
    this.workflowTaskInstanceClassName = workflowTaskInstanceClassName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.nasa.jpl.oodt.cas.resource.util.Configurable#configure(java.util.Properties)
   */
  public void configure(Properties props) {
    // looking for dyn.metadata.file
    // looking for static.config.file
    String staticConfigFile = props.getProperty("static.config.file");
    String dynMetadataFile = props.getProperty("dyn.metadata.file");

    if (staticConfigFile != null) {
      try {
        this.taskConfig.getProperties().load(
            new FileInputStream(new File(staticConfigFile)));
      } catch (Exception ignore) {
      }
    }

    if (dynMetadataFile != null) {
      try {
        this.dynMetadata = new Metadata(new FileInputStream(new File(
            dynMetadataFile)));
      } catch (Exception ignore) {
      }
    }

  }

}