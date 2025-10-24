# 📚 Library API

## О проекте

Цель работы — реализовать REST‑сервис с CRUD‑операциями для сущностей **Book**, **Author**, **Reader** и **Fine**.

Я постарался сделать проект максимально воспроизводимым: от настройки зависимостей до тестирования через Postman.

---

## 🧰 Используемые технологии

- Java 21
- Spring Boot 3.5.6
- Maven
- VSCode / IntelliJ IDEA
- Postman (для тестирования API)

---

## 📦 Зависимости

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Скриншоты работ

---

![alt text](image.png)
_Рис. 1. Структура зависимостей проекта_

![alt text](image-1.png)
_Рис. 2. POST-запрос для сущности Author_

![alt text](image-2.png)
_Рис. 3. POST-запрос для сущности Book_

![alt text](image-3.png)
_Рис. 4. POST-запрос для сущности Readers_

![alt text](image-4.png)
_Рис. 5. POST-запрос для сущности Fines_

---
