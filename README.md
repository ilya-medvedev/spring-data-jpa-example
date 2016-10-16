spring-data-jpa example
=======================

[![Build Status](https://travis-ci.org/ilya-medvedev/spring-data-jpa-example.svg?branch=master)](https://travis-ci.org/ilya-medvedev/spring-data-jpa-example)
[![Coverage Status](https://coveralls.io/repos/github/ilya-medvedev/spring-data-jpa-example/badge.svg?branch=master)](https://coveralls.io/github/ilya-medvedev/spring-data-jpa-example?branch=master)

Development Environment
-----------------------
1. Install Git ([Git SCM](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git))
2. Install Java Standard Edition Development Kit  8 ([Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or [OpenJDK](http://openjdk.java.net/install/))
3. Install Maven 3 ([Apache Maven Project](https://maven.apache.org/download.cgi))

Download
--------
    git clone git@github.com:ilya-medvedev/spring-data-jpa-example.git

Build
-----
    cd spring-data-jpa-example
    mvn clean package

Run
---
    java -jar target/registrator.jar

Properties
----------

See:
1. [Spring Boot Externalized Configuration](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)
2. [Spring Boot Common application properties](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)

Use
---

You can send HTTP POST JSON message with password to

    http://localhost:8080/v1/user

cURL example:

    curl -H "Content-Type:application/json" -d "{\"password\":\"\"}" http://localhost:8080/v1/user

Response:

    {"id":"1"}