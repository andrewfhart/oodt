//Copyright (c) 2005, California Institute of Technology.
//ALL RIGHTS RESERVED. U.S. Government sponsorship acknowledged.
//
//$Id$

package gov.nasa.jpl.oodt.cas.workflow.util;

//OODT imports
import gov.nasa.jpl.oodt.cas.workflow.structs.Workflow;
import gov.nasa.jpl.oodt.cas.workflow.structs.WorkflowInstance;
import gov.nasa.jpl.oodt.cas.workflow.structs.WorkflowTask;
import gov.nasa.jpl.oodt.cas.workflow.structs.WorkflowCondition;

//JDK imports
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author mattmann
 * @version $Revision$
 * 
 * <p>
 * A Factory for creating Workflow Manager objects from {@link ResultSet}s.
 * </p>
 * 
 */
public final class DbStructFactory {

    /* our log stream */
    private static Logger LOG = Logger.getLogger(DbStructFactory.class
            .getName());

    private DbStructFactory() throws InstantiationException {
        throw new InstantiationException("Don't construct DbStructFactories!");
    }

    public static String getEvent(ResultSet rs) throws SQLException {
        String eventName = rs.getString("event_name");
        return eventName;
    }

    public static WorkflowInstance getWorkflowInstance(ResultSet rs)
            throws SQLException {
        WorkflowInstance workflowInst = new WorkflowInstance();
        workflowInst.setStatus(rs.getString("workflow_instance_status"));
        workflowInst.setId(rs.getString("workflow_instance_id"));
        workflowInst.setCurrentTaskId(rs.getString("current_task_id"));
        workflowInst.setStartDateTimeIsoStr(rs.getString("start_date_time"));
        workflowInst.setEndDateTimeIsoStr(rs.getString("end_date_time"));
        workflowInst.setCurrentTaskStartDateTimeIsoStr(rs
                .getString("current_task_start_date_time"));
        workflowInst.setCurrentTaskEndDateTimeIsoStr(rs
                .getString("current_task_end_date_time"));
        Workflow workflow = new Workflow();
        workflow.setId(rs.getString("workflow_id"));
        workflowInst.setWorkflow(workflow);
        return workflowInst;
    }

    public static Workflow getWorkflow(ResultSet rs) throws SQLException {
        Workflow workflow = new Workflow();
        workflow.setName(rs.getString("workflow_name"));
        workflow.setId(String.valueOf(rs.getInt("workflow_id")));

        return workflow;
    }

    public static WorkflowTask getWorkflowTask(ResultSet rs, boolean setOrder)
            throws SQLException {
        String taskClassName = rs.getString("workflow_task_class");

        if (taskClassName != null) {
            WorkflowTask task = new WorkflowTask();
            task.setTaskInstanceClassName(taskClassName);
            task.setTaskId(String.valueOf(rs.getInt("workflow_task_id")));
            task.setTaskName(rs.getString("workflow_task_name"));
            if (setOrder) {
                task.setOrder(rs.getInt("task_order"));
            }
            return task;
        } else
            return null;
    }

    public static WorkflowCondition getWorkflowCondition(ResultSet rs,
            boolean setOrder) throws SQLException {

        String conditionClassName = rs.getString("workflow_condition_class");

        if (conditionClassName != null) {
            WorkflowCondition condition = new WorkflowCondition();
            condition.setConditionInstanceClassName(conditionClassName);
            condition.setConditionId(String.valueOf(rs
                    .getInt("workflow_condition_id")));
            condition.setConditionName(rs.getString("workflow_condition_name"));
            if (setOrder) {
                condition.setOrder(rs.getInt("condition_order"));
            }
            return condition;
        } else
            return null;
    }

}