package base.service;

import java.util.List;
import java.util.Map;

public interface Nop3SpringJdbcService {
	public int queryForInt(String sql);
	public List queryForList(String sql);
	public Map<String,Object> queryForMap(String sql);
	public void executeSql(String sql);
	
}
