package com.boco.activiti.partner.process.dao.hibernate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.ISchemeRateJDBCDao;
import com.boco.activiti.partner.process.po.ChildSceneReportsModel;

public class SchemeRateDaoJDBC extends JdbcDaoSupport implements ISchemeRateJDBCDao {
	/**
	 *   子场景工单统计
	 	 * @author WANGJUN
	 	 * @title: childSceneReports
	 	 * @date Jul 21, 2016 9:43:00 AM
	 	 * @param sheetAcceptLimit
	 	 * @param sheetCompleteLimit
	 	 * @param cityId
	 	 * @return List<TaskModel>
	 */
	public List<ChildSceneReportsModel> childSceneReports(String sheetAcceptLimit,String sheetCompleteLimit,String cityId,String flag){
		List paramList = new ArrayList();
		String sql = "select areaid,\n" +
				"       areaname,\n" + 
				"       total_num,\n" + 
				"       total_project_amount,\n" + 
				"       total_work_amout,\n" + 
				"       total_data_amout,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round(total_work_amout / total_project_amount * 100, 2)) as work_ratio, --工费占比(百分比值)\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round(total_data_amout / total_project_amount * 100, 2)) as data_ratio, --材料费占比(百分比值)\n" + 
				"       A1,\n" + 
				"       A2,\n" + 
				"       A3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((A3 + A2) / total_project_amount * 100, 2)) as A4, --敷设光缆（不含接续） 费用占比\n" + 
				"       B1,\n" + 
				"       B2,\n" + 
				"       B3,\n" + 
				"       decode(B1, 0, 0, round(B2 / B1, 2)) as B4,\n" + 
				"       decode(B1, 0, 0, round(B3 / B1, 2)) as B5,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((B3 + B2) / total_project_amount * 100, 2)) as B6, --光缆接头接续 接续费用占比\n" + 
				"       C1,\n" + 
				"       C2,\n" + 
				"       C3,\n" + 
				"       decode(C1, 0, 0, round(C2 / C1, 2)) as C4,\n" + 
				"       decode(C1, 0, 0, round(C3 / C1, 2)) as C5,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((C3 + C2) / total_project_amount * 100, 2)) as C6, --光缆成端接续 接续费用占比\n" + 
				"       D1,\n" + 
				"       D2,\n" + 
				"       D3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((D3 + D2) / total_project_amount * 100, 2)) as D4, --拆除光缆 费用占比\n" + 
				"       E1,\n" + 
				"       E2,\n" + 
				"       E3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((E3 + E2) / total_project_amount * 100, 2)) as E4, --光缆整理 费用占比\n" + 
				"       F1,\n" + 
				"       F2,\n" + 
				"       F3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((F3 + F2) / total_project_amount * 100, 2)) as F4, --光缆断纤、劣化纤处理 费用占比\n" + 
				"       G1,\n" + 
				"       G2,\n" + 
				"       G3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((G3 + G2) / total_project_amount * 100, 2)) as G4, --光交接箱安装或迁移 费用占比\n" + 
				"       H1,\n" + 
				"       H2,\n" + 
				"       H3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((H3 + H2) / total_project_amount * 100, 2)) as H4, --光分路器箱（光分纤箱）安装或迁移 费用占比\n" + 
				"       I1,\n" + 
				"       I2,\n" + 
				"       I3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((I3 + I2) / total_project_amount * 100, 2)) as I4, --障碍物清除 费用占比\n" + 
				"       J1,\n" + 
				"       J2,\n" + 
				"       J3,\n" + 
				"       J4,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((J4 + J3 + J2) / total_project_amount * 100, 2)) as J5, --立电杆 费用占比\n" + 
				"       K1,\n" + 
				"       K2,\n" + 
				"       K3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((K3 + K2) / total_project_amount * 100, 2)) as K4, --扶正电杆 费用占比\n" + 
				"       L1,\n" + 
				"       L2,\n" + 
				"       L3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((L3 + L2) / total_project_amount * 100, 2)) as L4, --拆电杆 费用占比\n" + 
				"       M1,\n" + 
				"       M2,\n" + 
				"       M3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((M3 + M2) / total_project_amount * 100, 2)) as M4, --增补拉线 费用占比\n" + 
				"       N1,\n" + 
				"       N2,\n" + 
				"       N3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((N3 + N2) / total_project_amount * 100, 2)) as N4, --拆除拉线 费用占比\n" + 
				"       O1,\n" + 
				"       O2,\n" + 
				"       O3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((O3 + O2) / total_project_amount * 100, 2)) as O4, --架设吊线 费用占比\n" + 
				"       P1,\n" + 
				"       P2,\n" + 
				"       P3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((P3 + P2) / total_project_amount * 100, 2)) as P4, --拆除吊线 费用占比\n" + 
				"       Q1,\n" + 
				"       Q2,\n" + 
				"       Q3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((Q3 + Q2) / total_project_amount * 100, 2)) as Q4, --安装吊线保护装置 费用占比\n" + 
				"       R1,\n" + 
				"       R2,\n" + 
				"       R3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((R3 + R2) / total_project_amount * 100, 2)) as R4, --升高吊线 费用占比\n" + 
				"       S1,\n" + 
				"       S2,\n" + 
				"       S3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((S3 + S2) / total_project_amount * 100, 2)) as S4, --增补挂钩 费用占比\n" + 
				"       T1,\n" + 
				"       T2,\n" + 
				"       T3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((T3 + T2) / total_project_amount * 100, 2)) as T4, --障碍物清除 费用占比\n" + 
				"       U1,\n" + 
				"       U2,\n" + 
				"       U3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((U3 + U2) / total_project_amount * 100, 2)) as U4, --管道迁改 费用占比\n" + 
				"       V1,\n" + 
				"       V2,\n" + 
				"       V3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((V3 + V2) / total_project_amount * 100, 2)) as V4, --管道疏通及整修 费用占比\n" + 
				"       W1,\n" + 
				"       W2,\n" + 
				"       W3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((W3 + W2) / total_project_amount * 100, 2)) as W4, --管道积水及杂物清理 费用占比\n" + 
				"\n" + 
				"       X1,\n" + 
				"       X2,\n" + 
				"       X3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((X3 + X2) / total_project_amount * 100, 2)) as X4, --管道人孔升降、人井上覆的更换 费用占比\n" + 
				"       Y1,\n" + 
				"       Y2,\n" + 
				"       Y3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((Y3 + Y2) / total_project_amount * 100, 2)) as Y4, --障碍物清除 费用占比\n" + 
				"       Z1,\n" + 
				"       Z2,\n" + 
				"       Z3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((Z3 + Z2) / total_project_amount * 100, 2)) as Z4, --线路资源清查 费用占比\n" + 
				"       AA1,\n" + 
				"       AA2,\n" + 
				"       AA3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((AA3 + AA2) / total_project_amount * 100, 2)) as AA4, --充气设备维修 费用占比\n" + 
				"       BB1,\n" + 
				"       BB2,\n" + 
				"       BB3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((BB3 + BB2) / total_project_amount * 100, 2)) as BB4, --标识安装 费用占比\n" + 
				"       CC1,\n" + 
				"       CC2,\n" + 
				"       CC3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((CC3 + CC2) / total_project_amount * 100, 2)) as CC4, --标识粉刷 费用占比\n" + 
				"       DD1,\n" + 
				"       DD2,\n" + 
				"       DD3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((DD3 + DD2) / total_project_amount * 100, 2)) as DD4, --护坡加固 费用占比\n" + 
				"       EE1,\n" + 
				"       EE2,\n" + 
				"       EE3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((EE3 + EE2) / total_project_amount * 100, 2)) as EE4, --架空交接箱站台维修 费用占比\n" + 
				"       FF1,\n" + 
				"       FF2,\n" + 
				"       FF3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((FF3 + FF2) / total_project_amount * 100, 2)) as FF4, --交接分线设备粉刷、编号喷涂 费用占比\n" + 
				"       GG1,\n" + 
				"       GG2,\n" + 
				"       GG3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((GG3 + GG2) / total_project_amount * 100, 2)) as GG4, --障碍物清除 费用占比\n" + 
				"       HH1,\n" + 
				"       HH2,\n" + 
				"       HH3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((HH3 + HH2) / total_project_amount * 100, 2)) as HH4, --光缆（含光交）_其他费用（手工填写） 费用占比\n" + 
				"       II1,\n" + 
				"       II2,\n" + 
				"       II3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((II3 + II2) / total_project_amount * 100, 2)) as II4, --杆路_其他费用（手工填写） 费用占比\n" + 
				"       JJ1,\n" + 
				"       JJ2,\n" + 
				"       JJ3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((JJ3 + JJ2) / total_project_amount * 100, 2)) as JJ4, --管道_其他费用（手工填写） 费用占比\n" + 
				"       KK1,\n" + 
				"       KK2,\n" + 
				"       KK3,\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((KK3 + KK2) / total_project_amount * 100, 2)) as KK4, --其他项目_其他费用（手工填写） 费用占比\n" + 
				"       HH1 + II1 + JJ1 + KK1 as LL1, --非标准模板项目（次）\n" + 
				"       decode(total_project_amount,\n" + 
				"              0,\n" + 
				"              0,\n" + 
				"              round((HH2 + HH3 + II2 + II3 + JJ2 + JJ3 + KK2 + KK3) /\n" + 
				"                    total_project_amount * 100,\n" + 
				"                    2)) as MM1 --非标准模板费用占比\n" + 
				"\n" + 
				"  from (select a.areaid,\n" + 
				"               a.areaname,\n" + 
				"               nvl(total_num, 0) as total_num,\n" + 
				"               nvl(total_project_amount, 0) as total_project_amount,\n" + 
				"               nvl(A2, 0) + nvl(B2, 0) + nvl(C2, 0) + nvl(D2, 0) +\n" + 
				"               nvl(E2, 0) + nvl(F2, 0) + nvl(G2, 0) + nvl(H2, 0) +\n" + 
				"               nvl(I2, 0) + nvl(J2, 0) + nvl(K2, 0) + nvl(L2, 0) +\n" + 
				"               nvl(M2, 0) + nvl(N2, 0) + nvl(O2, 0) + nvl(P2, 0) +\n" + 
				"               nvl(Q2, 0) + nvl(R2, 0) + nvl(S2, 0) + nvl(T2, 0) +\n" + 
				"               nvl(U2, 0) + nvl(V2, 0) + nvl(W2, 0) + nvl(X2, 0) +\n" + 
				"               nvl(Y2, 0) + nvl(Z2, 0) + nvl(AA2, 0) + nvl(BB2, 0) +\n" + 
				"               nvl(CC2, 0) + nvl(DD2, 0) + nvl(EE2, 0) + nvl(FF2, 0) +\n" + 
				"               nvl(GG2, 0) + nvl(HH2, 0) + nvl(II2, 0) + nvl(JJ2, 0) +\n" + 
				"               nvl(KK2, 0) as total_work_amout,\n" + 
				"               nvl(A3, 0) + nvl(B3, 0) + nvl(C3, 0) + nvl(D3, 0) +\n" + 
				"               nvl(E3, 0) + nvl(F3, 0) + nvl(G3, 0) + nvl(H3, 0) +\n" + 
				"               nvl(I3, 0) + nvl(J3, 0) + nvl(K3, 0) + nvl(L3, 0) +\n" + 
				"               nvl(M3, 0) + nvl(N3, 0) + nvl(O3, 0) + nvl(P3, 0) +\n" + 
				"               nvl(Q3, 0) + nvl(R3, 0) + nvl(S3, 0) + nvl(T3, 0) +\n" + 
				"               nvl(U3, 0) + nvl(V3, 0) + nvl(W3, 0) + nvl(X3, 0) +\n" + 
				"               nvl(Y3, 0) + nvl(Z3, 0) + nvl(AA3, 0) + nvl(BB3, 0) +\n" + 
				"               nvl(CC3, 0) + nvl(DD3, 0) + nvl(EE3, 0) + nvl(FF3, 0) +\n" + 
				"               nvl(GG3, 0) + nvl(HH3, 0) + nvl(II3, 0) + nvl(JJ3, 0) +\n" + 
				"               nvl(KK3, 0) as total_data_amout,\n" + 
				"               nvl(A1, 0) as A1,\n" + 
				"               nvl(A2, 0) as A2,\n" + 
				"               nvl(A3, 0) as A3,\n" + 
				"               nvl(B1, 0) as B1,\n" + 
				"               nvl(B2, 0) as B2,\n" + 
				"               nvl(B3, 0) as B3,\n" + 
				"               nvl(C1, 0) as C1,\n" + 
				"               nvl(C2, 0) as C2,\n" + 
				"               nvl(C3, 0) as C3,\n" + 
				"               nvl(D1, 0) as D1,\n" + 
				"               nvl(D2, 0) as D2,\n" + 
				"               nvl(D3, 0) as D3,\n" + 
				"               nvl(E1, 0) as E1,\n" + 
				"               nvl(E2, 0) as E2,\n" + 
				"               nvl(E3, 0) as E3,\n" + 
				"               nvl(F1, 0) as F1,\n" + 
				"               nvl(F2, 0) as F2,\n" + 
				"               nvl(F3, 0) as F3,\n" + 
				"               nvl(G1, 0) as G1,\n" + 
				"               nvl(G2, 0) as G2,\n" + 
				"               nvl(G3, 0) as G3,\n" + 
				"               nvl(H1, 0) as H1,\n" + 
				"               nvl(H2, 0) as H2,\n" + 
				"               nvl(H3, 0) as H3,\n" + 
				"               nvl(I1, 0) as I1,\n" + 
				"               nvl(I2, 0) as I2,\n" + 
				"               nvl(I3, 0) as I3,\n" + 
				"               nvl(J1, 0) as J1,\n" + 
				"               nvl(J2, 0) as J2,\n" + 
				"               nvl(J3, 0) as J3,\n" + 
				"               nvl(J4, 0) as J4,\n" + 
				"               nvl(K1, 0) as K1,\n" + 
				"               nvl(K2, 0) as K2,\n" + 
				"               nvl(K3, 0) as K3,\n" + 
				"               nvl(L1, 0) as L1,\n" + 
				"               nvl(L2, 0) as L2,\n" + 
				"               nvl(L3, 0) as L3,\n" + 
				"               nvl(M1, 0) as M1,\n" + 
				"               nvl(M2, 0) as M2,\n" + 
				"               nvl(M3, 0) as M3,\n" + 
				"               nvl(N1, 0) as N1,\n" + 
				"               nvl(N2, 0) as N2,\n" + 
				"               nvl(N3, 0) as N3,\n" + 
				"               nvl(O1, 0) as O1,\n" + 
				"               nvl(O2, 0) as O2,\n" + 
				"               nvl(O3, 0) as O3,\n" + 
				"               nvl(P1, 0) as P1,\n" + 
				"               nvl(P2, 0) as P2,\n" + 
				"               nvl(P3, 0) as P3,\n" + 
				"               nvl(Q1, 0) as Q1,\n" + 
				"               nvl(Q2, 0) as Q2,\n" + 
				"               nvl(Q3, 0) as Q3,\n" + 
				"               nvl(R1, 0) as R1,\n" + 
				"               nvl(R2, 0) as R2,\n" + 
				"               nvl(R3, 0) as R3,\n" + 
				"               nvl(S1, 0) as S1,\n" + 
				"               nvl(S2, 0) as S2,\n" + 
				"               nvl(S3, 0) as S3,\n" + 
				"               nvl(T1, 0) as T1,\n" + 
				"               nvl(T2, 0) as T2,\n" + 
				"               nvl(T3, 0) as T3,\n" + 
				"               nvl(U1, 0) as U1,\n" + 
				"               nvl(U2, 0) as U2,\n" + 
				"               nvl(U3, 0) as U3,\n" + 
				"               nvl(V1, 0) as V1,\n" + 
				"               nvl(V2, 0) as V2,\n" + 
				"               nvl(V3, 0) as V3,\n" + 
				"               nvl(W1, 0) as W1,\n" + 
				"               nvl(W2, 0) as W2,\n" + 
				"               nvl(W3, 0) as W3,\n" + 
				"               nvl(X1, 0) as X1,\n" + 
				"               nvl(X2, 0) as X2,\n" + 
				"               nvl(X3, 0) as X3,\n" + 
				"               nvl(Y1, 0) as Y1,\n" + 
				"               nvl(Y2, 0) as Y2,\n" + 
				"               nvl(Y3, 0) as Y3,\n" + 
				"               nvl(Z1, 0) as Z1,\n" + 
				"               nvl(Z2, 0) as Z2,\n" + 
				"               nvl(Z3, 0) as Z3,\n" + 
				"               nvl(AA1, 0) as AA1,\n" + 
				"               nvl(AA2, 0) as AA2,\n" + 
				"               nvl(AA3, 0) as AA3,\n" + 
				"               nvl(BB1, 0) as BB1,\n" + 
				"               nvl(BB2, 0) as BB2,\n" + 
				"               nvl(BB3, 0) as BB3,\n" + 
				"               nvl(CC1, 0) as CC1,\n" + 
				"               nvl(CC2, 0) as CC2,\n" + 
				"               nvl(CC3, 0) as CC3,\n" + 
				"               nvl(DD1, 0) as DD1,\n" + 
				"               nvl(DD2, 0) as DD2,\n" + 
				"               nvl(DD3, 0) as DD3,\n" + 
				"               nvl(EE1, 0) as EE1,\n" + 
				"               nvl(EE2, 0) as EE2,\n" + 
				"               nvl(EE3, 0) as EE3,\n" + 
				"               nvl(FF1, 0) as FF1,\n" + 
				"               nvl(FF2, 0) as FF2,\n" + 
				"               nvl(FF3, 0) as FF3,\n" + 
				"               nvl(GG1, 0) as GG1,\n" + 
				"               nvl(GG2, 0) as GG2,\n" + 
				"               nvl(GG3, 0) as GG3,\n" + 
				"               nvl(HH1, 0) as HH1,\n" + 
				"               nvl(HH2, 0) as HH2,\n" + 
				"               nvl(HH3, 0) as HH3,\n" + 
				"               nvl(II1, 0) as II1,\n" + 
				"               nvl(II2, 0) as II2,\n" + 
				"               nvl(II3, 0) as II3,\n" + 
				"               nvl(JJ1, 0) as JJ1,\n" + 
				"               nvl(JJ2, 0) as JJ2,\n" + 
				"               nvl(JJ3, 0) as JJ3,\n" + 
				"               nvl(KK1, 0) as KK1,\n" + 
				"               nvl(KK2, 0) as KK2,\n" + 
				"               nvl(KK3, 0) as KK3\n" + 
				"          from taw_system_area a,\n" + 
				"               (select a.areaid,\n" + 
				"                       count(n.process_instance_id) as total_num,\n" + 
				"                       sum(nvl(n.project_amount, 0)) as total_project_amount\n" + 
				"                  from pnr_act_transfer_office_main n\n" + 
				"                  join taw_system_area a\n";
		if("countyQuery".equals(flag)){
			sql+=   "                    on n.country = a.areaid\n"; //地市查询
		}else{
			 sql+=   "                    on n.city = a.areaid\n"; //地市查询
		}
				
	    sql+=   "                 where n.themeinterface = 'interface'\n" + 
				" and to_char(n.distributed_interface_time, 'yyyy-mm-dd') >= ? and to_char(n.distributed_interface_time, 'yyyy-mm-dd') <= ?";
				paramList.add(sheetAcceptLimit);
				paramList.add(sheetCompleteLimit);
		sql+=   "                   and a.parentareaid = ?\n";
				paramList.add(cityId);
		sql+=   "                 group by a.areaid) t1, ---- 统计项目总数和项目总金额\n" + 
				"               (select t.areaid,\n" + 
				"                       sum(decode(t.copy_no, 'ZEJ3AFR8', sum_num, 0)) as A1,\n" + 
				"                       sum(decode(t.copy_no, 'ZEJ3AFR8', sum_cost_work, 0)) as A2,\n" + 
				"                       sum(decode(t.copy_no, 'ZEJ3AFR8', sum_material_cost, 0)) as A3, --敷设光缆（不含接续）\n" + 
				"\n" + 
				"                       sum(CASE\n" + 
				"                             WHEN t.copy_no = 'EY3NZBTZ' AND\n" + 
				"                                  t.unit_id != 'V1IWXHBYMU27' THEN\n" + 
				"                              sum_num\n" + 
				"                             ELSE\n" + 
				"                              0\n" + 
				"                           END) as B1,\n" + 
				"                       sum(CASE\n" + 
				"                             WHEN t.copy_no = 'EY3NZBTZ' AND\n" + 
				"                                  t.unit_id != 'V1IWXHBYMU27' THEN\n" + 
				"                              sum_cost_work\n" + 
				"                             ELSE\n" + 
				"                              0\n" + 
				"                           END) as B2,\n" + 
				"                       sum(CASE\n" + 
				"                             WHEN t.copy_no = 'EY3NZBTZ' AND\n" + 
				"                                  t.unit_id != 'V1IWXHBYMU27' THEN\n" + 
				"                              sum_material_cost\n" + 
				"                             ELSE\n" + 
				"                              0\n" + 
				"                           END) as B3, --光缆接头接续\n" + 
				"\n" + 
				"                       sum(CASE\n" + 
				"                             WHEN t.copy_no = 'EY3NZBTZ' AND\n" + 
				"                                  t.unit_id = 'V1IWXHBYMU27' THEN\n" + 
				"                              sum_num\n" + 
				"                             ELSE\n" + 
				"                              0\n" + 
				"                           END) as C1,\n" + 
				"                       sum(CASE\n" + 
				"                             WHEN t.copy_no = 'EY3NZBTZ' AND\n" + 
				"                                  t.unit_id = 'V1IWXHBYMU27' THEN\n" + 
				"                              sum_cost_work\n" + 
				"                             ELSE\n" + 
				"                              0\n" + 
				"                           END) as C2,\n" + 
				"                       sum(CASE\n" + 
				"                             WHEN t.copy_no = 'EY3NZBTZ' AND\n" + 
				"                                  t.unit_id = 'V1IWXHBYMU27' THEN\n" + 
				"                              sum_material_cost\n" + 
				"                             ELSE\n" + 
				"                              0\n" + 
				"                           END) as C3, --光缆成端接续\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'XTHE9VA6', sum_num, 0)) as D1,\n" + 
				"                       sum(decode(t.copy_no, 'XTHE9VA6', sum_cost_work, 0)) as D2,\n" + 
				"                       sum(decode(t.copy_no, 'XTHE9VA6', sum_material_cost, 0)) as D3, --拆除光缆\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, '2G6Q2WGQ', sum_num, 0)) as E1,\n" + 
				"                       sum(decode(t.copy_no, '2G6Q2WGQ', sum_cost_work, 0)) as E2,\n" + 
				"                       sum(decode(t.copy_no, '2G6Q2WGQ', sum_material_cost, 0)) as E3, --光缆整理\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'VMHMC16Z', sum_num, 0)) as F1,\n" + 
				"                       sum(decode(t.copy_no, 'VMHMC16Z', sum_cost_work, 0)) as F2,\n" + 
				"                       sum(decode(t.copy_no, 'VMHMC16Z', sum_material_cost, 0)) as F3, --光缆断纤、劣化纤处理\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'F00WOGBR', sum_num, 0)) as G1,\n" + 
				"                       sum(decode(t.copy_no, 'F00WOGBR', sum_cost_work, 0)) as G2,\n" + 
				"                       sum(decode(t.copy_no, 'F00WOGBR', sum_material_cost, 0)) as G3, --光交接箱安装或迁移\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, '2G2QUCF2', sum_num, 0)) as H1,\n" + 
				"                       sum(decode(t.copy_no, '2G2QUCF2', sum_cost_work, 0)) as H2,\n" + 
				"                       sum(decode(t.copy_no, '2G2QUCF2', sum_material_cost, 0)) as H3, --光分路器箱（光分纤箱）安装或迁移\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'VQTCB7Y9', sum_num, 0)) as I1,\n" + 
				"                       sum(decode(t.copy_no, 'VQTCB7Y9', sum_cost_work, 0)) as I2,\n" + 
				"                       sum(decode(t.copy_no, 'VQTCB7Y9', sum_material_cost, 0)) as I3, --障碍物清除(光缆（含光交）)\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'KL9BCZZC', sum_num, 0)) as J1,\n" + 
				"                       sum(decode(t.copy_no, 'KL9BCZZC', sum_cost_work, 0)) as J2,\n" + 
				"                       sum(decode(t.copy_no, 'KL9BCZZC', sum_material_cost, 0)) as J3,\n" + 
				"                       sum(decode(t.copy_no, 'KL9BCZZC', sum_second, 0)) as J4, --立电杆\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'UHAYCLY4', sum_num, 0)) as K1,\n" + 
				"                       sum(decode(t.copy_no, 'UHAYCLY4', sum_cost_work, 0)) as K2,\n" + 
				"                       sum(decode(t.copy_no, 'UHAYCLY4', sum_material_cost, 0)) as K3, --扶正电杆\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'X8GX8WSQ', sum_num, 0)) as L1,\n" + 
				"                       sum(decode(t.copy_no, 'X8GX8WSQ', sum_cost_work, 0)) as L2,\n" + 
				"                       sum(decode(t.copy_no, 'X8GX8WSQ', sum_material_cost, 0)) as L3, --拆电杆\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'CBZO8580', sum_num, 0)) as M1,\n" + 
				"                       sum(decode(t.copy_no, 'CBZO8580', sum_cost_work, 0)) as M2,\n" + 
				"                       sum(decode(t.copy_no, 'CBZO8580', sum_material_cost, 0)) as M3, --增补拉线\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'L971GA4X', sum_num, 0)) as N1,\n" + 
				"                       sum(decode(t.copy_no, 'L971GA4X', sum_cost_work, 0)) as N2,\n" + 
				"                       sum(decode(t.copy_no, 'L971GA4X', sum_material_cost, 0)) as N3, --拆除拉线\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'YCO7XD6S', sum_num, 0)) as O1,\n" + 
				"                       sum(decode(t.copy_no, 'YCO7XD6S', sum_cost_work, 0)) as O2,\n" + 
				"                       sum(decode(t.copy_no, 'YCO7XD6S', sum_material_cost, 0)) as O3, --架设吊线\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'Y9AK4RZW', sum_num, 0)) as P1,\n" + 
				"                       sum(decode(t.copy_no, 'Y9AK4RZW', sum_cost_work, 0)) as P2,\n" + 
				"                       sum(decode(t.copy_no, 'Y9AK4RZW', sum_material_cost, 0)) as P3, --拆除吊线\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'W8ZL58DG', sum_num, 0)) as Q1,\n" + 
				"                       sum(decode(t.copy_no, 'W8ZL58DG', sum_cost_work, 0)) as Q2,\n" + 
				"                       sum(decode(t.copy_no, 'W8ZL58DG', sum_material_cost, 0)) as Q3, --安装吊线保护装置\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'RIZTV21V', sum_num, 0)) as R1,\n" + 
				"                       sum(decode(t.copy_no, 'RIZTV21V', sum_cost_work, 0)) as R2,\n" + 
				"                       sum(decode(t.copy_no, 'RIZTV21V', sum_material_cost, 0)) as R3, --升高吊线\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'L4469ZIB', sum_num, 0)) as S1,\n" + 
				"                       sum(decode(t.copy_no, 'L4469ZIB', sum_cost_work, 0)) as S2,\n" + 
				"                       sum(decode(t.copy_no, 'L4469ZIB', sum_material_cost, 0)) as S3, --增补挂钩\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'L98CW8WI', sum_num, 0)) as T1,\n" + 
				"                       sum(decode(t.copy_no, 'L98CW8WI', sum_cost_work, 0)) as T2,\n" + 
				"                       sum(decode(t.copy_no, 'L98CW8WI', sum_material_cost, 0)) as T3, --障碍物清除(杆路)\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'I7NLIHDQ', sum_num, 0)) as U1,\n" + 
				"                       sum(decode(t.copy_no, 'I7NLIHDQ', sum_cost_work, 0)) as U2,\n" + 
				"                       sum(decode(t.copy_no, 'I7NLIHDQ', sum_material_cost, 0)) as U3, --管道迁改\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'UN0TG8OK', sum_num, 0)) as V1,\n" + 
				"                       sum(decode(t.copy_no, 'UN0TG8OK', sum_cost_work, 0)) as V2,\n" + 
				"                       sum(decode(t.copy_no, 'UN0TG8OK', sum_material_cost, 0)) as V3, --管道疏通及整修\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'EEQDQI7I', sum_num, 0)) as W1,\n" + 
				"                       sum(decode(t.copy_no, 'EEQDQI7I', sum_cost_work, 0)) as W2,\n" + 
				"                       sum(decode(t.copy_no, 'EEQDQI7I', sum_material_cost, 0)) as W3, --管道积水及杂物清理\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'I8Y1G9YA', sum_num, 0)) as X1,\n" + 
				"                       sum(decode(t.copy_no, 'I8Y1G9YA', sum_cost_work, 0)) as X2,\n" + 
				"                       sum(decode(t.copy_no, 'I8Y1G9YA', sum_material_cost, 0)) as X3, --管道人孔升降、人井上覆的更换\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'QMFDKEMG', sum_num, 0)) as Y1,\n" + 
				"                       sum(decode(t.copy_no, 'QMFDKEMG', sum_cost_work, 0)) as Y2,\n" + 
				"                       sum(decode(t.copy_no, 'QMFDKEMG', sum_material_cost, 0)) as Y3, --障碍物清除(管道)\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'R4ZQ76VZ', sum_num, 0)) as Z1,\n" + 
				"                       sum(decode(t.copy_no, 'R4ZQ76VZ', sum_cost_work, 0)) as Z2,\n" + 
				"                       sum(decode(t.copy_no, 'R4ZQ76VZ', sum_material_cost, 0)) as Z3, --线路资源清查\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'NZQMQ410', sum_num, 0)) as AA1,\n" + 
				"                       sum(decode(t.copy_no, 'NZQMQ410', sum_cost_work, 0)) as AA2,\n" + 
				"                       sum(decode(t.copy_no, 'NZQMQ410', sum_material_cost, 0)) as AA3, --充气设备维修\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'GMNYRB0L', sum_num, 0)) as BB1,\n" + 
				"                       sum(decode(t.copy_no, 'GMNYRB0L', sum_cost_work, 0)) as BB2,\n" + 
				"                       sum(decode(t.copy_no, 'GMNYRB0L', sum_material_cost, 0)) as BB3, --标识安装\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'AF70NA05', sum_num, 0)) as CC1,\n" + 
				"                       sum(decode(t.copy_no, 'AF70NA05', sum_cost_work, 0)) as CC2,\n" + 
				"                       sum(decode(t.copy_no, 'AF70NA05', sum_material_cost, 0)) as CC3, --标识粉刷\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'JQ3XP2I5', sum_num, 0)) as DD1,\n" + 
				"                       sum(decode(t.copy_no, 'JQ3XP2I5', sum_cost_work, 0)) as DD2,\n" + 
				"                       sum(decode(t.copy_no, 'JQ3XP2I5', sum_material_cost, 0)) as DD3, --护坡加固\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, '8PO7EZB6', sum_num, 0)) as EE1,\n" + 
				"                       sum(decode(t.copy_no, '8PO7EZB6', sum_cost_work, 0)) as EE2,\n" + 
				"                       sum(decode(t.copy_no, '8PO7EZB6', sum_material_cost, 0)) as EE3, --架空交接箱站台维修\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, '82CBQN11', sum_num, 0)) as FF1,\n" + 
				"                       sum(decode(t.copy_no, '82CBQN11', sum_cost_work, 0)) as FF2,\n" + 
				"                       sum(decode(t.copy_no, '82CBQN11', sum_material_cost, 0)) as FF3, --交接分线设备粉刷、编号喷涂\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'K6QUHV57', sum_num, 0)) as GG1,\n" + 
				"                       sum(decode(t.copy_no, 'K6QUHV57', sum_cost_work, 0)) as GG2,\n" + 
				"                       sum(decode(t.copy_no, 'K6QUHV57', sum_material_cost, 0)) as GG3, --障碍物清除(其他项目)\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'P51MTTQI', sum_num, 0)) as HH1,\n" + 
				"                       sum(decode(t.copy_no, 'P51MTTQI', sum_cost_work, 0)) as HH2,\n" + 
				"                       sum(decode(t.copy_no, 'P51MTTQI', sum_material_cost, 0)) as HH3, --光缆（含光交）_其他费用（手工填写）\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'Q9A5TZ5Y', sum_num, 0)) as II1,\n" + 
				"                       sum(decode(t.copy_no, 'Q9A5TZ5Y', sum_cost_work, 0)) as II2,\n" + 
				"                       sum(decode(t.copy_no, 'Q9A5TZ5Y', sum_material_cost, 0)) as II3, --杆路_其他费用（手工填写）\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, 'EEX0V1C4', sum_num, 0)) as JJ1,\n" + 
				"                       sum(decode(t.copy_no, 'EEX0V1C4', sum_cost_work, 0)) as JJ2,\n" + 
				"                       sum(decode(t.copy_no, 'EEX0V1C4', sum_material_cost, 0)) as JJ3, --管道_其他费用（手工填写）\n" + 
				"\n" + 
				"                       sum(decode(t.copy_no, '1BCUVAD8', sum_num, 0)) as KK1,\n" + 
				"                       sum(decode(t.copy_no, '1BCUVAD8', sum_cost_work, 0)) as KK2,\n" + 
				"                       sum(decode(t.copy_no, '1BCUVAD8', sum_material_cost, 0)) as KK3 --其他项目_其他费用（手工填写）\n" + 
				"\n" + 
				"                  from (select a.areaid,\n" + 
				"                               m.copy_no,\n" + 
				"                               m.unit_id,\n" + 
				"                               sum(nvl(m.num, '0')) as sum_num,\n" + 
				"                               sum(nvl(m.COST_WORK, '0')) as sum_cost_work,\n" + 
				"                               sum(nvl(m.material_cost, '0')) as sum_material_cost,\n" + 
				"                               sum(nvl(m.second_cost_per, '0')) as sum_second\n" + 
				"                          from maste_rel_task_item m\n" + 
				"                          left join pnr_act_transfer_office_main n\n" + 
				"                            on m.process_instance_id = n.process_instance_id\n" + 
				"                          left join maste_copy_scene e\n" + 
				"                            on m.child_scene_id = e.copy_no\n" + 
				"                          left join taw_system_area a\n";
				if("countyQuery".equals(flag)){
					sql+=   "                    on n.country = a.areaid\n"; //地市查询
				}else{
					 sql+=   "                    on n.city = a.areaid\n"; //地市查询
				}
		sql+=   "                         where n.themeinterface = 'interface'\n" + 
				"                           and m.link_type = 'need'\n" + 

				" and to_char(n.distributed_interface_time, 'yyyy-mm-dd') >= ? and to_char(n.distributed_interface_time, 'yyyy-mm-dd') <= ?";
				paramList.add(sheetAcceptLimit);
				paramList.add(sheetCompleteLimit);
		sql+=   "                           and a.parentareaid = ?\n";
				paramList.add(cityId);
		sql+=   "                         group by a.areaid, m.copy_no, m.unit_id) t\n" + 
				"                 group by t.areaid) t2 -- 统计各个子场景的数量\n" + 
				"         where a.areaid = t1.areaid(+)\n" + 
				"           and a.areaid = t2.areaid(+)\n" + 
				"           and a.parentareaid = ?\n";
				paramList.add(cityId);
		sql+=   "         order by a.sort)";
		
		System.out.println("-------------sql="+sql);
		
		String[] fieldNames = new String[]{"A1","A2","A3","A4","B1","B2","B3","B4","B5","B6","C1","C2","C3","C4","C5","C6","D1","D2","D3","D4","E1","E2","E3","E4","F1","F2","F3","F4","G1","G2","G3","G4","H1","H2","H3","H4","I1","I2","I3","I4","J1","J2","J3","J4","J5","K1","K2","K3","K4","L1","L2","L3","L4","M1","M2","M3","M4","N1","N2","N3","N4","O1","O2","O3","O4","P1","P2","P3","P4","Q1","Q2","Q3","Q4","R1","R2","R3","R4","S1","S2","S3","S4","T1","T2","T3","T4","U1","U2","U3","U4","V1","V2","V3","V4","W1","W2","W3","W4","X1","X2","X3","X4","Y1","Y2","Y3","Y4","Z1","Z2","Z3","Z4","AA1","AA2","AA3","AA4","BB1","BB2","BB3","BB4","CC1","CC2","CC3","CC4","DD1","DD2","DD3","DD4","EE1","EE2","EE3","EE4","FF1","FF2","FF3","FF4","GG1","GG2","GG3","GG4","HH1","HH2","HH3","HH4","II1","II2","II3","II4","JJ1","JJ2","JJ3","JJ4","KK1","KK2","KK3","KK4","LL1","MM1"};
		Object[] param = paramList.toArray();
		List<Map> queryForList = this.getJdbcTemplate().queryForList(sql, param);
		List<ChildSceneReportsModel> list = new ArrayList<ChildSceneReportsModel>();
		for(Map map : queryForList){
			ChildSceneReportsModel model = new ChildSceneReportsModel();
			if (map.get("areaid") != null && !"".equals(map.get("areaid").toString())) {
				model.setAreaId(map.get("areaid").toString());
			}
			if (map.get("areaname") != null && !"".equals(map.get("areaname").toString())) {
				model.setAreaName(map.get("areaname").toString());
			}
			if (map.get("total_num") != null && !"".equals(map.get("total_num").toString())) {
				model.setTotalNum(map.get("total_num").toString());
			}
			if (map.get("total_project_amount") != null && !"".equals(map.get("total_project_amount").toString())) {
				model.setTotalProjectAmount(map.get("total_project_amount").toString());
			}
			if (map.get("total_work_amout") != null && !"".equals(map.get("total_work_amout").toString())) {
				model.setTotalWorkAmout(map.get("total_work_amout").toString());
			}
			if (map.get("total_data_amout") != null && !"".equals(map.get("total_data_amout").toString())) {
				model.setTotalDataAmout(map.get("total_data_amout").toString());
			}
			if (map.get("work_ratio") != null && !"".equals(map.get("work_ratio").toString())) {
				model.setWorkRatio(map.get("work_ratio").toString());
			}
			if (map.get("data_ratio") != null && !"".equals(map.get("data_ratio").toString())) {
				model.setDataRatio(map.get("data_ratio").toString());
			}
			
			Field field = null;
			for(int i = 0; i < fieldNames.length; i++){
				String fieldName = fieldNames[i];
				String value = map.get(fieldName).toString();
				try {
					field = model.getClass().getDeclaredField(fieldName);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
				field.setAccessible(true);
				try {
					field.set(model,value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			list.add(model);
		}
		return list;
	}
	
}
