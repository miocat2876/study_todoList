package com.example.famback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class FamBackApplicationTests {

	@SpringJUnitConfig(classes = {DBConfig.class})
	public class DBConnectionTest {

		@Autowired
		private JdbcTemplate jdbcTemplate;

		@Test
		public void testConnection() {
			assertNotNull(jdbcTemplate);
			int result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
			assertEquals(1, result);
		}
	}
}
