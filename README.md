# Эмулятор REST сервисов
На основании файлов эмулятор создаёт карту "запрос-ответ".

Эмулятор принимает REST запросы, если такой запрос есть в карте, отдаёт соответствующий ответ.

Если поступает неизвестный запрос, эмулятор отдаёт статус 404.

Для того чтобы добавить пару "запрос-ответ", необходимо создать соответствующий файл и положить его в папку, которую читает эмулятор.
1) В первой строке, первое слово - HTTP метод, заглавными буквами.
2) В первой строке, после HTTP метода, через пробел, URL путь и параметры запроса (без адреса сервера).
3) Вторая и последующие строки - ответ, который будет отдавать эмулятор, в случае получения данного запроса.
4) Один файл должен содержать один "запрос-ответ".

Пример такого файла:
```` 
GET /api/cats?id=1
{
    "id": 1,
    "name": "vasja"
}
````

Требуется сконфигурировать следующие свойства в **application.properties**:
- **path-to-folder** - папка с файлами запросов-ответов