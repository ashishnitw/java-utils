# Java Common Utils

[![Build Status](https://github.com/ashishnitw/java-common-utils/actions/workflows/publish.yml/badge.svg)](https://github.com/ashishnitw/java-common-utils/actions/workflows/publish.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-17-blue.svg)](https://openjdk.java.net/projects/jdk/17/)

A comprehensive Java utility library providing reusable components for common programming tasks across multiple Java services.

## Features

This library includes the following utility classes:

- **StringUtils** - String manipulation utilities
  - Empty/blank checks
  - Capitalization
  - Truncation
  - Null/empty conversions

- **DateTimeUtils** - Date and time utilities
  - Current timestamp retrieval
  - Date formatting and parsing
  - Epoch milliseconds conversion

- **JsonUtils** - JSON serialization/deserialization
  - Object to JSON conversion
  - JSON to object parsing
  - Pretty printing support
  - Built on Jackson library

- **ValidationUtils** - Common validation utilities
  - Email validation
  - URL validation
  - Numeric and alphanumeric checks

- **CollectionUtils** - Collection helper utilities
  - Empty/non-empty checks
  - Safe list handling
  - First element retrieval

- **ExceptionUtils** - Exception handling utilities
  - Stack trace extraction
  - Root cause analysis
  - Exception message retrieval

## Installation

### Gradle

First, configure your GitHub Personal Access Token (see [GitHub Personal Access Token Setup](#github-personal-access-token-setup) below).

Then add the following to your `build.gradle`:

```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/ashishnitw/java-common-utils")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GPR_USER")
            password = project.findProperty("gpr.token") ?: System.getenv("GPR_TOKEN")
        }
    }
}

dependencies {
    implementation 'io.github.ashishnitw:common-utils:1.0.0'
}
```

### Maven

First, configure your GitHub Personal Access Token (see [GitHub Personal Access Token Setup](#github-personal-access-token-setup) below).

Then add the following to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/ashishnitw/java-common-utils</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>io.github.ashishnitw</groupId>
        <artifactId>common-utils</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## GitHub Personal Access Token Setup

To download packages from GitHub Packages, you need a Personal Access Token (PAT) with the `read:packages` scope.

### Creating a GitHub PAT

1. Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token (classic)"
3. Give it a descriptive name (e.g., "GitHub Packages Read")
4. Select the `read:packages` scope
5. Click "Generate token"
6. **Copy the token immediately** (you won't be able to see it again)

### Configuring for Gradle

Create or edit `~/.gradle/gradle.properties` and add:

```properties
gpr.user=YOUR_GITHUB_USERNAME
gpr.token=YOUR_GITHUB_TOKEN
```

### Configuring for Maven

Edit `~/.m2/settings.xml` and add:

```xml
<settings>
    <servers>
        <server>
            <id>github</id>
            <username>YOUR_GITHUB_USERNAME</username>
            <password>YOUR_GITHUB_TOKEN</password>
        </server>
    </servers>
</settings>
```

### Security Best Practices

⚠️ **IMPORTANT**: Never commit your GitHub token to version control!

- Add `gradle.properties` to your `.gitignore`
- Keep your `settings.xml` file private
- Use environment variables in CI/CD pipelines
- Rotate tokens periodically
- Use the minimum required scope (`read:packages` for consuming packages)

## Usage Examples

### StringUtils

```java
import com.ashishnitw.utils.StringUtils;

// Check if string is empty or null
boolean isEmpty = StringUtils.isEmpty(str);  // true for null or ""
boolean isBlank = StringUtils.isBlank(str);  // true for null, "", or whitespace

// Capitalize first letter
String capitalized = StringUtils.capitalize("hello");  // "Hello"

// Truncate string
String truncated = StringUtils.truncate("Hello World", 5);  // "Hello"

// Convert null to empty and vice versa
String safe = StringUtils.nullToEmpty(null);  // ""
String nullable = StringUtils.emptyToNull("");  // null
```

### DateTimeUtils

```java
import com.ashishnitw.utils.DateTimeUtils;
import java.time.LocalDateTime;

// Get current timestamp
LocalDateTime now = DateTimeUtils.getCurrentTimestamp();

// Format date
LocalDateTime date = LocalDateTime.of(2024, 1, 15, 10, 30);
String formatted = DateTimeUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
// "2024-01-15 10:30:00"

// Parse date
LocalDateTime parsed = DateTimeUtils.parseDate("2024-01-15 10:30:00", "yyyy-MM-dd HH:mm:ss");

// Convert to/from epoch milliseconds
long epochMilli = DateTimeUtils.toEpochMilli(date);
LocalDateTime fromEpoch = DateTimeUtils.fromEpochMilli(epochMilli);
```

### JsonUtils

```java
import com.ashishnitw.utils.JsonUtils;

// Serialize object to JSON
MyObject obj = new MyObject("test", 123);
String json = JsonUtils.toJson(obj);

// Deserialize JSON to object
MyObject parsed = JsonUtils.fromJson(json, MyObject.class);

// Pretty print JSON
String prettyJson = JsonUtils.toJsonPretty(obj);
```

### ValidationUtils

```java
import com.ashishnitw.utils.ValidationUtils;

// Validate email
boolean isEmail = ValidationUtils.isValidEmail("test@example.com");  // true

// Validate URL
boolean isUrl = ValidationUtils.isValidUrl("https://example.com");  // true

// Check if numeric
boolean isNum = ValidationUtils.isNumeric("12345");  // true

// Check if alphanumeric
boolean isAlphaNum = ValidationUtils.isAlphanumeric("abc123");  // true

// Check not empty
boolean notEmpty = ValidationUtils.isNotEmpty("test");  // true
```

### CollectionUtils

```java
import com.ashishnitw.utils.CollectionUtils;
import java.util.List;
import java.util.Arrays;

// Check if collection is empty
boolean isEmpty = CollectionUtils.isEmpty(list);  // true for null or empty
boolean isNotEmpty = CollectionUtils.isNotEmpty(list);  // true if has elements

// Safe list (returns empty list if null)
List<String> safeList = CollectionUtils.safeList(nullableList);

// Get first element or null
String first = CollectionUtils.firstOrNull(Arrays.asList("a", "b", "c"));  // "a"
String noFirst = CollectionUtils.firstOrNull(null);  // null
```

### ExceptionUtils

```java
import com.ashishnitw.utils.ExceptionUtils;

try {
    // Some code that throws exception
} catch (Exception e) {
    // Get stack trace as string
    String stackTrace = ExceptionUtils.getStackTraceAsString(e);
    
    // Get root cause
    Throwable rootCause = ExceptionUtils.getRootCause(e);
    
    // Get root cause message
    String rootMessage = ExceptionUtils.getRootCauseMessage(e);
}
```

## Building from Source

### Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher (or use the included Gradle wrapper)

### Build Steps

```bash
# Clone the repository
git clone https://github.com/ashishnitw/java-common-utils.git
cd java-common-utils

# Build the project
./gradlew build

# Run tests
./gradlew test

# Generate Javadoc
./gradlew javadoc
```

## Publishing

This library is automatically published to GitHub Packages when changes are pushed to the `main` branch.

### Manual Publishing (for maintainers)

```bash
# Set environment variables
export GITHUB_TOKEN=your_token_here

# Publish to GitHub Packages
./gradlew publish
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Guidelines

- Follow Java code conventions
- Add unit tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting PR
- Add Javadoc comments for public methods

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Authors

- **ashishnitw** - *Initial work* - [@ashishnitw](https://github.com/ashishnitw)

## Acknowledgments

- Built with Java 17 LTS
- Uses Jackson for JSON processing
- Uses SLF4J for logging
- Testing with JUnit 5
