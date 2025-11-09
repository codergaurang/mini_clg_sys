USE java_project;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  role ENUM('ADMIN','TEACHER','STUDENT') NOT NULL,
  student_id INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_users_student
    FOREIGN KEY (student_id) REFERENCES studata(ID)
);
