技术栈：compose + mvi + retrofit + okhttp + Hilt + DataStore + Room

整体开发架构使用: mvi模式

网络层使用: retrofit + okhttp

依赖注入：hilt

本地持久化：DataStore

UI框架：compose

本地数据库：Room


运行时参数注入：
https://medium.com/scalereal/providing-assistedinject-supported-viewmodel-for-composable-using-hilt-ae973632e29a

Compose：
[LazyPagingItems在保留状态的时候切换的会重置滚动位置：](https://issuetracker.google.com/issues/177245496)
* 在切回的瞬间，条目为0，所以状态内部被修改为0，导致条目后续不为0的时候，丢失数据

[Paging3刷新问题]()
* 

[Retrofit全局拦截器实现手段]