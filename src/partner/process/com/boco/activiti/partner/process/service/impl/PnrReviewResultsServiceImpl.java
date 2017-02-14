package com.boco.activiti.partner.process.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import com.boco.activiti.partner.process.dao.IPnrReviewResultsDao;
import com.boco.activiti.partner.process.dao.IPnrReviewResultsJDBCDao;
import com.boco.activiti.partner.process.model.PnrReviewResults;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.service.IPnrReviewResultsService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectLinkService;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectMappingService;
import com.boco.eoms.partner.netresource.service.BsBtQuipmentMgr;
import com.boco.eoms.partner.netresource.util.DataSaveCallback;
import com.boco.eoms.partner.netresource.util.ExcelImport;
import com.boco.eoms.partner.netresource.util.ImportResult;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PnrReviewResultsServiceImpl extends
		CommonGenericServiceImpl<PnrReviewResults> implements
		IPnrReviewResultsService {

	private IPnrReviewResultsDao pnrReviewResultsDao;
	private IPnrReviewResultsJDBCDao pnrReviewResultsJDBCDao;

	// 工单号集合
	public List<String> processInstanceIdList;
	// 错误数
	private int errorCount = 0;
	// 错误信息
	private StringBuilder errMsg;
	// 工单主表
	private IPnrTransferOfficeService pnrTransferOfficeService;
	// 行数
	private int rowNum = 1;
	// 存放错误的数组
	private String[] errorDatas;

	public IPnrReviewResultsDao getPnrReviewResultsDao() {
		return pnrReviewResultsDao;
	}

	public void setPnrReviewResultsDao(IPnrReviewResultsDao pnrReviewResultsDao) {
		this.pnrReviewResultsDao = pnrReviewResultsDao;
		this.setCommonGenericDao(pnrReviewResultsDao);
	}

	public IPnrReviewResultsJDBCDao getPnrReviewResultsJDBCDao() {
		return pnrReviewResultsJDBCDao;
	}

	public void setPnrReviewResultsJDBCDao(
			IPnrReviewResultsJDBCDao pnrReviewResultsJDBCDao) {
		this.pnrReviewResultsJDBCDao = pnrReviewResultsJDBCDao;
	}

	/**
	 * 回填环节 枚举值转换
	 * 
	 * @param value
	 *            回填环节值
	 * @param flag
	 *            值为null或者1为中文转换成英文；值为2为英文转换成中文
	 * @return
	 */
	public String backfillLinkToKey(String value, String flag) {
		String result = "不存在";
		if (value != null && !"".equals(value)) {
			if (!"2".equals(flag)) {
				if ("cityManageExamineAgency".equals(value)) {
					result = "市运维主管待办";
				} else if ("cityManageExamineToReply".equals(value)) {
					result = "市运维主管待回复";
				} else if ("provinceLineExamineAgency".equals(value)) {
					result = "省线路主管待办";
				} else if ("provinceLineExamineToReply".equals(value)) {
					result = "省线路主管待回复";
				} else if ("waitingCallInterface".equals(value)) {
					result = "等待接口调用";
				}

			} else if (flag == null || "1".equals(flag)) {
				if ("市运维主管待办".equals(value)) {
					result = "cityManageExamineAgency";
				} else if ("市运维主管待回复".equals(value)) {
					result = "cityManageExamineToReply";
				} else if ("省线路主管待办".equals(value)) {
					result = "provinceLineExamineAgency";
				} else if ("省线路主管待回复".equals(value)) {
					result = "provinceLineExamineToReply";
				} else if ("等待接口调用".equals(value)) {
					result = "waitingCallInterface";
				}

			}
		}

		return result;
	}

	/**
	 * 导入会审结果
	 * 
	 * @param formFile
	 * @param creatorId
	 * @param osPath
	 * @return
	 * @throws Exception
	 */
	public ImportResult importReviewResultsFromFile(FormFile formFile,
			String creatorId, String osPath) throws Exception {

		pnrTransferOfficeService = (IPnrTransferOfficeService) ApplicationContextHolder
				.getInstance().getBean("pnrTransferOfficeService");

		// 查询主表
		String processInstanceId = "";
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		this.search(search);

		// 获取会审结果serve
		final List<Object> mainList = new ArrayList<Object>();
		final List<Object> errorList = new ArrayList<Object>();
		processInstanceIdList = new ArrayList<String>();
		final ExcelImport ei = new ExcelImport(2, 1, 7);
//		final ExcelImport ei = new ExcelImport(2, 1, 6);
		processInstanceIdList = ei.getExcelNameList(formFile, 1, 1);

		ImportResult result = ei.importFromFile(formFile, 1,
				new DataSaveCallback() {
					public void doSaveRow2Data(HSSFRow row) throws Exception {
						List<Object> mapList = this.fromRow2Object(row);
						if (mapList.size() == 1) {
							mainList.add(mapList.get(0));
						} else {
							errorCount++;
						}
					}

					TimeZone tz = TimeZone.getTimeZone("GMT");
					SimpleDateFormat dateformat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");

					// 将每行转化成SdBsBtResource对象
					@SuppressWarnings( { "unchecked", "static-access" })
					@Override
					public List<Object> fromRow2Object(HSSFRow row)
							throws Exception {
						List<Object> mainList = new ArrayList<Object>();

						// 会审结果
						PnrReviewResults pnrReviewResults = null;

						// 错误信息
						errorDatas = new String[8];
//						errorDatas = new String[7];
						errMsg = new StringBuilder();
						int colNum = 0;
						try {
							pnrReviewResults = new PnrReviewResults();

							// 主键
							String id = UUIDHexGenerator.getInstance().getID();
							pnrReviewResults.setId(id);
							colNum++;

							// 1. 工单号
							String processInstanceId;
							processInstanceId = StaticMethod
									.nullObject2String(row.getCell(1));
							if ("".equals(processInstanceId)) {
								errMsg.append("工单号不能为空;");
								//flag = 1;
							} else if (ei.isHaveSameName(processInstanceId,
									processInstanceIdList)) {
								errMsg.append("该工单号在Excel表中重复;");
								//flag = 3;
							} else {
								// 工单流程类型
								Search search = new Search();
								search.addFilterEqual("processInstanceId",
										processInstanceId);
								SearchResult t = pnrTransferOfficeService
										.searchAndCount(search);
								List<PnrTransferOffice> list = t.getResult();
								if (list != null&&list.size()>0) {
									PnrTransferOffice pnrTransferOffice = list
											.get(0);
									pnrReviewResults
											.setThemeInterface(pnrTransferOffice
													.getThemeInterface());
								} else {
									errMsg.append(processInstanceId
											+ "在系统中不存在;");
								}
							}
							pnrReviewResults
									.setProcessInstanceId(processInstanceId);
							colNum++;

							// 2. 回填环节
							String backfillLink = "";
							String backfillLinkStr = "";
							backfillLink = StaticMethod.nullObject2String(row
									.getCell(2));
							if ("".equals(backfillLink)) {
								errMsg.append("回填环节不能为空;");
								//flag = 1;
							} else {
								if ("市运维主管待办".equals(backfillLink)) {
									backfillLinkStr = "cityManageExamineAgency";
								} else if ("市运维主管待回复".equals(backfillLink)) {
									backfillLinkStr = "cityManageExamineToReply";
								} else if ("省线路主管待办".equals(backfillLink)) {
									backfillLinkStr = "provinceLineExamineAgency";
								} else if ("省线路主管待回复".equals(backfillLink)) {
									backfillLinkStr = "provinceLineExamineToReply";
								} else if ("等待接口调用".equals(backfillLink)) {
									backfillLinkStr = "waitingCallInterface";
								} else {
									errMsg.append("回填环节为非法值;");
								}
							}
							pnrReviewResults.setBackfillLink(backfillLinkStr);
							colNum++;

							// 3. 会审结果
							String reviewResult = "";
							String reviewResultStr = "";
							reviewResult = StaticMethod.nullObject2String(row
									.getCell(3));
							if ("".equals(reviewResult)) {
								errMsg.append("会审结果不能为空;");
								//flag = 1;
							} else {
								if ("不合格".equals(reviewResult)) {
									reviewResultStr = "NO";
								} else if ("合格".equals(reviewResult)) {
									reviewResultStr = "YES";
								} else {
									errMsg.append("会审结果为非法值;");
								}
							}
							pnrReviewResults.setReviewResult(reviewResultStr);
							colNum++;

							// 4. 专家意见
							String expertOpinion = "";
							expertOpinion = StaticMethod.nullObject2String(row
									.getCell(4));
							if ("".equals(expertOpinion)) {
								errMsg.append("专家意见不能为空;");
								//flag = 1;
							}
							pnrReviewResults.setExpertOpinion(expertOpinion);
							colNum++;

							// 5. 会审时间
							Date reviewTime = null;
							String reviewTime2 = StaticMethod
									.nullObject2String(row.getCell(5));
							if ("".equals(reviewTime2)) {
								errMsg.append("会审时间不能为空;");
							} else {
								// String dateSubtract =
								// StaticMethod.dateSubtract(reviewTime2);
								// eviewTime = dateformat.parse(dateSubtract);
								reviewTime = dateformat.parse(reviewTime2);
							}
							pnrReviewResults.setReviewTime(reviewTime);
							colNum++;
							
							// 6. 是否同意实施
							String isAgreeStriking = "";
							String isAgreeStrikingStr = "";
							isAgreeStriking = StaticMethod.nullObject2String(row
									.getCell(6));
							if ("".equals(isAgreeStriking)) {
								errMsg.append("是否同意实施不能为空;");
								//flag = 1;
							} else {
								if ("不同意".equals(isAgreeStriking)) {
									isAgreeStrikingStr = "NO";
								} else if ("同意".equals(isAgreeStriking)) {
									isAgreeStrikingStr = "YES";
								} else {
									errMsg.append("是否同意实施为非法值;");
								}
							}
							pnrReviewResults.setIsAgreeStriking(isAgreeStrikingStr);
							colNum++;

							// 保存错误信息的集合
							errorDatas[0] = Integer.toString(rowNum);// 序号
							errorDatas[1] = processInstanceId;
							errorDatas[2] = backfillLink;
							errorDatas[3] = reviewResult;
							errorDatas[4] = expertOpinion;
							errorDatas[5] = reviewTime2;
							errorDatas[6] = isAgreeStriking;

							// 区分错误与正确的列表
							if (errMsg.length() == 0) {
								mainList.add(pnrReviewResults);
								pnrReviewResults = null;
							} else {
								errorDatas[7] = errMsg.toString();
								errorList.add(errorDatas);
								//flag = 0;
								pnrReviewResults = null;
							}

						} catch (Exception e) {
							e.printStackTrace();
							colNum = colNum + 1;
							throw new Exception(",错误列数是:" + colNum
									+ e.getMessage());
						}
						rowNum++;
						return mainList;
					}
				});
		rowNum = 1;// 初始化
		// 在这里保存数据
		try {
			String fileName = StaticMethod.getCurrentDateTime("yyyyMMddHHmmss")
					+ StaticMethod.getRandomCharAndNumr(4);
			Date importTime = new Date();
			for (int k = 0; k < mainList.size(); k++) {
				PnrReviewResults pnrReviewResults = null;
				PnrReviewResults sr = (PnrReviewResults) mainList.get(k);
				pnrReviewResults = new PnrReviewResults();
				// 主键
				pnrReviewResults.setId(UUIDHexGenerator.getInstance().getID());
				// 工单号
				pnrReviewResults
						.setProcessInstanceId(sr.getProcessInstanceId());
				// 回单环节
				pnrReviewResults.setBackfillLink(sr.getBackfillLink());
				// 会审结果
				pnrReviewResults.setReviewResult(sr.getReviewResult());
				// 专家意见
				pnrReviewResults.setExpertOpinion(sr.getExpertOpinion());
				// 会审时间
				pnrReviewResults.setReviewTime(sr.getReviewTime());
				// 工单流程类型
				pnrReviewResults.setThemeInterface(sr.getThemeInterface());
				// 处理标志 0为处理中；1为处理完成
				pnrReviewResults.setHandleMark("0");
				// 导入人
				pnrReviewResults.setImportUser(creatorId);
				// 唯一标识
				pnrReviewResults.setUniqueMark(fileName);
				// 导入时间
				pnrReviewResults.setImportTime(importTime);
				//是否同意实施
				pnrReviewResults.setIsAgreeStriking(sr.getIsAgreeStriking());
				
				this.save(pnrReviewResults);
			}

			// 处理错误信息
			int errorSize = errorList.size();
			if (errorSize > 0) {
				// 开始将错误的信息导入到excel中
				String outfilepath = "huishenjieguo" + creatorId + ".xls";
				outfilepath = ExcelImport.outfilePath(osPath, outfilepath);

				int sheet = 1, rowNum = 3, totalColumns = 8;
				String[] names0 = { "序号", "工单号*", "回填环节*", "会审结果（合格/不合格）*",
						"专家意见*", "会审时间*","是否同意实施（同意/不同意）*","错误信息" };
				String[] names1 = { "填报说明：\n1.带*号为必填内容；\n2.所有内容都必须为文本格式",
						"按实际填写：\n注意必须为文本格式", "按实际填写:\n注意必须为文本格式",
						"按实际填写：\n注意必须为文本格式", "按实际填写：\n注意必须为文本格式",
						"必须为文本且按如下格式：\nyyyy-mm-dd hh:mm:ss","按实际填写:\n注意必须为文本格式", "" };
				Map<Integer, String[]> headers = new HashMap<Integer, String[]>();
				headers.put(new Integer("0"), names0);
				headers.put(new Integer("1"), names1);
				String sheetName = "会审结果导入";
				// ei.errorObjtoExcel(outfilepath, errorList, sheet, names,
				// sheetName, rowNum);
				ei.errorObjtoExcelNew(outfilepath, errorList, sheet, headers,
						sheetName, rowNum, totalColumns);

			}
			result.setErrorCount(errorCount);// 错误的个数
			errorCount = 0;// /重置
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode("503");
			String msg = result.getRestultMsg();
			if (!msg.equals("")) {
				msg += ",";
			}
			result.setRestultMsg(e.getMessage());
			throw new RuntimeException(result.getRestultMsg());
		}
	}
	
	/**
	 * 根据条件查询会审结果列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getReviewResultsList(final Integer curPage, final Integer pageSize,final String whereStr,final String importStartTime,final String importEndTime){
		return pnrReviewResultsDao.getReviewResultsList(curPage, pageSize, whereStr, importStartTime,importEndTime);
	}
	
	/**
	 * 删除会审信息
	 * @param whereStr
	 */
	public void deletePnrReviewResults(String whereStr){
		pnrReviewResultsJDBCDao.deletePnrReviewResults(whereStr);
	}

}
