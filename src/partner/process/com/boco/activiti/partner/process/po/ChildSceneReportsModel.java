package com.boco.activiti.partner.process.po;

/**
 * 
 	* @author Wangjun
 	* @ClassName: ChildSceneReportsModel
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright 公司名称
 	* @date Jul 21, 2016 11:06:32 AM
 	* @description 子场景工单统计model类
 */
public class ChildSceneReportsModel {
	//地区ID值
	private String areaId;
	//地区名称
	private String areaName;
	//市分公司预检预修项目总数（项）
	private String totalNum;
	//市分公司预检预修项目总金额（元）
	private String totalProjectAmount;
	//工费总额
	private String totalWorkAmout;
	//材料费总额
	private String totalDataAmout;
	//工费占比
	private String workRatio;
	//材料费占比
	private String dataRatio;

	
	//敷设光缆（不含接续）			
		//数量（千米）	
		private String A1;
	    //工费	
		private String A2;
	    //材料费	
		private String A3;
	    //费用占比
		private String A4;
	
	//光缆接头接续					
		//数量（头）	
		private String B1;
		//工费	
		private String B2;
		//材料费	
		private String B3;
		//单位接头工费(元)	
		private String B4;
		//单位接头材料费用（元）
		private String B5;
		//接续费用占比
		private String B6;
		
	//光缆成端接续					
		//数量（芯）	
		private String C1;
		//工费	
		private String C2;
		//材料费	
		private String C3;
		//单位成端工费（元）
		private String C4;
		//单位成端材料费（元）
		private String C5;
		//接续费用占比
		private String C6;
							
	//拆除光缆			
		//数量（千米）	
		private String D1;
		//工费
		private String D2;
		//材料费	
		private String D3;
		//费用占比
		private String D4;
		
	//光缆整理			
		//数量（档）
		private String E1;
		//工费	
		private String E2;
		//材料费
		private String E3;
		//费用占比
		private String E4;

	//光缆断纤、劣化纤处理			
		//数量（头）
		private String F1;
		//工费
		private String F2;
		//材料费	
		private String F3;
		//费用占比
		private String F4;
		
	//光交接箱安装或迁移			
		//数量（个）	
		private String G1;
		//工费	
		private String G2;
		//材料费	
		private String G3;
		//费用占比
		private String G4;

	//光分路器箱（光分纤箱）安装或迁移			
		//数量（个）	
		private String H1;
		//工费	
		private String H2;
		//材料费	
		private String H3;
		//费用占比
		private String H4;
		
	//障碍物清除			
		//数量（次）	
		private String I1;
		//工费
		private String I2;
		//材料费	
		private String I3;
		//费用占比
		private String I4;

	//立电杆				
		//数量（棵）
		private String J1;
		//工费	
		private String J2;
		//材料费
		private String J3;
		//二次搬运费
		private String J4;
		//费用占比
		private String J5;

	//扶正电杆			
		//数量（棵）
		private String K1;
		//工费	
		private String K2;
		//材料费	
		private String K3;
		//费用占比
		private String K4;
	
	//拆电杆			
		//数量（棵）	
		private String L1;
		//工费	
		private String L2;
		//材料费	
		private String L3;
		//费用占比
		private String L4;
					
	//增补拉线			
		//数量（条）
		private String M1;
		//工费
		private String M2;
		//材料费	
		private String M3;
		//费用占比
		private String M4;
					
	//拆除拉线			
		//数量（条）
		private String N1;
		//工费	
		private String N2;
		//材料费	
		private String N3;
		//费用占比
		private String N4;

	//架设吊线			
		//数量（千米）
		private String O1;
		//工费	
		private String O2;
		//材料费	
		private String O3;
		//费用占比
		private String O4;

	//拆除吊线			
		//数量（千米）
		private String P1;
		//工费
		private String P2;
		//材料费	
		private String P3;
		//费用占比
		private String P4;

	//安装吊线保护装置			
		//数量（米）	
		private String Q1;
		//工费	
		private String Q2;
		//材料费	
		private String Q3;
		//费用占比
		private String Q4;
		
	//升高吊线			
		//数量（杆档）
		private String R1;
		//工费	
		private String R2;
		//材料费	
		private String R3;
		//费用占比
		private String R4;

	//增补挂钩			
		//数量（米）
		private String S1;
		//工费	
		private String S2;
		//材料费
		private String S3;
		//费用占比
		private String S4;

