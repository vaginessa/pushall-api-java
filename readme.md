# PushAll-api-java
PushAll-api-java is a PushAll API wrapper for Java. It is asynchronous by default and uses Java 8
 classes like `CompletableFuture<T>`, so only **Java 8** or higher is supported.

## Install
```gradle
repositories {
	maven { url 'http://maven.ultramine.ru' }
}
dependencies {
	compile 'ru.pushall.api:pushall-api-java:0.1.0'
}
```
Maven central is coming soon

## Usage
First you need to create channel pushall.ru and get id and key of your channel. Also you can just register
 on pushall.ru and get key for your **self** channel (for send notifications to yourself). Then you can use
 this API.

### Channel creation:
```java
PushAllChannel ch = PushAll.getChannel(1234 /* your channel's id */, "your channel's key");
```
Or for self channel:
```java
PushAllSelfChannel ch = PushAll.getSelfChannel(1234 /* your user id */, "your self key");
```

### Sending notifications
```java
PushAllChannel ch = ...;
ch.newBroadcast()
	.setTitle("...")
	.setText("...")
	.setUrl("http://...")
	.setIcon("http://...")
	.setHidden(NotificationHideType.NOT_HIDE)
	.setPriority(NotificationPriority.DEFAULT)
	.setTtl(Duration.ofDays(25))
	.setBackground(true)
	.send().join();
```
All of these parameters are optional. You can simply write
```java
ch.newBroadcast().send().join();
```
Unicast is also supported
```java
ch.newUnicast(12345/* user id */).send().join();
```
Ok, so what is `send().join()`? It can be expanded to
```java
PushAllChannel ch = ...;
ExecutableNotificationBuilder<BroadcastResponse> builder = ch.newBroadcast();
CompletableFuture<BroadcastResponse> future = builder.send();
BroadcastResponse resp = future.join(); // awaiting request execution
```
All requests executes asynchronously, so `CompletableFuture<T>` provides server sesponse.
 Using `.join()` is the simplest way to return to "synchronous" behavior. Broadcast requests are also
 scheduled: interval maintained between requests to 30 seconds (according to API spec).
 So in this example, the thread will be blocked for 30 seconds.
```java
ch.newBroadcast().setTitle("1").send().join();
ch.newBroadcast().setTitle("2").send().join();
```
So don't use `.join()`, use handlers. For example
```java
ch.newBroadcast().send().whenComplete((response, error) -> {/* ... */});
...
// Awaiting for all requests execution before program exit
PushAllDefaults.getDefaultExecutor().shutdown();
PushAllDefaults.getDefaultExecutor().awaitTermination(10, TimeUnit.MINUTES);
```

### ShowList API
You can request information about last notifications and subscribers of the channel
```java
ch.showListNotifications(20 /* limit */).join(); // last 20 notifications
ch.showListNotification(12345 /* id */).join(); // notification with specified id
ch.showListUsers(); // All subscribers
ch.showListUser(12345 /* user id */); // subscriber with specified user id
```

### System properties
* `ru.pushall.api.installStartComCrt` = true/false (default true). pushall.ru uses StartCom SSL certificate
 that not supported by JDK defaultly. So root CA included to the library and installs by non-standard way
 (see StartComCrtInstaller). If you want to disable it and provide better way in your application, set this
 property to false
* `ru.pushall.api.executor.threads` = int (default 2). Number of workers in default thread pool. You
 can also set any custom executor by PushAllDefaults.setDefaultExecutor.

## Building
```shell
./gradlew build
```