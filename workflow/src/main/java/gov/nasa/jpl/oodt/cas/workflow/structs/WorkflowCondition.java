//Copyright (c) 2005, California Institute of Technology.
//ALL RIGHTS RESERVED. U.S. Government sponsorship acknowledged.
//
//$Id$

package gov.nasa.jpl.oodt.cas.workflow.structs;

/**
 * @author mattmann
 * @version $Revision$
 * 
 * <p>
 * A WorkflowCondition is some pre-condition that must evaluate to true in order
 * for a particular {@link WorkflowTask} to be permitted to execute .
 * </p>
 * 
 */
public class WorkflowCondition {

	/* the name of the condition */
	private String conditionName = null;

	/* the id for the condition */
	private String conditionId = null;

	/* the actual portion of the condition that performs the work */
	protected String conditionInstanceClassName = null;
	
	/* the order that this condition comes in */
	protected int order = -1;
	
    /* the static configuration parameters for the condition */
	protected WorkflowConditionConfiguration condConfig;
	
	/**
	 * <p>Default Constructor</p>
	 *
	 */
	public WorkflowCondition(){
		
	}
		

	/**
	 * <p>Constructs a new WorkflowCondition with the specified parameters.</p>
	 * 
	 * @param conditionName The display name of the condition.
	 * @param conditionId The ID for this condition.
	 * @param instanceClass The particular instance class name attached to this WorkflowCondition.
	 * @param order The order in which this condition should be checked for a particular WorkflowTask.
	 */
	public WorkflowCondition(String conditionName, String conditionId, String instanceClass, int order) {
		// TODO Auto-generated constructor stub
		this.conditionName = conditionName;
		this.conditionId = conditionId;
		this.conditionInstanceClassName = instanceClass;
		this.order = order;
	}
	
    /**
     * @return Returns the taskConfig.
     */
    public WorkflowConditionConfiguration getTaskConfig() {
        return condConfig;
    }

    /**
     * @param taskConfig
     *            The taskConfig to set.
     */
    public void setCondConfig(WorkflowConditionConfiguration condConfig) {
        this.condConfig = condConfig;
    }

	/**
	 * @return Returns the conditionId.
	 */
	public String getConditionId() {
		return conditionId;
	}

	/**
	 * @param conditionId The conditionId to set.
	 */
	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	/**
	 * @return Returns the conditionName.
	 */
	public String getConditionName() {
		return conditionName;
	}

	/**
	 * @param conditionName The conditionName to set.
	 */
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}


	/**
	 * @return Returns the conditionInstanceClassName.
	 */
	public String getConditionInstanceClassName() {
		return conditionInstanceClassName;
	}


	/**
	 * @param conditionInstanceClassName The conditionInstanceClassName to set.
	 */
	public void setConditionInstanceClassName(String conditionInstanceClassName) {
		this.conditionInstanceClassName = conditionInstanceClassName;
	}


	/**
	 * @return Returns the order.
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order The order to set.
	 */
	public void setOrder(int order) {
		this.order = order;
	}

}