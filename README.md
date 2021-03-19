# KotlinKtx
### 开发常用的拓展函数，加入协程拓展，可实现协程异步同步快速开发（该思想来自 [Rxhttp](https://github.com/liujingxing/okhttp-RxHttp) 一个非常好的网络请求框架）

## JCenter

androidx

```
implementation 'com.ayvytr:ktx-androidx:3.0.0'
```



#### 先说协程

##### 我们经常使用协程的时候，需要同步或者异步操作，还要判断错误或者返回不同的数值，这需要使用该框架可以直接使用，可对数组等进行操作,下面是例子，可自行食用（注意，建议使用rxLifeScope.launch运行携程块，可在fragment\activity\viewmodel 销毁时候自动注销，如果不是ac等，可以使用RxLifeScope.launch返回JOB，JOB.cacel()）

```kotlin
        rxLifeScope.launch({
            val test1 = Ktx.run {
                arrayListOf("444", "5555")
            }.startDelay(1300).sort()//数组等可操作
            val test2 = Ktx.run {
                5 / 0
            }.delay(1500)//可延时，
            Log.e("TAG", "onCreate:${test1.tryAwait()}   ${test2.tryAwait()} ")
        }, {
            Log.e("TAG", "onCreate error:${it.message}} ")
        }, {
            Log.e("TAG", "开始")
        }, {
            Log.e("TAG", "结束")
        })
```
## ktx包含内容（列举大概，后续加上）


### dp.kt Float，Int的dp，sp相关扩展方法

```kotlin
Int.dp
Int.sp
Float.dp
Float.sp
Int.dp2px
Float.dp2px
Int.sp2px
Float.sp2px
Int.px2dp
Float.px2dp
Int.px2sp
Float.px2sp

```

### Res.kt 获取android字符串，drawable等资源

```kotlin
    context.getStringArray()
    context.getDrawable2()
    context.getColor2()
    context.getDrawableArray()
    context.getDrawableIdArray()
...
```


### Spanner SpannableString操作工具类
```kotlin
bold
italic
normal
boldItalic
font
strikethrough
underline
backgroundColor
backgroundColorRes
textColorRes
textColor
pressedBackgroundColor
pressedBackgroundRes
onClick
onLongClick
```


### Screen.kt 获取屏幕尺寸，判断横竖屏，切换横竖屏等方法
```kotlin
    context.getDisplayMetrics()
    context.isLandscape()
    context.setLandscape()
    context.getScreenWidth()
    context.getScreenHeight()
```


### Clipboard.kt 剪贴板操作常用方法
```kotlin
    val clipboardManager = getClipboardManager()
    clibboardManager.getText2()
    clibboardManager.setText2()
    clibboardManager.getUri()
    clibboardManager.setUri()
    clibboardManager.getIntent()
    clibboardManager.setIntent()
```


### network.kt

```kotlin
getConnectivityManager
isNetworkConnected
isWifiConnected
isMobileDataConnected
```








