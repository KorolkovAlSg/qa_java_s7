<h1 align="center">Automatization tests for API in Java</h1>
<h2 align="center">Sprint 7</h2>
<h3 align="center">Korol'kov Aleksey</h3>

## Description
Тестовые сценарии

Требуется протестировать API учебного сервиса [Яндекс.Самокат](http://qa-scooter.praktikum-services.ru/). 
А именно, ручки:

- Создание курьера
- Логин курьера
- Создание заказа
- Список заказов

И создать отчет Allure
# Как запустить тесты?
`mvn clean test`
# Как создать отчет Allure?
`mvn allure:serve `

## Какие технологии исп. в проекте?
Allure 2.15.0

JUnit 4.13.1

REST Assured 5.5.6

GSON 2.8.9