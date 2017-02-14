package com.boco.eoms.partner.question.dao.hibernate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.question.dao.IPnrTransferQuestionStateJDBCDao;
import com.boco.eoms.partner.question.model.PnrTransferQuestionState;

public class PnrTransferQuestionStateDaoJDBC extends JdbcDaoSupport implements IPnrTransferQuestionStateJDBCDao{
	
	public List<PnrTransferQuestionState> selectPnrTransferQuestionStateByQuestionNumber(String number){
		String sql = "select * from pnr_act_question_state s where s.question_number = '"+number+"' order by s.change_time asc";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		List<PnrTransferQuestionState> questoinStateList = new ArrayList<PnrTransferQuestionState>();
		if(list.isEmpty()||list==null||list.size()==0){
			return null;
		}else{
			for(Map oneData :list){
				PnrTransferQuestionState  ps = new PnrTransferQuestionState();
				Date time = (Date)oneData.get("CHANGE_TIME");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ps.setUseTime(sdf.format(time));
				ps.setQuesitonState(oneData.get("QUESTION_STATE").toString());
				questoinStateList.add(ps);
			}
			return questoinStateList;
		}
	}
}
