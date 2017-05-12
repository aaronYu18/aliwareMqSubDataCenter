package com.courier.core.service;

import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.FaqMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.FAQ;
import com.courier.sdk.constant.CodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class FAQService extends BaseManager<FAQ> {
    private static final Logger logger = LoggerFactory.getLogger(FAQService.class);

    @Autowired
    private FaqMapper faqMapper;

    @Override
    public FAQ getEntity() {
        return new FAQ();
    }

    @Override
    public BaseMapper<FAQ> getBaseMapper() {
        return faqMapper;
    }

    public ResponseDTO getContent() {
        List<FAQ> categories = faqMapper.findCategory();
        if(categories == null || categories.size() < 0) return new ResponseDTO(CodeEnum.C1000);

        List<FAQ> result = new ArrayList<>();
        for (FAQ faq : categories){
            List<FAQ> faqs = faqMapper.findByParent(faq.getPath());

            faq.setChildren(faqs);
            result.add(faq);
        }

        return new ResponseDTO(CodeEnum.C1000, result, null);
    }
}
