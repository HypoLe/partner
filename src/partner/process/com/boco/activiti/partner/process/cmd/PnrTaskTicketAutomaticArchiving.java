package com.boco.activiti.partner.process.cmd;

import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.boco.activiti.partner.process.model.PnrTaskTicket;
import com.boco.activiti.partner.process.service.IPnrTaskTicketService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.googlecode.genericdao.search.Search;

/**
 * User: zhuchengxu
 * Date: 13-9-14
 * Time: 下午2:48
 */
public class PnrTaskTicketAutomaticArchiving   implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processInstanceId= execution.getProcessInstanceId();
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) ApplicationContextHolder.getInstance().getBean("pnrTaskTicketService");
        Search search = new Search();
        search.addFilterEqual("processInstanceId", processInstanceId);
        List<PnrTaskTicket> list =pnrTaskTicketService.search(search);
        if(list!=null&&list.size()==1){
        	PnrTaskTicket pnrTaskTicket= list.get(0);
        	pnrTaskTicket.setArchivingTime(new Date());
        	pnrTaskTicket.setState(5);//归档标识
        	pnrTaskTicketService.save(pnrTaskTicket);
        }
    }

}
