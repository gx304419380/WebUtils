# WebUtils
采用反射、注解以及DBUtils，对Dao层进行了抽取，包含了基本的增删改查操作；同时对Service层也做了一定的抽取。

项目包含：
BaseDao               interface of Dao
BaseDaoImpl           abstract implementation of BaseDao
BaseService           interface of service
BaseServiceImpl       abstract implementation of BaseService
Id                    annotation of primary key
NoneProperty          annotation of none-field in a java bean
PageBean              just as you see
