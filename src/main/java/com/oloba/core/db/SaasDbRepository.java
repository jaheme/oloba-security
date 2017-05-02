package com.oloba.core.db;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

/**
 * 业务定制的数据库连接对象
 * @author jahe.me
 *
 */
@Repository
public class SaasDbRepository extends AbstractDbService{
	
//	初始化jdbc
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		setSource(dataSource);
	}

}
