package org.daily.bill.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by vano on 13.8.16.
 */
public abstract class AbstractDao {

    @Autowired
    private SqlSession sqlSession;

    protected SqlSession getSqlSession() {
        return sqlSession;
    }
}
