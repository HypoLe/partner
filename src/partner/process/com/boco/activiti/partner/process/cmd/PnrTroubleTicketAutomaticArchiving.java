package com.boco.activiti.partner.process.cmd;

import com.boco.activiti.partner.process.model.PnrTroubleTicket;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.googlecode.genericdao.search.Search;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Date;
import java.util.List;

/**
 * User: zhuchengxu
 * Date: 13-9-14
 * Time: 下午2:48
 */
public class PnrTroubleTicketAutomaticArchiving  implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processInstanceId= execution.getProcessInstanceId();
        IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService)ApplicationContextHolder.getInstance().getBean("pnrTroubleTicketService");
        Search search = new Search();
        search.addFilterEqual("processInstanceId", processInstanceId);
        List<PnrTroubleTicket> list =pnrTroubleTicketService.search(search);
        if(list!=null&&list.size()==1){
            PnrTroubleTicket pnrTroubleTicket= list.get(0);
            pnrTroubleTicket.setArchivingTime(new Date());
            pnrTroubleTicket.setState(5);//归档标识
            pnrTroubleTicketService.save(pnrTroubleTicket);
        }
    }

}
