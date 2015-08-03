DROP TABLE IF EXISTS grades;

CREATE TABLE grades  (
    student_id BIGINT NOT NULL PRIMARY KEY,
    course_id VARCHAR(20),
    score VARCHAR(10),
    grade VARCHAR(3)
);
