**定义**
> 使用   不同接口的类所提供的服务    为客户端提供它所期望的接口（断句已经用空格分开
--使用各种可能的服务，为客户端提供它所期望的接口）

**举例说明**
> **例1** - 我们工作的电脑，需要正常使用，必须提供标准的电源服务（额定电压，功率等），但是有可能某些情况下
你使用的电源并非计算机要求的标准电源，如：电脑标准电源服务需要110V的额定电压，但是生活用电为220V，工业用电为360V，
均不能满足需求，而此时我们就需要用到电源适配器来使得生活用电或者工业用电能为电脑提供所期望的电压。  
**例2** - 我们在开发程序的过程中，很多地方需要记录日志，知道`slf4j`的同事都知道，`slf4j`并没有提供相应的日志功能的实现，
要让他正常工作，我们需要提供一个具体的日志实现类`(e.g. java.util.logging, logback, log4j)`光是有这些样东西还是不够的，他们没有办法组合起来提供服务，如何才能让`Log4j`或者`logback`为`slf4j`提供日志服务呢？这个时候就需要一个适配器，将两个东西组合在一起``(e.g. slf4j-log4j12,jcl-over-slf4j,log4j-slf4j-impl等)`

**代码实现**
> 我们来简单实现例子1所介绍的情况
