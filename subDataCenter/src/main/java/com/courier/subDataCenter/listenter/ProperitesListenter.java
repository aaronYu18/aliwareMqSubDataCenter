package com.courier.subDataCenter.listenter;

import com.courier.commons.service.AbstractService;
import com.courier.commons.service.RegServiceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.*;

/**
 * Created by ryan on 16/2/21.
 * 启动文件属性监听器服务
 */
@Component
public class ProperitesListenter extends AbstractService{

    @Autowired
    private DataCheck dataCheck;
    @Value("${system.listenter.path}")
    private String confPath;
    private String propFile = "subDataCenter.properties";
    private WatchService watchService = null;

    @Override
    public void init() {
    }

    @Override
    public RegServiceVo getRegServiceVo() {
        return null;
    }

    @Override
    public void beforeStart() {
        /*if(idDebug)
            confPath = new File(System.getProperty("user.dir")) + File.separator + "src" + File.separator
                    + "main" + File.separator
                    + "resources" + File.separator
                    + "development" + File.separator;
        else
            confPath = new File(System.getProperty("user.dir")) + File.separator + "WEB-INF" + File.separator + "classes" + File.separator;*/

        Path path = Paths.get(confPath);
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            logger.info("start watch path:{}", confPath);

        } catch (Exception e){
            logger.error("init WatchService error, cause:{}", e.getMessage());
        }
    }

    @Override
    public void afterStart() {

    }

    @Override
    public void beforeStop() {

    }

    @Override
    public void afterStop() {

    }

    @Override
    public void run() {
        if(watchService != null){
            try {
                while (true) {
                    WatchKey watchKey = watchService.take();
                    for(WatchEvent<?> event : watchKey.pollEvents()){
                        logger.info("WatchEvent's context:{}, type:{}"
                                , event.context().toString()
                                , event.kind());

                        if(StandardWatchEventKinds.ENTRY_MODIFY == event.kind()
                                && propFile.equals(event.context().toString())){
                            logger.info("modify event... file:{}", confPath + propFile);
                            File file = new File(confPath + propFile);
                            dataCheck.loadProp2Redis(file);
                        }
                    }
                    if(!watchKey.reset()) {
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
