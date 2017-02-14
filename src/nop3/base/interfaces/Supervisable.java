package base.interfaces;

/**
 * 实现该接口以实现合作伙伴督办接口,表示一种"可督办的"的任务
 * 
 * @author Steve
 * 
 */
public interface Supervisable<T> {

	/**
	 * 准备督办项
	 */
	T prepare();

	/**
	 * 生成具体的督办项
	 * 
	 */
	T create();

	/**
	 * 完成具体的督办项
	 * 
	 */
	T finish();
}
