# 规范  

## 编译环境

* 统一gradle的版本号为5.4.1  

------
## 命名  

* 所有布局文件,素材以小写字母,下划线连接来命名,布局文件名参考对应例图名称命名  

* 类与接口每个单词首字母大写,如DataOrderAdapter  

* 文件夹名全小写字母  

* 常量全大写,下划线连接  

* 方法名和实例名首单词小写,后面首字母大写 ,如onCreate()  

* 同类型实例名尽量保持前缀相等,如strCustomerFirst和strCustomerLast，而不是strFirstCustomer和strLastCustomer  

------

## 示例  
* **应用主页**,点击地址进入[地址选择页面](),点击商家进入商家页,点我的进入[用户详情页](),点订单看[所有订单]()   

  ![](res/activity_main.png)

* **地址选择**随便列几个食堂(可以点)  

  ![](res/activity_address.png)
  
* **店铺菜单**,点击加号同时购物车更新信息,点“商家”切入[商家详情页](),点[购物车]()弹出详情  

  ![](res/activity_shop_food.png)
  
* **商家详情页**,电话+地址+公告,评论区样式暂定或随意发挥  

  ![](res/activity_shop_details.png)

* **购物车**,点加号加一份,减号少一份,只有一份直接删,点清空就清空,点结算进[提交订单页]()  

  ![](res/activity_shopping_car.png)

* **提交订单页**,自提时间就是预计可以去拿的时间,支付方式可以砍掉,最上一栏为个人信息及食堂店铺地址,中间列出买的菜及数量价格,活动折扣类向内缩进4个空格,底栏显示优惠金额与实际需支付金额  

  ![](res/activity_pushing_order.png)
  
* **用户个人页**  

* **订单页**  

* ……

## android studio代码同步  

### 对master分支做操作时必须先pull一下![](res/pull.png)  

![](res/PullMaster.png)  

> origin/master打勾就行  

### 开始开发  

**1** 点`new branch`新建本地分支![](res/newbranch.png)  

> `checkout tag...`:切换版本(应该用不到,别点)
>
> `local branches`:本地的分支  
>
> `remote branches`:远程仓库的分支  

**2** 分支取个名字![](res/checkoutnewbranch.png)  

在这里也可以随时切换分支![](res/checkoutbranch.png)  

> `checkout branch`:切换到新建的这个分支  

**3** 切到自己的分支再做改动就影响不到主分支了,写好一次改动且不报错后可以提交改动![](res/commit.png)  ![](res/CommitFile.png)

> 底下就是改前改后对比,给要提交改动的文件打上勾后点commit(commit message一定要写自己干了啥)  



