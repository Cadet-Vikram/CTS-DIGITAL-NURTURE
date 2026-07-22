-- ════════════════════════════════════════════════════════════════
-- data.sql – seed data for orm-learn exercises
-- Runs automatically after Hibernate creates the schema
-- (spring.jpa.defer-datasource-initialization=true)
-- ════════════════════════════════════════════════════════════════

-- ── Country data (hands-on 1 doc 1: Quick Example) ──────────────
-- Using column names as mapped by @Column on the entity
INSERT INTO country (co_code, co_name) VALUES ('IN', 'India');
INSERT INTO country (co_code, co_name) VALUES ('US', 'United States');
INSERT INTO country (co_code, co_name) VALUES ('AF', 'Afghanistan');
INSERT INTO country (co_code, co_name) VALUES ('AL', 'Albania');
INSERT INTO country (co_code, co_name) VALUES ('AU', 'Australia');
INSERT INTO country (co_code, co_name) VALUES ('AT', 'Austria');
INSERT INTO country (co_code, co_name) VALUES ('BD', 'Bangladesh');
INSERT INTO country (co_code, co_name) VALUES ('BR', 'Brazil');
INSERT INTO country (co_code, co_name) VALUES ('BV', 'Bouvet Island');
INSERT INTO country (co_code, co_name) VALUES ('CA', 'Canada');
INSERT INTO country (co_code, co_name) VALUES ('CN', 'China');
INSERT INTO country (co_code, co_name) VALUES ('DJ', 'Djibouti');
INSERT INTO country (co_code, co_name) VALUES ('FR', 'France');
INSERT INTO country (co_code, co_name) VALUES ('DE', 'Germany');
INSERT INTO country (co_code, co_name) VALUES ('GP', 'Guadeloupe');
INSERT INTO country (co_code, co_name) VALUES ('GS', 'South Georgia and the South Sandwich Islands');
INSERT INTO country (co_code, co_name) VALUES ('ID', 'Indonesia');
INSERT INTO country (co_code, co_name) VALUES ('JP', 'Japan');
INSERT INTO country (co_code, co_name) VALUES ('LU', 'Luxembourg');
INSERT INTO country (co_code, co_name) VALUES ('MX', 'Mexico');
INSERT INTO country (co_code, co_name) VALUES ('NP', 'Nepal');
INSERT INTO country (co_code, co_name) VALUES ('NG', 'Nigeria');
INSERT INTO country (co_code, co_name) VALUES ('PK', 'Pakistan');
INSERT INTO country (co_code, co_name) VALUES ('RU', 'Russian Federation');
INSERT INTO country (co_code, co_name) VALUES ('SG', 'Singapore');
INSERT INTO country (co_code, co_name) VALUES ('SS', 'South Sudan');
INSERT INTO country (co_code, co_name) VALUES ('ZA', 'South Africa');
INSERT INTO country (co_code, co_name) VALUES ('LK', 'Sri Lanka');
INSERT INTO country (co_code, co_name) VALUES ('TF', 'French Southern Territories');
INSERT INTO country (co_code, co_name) VALUES ('TH', 'Thailand');
INSERT INTO country (co_code, co_name) VALUES ('TR', 'Turkey');
INSERT INTO country (co_code, co_name) VALUES ('GB', 'United Kingdom');
INSERT INTO country (co_code, co_name) VALUES ('UM', 'United States Minor Outlying Islands');
INSERT INTO country (co_code, co_name) VALUES ('VN', 'Viet Nam');
INSERT INTO country (co_code, co_name) VALUES ('ZM', 'Zambia');
INSERT INTO country (co_code, co_name) VALUES ('ZW', 'Zimbabwe');

-- ── Department data (hands-on 4 doc 2: O/R Mapping) ─────────────
INSERT INTO department (dp_name) VALUES ('Engineering');
INSERT INTO department (dp_name) VALUES ('Human Resources');
INSERT INTO department (dp_name) VALUES ('Finance');

-- ── Skill data (hands-on 6 doc 2: Many-to-Many) ─────────────────
INSERT INTO skill (sk_name) VALUES ('Java');
INSERT INTO skill (sk_name) VALUES ('Spring Boot');
INSERT INTO skill (sk_name) VALUES ('Python');
INSERT INTO skill (sk_name) VALUES ('SQL');
INSERT INTO skill (sk_name) VALUES ('React');

-- ── Employee data (hands-on 4 doc 2: ManyToOne with Department) ──
INSERT INTO employee (em_name, em_salary, em_permanent, em_date_of_birth, em_dp_id)
VALUES ('Alice Johnson', 75000.00, TRUE,  '1990-03-15', 1);

INSERT INTO employee (em_name, em_salary, em_permanent, em_date_of_birth, em_dp_id)
VALUES ('Bob Smith',    60000.00, TRUE,  '1988-07-22', 2);

INSERT INTO employee (em_name, em_salary, em_permanent, em_date_of_birth, em_dp_id)
VALUES ('Carol Davis',  55000.00, FALSE, '1995-11-05', 1);

INSERT INTO employee (em_name, em_salary, em_permanent, em_date_of_birth, em_dp_id)
VALUES ('David Lee',    80000.00, TRUE,  '1985-01-30', 3);

INSERT INTO employee (em_name, em_salary, em_permanent, em_date_of_birth, em_dp_id)
VALUES ('Eve Wilson',   65000.00, FALSE, '1993-06-18', 2);

-- ── Employee-Skill join data (hands-on 6 doc 2: ManyToMany) ──────
-- Alice: Java, Spring Boot, React
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (1, 1);
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (1, 2);
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (1, 5);

-- Bob: Python, SQL
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (2, 3);
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (2, 4);

-- Carol: Java, SQL
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (3, 1);
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (3, 4);

-- David: Java, Spring Boot, SQL
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (4, 1);
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (4, 2);
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (4, 4);

-- Eve: Python, React
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (5, 3);
INSERT INTO employee_skill (es_em_id, es_sk_id) VALUES (5, 5);
