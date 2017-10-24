# WebUtils
采用反射、注解以及DBUtils，对Dao层进行了抽取，包含了基本的增删改查操作；同时对Service层也做了一定的抽取。

项目包含：
BaseDao               interface of Dao;<br>
BaseDaoImpl           abstract implementation of BaseDao;<br>
BaseService           interface of service;<br>
BaseServiceImpl       abstract implementation of BaseService;<br>
Id                    annotation of primary key;<br>
NoneProperty          annotation of none-field in a java bean;<br>
PageBean              just as you see;<br>

