# ScheduleAndroid
Мобильная версия сайта [l12.scripthub.ru/test](https://l12.scripthub.ru/test/index.php).
* **Ссылка на репозиторий сайта:** [KolyaginVlad/schedule](https://github.com/KolyaginVlad/schedule)
* **Язык:** Java
* **Библиотеки:** Retrofit 2 + Gson для связи с сервером
* **Метод запросов:** Get
* **Формат ответов:** JSON
* **Модули:**
  * **MainActivity** - модуль, отвечающий за отображение главной activity.
    * **Наследуется от AppCompatActivity, реализует интерфейс Presenter.ViewMain**
    * **Глобальные переменные:**
      ```java
      private CalendarView calendar; - Календарь
      private Spinner subjects; - Выпадающий список
      private TextView date; - Выбранная дата добавления
      private Presenter presenter; - Модуль, отвечающий за логику
      private ProgressBar progressBar; - Индикатор загрузки
      private Button addButton; - Кнопка для добавления записи
      private TextView addText; - Текст-пояснение
      ```
    * **Методы:**
      ```java
       protected void onCreate(Bundle savedInstanceState) - Инициализация activity
       public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) - Получение разрешений от пользователя
       private void callTimePicker() - Вызов диалогового окна для получение времени
       private void callDatePicker() - Вызов диалогового окна для получение даты
       public void addNew(View view) throws ParseException - Метод, вызываемый при нажатии addButton
       public void showProgressBar() - Показать индикатор загрузки
       public void hideProgressBar() - Спрятать индикатор загрузки
       public void hideViews() - Показать все остальные элементы интерфейса
       public void showViews() - Спрятать все остальные элементы интерфейса
       public void setSubjects(PostSubject postSubject) - Установить полученные от сервера данные о предметах
       public void sayAboutError(String s) - Сообщить об ошибке
       public void saySuccess(String s) - Сообщить, что операция успешна
      ```
    * **Элементы интерфейса:**
      * ConstraintLayout;
          * Toolbar;
          * ScrollView;
            * ConstraintLayout;
                * Календарь;
                * Текстовая метка;
                * Выпадающий список;
                * Текст, для выбора даты;
                * Кнопка для отправки данных;
          * ProgressBar;
    * **События:**
      * *Загрузка activity* - отправка на модуль Presenter запроса на получение данных о предметах с сервера
      * *Выбор даты на календаре* - переход на DayActivity с передачей выбранного дня
      * *Нажатие на текст даты для добавления нового экзамена* - открытие DatePicker, а после выбора TimePicker. Данные записываются в текстовое поле
      * *Нажатие на кнопку "Добавить"* - отправка данных на модуль Presenter с последующей отправкой на сервер
  * **DayActivity** - модуль, отвечающий за отображение activity, содержащей данные об экзаменах на определённый день
      * **Наследуется от AppCompatActivity, реализует интерфейс DayPresenter.ViewDay**
      * **Глобальные переменные:**
        ```java
          private TableLayout mTableLayout; - Таблица
          private ProgressBar progressBar; - Индикатор загрузки
          private TextView day; - Метка, сообщающая о просматриваемом дне
          private DayPresenter dayPresenter; - Модуль, отвечающий за логику
        ```
          
      * **Методы:**
        ```java
         protected void onCreate(Bundle savedInstanceState) - Инициализация activity
         public void showProgressBar() - Показать индикатор загрузки
         public void hideProgressBar() - Спрятать индикатор загрузки
         public void hideViews() - Показать все остальные элементы интерфейса
         public void showViews() - Спрятать все остальные элементы интерфейса
         public void setSchedules(PostSchedule postSchedule) - Установить полученные от сервера данные о расписании
         public void sayAboutError(String s) - Сообщить об ошибке
         public void saySuccess(String s) - Сообщить, что операция успешна
        ```
      * **Элементы интерфейса:**
        * ConstraintLayout;
            * Toolbar;
            * Метка, сообщающая о просматриваемом дне;
            * Таблица;
            * ProgressBar;
      * **События:**
        * *Загрузка activity* - отправка на модуль DayPresenter запроса на получение данных о расписании с сервера
        * *Нажатие на навигационную кнопку в Toolbar*  - возвращение на MainActivity
  * **Presenter** - модуль, отвечающий за логику MainActivity
    * **Содержит описание интерфейса `public interface ViewMain`, предназначенного для взаимодействия с activity**
    * **Глобальные переменные:**
      ```java
      private ViewMain viewMain; - ссылка на реализующую интерфейс activity (MainActivity)
      ```
    * **Конструкторы:**
      ```java
      public Presenter(ViewMain viewMain)
      ```
    * **Методы:**
      ```java
      public void getSubjects() - Отправить запрос на сервер на получение списка предметов, а также получение этого списка и его отправка в модуль MainActiivity для присваивания данных выпадающему списку
      public void addNew(String subject, String date, String time) - Добавить новый экзамен
      ```
  * **DayPresenter** - модуль, отвечающий за логику DayActivity
    * **Содержит описание интерфейса `public interface ViewDay`, предназначенного для взаимодействия с activity**
    * **Глобальные переменные:**
      ```java
      private ViewDay viewDay; - ссылка на реализующую интерфейс activity (DayActivity)
      private String date; - выбранная дата
      ```
    * **Конструкторы:**
      ```java
      public DayPresenter(ViewDay viewDay, String date)
      ```
    * **Методы:**
      ```java
      public void getSchedule() - Отправить запрос на сервер на получение расписания, а также его получение и отправка в модуль DayActiivity для демонстрации данных в таблице
      ```
  * **App** - модуль, отвечающий за загрузку приложения
    * **Наследуется от Application**
    * **Глобальные переменные:**
      ```java
      private static Api api; - интерфейс взаимодействия с сервером
      ```
    * **Методы:**
      ```java
       public static Api getApi()
       public void onCreate() - инициализация приложения, а так же Retrofit
      ```
  * **Api** - модуль, отвечающий за взаимодействие с сервером
    ```java
    public interface Api {
    //Получить предметы
    @GET("api.php")
    Call<PostSubject> getSubjects(@Query("module") String module);

    //Получить расписание на день
    @GET("api.php")
    Call<PostSchedule> getSchedules(@Query("module") String module, @Query("date")String date);

    //Добавить новый экзамен
    @GET("api.php")
    Call<GetAdd> addNew(@Query("module") String module, @Query("subject") String subject,
                        @Query("date")String date,
                        @Query("time") String time);


    }
    ```
  * **Пакет api и содержимое** - классы-контейнеры для обработки JSON
