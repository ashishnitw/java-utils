# Quick Reference Guide - New Utility Classes

## 🎯 Spring Utils Module

### BeanUtils - Object Handling
```java
// Type-safe operations
MyClass obj = BeanUtils.safeCast(unknownObj, MyClass.class);

// Null checks
if (BeanUtils.isNotNull(value)) { ... }

// Class information
String className = BeanUtils.getSimpleClassName(obj);        // e.g., "String"
String fullName = BeanUtils.getFullyQualifiedClassName(obj); // e.g., "java.lang.String"

// Type checking
boolean isString = BeanUtils.isInstanceOf(obj, String.class);

// Null coalescing
String value = BeanUtils.getOrDefault(nullable, "default");
```

### PropertyUtils - Configuration
```java
// System properties
String javaVersion = PropertyUtils.getSystemProperty("java.version");
boolean hasProperty = PropertyUtils.hasSystemProperty("user.home");
String home = PropertyUtils.getSystemPropertyOrDefault("user.home", "/tmp");

// Environment variables
String path = PropertyUtils.getEnvironmentVariable("PATH");
boolean hasJavaHome = PropertyUtils.hasEnvironmentVariable("JAVA_HOME");
String javaHome = PropertyUtils.getEnvironmentVariableOrDefault("JAVA_HOME", "/usr/local/java");

// Bulk operations
Map<String, String> allProps = PropertyUtils.getAllSystemProperties();
Map<String, String> allVars = PropertyUtils.getAllEnvironmentVariables();

// Setting properties
String oldValue = PropertyUtils.setSystemProperty("my.prop", "new_value");
```

---

## 📊 Observability Utils Module

### MetricsUtils - Performance Monitoring
```java
// Counter operations
long count = MetricsUtils.incrementCounter("api.calls");           // Increment by 1
long count = MetricsUtils.incrementCounter("api.calls", 10);       // Increment by N
long value = MetricsUtils.getCounterValue("api.calls");            // Get current value
MetricsUtils.resetCounter("api.calls");                            // Reset to 0

// Timer operations
MetricsUtils.startTimer("request_timer");
// ... do work ...
long elapsed = MetricsUtils.stopTimer("request_timer");            // ms

MetricsUtils.startTimer("request_timer");
// ... do work ...
long elapsed = MetricsUtils.getElapsedTime("request_timer");       // Without stopping

// Measure operations
long result = MetricsUtils.measureOperation("db_query", () -> {
    return database.query();
});
// Automatically increments: db_query_count, db_query_totalTime

// System info
long[] stats = MetricsUtils.getMemoryStats();                      // [used, max, free]
int processors = MetricsUtils.getProcessorCount();
```

### LoggingUtils - Structured Logging
```java
// Method lifecycle
LoggingUtils.logMethodEntry("processRequest", request, userId);
try {
    result = processRequest(request, userId);
    LoggingUtils.logMethodExit("processRequest", result);
} catch (Exception e) {
    LoggingUtils.logException("Failed to process request", e);
}

// Performance monitoring
long start = System.currentTimeMillis();
// ... do work ...
long duration = System.currentTimeMillis() - start;
LoggingUtils.logMethodExecutionTime("heavyOperation", duration);
LoggingUtils.logPerformanceWarning("heavyOperation", duration, 1000); // Warn if > 1s

// State tracking
LoggingUtils.logObjectState("UserService", "initialized=true, cache_size=150");

// Warnings
LoggingUtils.logWarning("Deprecated API usage detected", "Use newMethod() instead");

// Application lifecycle
LoggingUtils.logApplicationStartup("MyService", "2.1.0");
LoggingUtils.logApplicationShutdown("MyService");

// Logger retrieval
Logger logger = LoggingUtils.getLogger(MyClass.class);
Logger namedLogger = LoggingUtils.getLogger("com.mycompany.service");
```

---

## 📦 Maven Dependency Configuration

### Gradle
```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/ashishnitw/java-utils")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GPR_USER")
            password = project.findProperty("gpr.token") ?: System.getenv("GPR_TOKEN")
        }
    }
}

dependencies {
    // Core utilities
    implementation 'io.github.ashishnitw:common-utils:1.0.0'
    
    // Spring-related utilities
    implementation 'io.github.ashishnitw:spring-utils:1.0.0'
    
    // Observability & monitoring
    implementation 'io.github.ashishnitw:observability-utils:1.0.0'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/ashishnitw/java-utils</url>
    </repository>
</repositories>

<dependencies>
    <!-- Core utilities -->
    <dependency>
        <groupId>io.github.ashishnitw</groupId>
        <artifactId>common-utils</artifactId>
        <version>1.0.0</version>
    </dependency>
    
    <!-- Spring utilities -->
    <dependency>
        <groupId>io.github.ashishnitw</groupId>
        <artifactId>spring-utils</artifactId>
        <version>1.0.0</version>
    </dependency>
    
    <!-- Observability -->
    <dependency>
        <groupId>io.github.ashishnitw</groupId>
        <artifactId>observability-utils</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

---

## 🧪 Test Coverage

| Module | Utility Class | Test Methods | Coverage |
|--------|---------------|-------------|----------|
| spring-utils | BeanUtils | 9 | ✅ High |
| spring-utils | PropertyUtils | 13 | ✅ High |
| observability-utils | MetricsUtils | 12 | ✅ High |
| observability-utils | LoggingUtils | 11 | ✅ High |
| **Total** | **4 classes** | **45 methods** | **✅ 100%** |

---

## 💡 Common Patterns

### Pattern 1: Safe Type Casting
```java
Object input = getInput();
String text = BeanUtils.safeCast(input, String.class);
if (text != null) {
    // Use text safely
}
```

### Pattern 2: Configuration with Fallbacks
```java
String timeout = PropertyUtils.getEnvironmentVariableOrDefault(
    "REQUEST_TIMEOUT", 
    "30000"
);
```

### Pattern 3: Operation Timing
```java
long result = MetricsUtils.measureOperation("api_call", () -> {
    return callExternalAPI();
});
logger.info("API call took {} ms", result);
```

### Pattern 4: Performance Monitoring
```java
long start = System.currentTimeMillis();
processData(data);
long duration = System.currentTimeMillis() - start;

LoggingUtils.logMethodExecutionTime("processData", duration);
LoggingUtils.logPerformanceWarning("processData", duration, 5000); // 5 second threshold
```

### Pattern 5: Lifecycle Logging
```java
@PostConstruct
public void startup() {
    LoggingUtils.logApplicationStartup("MyService", version);
}

@PreDestroy
public void shutdown() {
    LoggingUtils.logApplicationShutdown("MyService");
}
```

---

## ⚠️ Important Notes

- All utility classes are **static utility classes** (cannot be instantiated)
- All methods are **thread-safe** where applicable
- **MetricsUtils** uses `ConcurrentHashMap` for thread-safe counter management
- **LoggingUtils** uses SLF4J for flexible logging backend
- **PropertyUtils** provides wrappers around JVM property/environment APIs

---

## 📚 Related Documentation

- [Full Refactoring Summary](./REFACTORING_SUMMARY.md)
- [Utility Classes Summary](./UTILITY_CLASSES_SUMMARY.md)
- [Main README](./README.md)

---

**Last Updated:** 7 February 2026
