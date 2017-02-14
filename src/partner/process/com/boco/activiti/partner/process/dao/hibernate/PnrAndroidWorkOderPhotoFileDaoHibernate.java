package com.boco.activiti.partner.process.dao.hibernate;

import com.boco.activiti.partner.process.dao.IPnrAndroidWorkOderPhotoFileDao;
import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;

public class PnrAndroidWorkOderPhotoFileDaoHibernate  extends CommonGenericDaoImpl<PnrAndroidWorkOderPhotoFile, String> implements IPnrAndroidWorkOderPhotoFileDao {

	@Override
	public void savePhoto(
			PnrAndroidWorkOderPhotoFile pnrAndroidWorkOderPhotoFile) {
		getHibernateTemplate().save(pnrAndroidWorkOderPhotoFile);
		
	}

	@Override
	public PnrAndroidWorkOderPhotoFile find(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PnrAndroidWorkOderPhotoFile[] find(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PnrAndroidWorkOderPhotoFile getReference(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PnrAndroidWorkOderPhotoFile[] getReferences(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeById(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeByIds(String... arg0) {
		// TODO Auto-generated method stub
		
	}

}