	//障碍物清除			
		//数量（次）	
		private String T1;
		//工费
		private String T2;
		//材料费	
		private String T3;
		//费用占比
		private String T4;

	//管道迁改			
		//数量（百米）	
		private String U1;
		//工费
		private String U2;
		//材料费	
		private String U3;
		//费用占比
		private String U4;
		
	//管道疏通及整修			
		//数量（米）
		private String V1;
		//工费	
		private String V2;
		//材料费	
		private String V3;
		//费用占比
		private String V4;

	//管道积水及杂物清理			
		//数量（次）	
		private String W1;
		//工费	
		private String W2;
		//材料费	
		private String W3;
		//费用占比
		private String W4;
		
	//管道人孔升降、人井上覆的更换			
		//数量（次）
		private String X1;
		//工费	
		private String X2;
		//材料费
		private String X3;
		//费用占比
		private String X4;

	//障碍物清除			
		//数量（次）	
		private String Y1;
		//工费	
		private String Y2;
		//材料费	
		private String Y3;
		//费用占比
		private String Y4;

	//线路资源清查			
		//数量（人天）
		private String Z1;
		//工费
		private String Z2;
		//材料费	
		private String Z3;
		//费用占比
		private String Z4;

	//充气设备维修			
		//数量（次）	
		private String AA1;
		//工费
		private String AA2;
		//材料费	
		private String AA3;
		//费用占比
		private String AA4;

	//标识安装			
		//数量（个）	
		private String BB1;
		//工费
		private String BB2;
		//材料费	
		private String BB3;
		//费用占比
		private String BB4;

	//标识粉刷			
		//数量（个）
		private String CC1;
		//工费
		private String CC2;
		//材料费	
		private String CC3;
		//费用占比
		private String CC4;

	//护坡加固			
		//数量	
		private String DD1;
		//工费
		private String DD2;
		//材料费	
		private String DD3;
		//费用占比
		private String DD4;

	//架空交接箱站台维修			
		//数量（个）
		private String EE1;
		//工费
		private String EE2;
		//材料费	
		private String EE3;
		//费用占比
		private String EE4;
	
	//交接分线设备粉刷、编号喷涂			
		//数量（次）	
		private String FF1;
		//工费
		private String FF2;
		//材料费	
		private String FF3;
		//费用占比
		private String FF4;

	//障碍物清除			
		//数量（次）	
		private String GG1;
		//工费
		private String GG2;
		//材料费	
		private String GG3;
		//费用占比
		private String GG4;

	//光缆（含光交）_其他费用（手工填写）			
		//数量（次）	
		private String HH1;
		//工费
		private String HH2;
		//材料费	
		private String HH3;
		//费用占比
		private String HH4;

	//杆路_其他费用（手工填写）			
		//数量（次）	
		private String II1;
		//工费
		private String II2;
		//材料费	
		private String II3;
		//费用占比
		private String II4;

	//管道_其他费用（手工填写）			
		//数量（次）	
		private String JJ1;
		//工费	
		private String JJ2;
		//材料费	
		private String JJ3;
		//费用占比
		private String JJ4;

	//其他项目_其他费用（手工填写）			
		//数量（次）
		private String KK1;
		//工费
		private String KK2;
		//材料费	
		private String KK3;
		//费用占比
		private String KK4;

