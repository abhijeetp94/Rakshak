CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT
                   , userid TEXT UNIQUE NOT NULL
                   , username TEXT
                   , password TEXT
                   , firstname TEXT
                   , lastname TEXT
                   , email TEXT
                   , phone TEXT
                   , joining_date TEXT);

CREATE TABLE staff (_id INTEGER PRIMARY KEY AUTOINCREMENT
                   , user INTEGER UNIQUE NOT NULL
                   , staffID TEXT UNIQUE NOT NULL
                   , title TEXT
                   , isAdmin INTEGER
                   , isDoctor INTEGER
                   , isReceptionist INTEGER
                   , FOREIGN KEY (user) references users(_id));

CREATE TABLE doctors (_id INTEGER PRIMARY KEY AUTOINCREMENT
                     , staff INTEGER UNIQUE NOT NULL
                     , doctorID TEXT UNIQUE NOT NULL
                     , speciality TEXT, degrees TEXT
                     , experience INTEGER
                     , cabin_number INTEGER
                     , available INTEGER
                     , FOREIGN KEY (staff) REFERENCES staff(_id));

CREATE TABLE beds (_id INTEGER PRIMARY KEY AUTOINCREMENT
                  , bed_number INTEGER
                  , cabin_number INTEGER
                  , type TEXT
                  , occupied INTEGER
                  , patient_id TEXT
                  , doctor_id TEXT
                  , UNIQUE (bed_number, cabin_number));

CREATE TABLE patients (_id INTEGER PRIMARY KEY AUTOINCREMENT
                      , user INTEGER UNIQUE
                      , details TEXT
                      , doctor INTEGER
                      , FOREIGN KEY (user) REFERENCES users(_id)
                      , FOREIGN KEY (doctor) REFERENCES doctors(_id));

CREATE TABLE blood_donations (_id INTEGER PRIMARY KEY AUTOINCREMENT
                             , user INTEGER UNIQUE
                             , quantity INTEGER
                             , current_quantity NUMERIC
                             , blood_type TEXT
                             , FOREIGN KEY (user) REFERENCES users(_id));


CREATE TABLE plasma_donations (_id INTEGER PRIMARY KEY AUTOINCREMENT
                            , user INTEGER UNIQUE
                            , quantity INTEGER
                            , current_quantity NUMERIC
                            , plasma_type TEXT
                            , FOREIGN KEY (user) REFERENCES users(_id));

CREATE TABLE schedules (_id INTEGER PRIMARY KEY AUTOINCREMENT
                       , date TEXT
                       , shift INTEGER
                       , approved INTEGER
                       , user_id TEXT
                       , doctor INTEGER
                       , FOREIGN KEY (doctor) REFERENCES doctors(_id));

CREATE TABLE time_table (_id INTEGER PRIMARY KEY AUTOINCREMENT
                        , doctor INTEGER
                        , start_time TEXT
                        , end_time TEXT
                        , date TEXT
                        , shift INTEGER
                        , FOREIGN KEY (doctor) REFERENCES doctors(_id));

CREATE TABLE pay_managers (_id INTEGER PRIMARY KEY AUTOINCREMENT
                        , grade_pay NUMERIC
                        , base_salary NUMERIC
                        , staff INTEGER
                        , month INTEGER
                        , paid_leaves INTEGER
                        , FOREIGN KEY (staff) REFERENCES staff(_id));

CREATE TABLE bonuses (_id INTEGER PRIMARY KEY AUTOINCREMENT
                     , pay_manager INTEGER
                     , amount NUMERIC
                     , title TEXT
                     , FOREIGN KEY (pay_manager) REFERENCES pay_managers(_id));

CREATE TABLE medicines (_id INTEGER PRIMARY KEY AUTOINCREMENT
                       , medicine_id TEXT NOT NULL UNIQUE
                       , description TEXT
                       , name TEXT
                       , available INTEGER
                       , price NUMERIC
                       , quantity INTEGER
                       , price_description TEXT);

CREATE TABLE attendance (_id INTEGER PRIMARY KEY AUTOINCREMENT
                        , staff INTEGER NOT NULL
                        , date TEXT
                        , is_present INTEGER
                        , FOREIGN KEY (staff) REFERENCES staff(_id));

CREATE TABLE vaccines (_id INTEGER PRIMARY KEY AUTOINCREMENT
                      , name TEXT
                      , type TEXT
                      , quantity INTEGER
                      , available INTEGER
                      , vaccine_id TEXT UNIQUE
                      , dose INTEGER);


CREATE TABLE vaccination (_id INTEGER PRIMARY KEY AUTOINCREMENT
                         , user_id TEXT
                         , vaccine INTEGER
                         , dose INTEGER
                         , date TEXT
                         , FOREIGN KEY (vaccine) REFERENCES vaccines(_id));

CREATE TABLE covid_tests (_id INTEGER PRIMARY KEY AUTOINCREMENT
                         , user_id TEXT
                         , date TEXT
                         , result INTEGER);
