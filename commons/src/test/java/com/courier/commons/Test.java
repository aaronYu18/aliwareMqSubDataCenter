package com.courier.commons;

import com.courier.commons.util.ValidationUtil;
import com.courier.sdk.constant.Enumerate;
import com.google.gson.Gson;
import com.courier.commons.util.Uuid;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Test {

    public static final Logger logger = LoggerFactory.getLogger(Test.class);

    @Before
    public void setUp() throws Exception {

    }

    @org.junit.Test
    public void testdUrl() {
        String byCode = Enumerate.PlatformType.getByCode((byte) 1);
        System.out.println(byCode);
    }



    @org.junit.Test
    public void testStringFilter() throws PatternSyntaxException {
       /* String str = "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.()（）中国}34\n     {45[]12.fd" +
                "'*&999下面是中文的字符\t￥……{}【】。，；’“‘”？";*/
        String str = "address=湖南省长沙市开福区\t湖南省长沙市开福\n区万家丽北路    湖南机电职业技术学院";
        System.out.println(str);

        System.out.println(str);
    }

    @org.junit.Test
    public void testEmail() {
        EmailValidator validator = EmailValidator.getInstance();
        String str = "wangxudong@126.com";
        boolean f = validator.isValid(str);
        Assert.assertTrue(f);
    }

    @org.junit.Test
    public void testUrl() {
        UrlValidator validator = new UrlValidator();
        String str = "http://www.baidu.com";
        boolean f = validator.isValid(str);
        Assert.assertTrue(f);
    }

    @org.junit.Test
    public void testRedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
        config.setMaxIdle(5);

        String host = "192.168.5.128";
        String localhost = "7110";
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
//        for(int i=7000;i<=7005;i++) {
        jedisClusterNodes.add(new HostAndPort(host, 7110));
//        }
        JedisCluster jc = new JedisCluster(jedisClusterNodes, 10000, config);
        String s1 = jc.set("test", host + new Uuid().toString());
        String s2 = jc.get("test");
        logger.info("s1:{}, s2:{}", s1, s2);

        Assert.assertNotNull(s1);
        Assert.assertNotNull(s2);
    }

    @org.junit.Test
    public void testRedisCluste() {
        String host = "172.16.204.102";
        Set<HostAndPort> clusterNodes = new HashSet<>();
        clusterNodes.add(new HostAndPort(host, 7110));
        JedisCluster cluster = new JedisCluster(clusterNodes);
//        cluster.set("foo", "jedis test");
        Map<String, JedisPool> jedisPoolMap = cluster.getClusterNodes();
        logger.info("$$$$$$$$$$");
        for (Map.Entry<String, JedisPool> entry : jedisPoolMap.entrySet()) {
            logger.info(entry.getKey() + "," + entry.getValue() + "," + entry.getValue().getResource());
//            logger.info(entry.getValue().getResource().get("foo"));
        }
        cluster.set("foo", "jedis test");
        logger.info("foo:" + cluster.get("foo"));
        logger.info("##########");

    }

    @org.junit.Test
    public void test() throws Exception {
//        Gson gson = new GsonBuilder()
//                .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .create();
        Gson gson = new Gson();
        TestBean testBean = new TestBean();
        testBean.setDt(new Date());
        testBean.setUid(new Uuid());

        String str = "{\"dt\":\"2015-07-09 19:22:15\",\"uid\":{\"uid\":null,\"str\":\"fb8d6174b17e41ee8ad8e46dec87c808\"}}";

        logger.info(gson.toJson(testBean));

        TestBean bean = gson.fromJson(str, TestBean.class);

        logger.info("#########");


        String path = "/Users/ryan/ProgramFiles/project/ytoxl/sxg_v2.0/logs/ProcessCenter/job1.log";
        File file = new File(path);
        File f1 = file.getAbsoluteFile();
        File f2 = file.getCanonicalFile();
        logger.info(file.getCanonicalPath());
        logger.info(file.getAbsolutePath());

    }

    class TestBean {
        private Date dt;
        private Uuid uid;

        public Date getDt() {
            return dt;
        }

        public void setDt(Date dt) {
            this.dt = dt;
        }

        public Uuid getUid() {
            return uid;
        }

        public void setUid(Uuid uid) {
            this.uid = uid;
        }
    }

    @After
    public void tearDown() throws Exception {

    }
}