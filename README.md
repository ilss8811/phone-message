# Тестовое задание
***
## Описание

[route-schema]: **Приложение реализует маршрут**

* (1) Приём HTTP POST запроса на сервер
* (2) При наличии тела запроса, отвечает пустым сообщнием с HTTP кодом 200 OK
* (3) Отправляет RPC запрос брокеру сообщений
* (4) Получает ответ от брокера (Следствие RPC запроса)
и обрабатывает сообщение в Processor
* (5) Отправляет изменённое сообщение в брокер

## Модули приложения

### phone-message-divided-route-chain
***

[**Цепочка маршрута разделена на отдельные классы:**][route-schema]:

* RestPhoneMessageRoute - (1)
* PhoneMessageOkResponseRoute - (2)
* PhoneMessageRpcRequestRoute - (3)
* PhoneMessageProcessRoute - (4)
* PhoneMessageDefaultRequestRoute - (5)

### phone-message-monolith-route-chain
***
[**Цепочка маршрута сосредоточена в одном классе:**][route-schema]:

* PhoneMessageMonolithRoute