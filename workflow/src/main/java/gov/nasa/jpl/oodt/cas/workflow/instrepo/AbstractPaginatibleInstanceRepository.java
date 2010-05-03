//Copyright (c) 2008, California Institute of Technology.
//ALL RIGHTS RESERVED. U.S. Government sponsorship acknowledged.
//
//$Id$

package gov.nasa.jpl.oodt.cas.workflow.instrepo;

//JDK imports
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

//OODT imports
import gov.nasa.jpl.oodt.cas.workflow.structs.WorkflowInstance;
import gov.nasa.jpl.oodt.cas.workflow.structs.WorkflowInstancePage;
import gov.nasa.jpl.oodt.cas.workflow.structs.exceptions.InstanceRepositoryException;
import gov.nasa.jpl.oodt.cas.commons.pagination.PaginationUtils;

/**
 * @author mattmann
 * @version $Revision$
 * 
 * <p>
 * Describe your class here
 * </p>.
 */
public abstract class AbstractPaginatibleInstanceRepository implements
        WorkflowInstanceRepository {

    protected int pageSize = -1;

    /* our log stream */
    private static final Logger LOG = Logger
            .getLogger(AbstractPaginatibleInstanceRepository.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see gov.nasa.jpl.oodt.cas.workflow.util.Pagination#getFirstPage()
     */
    public WorkflowInstancePage getFirstPage() {
        WorkflowInstancePage firstPage = null;

        try {
            firstPage = getPagedWorkflows(1);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.log(Level.WARNING, "Exception getting first page: Message: "
                    + e.getMessage());
        }
        return firstPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nasa.jpl.oodt.cas.workflow.util.Pagination#getLastPage()
     */
    public WorkflowInstancePage getLastPage() {
        WorkflowInstancePage lastPage = null;
        WorkflowInstancePage firstPage = getFirstPage();

        try {
            lastPage = getPagedWorkflows(firstPage.getTotalPages());
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Exception getting last page: Message: "
                    + e.getMessage());
        }

        return lastPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nasa.jpl.oodt.cas.workflow.util.Pagination#getNextPage(gov.nasa.jpl.oodt.cas.workflow.structs.WorkflowInstancePage)
     */
    public WorkflowInstancePage getNextPage(WorkflowInstancePage currentPage) {
        if (currentPage == null) {
            return getFirstPage();
        }

        if (currentPage.isLastPage()) {
            return currentPage;
        }

        WorkflowInstancePage nextPage = null;

        try {
            nextPage = getPagedWorkflows(currentPage.getPageNum() + 1);
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Exception getting next page: Message: "
                    + e.getMessage());
        }

        return nextPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nasa.jpl.oodt.cas.workflow.util.Pagination#getPrevPage(gov.nasa.jpl.oodt.cas.workflow.structs.WorkflowInstancePage)
     */
    public WorkflowInstancePage getPrevPage(WorkflowInstancePage currentPage) {
        if (currentPage == null) {
            return getFirstPage();
        }

        if (currentPage.isLastPage()) {
            return currentPage;
        }

        WorkflowInstancePage nextPage = null;

        try {
            nextPage = getPagedWorkflows(currentPage.getPageNum() - 1);
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Exception getting next page: Message: "
                    + e.getMessage());
        }

        return nextPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nasa.jpl.oodt.cas.workflow.util.Pagination#getPagedWorkflows(int)
     */
    public WorkflowInstancePage getPagedWorkflows(int pageNum)
            throws InstanceRepositoryException {
        return getPagedWorkflows(pageNum, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nasa.jpl.oodt.cas.workflow.util.Pagination#getPagedWorkflows(int,
     *      java.lang.String)
     */
    public WorkflowInstancePage getPagedWorkflows(int pageNum, String status)
            throws InstanceRepositoryException {
        int totalPages = PaginationUtils.getTotalPage(
                status != null ? getNumWorkflowInstancesByStatus(status)
                        : getNumWorkflowInstances(), this.pageSize);

        /*
         * if there are 0 total pages in the result list size then don't bother
         * returning a valid product page instead, return blank ProductPage
         */
        if (totalPages == 0 || pageNum > totalPages || pageNum <= 0) {
            return WorkflowInstancePage.blankPage();
        }

        WorkflowInstancePage retPage = new WorkflowInstancePage();
        retPage.setPageNum(pageNum);
        retPage.setPageSize(this.pageSize);
        retPage.setTotalPages(totalPages);

        List wInstIds = paginateWorkflows(pageNum, status);

        if (wInstIds != null && wInstIds.size() > 0) {
            List workflowInstances = new Vector(wInstIds.size());

            for (Iterator i = wInstIds.iterator(); i.hasNext();) {
                String workflowInstId = (String) i.next();
                WorkflowInstance inst = getWorkflowInstanceById(workflowInstId);
                workflowInstances.add(inst);
            }

            retPage.setPageWorkflows(workflowInstances);
        }

        return retPage;
    }

    /**
     * 
     * @param pageNum
     * @return
     * @throws InstanceRepositoryException
     */
    protected List paginateWorkflows(int pageNum)
            throws InstanceRepositoryException {
        return paginateWorkflows(pageNum, null);
    }

    /**
     * 
     * @param pageNum
     * @param status
     * @return
     * @throws InstanceRepositoryException
     */
    protected abstract List paginateWorkflows(int pageNum, String status)
            throws InstanceRepositoryException;

}