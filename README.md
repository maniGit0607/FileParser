# File Processor Application

This project demonstrates a file processing at certain intervals using spring batch.

---

## Prerequisites

Before you start, ensure you have the following installed on your system:
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (version 11 or above)
- [Apache Maven](https://maven.apache.org/)

---

## Getting Started

### 1. Navigate to the Project Directory
```bash
cd FileParser
```

### 2. Start Supporting Services with Docker Compose
```bash
docker-compose up
```

### 3. Start the SIMFileParser
```bash
cd SIMFileParser
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dsim.data.filepath=/Users/manikandankannan/sim"
```
