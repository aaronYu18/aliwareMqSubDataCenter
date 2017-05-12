package com.courier.core.service;

import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.FeedbackMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.Feedback;
import com.courier.sdk.constant.CodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bin on 2015/10/30.
 */
@Service
@Transactional
public class FeedBackService extends BaseManager<Feedback> {
    private static final Logger logger = LoggerFactory.getLogger(FeedBackService.class);

    @Autowired UserService userService;
    @Autowired private FeedbackMapper feedbackMapper;

    @Override
    public Feedback getEntity() {
        return new Feedback();
    }

    @Override
    public BaseMapper<Feedback> getBaseMapper() {
        return feedbackMapper;
    }

    /**
     * 提交，保存快递员反馈信息
     *
     * @param uuid
     * @param title
     * @param content
     * @return
     */
    public ResponseDTO commitFeedBack(String uuid, String title, String content, String phone) {
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000)
            return responseDTO;

        Long userId = (Long) responseDTO.getT2();

        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(phone)) {
            logger.debug("save feedBack error, error is : 'title or phone is empty'");
            return new ResponseDTO(CodeEnum.C1034);
        }

        Feedback feedback = new Feedback(content, phone, title, userId);
        save(feedback);

        return new ResponseDTO(CodeEnum.C1000);
    }



    /******************* begin private method  *******************************/


}
