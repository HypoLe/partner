<%@ page language="java" pageEncoding="utf-8"%>
<div id="materialQuantityDiv" ${statisticsFlag== "yes"? 'style="display:block;"':'style="display:none;"'}>
	<table class="formTable" style="100%">
		<tr>
			<td class="label">材料金额</td>			
			<td colspan="8"><span id="totalAmountSpan">${maleSceneStatisticsModel.totalAmount}</span>元</td>	
			<td colspan="2"><input type="button" value="导出" class="btn" id="exportMaterialQuantity" name="exportMaterialQuantity" /></td>	
		</tr>
		<tr>
			<td class="label">材料类型</td>
			<td class="label">光缆接头盒_双端 12芯 4孔 架空</td>
			<td class="label">光缆接头盒_双端 24芯 4孔 架空</td>
			<td class="label">光缆接头盒_双端 48芯 4孔 架空</td>
			<td class="label">光缆接头盒_双端 96芯 4孔 架空</td>
			<td class="label">光缆接头盒_双端 144芯 4孔 架空</td>
			<td class="label">光缆_GYTA-4B1.3</td>
			<td class="label">光缆_GYTA-6B1.3</td>
			<td class="label">光缆_GYTA-8B1.3</td>
			<td class="label">光缆_GYTA-12B1.3</td>
			<td class="label">光缆_GYTA-24B1.3</td>
			
		</tr>
		<tr>
			<td>数量</td>
			<td><span id="PRSOCR">${materialQuantityModel.PRSOCR}</span></td><!-- 光缆接头盒_双端 12芯 4孔 架空 -->
			<td><span id="QZHQFL">${materialQuantityModel.QZHQFL}</span></td><!-- 光缆接头盒_双端 24芯 4孔 架空 -->
			<td><span id="SJPFQY">${materialQuantityModel.SJPFQY}</span></td><!-- 光缆接头盒_双端 48芯 4孔 架空 -->
			<td><span id="SGWVOU">${materialQuantityModel.SGWVOU}</span></td><!-- 光缆接头盒_双端 96芯 4孔 架空 -->
			<td><span id="OUBYVD">${materialQuantityModel.OUBYVD}</span></td><!-- 光缆接头盒_双端 144芯 4孔 架空 -->
			<td><span id="TJHREL">${materialQuantityModel.TJHREL}</span></td><!-- 光缆_GYTA-4B1.3 -->
			<td><span id="XOFSKA">${materialQuantityModel.XOFSKA}</span></td><!-- 光缆_GYTA-6B1.3 -->
			<td><span id="ZQIKEA">${materialQuantityModel.ZQIKEA}</span></td><!-- 光缆_GYTA-8B1.3 -->
			<td><span id="CKBRQI">${materialQuantityModel.CKBRQI}</span></td><!-- 光缆_GYTA-12B1.3 -->
			<td><span id="OJRJES">${materialQuantityModel.OJRJES}</span></td><!-- 光缆_GYTA-24B1.3 -->
		</tr>
		
		<tr>
			<td class="label">材料类型</td>
			<td class="label">光缆_GYTA-36B1.3</td>
			<td class="label">光缆_GYTA-48B1.3</td>
			<td class="label">光缆_GYTA-72B1.3</td>
			<td class="label">光缆_GYTA-96B1.3</td>
			<td class="label">光缆_GYTA-144B1.3</td>
			<td class="label">光缆_GYDTA-144B1.3</td>
			<td class="label">光缆预留架_4*40*1000</td>
			<td class="label">二线担</td>
			<td class="label">担夹</td>
			<td class="label">U型穿钉</td>
			
		</tr>
		<tr>
			<td>数量</td>
			<td><span id="EELTNY">${materialQuantityModel.EELTNY}</span></td><!-- 光缆_GYTA-36B1.3 -->
			<td><span id="UJIPIJ">${materialQuantityModel.UJIPIJ}</span></td><!-- 光缆_GYTA-48B1.3 -->
			<td><span id="WRAYQU">${materialQuantityModel.WRAYQU}</span></td><!-- 光缆_GYTA-72B1.3 -->
			<td><span id="ZUQXME">${materialQuantityModel.ZUQXME}</span></td><!-- 光缆_GYTA-96B1.3 -->
			<td><span id="DDDTIA">${materialQuantityModel.DDDTIA}</span></td><!-- 光缆_GYTA-144B1.3 -->
			<td><span id="VXNTLR">${materialQuantityModel.VXNTLR}</span></td><!-- 光缆_GYDTA-144B1.3 -->
			<td><span id="KUARAM">${materialQuantityModel.KUARAM}</span></td><!-- 光缆预留架_4*40*1000 -->
			<td><span id="KZBBPH">${materialQuantityModel.KZBBPH}</span></td><!-- 二线担 -->
			<td><span id="YQHWJT">${materialQuantityModel.YQHWJT}</span></td><!-- 担夹 -->
			<td><span id="YQHWJT">${materialQuantityModel.CODHHR}</span></td><!-- U型穿钉 -->
			
		</tr>
		
		<tr>
			<td class="label">材料类型</td>
			<td class="label">塑料保护管</td>
			<td class="label">8米水泥杆</td>
			<td class="label">10米水泥杆</td>
			<td class="label">12米水泥杆</td>
			<td class="label">15米水泥杆</td>
			<td class="label">8米木杆</td>
			<td class="label">10米木杆</td>
			<td class="label">电缆挂钩 35#</td>
			<td class="label">电力线保护管</td>
			<td class="label">地线保护管</td>
			
		</tr>
		<tr>
			<td>数量</td>
			<td><span id="XHHOMV">${materialQuantityModel.XHHOMV}</span></td><!-- 塑料保护管 -->
			<td><span id="BHMAQR">${materialQuantityModel.BHMAQR}</span></td><!-- 8米水泥杆 -->
			<td><span id="HKTSZC">${materialQuantityModel.HKTSZC}</span></td><!-- 10米水泥杆 -->
			<td><span id="MGRGAQ">${materialQuantityModel.MGRGAQ}</span></td><!-- 12米水泥杆 -->
			<td><span id="RSFUIX">${materialQuantityModel.RSFUIX}</span></td><!-- 15米水泥杆 -->
			<td><span id="NIKHNJ">${materialQuantityModel.NIKHNJ}</span></td><!-- 8米木杆 -->
			<td><span id="FLGOCG">${materialQuantityModel.FLGOCG}</span></td><!-- 10米木杆 -->
			<td><span id="YKYXZH">${materialQuantityModel.YKYXZH}</span></td><!-- 电缆挂钩 35# -->
			<td><span id="QOQWIE">${materialQuantityModel.QOQWIE}</span></td><!-- 电力线保护管 -->
			<td><span id="AMWAGC">${materialQuantityModel.AMWAGC}</span></td><!-- 地线保护管 -->
			
		</tr>
		
		<tr>
			<td class="label">材料类型</td>
			<td class="label">7/2.6钢绞线</td>
			<td class="label">7/2.2钢绞线</td>
			<td class="label">PVC套管</td>
			<td class="label">光分纤箱</td>
			<td class="label">光分器</td>
			<td class="label">法兰盘</td>
			<td class="label">3.0铁丝</td>
			<td class="label">1.5铁丝</td>
			<td class="label">单槽夹板</td>
			<td class="label">16x60mm镀锌穿钉</td>
		</tr>
		<tr>
			<td>数量</td>
			<td><span id="IKQPEA">${materialQuantityModel.IKQPEA}</span></td><!-- 7/2.6钢绞线 -->
			<td><span id="XLBSTE">${materialQuantityModel.XLBSTE}</span></td><!-- 7/2.2钢绞线 -->
			<td><span id="BWZWKK">${materialQuantityModel.BWZWKK}</span></td><!-- PVC套管 -->
			<td><span id="JCJWWQ">${materialQuantityModel.JCJWWQ}</span></td><!-- 光分纤箱 -->
			<td><span id="MTJMJB">${materialQuantityModel.MTJMJB}</span></td><!-- 光分器 -->
			<td><span id="GHPVVH">${materialQuantityModel.GHPVVH}</span></td><!-- 法兰盘 -->
			<td><span id="EYIBDZ">${materialQuantityModel.EYIBDZ}</span></td><!-- 3.0铁丝 -->
			<td><span id="NTBFQU">${materialQuantityModel.NTBFQU}</span></td><!-- 1.5铁丝 -->
			<td><span id="WWWDQY">${materialQuantityModel.WWWDQY}</span></td><!-- 单槽夹板 -->
			<td><span id="WCDLYM">${materialQuantityModel.WCDLYM}</span></td><!-- 16x60mm镀锌穿钉 -->
		</tr>
		
		<tr>
			<td class="label">材料类型</td>
			<td class="label">16x80mm镀锌螺丝</td>
			<td class="label">地锚铁柄</td>
			<td class="label">地锚石</td>
			<td class="label">单槽夹板、16x60mm镀锌穿钉</td>
			<td class="label">二线担、担夹、U型穿钉</td>
			<td class="label">电缆接线子</td>
			<td class="label">电缆</td>
			<td class="label">开启式套管1#</td>
			<td class="label">开启式套管2#</td>
			<td class="label">开启式套管3#</td>
			
		</tr>
		<tr>
			<td>数量</td>
			<td><span id="QAMLSA">${materialQuantityModel.QAMLSA}</span></td><!-- 16x80mm镀锌螺丝 -->
			<td><span id="SZZVRS">${materialQuantityModel.SZZVRS}</span></td><!-- 地锚铁柄 -->
			<td><span id="CYYGII">${materialQuantityModel.CYYGII}</span></td><!-- 地锚石 -->
			<td><span id="BAVWBY">${materialQuantityModel.BAVWBY}</span></td><!-- 单槽夹板、16x60mm镀锌穿钉 -->
			<td><span id="OMDOPG">${materialQuantityModel.OMDOPG}</span></td><!-- 二线担、担夹、U型穿钉 -->
			<td><span id="UMFWWU">${materialQuantityModel.UMFWWU}</span></td><!-- 电缆接线子 -->
			<td><span id="IHOJOB">${materialQuantityModel.IHOJOB}</span></td><!-- 电缆 -->
			<td><span id="KPUDEF">${materialQuantityModel.KPUDEF}</span></td><!-- 开启式套管1# -->
			<td><span id="ADLKKF">${materialQuantityModel.ADLKKF}</span></td><!-- 开启式套管2# -->
			<td><span id="XKXXEV">${materialQuantityModel.XKXXEV}</span></td><!-- 开启式套管3# -->
		</tr>
		
		<tr>
			<td class="label">材料类型</td>
			<td class="label">开启式套管4#</td>
			<td class="label">开启式套管5#</td>
			<td class="label">电缆模块</td>
			<td class="label">电缆分线盒5对</td>
			<td class="label">电缆分线盒10对</td>
			<td class="label">电缆分线盒20对</td>
			<td class="label">电缆分线盒30对</td>
			<td class="label">电缆分线盒50对</td>
			<td class="label">架空跨路警示牌</td>
			<td class="label">拉线保护管</td>
			
		</tr>
		<tr>
			<td>数量</td>
			<td><span id="AUIKES">${materialQuantityModel.AUIKES}</span></td><!-- 开启式套管4# -->
			<td><span id="MXSMPE">${materialQuantityModel.MXSMPE}</span></td><!-- 开启式套管5# -->
			<td><span id="MXFNIJ">${materialQuantityModel.MXFNIJ}</span></td><!-- 电缆模块 -->
			<td><span id="ZYSQDU">${materialQuantityModel.ZYSQDU}</span></td><!-- 电缆分线盒5对 -->
			<td><span id="FSWCUA">${materialQuantityModel.FSWCUA}</span></td><!-- 电缆分线盒10对 -->
			<td><span id="QJBWTS">${materialQuantityModel.QJBWTS}</span></td><!-- 电缆分线盒20对 -->
			<td><span id="UWMMPT">${materialQuantityModel.UWMMPT}</span></td><!-- 电缆分线盒30对 -->
			<td><span id="KMARYS">${materialQuantityModel.KMARYS}</span></td><!-- 电缆分线盒50对 -->
			<td><span id="KYZSUF">${materialQuantityModel.KYZSUF}</span></td><!-- 架空跨路警示牌 -->
			<td><span id="LQIMRR">${materialQuantityModel.LQIMRR}</span></td><!-- 拉线保护管 -->
		</tr>
		
		
		<tr>
			<td class="label">材料类型</td>
			<td class="label">拉线抱箍</td>
			<td class="label">拉线衬环</td>
			<td class="label">拉线衬环、16x80mm镀锌螺丝</td>
			<td class="label">热缩管</td>
			<td class="label">人井井盖</td>
			<td class="label">熔纤盘</td>
			<td class="label">室外电话皮线</td>
			<td class="label">松香蜡</td>
			<td class="label">跳纤</td>
			<td class="label">尾纤</td>
		</tr>
		<tr>
			<td>数量</td>
			<td><span id="QMAOFE">${materialQuantityModel.QMAOFE}</span></td><!-- 拉线抱箍 -->
			<td><span id="BTQRFA">${materialQuantityModel.BTQRFA}</span></td><!-- 拉线衬环 -->
			<td><span id="TZNPVL">${materialQuantityModel.TZNPVL}</span></td><!-- 拉线衬环、16x80mm镀锌螺丝 -->
			<td><span id="FFRYTI">${materialQuantityModel.FFRYTI}</span></td><!-- 热缩管 -->
			<td><span id="KZWUXF">${materialQuantityModel.KZWUXF}</span></td><!-- 人井井盖 -->
			<td><span id="JDTDOZ">${materialQuantityModel.JDTDOZ}</span></td><!-- 熔纤盘 -->
			<td><span id="AHWXID">${materialQuantityModel.AHWXID}</span></td><!-- 室外电话皮线 -->
			<td><span id="VBMPPD">${materialQuantityModel.VBMPPD}</span></td><!-- 松香蜡 -->
			<td><span id="UJNYVN">${materialQuantityModel.UJNYVN}</span></td><!-- 跳纤 -->
			<td><span id="NJCKFK">${materialQuantityModel.NJCKFK}</span></td><!-- 尾纤 -->
		</tr>
		
		<tr>
			<td class="label">材料类型</td>
			<td class="label">小三角旗（市管物资）</td>
			<td class="label">光缆接头盒_双端 72芯 4孔 架空</td>
			<td class="label">人井口圈</td>
			<td class="label">标识牌</td>
			<td class="label">旋转卡接</td>
			<td class="label">升高槽钢</td>
		</tr>
		<tr>
			<td>数量</td>
			<td><span id="PWXMMA">${materialQuantityModel.PWXMMA}</span></td><!-- 小三角旗（市管物资） -->
			<td><span id="GL72">${materialQuantityModel.PWXMMA}</span></td>
			<td><span id="RJKQ">${materialQuantityModel.PWXMMA}</span></td>
			<td><span id="BZP">${materialQuantityModel.PWXMMA}</span></td>
			<td><span id="XZKJ">${materialQuantityModel.PWXMMA}</span></td>
			<td><span id="SGCG">${materialQuantityModel.PWXMMA}</span></td>
		</tr>
		
	</table>
</div>
<br />