### TODO List

1. ~~消息队列增强服务的使用~~
2. 微服务部署框架 
   1. ~~nacos注册~~
      1. ~~注册ip配置为变量~~
   2. ~~gateway发现服务~~
   3. ~~feign请求调用~~

problem:

- 不能够直接通过name属性获取到service ip，必须设定url才可请求(报错信息为302重定向错误，初步推测为header中缺少信息)

- gateway路由需要手动设置https://apps.ce.bktencent.com后的前缀访问路径

  如：

![image-20230424173339482](https://lye255-1316619619.cos.ap-guangzhou.myqcloud.com//markdown202304241733664.png)

否则会导致端口号加在https://apps.ce.bktencent.com/bkguidance后面

如：https://apps.ce.bktencent.com/bkguidance:80(正确url为https://apps.ce.bktencent.com:80/bkguidance)

