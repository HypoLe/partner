package com.boco.eoms.check.util;
import java.util.Vector;

import com.boco.eoms.check.model.*;
public class TawCheckSCOREMethod {
public static double getScore(double targer,TawCheckTarger tawCheckTarger)
{
	double score=0;
	if(97.79==targer){
		System.out.println("asdfsadsdf");
	}
	try
	{
//������ֹ���
if(tawCheckTarger.getTarger_prin().equals("0"))
{
	  if(targer>tawCheckTarger.getTarger_base())
	  {
		  score=(targer-tawCheckTarger.getTarger_base())/tawCheckTarger.getTarger_add()*tawCheckTarger.getTarger_score_add()+tawCheckTarger.getTarger_score_base();
		  if(score>tawCheckTarger.getTarger_score_full() || score==tawCheckTarger.getTarger_score_full())
		  {
			  score=tawCheckTarger.getTarger_score_full();
		  }
	  }
	  else if(targer<tawCheckTarger.getTarger_base())
	  {
		  score=tawCheckTarger.getTarger_score_base()-(tawCheckTarger.getTarger_base()-targer)/tawCheckTarger.getTarger_del()*tawCheckTarger.getTarger_score_del();
		  if((score<0 || score==0)&&tawCheckTarger.getTarger_score_base()>=0)
		  {
			  score=0;
		  }		  
	  }
	  else if(targer==tawCheckTarger.getTarger_base())
	  {
		  score=tawCheckTarger.getTarger_score_base();
	  }
}
//��������ֹ���
else if(tawCheckTarger.getTarger_prin().equals("1"))
{
	  if(targer>tawCheckTarger.getTarger_base())
	  {
		  score=tawCheckTarger.getTarger_score_base()-(targer-tawCheckTarger.getTarger_base())/tawCheckTarger.getTarger_del()*tawCheckTarger.getTarger_score_del();
		  if(score<0 || score==0)
		  {
			  score=0;
		  }	
	  }
	  else if(targer<tawCheckTarger.getTarger_base())
	  {
		  score=tawCheckTarger.getTarger_score_base()+(tawCheckTarger.getTarger_base()-targer)/tawCheckTarger.getTarger_add()*tawCheckTarger.getTarger_score_add();
		  if(score>tawCheckTarger.getTarger_score_full() || score==tawCheckTarger.getTarger_score_full())
		  {
			  score=tawCheckTarger.getTarger_score_full();
		  }
	  }
	  else if(targer==tawCheckTarger.getTarger_base())
	  {
		  score=tawCheckTarger.getTarger_score_base();
	  }	
}
	}catch(Exception e)
	{e.printStackTrace();}
	return score;
}

public static String getStr2str(String str){
	String strVar = "";
	try{
		java.util.StringTokenizer st = new java.util.StringTokenizer(str,",");
		while(st.hasMoreTokens()){
			strVar += "'"+st.nextToken()+"',";
		}
		if(strVar.length()>1){
			strVar = strVar.substring(0,strVar.length()-1);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return strVar;
}

public static int getStrNum(String str){
	int num = 0;
	try{
		java.util.StringTokenizer st = new java.util.StringTokenizer(str,",");
		num = st.countTokens();
	}catch(Exception e){
		e.printStackTrace();
	}
	return num;
}
/**
 * ����ne_id��õ�����������
 * @param ne_id
 * @return
 */
public String getZhLaber(int ne_id)
	{
		String ZH = "";
		
		try {
			if (ne_id == -1128953347){	
				ZH = "ȫʡ";
			}else if (ne_id == -1744891292){	
				ZH = "����";
			}else if(ne_id == 1948613501){
				ZH = "����";
			}else if(ne_id == 867223656){
				ZH = "�е�";
			}else if(ne_id == -402456014){
				ZH = "����";
			}else if(ne_id == 91461549){
				ZH = "��ˮ";
			}else if(ne_id == 414603468){
				ZH = "�ȷ�";
			}else if(ne_id == -1458085134){
				ZH = "�ػʵ�";
			}else if(ne_id == -578147474){
				ZH = "ʯ��ׯ";
			}else if(ne_id == 1478501655){
				ZH = "��ɽ";
			}else if(ne_id == 202839087){
				ZH = "��̨";
			}else if(ne_id == -75534827){
				ZH = "�żҿ�";
			}else {
				ZH = "δ֪����";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ZH;
		}
	public static String[] getAreaNams(){
		String areaNames[]={"����","ʯ��ׯ","����","�żҿ�","�е�","��ɽ","�ȷ�","����","��ˮ",	"��̨","�ػʵ�","ȫʡ"};
		return areaNames;
	}
	public static String getareaId(String name){
		String id="";
		String areaIds[]={"1","2","3","4","5","6","7","8","9","10","11","12"};
		String areaNames[]=getAreaNams();
		for(int i=0;i<areaNames.length;i++){
			if(areaNames[i].equals(name)){
				id=areaIds[i];
				break;
			}
		}
		return id;
	}
	public static boolean checkUserRoles(String userId){
		boolean flag=false;
		String roleUserIds[]={"yuanyanbin","wangliying","jiacuiran","duanshuxia","admin"};
		for(int i=0;i<roleUserIds.length;i++){
			if(userId.equals(roleUserIds[i])){
				flag=true;
				break;
			}
		}
		return flag;
	}
}