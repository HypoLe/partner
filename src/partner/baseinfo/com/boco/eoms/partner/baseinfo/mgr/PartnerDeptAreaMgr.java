package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.PartnerDept;

/**
 * <p>
 * Title:����
 * </p>
 * <p>
 * Description:����
 * </p>
 * <p>
 * Mon Feb 09 10:57:12 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
 public interface PartnerDeptAreaMgr {
 
	/**
	 *
	 * ȡ���� �б�
	 * @return ���س����б�
	 */
	public List getPartnerDepts();
    
	/**
	 * �������ѯ����
	 * @param id ���
	 * @return ����ĳid�Ķ���
	 */
	public PartnerDept getPartnerDept(final String id);
    
	/**
	 * ���泧��
	 * @param partnerDept ����
	 */
	public void savePartnerDept(PartnerDept partnerDept);
    
	/**
	 * ������ɾ����
	 * @param id ���
	 */
	public void removePartnerDept(final String id);
    
	/**
	 * ��������ҳ��ѯ����
	 * @param curPage ��ǰҳ
	 * @param pageSize ÿҳ���¼����
	 * @param whereStr ��ѯ���
	 * @return ���س��̵ķ�ҳ�б�
	 */
	public Map getPartnerDepts(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	/**
	 * 由字段treeId得到partnerDept
	 */
	public PartnerDept getPartnerDeptByTreeId(final String treeId);
	/**
	 * 由字段treeNodeId得到partnerDept
	 */
	public PartnerDept getPartnerDeptByTreeNodeId(final String treeNodeId);
	/**
	 * id:EOMS-liujinlong-20090924143614
	 * 开发人邮箱：liujinlong@boco.com.cn
	 * 时间：2009-09-24
	 * 开发目的：将删除改为设置删除字段置deleted为1
	 * 参数parentNodeId：合作伙伴父节点nodeID，也可能是合作伙伴的nodeId
	 */
	public void removePartnerDeptByNodeId(final String parentNodeId);
	
	/**
	 * 按条件查询返回合作伙伴的集合
	 */	
	public List getPartnerDepts(final String where);
	
	
}