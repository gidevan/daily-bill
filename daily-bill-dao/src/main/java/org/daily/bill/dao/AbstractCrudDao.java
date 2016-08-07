package org.daily.bill.dao;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractCrudDao {

    @Autowired
    private SqlSession sqlSession;

    protected SqlSession getSqlSession() {
        return sqlSession;
    }
}
