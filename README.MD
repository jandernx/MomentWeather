# MomentWeather
## Виконали:
1. [Осінній Дмитро](https://github.com/DimaOsinnii) - ІС-43
2. [Ілуца Євген](https://github.com/jandernx) - ІС-43
3. [Галактіонов Ілля](https://github.com/Dayrann) - ІС-42

# Лабораторна робота  № 1 
## Опис програмного продукту
  Програмний продукт був створений для моментального доступу до прогнозу погоди в будь-якій точці світу.
  Це може бути зручно для людей, які планують подорожі, що часто бувають в різних містах або 
  просто для тих, хто хоче дізнатися прогноз погоди.Для цього ми використовуэмо  Google Places API.
  
      Із Google Places API наш додаток вигідно відрізнялося від інших, додавши в нього детальну інформацію 
      приблизно про 100 мільйонів місць по всьому світу, упорядкованих за різними категоріями, з тієї ж бази даних, 
      що і у служб Google Maps і Google+.
  
   
## Платформа, мова програмування та тип дизайну
   - Android 4.1+
   - Java(Android Studio IDE)
   - Material Design


# Лабораторна робота  № 2
## Архітектура
   В даній роботі ми використали архітектуру MVP(Model-View-Presenter).
   MVP - шаблон проектування призначеного для користувача інтерфейсу, який був розроблений для полегшення автоматичного модульного          тестування та покращення поділу відповідальності у презентаційній логіці (відділення логіки від відображення):

   - Model — зберігає в собі всю бізнес-логіку, при необхідності отримує дані зі сховища.
   - View — реалізує відображення даних (з моделі), звертається до Presenter за оновленнями.
   - Presenter — реалізує взаємодію між моделлю і представленням.
   
   
# Лабораторна робота  № 3
## Прототип екранів
   - Для верстки використовуємо xml тому що в проекті немає динамічного добавлення компонентів View.
   - Екран статичний.
   
### Схема Activity:
   - activity: main activity
   - application: application.java
### Схема Fragment:
   SpecificWeatherFragment; SearchWeatherFragment; HistoryFragment; FavoritesFragment; LocationFragment;
   DaysFragment; CurrentFragment;
#### Приклад
[![|Solid](https://github.com/jandernx/MomentWeather/blob/master/screenshots/6.jpg)]() 

### Інструменти дизайну
   - Photoshop
   - GIMP(Linux)
    

# Лабораторна робота  № 4
## База даних
   SQLite — полегшена реляційна система керування базами даних. Втілена у вигляді бібліотеки, де реалізовано багато зі стандарту SQL-      92.SQLite зберігає всю базу даних (включаючи визначення, таблиці, індекси і дані) в єдиному стандартному файлі на тому пристрої,        на якому виконується застосунок.
   
   В нашому проекті ми використали БД для зберігання інформації про дії кінцевого користувача а саме: історія його переглядів ,обрані      місця.
   
   Дані які зберігаємо в базі данних: обрані місця,історія,назва міст,довгота,широта.
   
# Лабораторна робота  № 5
## Робота з мережею
   - Google Places API(для пошуку міст,вулиць...)
   
   - Google Geo API(місцезнаходження)
   
   - Retrofit - бібліотека для створення запитів на сервер,отримує погоду з сервіса openweathermap.org
      
          Retrofit  - це надійний REST-клієнт для Android, розроблений Square. Бібліотека забезпечує потужну 
          систему для автентифікації та взаємодії з інтерфейсами API та надсиланням мережевих запитів за допомогою 
          OkHttp
          Ця бібліотека робить завантаження JSON або XML-даних з веб-API досить простим. 
          Після завантаження даних, він конвертується у POJO,який повинен бути визначений для кожного "ресурсу" 
          у відповіді.
          

 
 
 # Лабораторна робота  № 6
 ## Hardware
   В даному проекті задіяний GPS модуль для місцезнаходження.Працюємо з ним посередньо, тому що багато роботи замість нас робить 
   Google Geo API.
  
      Інтерфейс Google Maps Geolocation API повертає розташування і радіус похибки на основі інформації 
      від веж стільникового зв'язку і точок доступу Wi-Fi, які може виявити мобільний клієнт.
      Обмін даними здійснюється по протоколу HTTPS з використанням методу POST. Запит і відповідь представлені 
      в форматі JSON,а контент може мати тип application або json.
   
   [Фрагмент коду](https://github.com/jandernx/MomentWeather/blob/master/app/src/main/java/com/dreamteam/momentweather/SearchWeather/Favorites/Location/LocationFragment.java)
 
 # Лабораторна робота  № 7
 
<img align="left" src="https://github.com/jandernx/MomentWeather/blob/master/screenshots/%D0%A1%D0%BA%D1%80%D1%96%D0%BD%D1%88%D0%BE%D1%82%20%D0%B2%D1%85%D0%BE%D0%B4%D1%83%20%D1%83%20%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D1%83.jpg">
  
 <img align="right" src="https://github.com/jandernx/MomentWeather/blob/master/screenshots/%D0%A1%D0%BA%D1%80%D1%96%D0%BD%D1%88%D0%BE%D1%82%20%D0%B2%D0%B8%D0%B2%D0%BE%D0%B4%D1%83%20%D0%BF%D0%BE%D0%BC%D0%B8%D0%BB%D0%BA%D0%B8%20%D0%BF%D1%80%D0%BE%20%D0%B2%D1%96%D0%B4%D0%BA%D0%BB%D1%8E%D1%87%D0%B5%D0%BD%D0%BD%D1%8F%20%D0%B3%D0%B5%D0%BE%D0%BB%D0%BE%D0%BA%D0%B0%D1%86%D1%96%D0%B9.jpg">
   
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Скріншот входу у програму &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Скріншот виводу помилки про відключення<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;геолокацій
   
   
   
   

