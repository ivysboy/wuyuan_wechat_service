## 旺生活微信平台
- 打包
        jar包 

        测试：mvn clean package -Ptest -U
       
        预发布： mvn clean package -Pprod -U
        
        线上： mvn clean package -Pprod -U
- 访问路径：

        测试：https://test.wang-guanjia.com/wechat-service/
        
        预发布：https://p-api.wang-guanjia.com/wechat-service/
        
        线上：https://api.wang-guanjia.com/wechat-service/
