package com.boco.eoms.partner.question.dao.hibernate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.question.dao.IPnrTransferQuestionJDBCDao;
import com.boco.eoms.partner.question.model.PnrTransferQuestion;

public class PnrTransferQuestionDaoJDBC extends JdbcDaoSupport implements IPnrTransferQuestionJDBCDao{
	private TaskService taskService;
	private HistoryService historyService;
	
	@Override
	public List<PnrTransferQuestion> getQuestionNumber(PnrTransferQuestion pnrTransferQuestion,int firstIndex,int lastIndex) {
		
		List<PnrTransferQuestion> pnrTransferQuestionList = new ArrayList<PnrTransferQuestion>();
		//List<PnrTransferQuestion> newPnrTransferQuestionList = new ArrayList<PnrTransferQuestion>();
		String sql = "select * from pnr_act_transfer_question q where 1=1 ";
		
		String linkman = pnrTransferQuestion.getLinkman();
		String linkmanPhone = pnrTransferQuestion.getLinkmanPhone();
		String commonQuestion = pnrTransferQuestion.getQuestionContent();
		String raiseTime = pnrTransferQuestion.getUseTime();
		
		if(linkman!=null && !"".equals(linkman.trim())){
			sql = sql +" and q.linkman like '%"+linkman+"%' ";
		}
		if(commonQuestion!=null && !"".equals(commonQuestion.trim())){
			sql = sql +" and q.question_content like '%"+commonQuestion+"%' ";
		}
		if(linkmanPhone!=null && !"".equals(linkmanPhone.trim())){
			sql = sql +" and q.linkman_phone = '"+linkmanPhone+"' ";
		}
		if(raiseTime!=null && !"".equals(raiseTime.trim())){
			sql = sql +" and q.raise_time>=to_date('"+raiseTime+"','yyyy-mm-dd') ";
		}
		
		List<Map>  list= this.getJdbcTemplate().queryForList(sql);
		System.out.println(sql);
		if(list.isEmpty() || list.size()==0 || list==null){
			return null;
		}else{
			for(Map oneData:list){
				PnrTransferQuestion pq = new PnrTransferQuestion();
				pq.setLinkman(oneData.get("LINKMAN").toString());
				pq.setLinkmanPhone(oneData.get("LINKMAN_PHONE").toString());
				pq.setQuestionContent(oneData.get("QUESTION_CONTENT").toString());
				pq.setQuestionNumber(oneData.get("QUESTION_NUM").toString());
				SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date time = (Date)oneData.get("RAISE_TIME");
				pq.setUseTime(sdf.format(time));
				pnrTransferQuestionList.add(pq);
			}
			/*int size = pnrTransferQuestionList.size();
	        int maxSize =0;
	        int begin = firstIndex;
	        //当取的值，为最大时
	        if(size>=lastIndex){
	        	maxSize =lastIndex;
	        }else{
	        	maxSize=size;
	        }
	        for(int index=begin;index<maxSize;index++){
	        	newPnrTransferQuestionList.add(pnrTransferQuestionList.get(index));
		        } 
			return newPnrTransferQuestionList;
		}*/
			return pnrTransferQuestionList;
		}
	}
	@Override
	public PnrTransferQuestion getQuestionInformationByQuestionNumber(String questionNumber){
		String sql = "select * from pnr_act_transfer_question p where p.question_num='"+questionNumber+"'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		
		PnrTransferQuestion pq = new PnrTransferQuestion();
		
		if(list.isEmpty() || list.size()==0 || list==null){
			return null;
		}else{
			for(Map oneData:list){
				pq.setLinkman(oneData.get("LINKMAN").toString());
				pq.setLinkmanPhone(oneData.get("LINKMAN_PHONE").toString());
				pq.setQuestionContent(oneData.get("QUESTION_CONTENT").toString());
				pq.setQuestionNumber(oneData.get("QUESTION_NUM").toString());
				SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date time = (Date)oneData.get("RAISE_TIME");
				pq.setUseTime(sdf.format(time));
				pq.setQuestionLevel(oneData.get("QUESTION_LEVEL").toString());
				pq.setQuestioner(oneData.get("QUESTIONER").toString());
				pq.setQuestionState(oneData.get("QUESTION_STATE").toString());
			}
			return pq;
		}
	}
	@Override
	public List<PnrTransferQuestion> selQuestionList(PnrTransferQuestion pnrTransferQuestion,int firstIndex,int lastIndex){
		String sql ="select * from pnr_act_transfer_question q where 1=1 ";
		String linkman = pnrTransferQuestion.getLinkman();
		String linkmanPhone = pnrTransferQuestion.getLinkmanPhone();
		String questionContent = pnrTransferQuestion.getQuestionContent();
		String raiseTime = pnrTransferQuestion.getUseTime();
		String questionState = pnrTransferQuestion.getQuestionState();
		String questionLevel = pnrTransferQuestion.getQuestionLevel();
		if(linkman!=null && !"".equals(linkman.trim())){
			sql = sql + " and q.linkman like '%"+linkman+"%' ";
		}
		if(linkmanPhone!=null && !"".equals(linkmanPhone.trim())){
			sql = sql +" and q.linkman_phone='"+linkmanPhone+"' ";
		}
		if(questionContent!=null && !"".equals(questionContent.trim())){
			sql = sql + " and q.question_content like '%"+questionContent+"%' ";
		}
		if(raiseTime!=null && !"".equals(raiseTime.trim())){
			sql = sql +" and q.raise_time>=to_date('"+raiseTime+"','yyyy-mm-dd hh24:mi:ss') "; 
		}
		if(questionState!=null && !"".equals(questionState.trim())){
			sql = sql + "  and q.question_state ='"+questionState+"' ";
		}
		if(questionLevel!=null && !"".equals(questionLevel.trim())&&!"3".equals(questionLevel.trim())){
			sql =sql +" and q.question_level = '"+questionLevel+"' ";
		}
			sql = sql+ " order by q.raise_time asc";
		System.out.println(sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		List<PnrTransferQuestion> firstList = new ArrayList<PnrTransferQuestion>();
		//List<PnrTransferQuestion> questionList = new ArrayList<PnrTransferQuestion>();
		if(list.isEmpty() || list==null || list.size()==0){
			return null;
		}else{
			for(Map one:list){
				PnrTransferQuestion qq = new PnrTransferQuestion();
				qq.setId(one.get("ID").toString());
				qq.setQuestionContent(one.get("QUESTION_CONTENT").toString());
				qq.setQuestionNumber(one.get("QUESTION_NUM").toString());
				qq.setQuestionLevel(one.get("QUESTION_LEVEL").toString());
				qq.setQuestionState(one.get("QUESTION_STATE").toString());
				SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date time = (Date)one.get("RAISE_TIME");
				qq.setUseTime(sdf.format(time));
				
				firstList.add(qq);
			}
			/*int size = firstList.size();
	        int maxSize =0;
	        int begin = firstIndex;
	        //当取的值，为最大时
	        if(size>=lastIndex){
	        	maxSize =lastIndex;
	        }else{
	        	maxSize=size;
	        }
	        for(int index=begin;index<maxSize;index++){
	        	questionList.add(firstList.get(index));
		        } 
			return questionList;*/
			return firstList;
		}
	}
	@Override
	public PnrTransferQuestion getOneQuestion(String number){
		String sql = "select * from pnr_act_transfer_question q where q.question_num ='"+number+"'";
			List<Map> list = this.getJdbcTemplate().queryForList(sql);
			PnrTransferQuestion pnrTransferQuestion = new PnrTransferQuestion();
			if(list==null || list.isEmpty()||list.size()==0){
				return null;
			}else{
				for(Map one:list){
					pnrTransferQuestion.setId(one.get("ID").toString());
					pnrTransferQuestion.setLinkman(one.get("LINKMAN").toString());
					pnrTransferQuestion.setLinkmanPhone(one.get("LINKMAN_PHONE").toString());
					pnrTransferQuestion.setQuestionContent(one.get("QUESTION_CONTENT").toString());
					pnrTransferQuestion.setQuestioner(one.get("QUESTIONER").toString());
					pnrTransferQuestion.setQuestionLevel(one.get("QUESTION_LEVEL").toString());
					pnrTransferQuestion.setQuestionState(one.get("QUESTION_STATE").toString());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					pnrTransferQuestion.setUseTime(sdf.format((Date)one.get("RAISE_TIME")));
					pnrTransferQuestion.setQuestionNumber(one.get("QUESTION_NUM").toString());
				}
			}
		return pnrTransferQuestion;
	}
	
	@Override
	public boolean ChangeQuestionInformation(String questionNumber,String questionState,String questionLevel){
		boolean flag = false;
		String sql = "update pnr_act_transfer_question p set ";
		if(questionNumber==null || "".equals(questionNumber.trim())){
			return false;
		}else if(questionState!=null && !"".equals(questionState.trim())&&!"请选择".equals(questionState)){
			flag = true;
			sql = sql + "  p.question_state='"+questionState+"' ";
		}
		if(questionLevel!=null && !"".equals(questionLevel.trim())){
			if(flag){
				sql = sql + " , p.question_level='"+questionLevel+"'";
			}else{
				sql = sql + " p.question_level='"+questionLevel+"'";
			}
			flag = true;
		}
		if(flag){
			sql = sql + " where p.question_num='"+questionNumber+"'";
			this.getJdbcTemplate().update(sql);
			return true;			
		}else{
			return false;
		}
	}
	
	
	
	
	
}
