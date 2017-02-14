package base.interfaces;

/**
 * 一种生成器,用来产生指定对象的数据集合
 * 
 * @author Steve
 * 
 * @param <T>
 */
public interface Generate<T> {
	void generate(String identifier);
}
