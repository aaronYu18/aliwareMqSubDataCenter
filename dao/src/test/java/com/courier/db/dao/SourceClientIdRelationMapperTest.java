package com.courier.db.dao;

import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.SourceClientIdRelation;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(locations = {"classpath:applicationDBContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SourceClientIdRelationMapperTest extends TestCase {

    @Autowired
    private SourceClientIdRelationMapper sourceClientIdRelationMapper;
    @Test
    public void testFindAll() throws Exception {

       List<SearchFilter> filters = new ArrayList<>();
        SearchFilter searchFilter1 = new SearchFilter("source", SearchFilter.Operator.EQ,"5", SearchFilter.Link.OR);

        SearchFilter searchFilter2 = new SearchFilter("source", SearchFilter.Operator.EQ,"1", SearchFilter.Link.OR);

        SearchFilter searchFilter3 = new SearchFilter("source", SearchFilter.Operator.EQ,"3", SearchFilter.Link.OR);
        filters.add(searchFilter1);
        filters.add(searchFilter2);
        filters.add(searchFilter3);
        List<SourceClientIdRelation> by = sourceClientIdRelationMapper.findAll(new SourceClientIdRelation(), filters, new ExtSqlProp());
        System.out.println(by);

    }
}