	//非标准模板项目（次）
	private String LL1;
	//非标准模板费用占比
	private String MM1;
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getTotalProjectAmount() {
		return totalProjectAmount;
	}
	public void setTotalProjectAmount(String totalProjectAmount) {
		this.totalProjectAmount = totalProjectAmount;
	}
	public String getTotalWorkAmout() {
		return totalWorkAmout;
	}
	public void setTotalWorkAmout(String totalWorkAmout) {
		this.totalWorkAmout = totalWorkAmout;
	}
	public String getTotalDataAmout() {
		return totalDataAmout;
	}
	public void setTotalDataAmout(String totalDataAmout) {
		this.totalDataAmout = totalDataAmout;
	}
	public String getWorkRatio() {
		return workRatio;
	}
	public void setWorkRatio(String workRatio) {
		this.workRatio = workRatio;
	}
	public String getDataRatio() {
		return dataRatio;
	}
	public void setDataRatio(String dataRatio) {
		this.dataRatio = dataRatio;
	}
	public String getA1() {
		return A1;
	}
	public void setA1(String a1) {
		A1 = a1;
	}
	public String getA2() {
		return A2;
	}
	public void setA2(String a2) {
		A2 = a2;
	}
	public String getA3() {
		return A3;
	}
	public void setA3(String a3) {
		A3 = a3;
	}
	public String getA4() {
		return A4;
	}
	public void setA4(String a4) {
		A4 = a4;
	}
	public String getB1() {
		return B1;
	}
	public void setB1(String b1) {
		B1 = b1;
	}
	public String getB2() {
		return B2;
	}
	public void setB2(String b2) {
		B2 = b2;
	}
	public String getB3() {
		return B3;
	}
	public void setB3(String b3) {
		B3 = b3;
	}
	public String getB4() {
		return B4;
	}
	public void setB4(String b4) {
		B4 = b4;
	}
	public String getB5() {
		return B5;
	}
	public void setB5(String b5) {
		B5 = b5;
	}
	public String getB6() {
		return B6;
	}
	public void setB6(String b6) {
		B6 = b6;
	}
	public String getC1() {
		return C1;
	}
	public void setC1(String c1) {
		C1 = c1;
	}
	public String getC2() {
		return C2;
	}
	public void setC2(String c2) {
		C2 = c2;
	}
	public String getC3() {
		return C3;
	}
	public void setC3(String c3) {
		C3 = c3;
	}
	public String getC4() {
		return C4;
	}
	public void setC4(String c4) {
		C4 = c4;
	}
	public String getC5() {
		return C5;
	}
	public void setC5(String c5) {
		C5 = c5;
	}
	public String getC6() {
		return C6;
	}
	public void setC6(String c6) {
		C6 = c6;
	}
	public String getD1() {
		return D1;
	}
	public void setD1(String d1) {
		D1 = d1;
	}
	public String getD2() {
		return D2;
	}
	public void setD2(String d2) {
		D2 = d2;
	}
	public String getD3() {
		return D3;
	}
	public void setD3(String d3) {
		D3 = d3;
	}
	public String getD4() {
		return D4;
	}
	public void setD4(String d4) {
		D4 = d4;
	}
	public String getE1() {
		return E1;
	}
	public void setE1(String e1) {
		E1 = e1;
	}
	public String getE2() {
		return E2;
	}
	public void setE2(String e2) {
		E2 = e2;
	}
	public String getE3() {
		return E3;
	}
	public void setE3(String e3) {
		E3 = e3;
	}
	public String getE4() {
		return E4;
	}
	public void setE4(String e4) {
		E4 = e4;
	}
	public String getF1() {
		return F1;
	}
	public void setF1(String f1) {
		F1 = f1;
	}
	public String getF2() {
		return F2;
	}
	public void setF2(String f2) {
		F2 = f2;
	}
	public String getF3() {
		return F3;
	}
	public void setF3(String f3) {
		F3 = f3;
	}
	public String getF4() {
		return F4;
	}
	public void setF4(String f4) {
		F4 = f4;
	}
	public String getG1() {
		return G1;
	}
	public void setG1(String g1) {
		G1 = g1;
	}
	public String getG2() {
		return G2;
	}
	public void setG2(String g2) {
		G2 = g2;
	}
	public String getG3() {
		return G3;
	}
	public void setG3(String g3) {
		G3 = g3;
	}
	public String getG4() {
		return G4;
	}
	public void setG4(String g4) {
		G4 = g4;
	}
	public String getH1() {
		return H1;
	}
	public void setH1(String h1) {
		H1 = h1;
	}
	public String getH2() {
		return H2;
	}
	public void setH2(String h2) {
		H2 = h2;
	}
	public String getH3() {
		return H3;
	}
	public void setH3(String h3) {
		H3 = h3;
	}
	public String getH4() {
		return H4;
	}
	public void setH4(String h4) {
		H4 = h4;
	}
	public String getI1() {
		return I1;
	}
	public void setI1(String i1) {
		I1 = i1;
	}
	public String getI2() {
		return I2;
	}
	public void setI2(String i2) {
		I2 = i2;
	}
	public String getI3() {
		return I3;
	}
	public void setI3(String i3) {
		I3 = i3;
	}
	public String getI4() {
		return I4;
	}
	public void setI4(String i4) {
		I4 = i4;
	}
	public String getJ1() {
		return J1;
	}
	public void setJ1(String j1) {
		J1 = j1;
	}
	public String getJ2() {
		return J2;
	}
	public void setJ2(String j2) {
		J2 = j2;
	}
	public String getJ3() {
		return J3;
	}
	public void setJ3(String j3) {
		J3 = j3;
	}
	public String getJ4() {
		return J4;
	}
	public void setJ4(String j4) {
		J4 = j4;
	}
	public String getJ5() {
		return J5;
	}
	public void setJ5(String j5) {
		J5 = j5;
	}
	public String getK1() {
		return K1;
	}
	public void setK1(String k1) {
		K1 = k1;
	}
	public String getK2() {
		return K2;
	}
	public void setK2(String k2) {
		K2 = k2;
	}
	public String getK3() {
		return K3;
	}
	public void setK3(String k3) {
		K3 = k3;
	}
	public String getK4() {
		return K4;
	}
	public void setK4(String k4) {
		K4 = k4;
	}
	public String getL1() {
		return L1;
	}
	public void setL1(String l1) {
		L1 = l1;
	}
	public String getL2() {
		return L2;
	}
	public void setL2(String l2) {
		L2 = l2;
	}
	public String getL3() {
		return L3;
	}
	public void setL3(String l3) {
		L3 = l3;
	}
	public String getL4() {
		return L4;
	}
	public void setL4(String l4) {
		L4 = l4;
	}
	public String getM1() {
		return M1;
	}
	public void setM1(String m1) {
		M1 = m1;
	}
	public String getM2() {
		return M2;
	}
	public void setM2(String m2) {
		M2 = m2;
	}
	public String getM3() {
		return M3;
	}
	public void setM3(String m3) {
		M3 = m3;
	}
	public String getM4() {
		return M4;
	}
	public void setM4(String m4) {
		M4 = m4;
	}
	public String getN1() {
		return N1;
	}
	public void setN1(String n1) {
		N1 = n1;
	}
	public String getN2() {
		return N2;
	}
	public void setN2(String n2) {
		N2 = n2;
	}
	public String getN3() {
		return N3;
	}
	public void setN3(String n3) {
		N3 = n3;
	}
	public String getN4() {
		return N4;
	}
	public void setN4(String n4) {
		N4 = n4;
	}
	public String getO1() {
		return O1;
	}
	public void setO1(String o1) {
		O1 = o1;
	}
	public String getO2() {
		return O2;
	}
	public void setO2(String o2) {
		O2 = o2;
	}
	public String getO3() {
		return O3;
	}
	public void setO3(String o3) {
		O3 = o3;
	}
	public String getO4() {
		return O4;
	}
	public void setO4(String o4) {
		O4 = o4;
	}
	public String getP1() {
		return P1;
	}
	public void setP1(String p1) {
		P1 = p1;
	}
	public String getP2() {
		return P2;
	}
	public void setP2(String p2) {
		P2 = p2;
	}
	public String getP3() {
		return P3;
	}
	public void setP3(String p3) {
		P3 = p3;
	}
	public String getP4() {
		return P4;
	}
	public void setP4(String p4) {
		P4 = p4;
	}
	public String getQ1() {
		return Q1;
	}
	public void setQ1(String q1) {
		Q1 = q1;
	}
	public String getQ2() {
		return Q2;
	}
	public void setQ2(String q2) {
		Q2 = q2;
	}
	public String getQ3() {
		return Q3;
	}
	public void setQ3(String q3) {
		Q3 = q3;
	}
	public String getQ4() {
		return Q4;
	}
	public void setQ4(String q4) {
		Q4 = q4;
	}
	public String getR1() {
		return R1;
	}
	public void setR1(String r1) {
		R1 = r1;
	}
	public String getR2() {
		return R2;
	}
	public void setR2(String r2) {
		R2 = r2;
	}
	public String getR3() {
		return R3;
	}
	public void setR3(String r3) {
		R3 = r3;
	}
	public String getR4() {
		return R4;
	}
	public void setR4(String r4) {
		R4 = r4;
	}
	public String getS1() {
		return S1;
	}
	public void setS1(String s1) {
		S1 = s1;
	}
	public String getS2() {
		return S2;
	}
	public void setS2(String s2) {
		S2 = s2;
	}
	public String getS3() {
		return S3;
	}
	public void setS3(String s3) {
		S3 = s3;
	}
	public String getS4() {
		return S4;
	}
	public void setS4(String s4) {
		S4 = s4;
	}
	public String getT1() {
		return T1;
	}
	public void setT1(String t1) {
		T1 = t1;
	}
	public String getT2() {
		return T2;
	}
	public void setT2(String t2) {
		T2 = t2;
	}
	public String getT3() {
		return T3;
	}
	public void setT3(String t3) {
		T3 = t3;
	}
	public String getT4() {
		return T4;
	}
	public void setT4(String t4) {
		T4 = t4;
	}
	public String getU1() {
		return U1;
	}
	public void setU1(String u1) {
		U1 = u1;
	}
	public String getU2() {
		return U2;
	}
	public void setU2(String u2) {
		U2 = u2;
	}
	public String getU3() {
		return U3;
	}
	public void setU3(String u3) {
		U3 = u3;
	}
	public String getU4() {
		return U4;
	}
	public void setU4(String u4) {
		U4 = u4;
	}
	public String getV1() {
		return V1;
	}
	public void setV1(String v1) {
		V1 = v1;
	}
	public String getV2() {
		return V2;
	}
	public void setV2(String v2) {
		V2 = v2;
	}
	public String getV3() {
		return V3;
	}
	public void setV3(String v3) {
		V3 = v3;
	}
	public String getV4() {
		return V4;
	}
	public void setV4(String v4) {
		V4 = v4;
	}
	public String getW1() {
		return W1;
	}
	public void setW1(String w1) {
		W1 = w1;
	}
	public String getW2() {
		return W2;
	}
	public void setW2(String w2) {
		W2 = w2;
	}
	public String getW3() {
		return W3;
	}
	public void setW3(String w3) {
		W3 = w3;
	}
	public String getW4() {
		return W4;
	}
	public void setW4(String w4) {
		W4 = w4;
	}
	public String getX1() {
		return X1;
	}
	public void setX1(String x1) {
		X1 = x1;
	}
	public String getX2() {
		return X2;
	}
	public void setX2(String x2) {
		X2 = x2;
	}
	public String getX3() {
		return X3;
	}
	public void setX3(String x3) {
		X3 = x3;
	}
	public String getX4() {
		return X4;
	}
	public void setX4(String x4) {
		X4 = x4;
	}
	public String getY1() {
		return Y1;
	}
	public void setY1(String y1) {
		Y1 = y1;
	}
	public String getY2() {
		return Y2;
	}
	public void setY2(String y2) {
		Y2 = y2;
	}
	public String getY3() {
		return Y3;
	}
	public void setY3(String y3) {
		Y3 = y3;
	}
	public String getY4() {
		return Y4;
	}
	public void setY4(String y4) {
		Y4 = y4;
	}
	public String getZ1() {
		return Z1;
	}
	public void setZ1(String z1) {
		Z1 = z1;
	}
	public String getZ2() {
		return Z2;
	}
	public void setZ2(String z2) {
		Z2 = z2;
	}
	public String getZ3() {
		return Z3;
	}
	public void setZ3(String z3) {
		Z3 = z3;
	}
	public String getZ4() {
		return Z4;
	}
	public void setZ4(String z4) {
		Z4 = z4;
	}
	public String getAA1() {
		return AA1;
	}
	public void setAA1(String aa1) {
		AA1 = aa1;
	}
	public String getAA2() {
		return AA2;
	}
	public void setAA2(String aa2) {
		AA2 = aa2;
	}
	public String getAA3() {
		return AA3;
	}
	public void setAA3(String aa3) {
		AA3 = aa3;
	}
	public String getAA4() {
		return AA4;
	}
	public void setAA4(String aa4) {
		AA4 = aa4;
	}
	public String getBB1() {
		return BB1;
	}
	public void setBB1(String bb1) {
		BB1 = bb1;
	}
	public String getBB2() {
		return BB2;
	}
	public void setBB2(String bb2) {
		BB2 = bb2;
	}
	public String getBB3() {
		return BB3;
	}
	public void setBB3(String bb3) {
		BB3 = bb3;
	}
	public String getBB4() {
		return BB4;
	}
	public void setBB4(String bb4) {
		BB4 = bb4;
	}
	public String getCC1() {
		return CC1;
	}
	public void setCC1(String cc1) {
		CC1 = cc1;
	}
	public String getCC2() {
		return CC2;
	}
	public void setCC2(String cc2) {
		CC2 = cc2;
	}
	public String getCC3() {
		return CC3;
	}
	public void setCC3(String cc3) {
		CC3 = cc3;
	}
	public String getCC4() {
		return CC4;
	}
	public void setCC4(String cc4) {
		CC4 = cc4;
	}
	public String getDD1() {
		return DD1;
	}
	public void setDD1(String dd1) {
		DD1 = dd1;
	}
	public String getDD2() {
		return DD2;
	}
	public void setDD2(String dd2) {
		DD2 = dd2;
	}
	public String getDD3() {
		return DD3;
	}
	public void setDD3(String dd3) {
		DD3 = dd3;
	}
	public String getDD4() {
		return DD4;
	}
	public void setDD4(String dd4) {
		DD4 = dd4;
	}
	public String getEE1() {
		return EE1;
	}
	public void setEE1(String ee1) {
		EE1 = ee1;
	}
	public String getEE2() {
		return EE2;
	}
	public void setEE2(String ee2) {
		EE2 = ee2;
	}
	public String getEE3() {
		return EE3;
	}
	public void setEE3(String ee3) {
		EE3 = ee3;
	}
	public String getEE4() {
		return EE4;
	}
	public void setEE4(String ee4) {
		EE4 = ee4;
	}
	public String getFF1() {
		return FF1;
	}
	public void setFF1(String ff1) {
		FF1 = ff1;
	}
	public String getFF2() {
		return FF2;
	}
	public void setFF2(String ff2) {
		FF2 = ff2;
	}
	public String getFF3() {
		return FF3;
	}
	public void setFF3(String ff3) {
		FF3 = ff3;
	}
	public String getFF4() {
		return FF4;
	}
	public void setFF4(String ff4) {
		FF4 = ff4;
	}
	public String getGG1() {
		return GG1;
	}
	public void setGG1(String gg1) {
		GG1 = gg1;
	}
	public String getGG2() {
		return GG2;
	}
	public void setGG2(String gg2) {
		GG2 = gg2;
	}
	public String getGG3() {
		return GG3;
	}
	public void setGG3(String gg3) {
		GG3 = gg3;
	}
	public String getGG4() {
		return GG4;
	}
	public void setGG4(String gg4) {
		GG4 = gg4;
	}
	public String getHH1() {
		return HH1;
	}
	public void setHH1(String hh1) {
		HH1 = hh1;
	}
	public String getHH2() {
		return HH2;
	}
	public void setHH2(String hh2) {
		HH2 = hh2;
	}
	public String getHH3() {
		return HH3;
	}
	public void setHH3(String hh3) {
		HH3 = hh3;
	}
	public String getHH4() {
		return HH4;
	}
	public void setHH4(String hh4) {
		HH4 = hh4;
	}
	public String getII1() {
		return II1;
	}
	public void setII1(String ii1) {
		II1 = ii1;
	}
	public String getII2() {
		return II2;
	}
	public void setII2(String ii2) {
		II2 = ii2;
	}
	public String getII3() {
		return II3;
	}
	public void setII3(String ii3) {
		II3 = ii3;
	}
	public String getII4() {
		return II4;
	}
	public void setII4(String ii4) {
		II4 = ii4;
	}
	public String getJJ1() {
		return JJ1;
	}
	public void setJJ1(String jj1) {
		JJ1 = jj1;
	}
	public String getJJ2() {
		return JJ2;
	}
	public void setJJ2(String jj2) {
		JJ2 = jj2;
	}
	public String getJJ3() {
		return JJ3;
	}
	public void setJJ3(String jj3) {
		JJ3 = jj3;
	}
	public String getJJ4() {
		return JJ4;
	}
	public void setJJ4(String jj4) {
		JJ4 = jj4;
	}
	public String getKK1() {
		return KK1;
	}
	public void setKK1(String kk1) {
		KK1 = kk1;
	}
	public String getKK2() {
		return KK2;
	}
	public void setKK2(String kk2) {
		KK2 = kk2;
	}
	public String getKK3() {
		return KK3;
	}
	public void setKK3(String kk3) {
		KK3 = kk3;
	}
	public String getKK4() {
		return KK4;
	}
	public void setKK4(String kk4) {
		KK4 = kk4;
	}
	public String getLL1() {
		return LL1;
	}
	public void setLL1(String ll1) {
		LL1 = ll1;
	}
	public String getMM1() {
		return MM1;
	}
	public void setMM1(String mm1) {
		MM1 = mm1;
	}
}
