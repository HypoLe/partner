package base.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class Nop3SpringJdbcServiceImpl implements Nop3SpringJdbcService {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int queryForInt(String sql) {
		return this.jdbcTemplate.queryForInt(sql);
	}

	public List queryForList(String sql) {
		return this.jdbcTemplate.queryForList(sql);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> queryForMap(String sql) {
		return this.jdbcTemplate.queryForMap(sql);
	}

	public void executeSql(String sql) {
		this.jdbcTemplate.execute(sql);
	}
}
