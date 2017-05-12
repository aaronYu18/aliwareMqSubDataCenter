package com.courier.core.service;

import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.ManageFeedbackMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.ManageFeedback;
import com.courier.db.entity.Manager;
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
public class ManageFeedBackService extends BaseManager<ManageFeedback> {
    private static final Logger logger = LoggerFactory.getLogger(ManageFeedBackService.class);

    @Autowired ManagerService managerService;
    @Autowired private ManageFeedbackMapper manageFeedbackMapper;

    @Override
    public ManageFeedback getEntity() {
        return new ManageFeedback();
    }

    @Override
    public BaseMapper<ManageFeedback> getBaseMapper() {
        return manageFeedbackMapper;
    }

    /**
     * 提交，保存网点反馈信息
     *
     * @param uuid
     * @param title
     * @param content
     * @return
     */
    public ResponseDTO commitFeedBack(String uuid, String title, String content, String phone) {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000)
            return responseDTO;

        Manager manager = (Manager) responseDTO.getT2();

        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(phone)) {
            logger.debug("save manageFeedBack error, error is : 'title or phone is empty'");
            return new ResponseDTO(CodeEnum.C1034);
        }

        ManageFeedback manageFeedback = new ManageFeedback(content, phone, title, manager.getId());
        save(manageFeedback);

        return new ResponseDTO(CodeEnum.C1000);
    }



    /******************* begin private method  *******************************/


}
