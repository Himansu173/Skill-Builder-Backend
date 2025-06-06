DROP SCHEMA IF EXISTS skill_builder_db;



CREATE SCHEMA skill_builder_db;

USE skill_builder_db;



CREATE TABLE admin (
admin_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
profile_pic VARCHAR(200),
password VARCHAR(255) NOT NULL
);



CREATE TABLE skills (
skill_id INT PRIMARY KEY AUTO_INCREMENT,
skill_name VARCHAR(100) NOT NULL UNIQUE
);



CREATE TABLE specializations (
specialization_id INT PRIMARY KEY AUTO_INCREMENT,
specialization_name VARCHAR(100) NOT NULL UNIQUE
);



CREATE TABLE mentor (
mentor_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
age INT,
email VARCHAR(100) NOT NULL UNIQUE,
profile_pic VARCHAR(200),
education VARCHAR(100),
bio VARCHAR(300),
rating INT,
review_count INT,
specialization_id INT REFERENCES specializations(specialization_id),
password VARCHAR(255) NOT NULL
);



CREATE TABLE mentor_skills (
mentor_skill_id INT PRIMARY KEY AUTO_INCREMENT,
skill_id INT REFERENCES skills(skill_id),
mentor_id INT REFERENCES mentor(mentor_id),
skill_level VARCHAR(20) NOT NULL,
UNIQUE (skill_id, mentor_id));



CREATE TABLE work_experiences (
work_id INT PRIMARY KEY AUTO_INCREMENT,
mentor_id INT REFERENCES mentor(mentor_id),
company_name VARCHAR(150) NOT NULL,
role VARCHAR(100) NOT NULL,
start_date DATE NOT NULL,
end_date DATE NOT NULL,
role_description VARCHAR(400),
UNIQUE (mentor_id, company_name, role));



CREATE TABLE mentee (
mentee_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
age INT NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
profile_pic VARCHAR(200),
career_objective VARCHAR(100),
password VARCHAR(255) NOT NULL
);



CREATE TABLE desired_skills (
desired_skill_id INT PRIMARY KEY AUTO_INCREMENT,
mentee_id INT REFERENCES mentee(mentee_id),
skill_id INT REFERENCES skills(skill_id),
UNIQUE (mentee_id, skill_id));



CREATE TABLE mentee_industry_interests (
mentee_industry_interests_id INT PRIMARY KEY AUTO_INCREMENT,
mentee_id INT REFERENCES mentee(menteeId),
specialization_id INT REFERENCES specializations(specializations_id),
UNIQUE (mentee_id, specialization_id));



CREATE TABLE favourite_mentors (
favourite_id INT PRIMARY KEY AUTO_INCREMENT,
mentee_id INT REFERENCES mentee(mentee_id),
mentor_id INT REFERENCES mentor(mentor_id),
UNIQUE (mentee_id, mentor_id));



CREATE TABLE mentor_availability (
availability_id INT PRIMARY KEY AUTO_INCREMENT,
mentor_id INT REFERENCES mentor(mentor_id),
date DATE NOT NULL,
start_time TIME NOT NULL,
end_time TIME NOT NULL,
is_recurring BOOLEAN DEFAULT false,
time_zone VARCHAR(30) NOT NULL,
status VARCHAR(30) DEFAULT 'AVAILABLE',
UNIQUE (mentor_id, date, start_time, end_time, time_zone));



CREATE TABLE reviews (
review_id INT PRIMARY KEY AUTO_INCREMENT,
mentee_id INT REFERENCES mentee(mentee_id),
mentor_id INT REFERENCES mentor(mentor_id),
rating INT CHECK (rating BETWEEN 1 AND 5),
review_description VARCHAR(300),
response VARCHAR(300),
created_at DATETIME,
response_created_at DATETIME
);



CREATE TABLE appointments (
appointment_id INT PRIMARY KEY AUTO_INCREMENT,
mentor_id INT REFERENCES mentor(mentor_id),
mentee_id INT REFERENCES mentee(mentee_id),
agenda VARCHAR(300) NOT NULL,
duration INT NOT NULL,
date DATE,
start_time TIME,
end_time TIME,
status VARCHAR(50) DEFAULT 'PENDING',
created_at DATETIME,
note_by_mentee VARCHAR(300),
reason_by_mentor_if_rejecting VARCHAR(300),
review_id INT REFERENCES reviews(review_id),
rescheduled_id INT REFERENCES appointments(appointment_id));



CREATE TABLE reports (
report_id INT PRIMARY KEY AUTO_INCREMENT,
user_id INT NOT NULL,
user_type VARCHAR(50) NOT NULL,
category_of_violation VARCHAR(100) NOT NULL,
explanation VARCHAR(300) NOT NULL,
created_at DATETIME,
status VARCHAR(30) DEFAULT 'PENDING'
);



CREATE TABLE suggestions (
suggestion_id INT PRIMARY KEY AUTO_INCREMENT,
user_id INT NOT NULL,
user_type VARCHAR(50) NOT NULL,
suggestions_type VARCHAR(100) NOT NULL,
description VARCHAR(300) NOT NULL,
status VARCHAR(50) DEFAULT 'PENDING',
note VARCHAR(300),
created_at DATETIME
);



CREATE TABLE notifications (
notification_id INT PRIMARY KEY AUTO_INCREMENT,
sender_id INT NOT NULL,
sender_type VARCHAR(10) NOT NULL,
receiver_id INT NOT NULL,
receiver_type VARCHAR(10) NOT NULL,
message VARCHAR(300) NOT NULL,
is_read BOOLEAN DEFAULT FALSE,
created_at DATETIME
);



INSERT INTO admin (admin_id, name, email, profile_pic, password)
VALUES(10001,'John Deo', 'john.deo@gmail.com','http://localhost:8080/skillbuilder/images/adminImages/admin_10001.jpg','John123');



INSERT INTO skills (skill_name) VALUES
('Python'),
('JavaScript'),
('Java'),
('C++'),
('SQL'),
('HTML'),
('CSS'),
('React'),
('Node.js'),
('Django'),
('Flask'),
('Machine Learning'),
('Data Analysis'),
('Cybersecurity'),
('Cloud Computing'),
('AWS'),
('DevOps'),
('Git'),
('Project Management'),
('Agile Methodology'),
('UI/UX Design'),
('Mobile App Development'),
('iOS Development'),
('Android Development'),
('Public Speaking'),
('Leadership'),
('Time Management'),
('Communication Skills'),
('Career Counseling'),
('Resume Writing');



INSERT INTO specializations (specialization_name) VALUES
('Software Engineering'),
('Data Science'),
('Cybersecurity'),
('Cloud Architecture'),
('Mobile Development'),
('Web Development'),
('Artificial Intelligence'),
('Machine Learning'),
('Blockchain Technology'),
('Digital Marketing'),
('UI/UX Design'),
('Product Management'),
('Project Management'),
('DevOps Engineering'),
('Network Engineering'),
('Business Analysis'),
('Quality Assurance'),
('Game Development'),
('IT Support'),
('Database Administration'),
('Embedded Systems'),
('Computer Vision'),
('Natural Language Processing'),
('Technical Writing'),
('E-commerce Development'),
('Robotics'),
('Augmented Reality'),
('Financial Technology (FinTech)'),
('Healthcare IT'),
('Educational Technology (EdTech)');



INSERT INTO mentor (name, age, email, rating, review_count, education, bio, specialization_id, password) VALUES
('Alice Johnson', 35, 'alice.johnson@example.com', 9, 2, 'M.Sc. Computer Science', 'Passionate about backend systems.', 1, 'pass1234'),
('Bob Smith', 40, 'bob.smith@example.com', 60, 17, 'B.Tech IT', 'Experienced full-stack developer.', 2, 'bobpass1'),
('Carol Davis', 30, 'carol.davis@example.com', 80, 22, 'MBA Technology Management', 'Helping bridge tech & business.', 3, 'carol321'),
('David Lee', 29, 'david.lee@example.com', 30, 12, 'B.Sc. Cybersecurity', 'Security is not just a job, it\'s a mindset.', 4, 'secureme'),
('Eve Thompson', 33, 'eve.thompson@example.com', 48, 10, 'M.Tech Cloud Computing', 'Cloud enthusiast and AWS certified.', 5, 'cloud123'),
('Frank White', 45, 'frank.white@example.com', 200, 45, 'Ph.D. Computer Science', 'Love teaching ML concepts.', 6, 'mlmentor'),
('Grace Kim', 31, 'grace.kim@example.com', 0, 0, 'M.Sc. Data Science', 'Turning data into decisions.', 7, 'gracepass'),
('Henry Brown', 37, 'henry.brown@example.com', 250, 43, 'B.E. Electronics', 'Robotics & AI enthusiast.', 8, 'henryrobot'),
('Isla Green', 27, 'isla.green@example.com', 50, 17, 'B.Sc. Software Engineering', 'Specialist in scalable systems.', 9, 'islapass'),
('Jack Wilson', 39, 'jack.wilson@example.com', 15, 4, 'MBA', 'Product thinker & strategist.', 10, 'jackmba'),
('Karen Young', 36, 'karen.young@example.com', 300, 64, 'M.Des.', 'UI/UX mentor with 10 years of experience.', 11, 'karenui'),
('Leo Clark', 32, 'leo.clark@example.com', 4, 1, 'B.Sc. Comp Sci', 'Project leader and mentor.', 12, 'leadme'),
('Mona Adams', 28, 'mona.adams@example.com', 40, 17, 'B.Tech CSE', 'QA expert and bug hunter.', 13, 'monatest'),
('Nathan Scott', 34, 'nathan.scott@example.com', 45, 11, 'M.Tech AI', 'Helping students with NLP.', 14, 'nathanai'),
('Olivia Turner', 26, 'olivia.turner@example.com', 0, 2, 'BCA', 'Mobile development mentor.', 15, 'oliviaapp'),
('Paul Baker', 38, 'paul.baker@example.com', 17, 4, 'B.Sc IT', 'Network & cloud specialist.', 16, 'networkit'),
('Queenie Ross', 30, 'queenie.ross@example.com', 70, 17, 'MCA', 'Love mentoring women in tech.', 17, 'queenpass'),
('Robert Hill', 41, 'robert.hill@example.com', 350, 76, 'MBA PM', 'Experienced product manager.', 18, 'robertpm'),
('Sophie Cox', 29, 'sophie.cox@example.com', 20, 17, 'B.Tech', 'Frontend & React mentor.', 19, 'sophiejs'),
('Tom Wright', 43, 'tom.wright@example.com', 80, 24, 'M.Tech', 'EdTech enthusiast.', 20, 'tomedu'),
('Uma Patel', 33, 'uma.patel@example.com', 29, 7, 'BBA', 'Career guidance & resume expert.', 21, 'umaguide'),
('Victor Evans', 35, 'victor.evans@example.com', 70, 23, 'Ph.D. NLP', 'Research mentor in language AI.', 22, 'victorai'),
('Wendy Flores', 27, 'wendy.flores@example.com', 0, 0, 'M.Sc.', 'Game dev specialist.', 23, 'gameon'),
('Xander Price', 36, 'xander.price@example.com', 300, 65, 'B.E.', 'Hardware & embedded mentor.', 24, 'xtech'),
('Yara Morris', 31, 'yara.morris@example.com', 150, 34, 'M.Tech', 'Blockchain advocate.', 25, 'yarablock'),
('Zack Reed', 38, 'zack.reed@example.com', 24, 6, 'MCA', 'Experienced in fintech solutions.', 26, 'finzack'),
('Amelia Gray', 29, 'amelia.gray@example.com', 43, 12, 'MBA', 'Marketing mentor.', 27, 'ameliaads'),
('Ben Knight', 40, 'ben.knight@example.com', 69, 23, 'M.Sc.', 'Tech writer and trainer.', 28, 'benwrite'),
('Clara Lewis', 28, 'clara.lewis@example.com', 52, 17, 'M.Ed.', 'EdTech and instructional design.', 29, 'claralearn'),
('Dylan Scott', 34, 'dylan.scott@example.com', 100, 65, 'M.Tech Robotics', 'Robotics for real-world applications.', 30, 'dylanbot');



INSERT INTO mentor_skills (skill_id, mentor_id, skill_level)
VALUES (1, 1, 'BEGINNER'),
(7, 1, 'INTERMEDIATE'),
(14, 1, 'EXPERT'),
(20, 1, 'BEGINNER'),
(5, 2, 'EXPERT'),
(11, 2, 'BEGINNER'),
(19, 2, 'INTERMEDIATE'),
(2, 3, 'INTERMEDIATE'),
(6, 3, 'BEGINNER'),
(25, 3, 'EXPERT'),
(3, 4, 'BEGINNER'),
(9, 4, 'INTERMEDIATE'),
(30, 4, 'EXPERT'),
(4, 5, 'EXPERT'),
(8, 5, 'INTERMEDIATE'),
(13, 6, 'EXPERT'),
(22, 6, 'INTERMEDIATE'),
(27, 6, 'BEGINNER'),
(10, 7, 'BEGINNER'),
(15, 7, 'BEGINNER'),
(18, 7, 'EXPERT'),
(25, 7, 'INTERMEDIATE'),
(16, 8, 'INTERMEDIATE'),
(29, 8, 'EXPERT'),
(12, 9, 'EXPERT'),
(24, 9, 'INTERMEDIATE'),
(6, 10, 'INTERMEDIATE'),
(17, 10, 'BEGINNER'),
(28, 10, 'EXPERT'),
(23, 11, 'EXPERT'),
(1, 11, 'BEGINNER'),
(26, 11, 'EXPERT'),
(11, 11, 'BEGINNER'),
(9, 12, 'INTERMEDIATE'),
(18, 12, 'BEGINNER'),
(23, 12, 'INTERMEDIATE'),
(13, 12, 'EXPERT'),
(3, 13, 'BEGINNER'),
(21, 13, 'INTERMEDIATE'),
(30, 13, 'EXPERT'),
(11, 14, 'INTERMEDIATE'),
(26, 14, 'BEGINNER'),
(4, 15, 'EXPERT'),
(16, 15, 'BEGINNER'),
(27, 15, 'INTERMEDIATE'),
(5, 16, 'EXPERT'),
(10, 16, 'INTERMEDIATE'),
(23, 17, 'EXPERT'),
(9, 17, 'BEGINNER'),
(13, 17, 'EXPERT'),
(19, 17, 'BEGINNER'),
(7, 18, 'INTERMEDIATE'),
(20, 18, 'EXPERT'),
(12, 18, 'INTERMEDIATE'),
(22, 18, 'BEGINNER'),
(14, 19, 'BEGINNER'),
(17, 19, 'INTERMEDIATE'),
(29, 19, 'EXPERT'),
(6, 20, 'INTERMEDIATE'),
(12, 20, 'BEGINNER'),
(26, 20, 'EXPERT'),
(21, 20, 'BEGINNER'),
(18, 21, 'INTERMEDIATE'),
(28, 21, 'EXPERT'),
(19, 21, 'INTERMEDIATE'),
(11, 21, 'BEGINNER'),
(8, 22, 'EXPERT'),
(25, 22, 'INTERMEDIATE'),
(7, 23, 'INTERMEDIATE'),
(15, 23, 'BEGINNER'),
(26, 23, 'EXPERT'),
(1, 24, 'BEGINNER'),
(30, 24, 'EXPERT'),
(5, 25, 'EXPERT'),
(14, 25, 'BEGINNER'),
(27, 25, 'INTERMEDIATE'),
(3, 25, 'BEGINNER'),
(2, 26, 'INTERMEDIATE'),
(12, 26, 'BEGINNER'),
(22, 26, 'EXPERT'),
(11, 27, 'INTERMEDIATE'),
(14, 27, 'BEGINNER'),
(9, 27, 'INTERMEDIATE'),
(4, 27, 'EXPERT'),
(3, 28, 'BEGINNER'),
(2, 28, 'INTERMEDIATE'),
(8, 28, 'EXPERT'),
(24, 28, 'BEGINNER'),
(29, 28, 'EXPERT'),
(18, 29, 'EXPERT'),
(7, 29, 'BEGINNER'),
(17, 29, 'INTERMEDIATE'),
(12, 29, 'INTERMEDIATE'),
(4, 30, 'BEGINNER'),
(19, 30, 'INTERMEDIATE'),
(23, 30, 'EXPERT');



INSERT INTO work_experiences (mentor_id, company_name, role, start_date, end_date, role_description)
VALUES (1, 'Tech Innovators Inc.', 'Software Engineer', '2020-01-15', '2022-06-30', 'Developed and maintained web applications, focusing on back-end services.'),
(1, 'Digital Solutions Ltd.', 'Senior Developer', '2022-07-01', '2024-03-15', 'Led a team of developers to build scalable applications and collaborated with cross-functional teams.'),
(2, 'Global Tech Corp.', 'Data Scientist', '2018-06-10', '2021-04-25', 'Analyzed large datasets to derive insights and created predictive models.'),
(2, 'Innovate Labs', 'Machine Learning Engineer', '2021-05-01', '2023-12-31', 'Developed and deployed machine learning models to optimize business operations.'),
(2, 'Creative Technologies', 'UX/UI Designer', '2019-02-01', '2021-08-30', 'Designed user interfaces and improved the user experience across various platforms.'),
(3, 'Visionary Solutions', 'Lead Designer', '2021-09-01', '2023-10-01', 'Managed the design team and collaborated on UI/UX improvements for flagship products.'),
(4, 'TechPioneers', 'Full Stack Developer', '2017-03-10', '2020-01-20', 'Developed end-to-end software solutions and optimized front-end and back-end architectures.'),
(4, 'Smart Web Solutions', 'Back-End Developer', '2020-02-01', '2022-07-15', 'Focused on creating robust server-side solutions and managing databases.'),
(5, 'NextGen Corp.', 'Project Manager', '2016-08-15', '2019-05-30', 'Led multiple projects across various departments, focusing on timely delivery and quality.'),
(5, 'FutureWorks Solutions', 'Senior Project Manager', '2019-06-01', '2022-12-31', 'Managed large-scale projects and ensured optimal resource allocation.'),
(5, 'Tech Solutions Group', 'DevOps Engineer', '2017-11-10', '2020-03-12', 'Maintained cloud infrastructure and automated deployment pipelines.'),
(6, 'SkyTech Ltd.', 'Cloud Architect', '2020-04-01', '2023-09-30', 'Designed and implemented cloud architecture solutions for enterprise clients.'),
(7, 'Innovate Digital', 'Product Manager', '2018-05-01', '2021-11-30', 'Oversaw product development lifecycle, from concept to launch, focusing on user-centric design.'),
(7, 'TechVision', 'Lead Product Manager', '2021-12-01', '2024-04-30', 'Managed product strategy and roadmaps, aligning business goals with development teams.'),
(8, 'Bright Future Tech', 'Marketing Strategist', '2015-06-01', '2018-02-20', 'Developed and executed digital marketing campaigns to increase brand awareness and lead generation.'),
(8, 'Creative Labs', 'Digital Marketing Manager', '2018-03-01', '2021-05-15', 'Managed SEO, SEM, and social media strategies to enhance digital presence.'),
(9, 'TechWave Solutions', 'Business Analyst', '2016-09-01', '2019-04-10', 'Analyzed business processes and provided insights for process optimization.'),
(9, 'FutureTech Enterprises', 'Senior Business Analyst', '2019-05-01', '2022-08-25', 'Led the analysis of large data sets and supported business decision-making.'),
(10, 'DataDriven Insights', 'Data Analyst', '2017-07-15', '2020-01-20', 'Analyzed customer data to identify trends and opportunities for growth.'),
(10, 'SmartTech Solutions', 'Senior Data Analyst', '2020-02-01', '2023-04-10', 'Designed and executed advanced data models to support business operations.'),
(10, 'NextEra Analytics', 'Lead Data Scientist', '2023-05-01', '2025-04-10', 'Developed machine learning algorithms and provided insights to guide strategic decisions.'),
(11, 'TechVisionary Corp.', 'System Architect', '2016-01-10', '2018-06-30', 'Designed and implemented complex system architectures for enterprise software solutions.'),
(11, 'Digital Innovators', 'Senior System Engineer', '2018-07-01', '2021-04-30', 'Led the development and deployment of secure, high-performance systems.'),
(12, 'Web Solutions Ltd.', 'Software Engineer', '2017-03-15', '2020-08-30', 'Developed full-stack web applications and integrated third-party services.'),
(12, 'Cloud Innovations', 'Cloud Solutions Architect', '2020-09-01', '2023-03-15', 'Designed cloud-based infrastructure to support global enterprise clients.'),
(13, 'FutureTech Solutions', 'DevOps Engineer', '2018-07-01', '2021-05-15', 'Automated infrastructure management and deployment pipelines for scaling cloud systems.'),
(13, 'CloudCrafters', 'Cloud Architect', '2021-06-01', '2024-04-20', 'Designed and implemented multi-cloud environments for large-scale businesses.'),
(14, 'Visionary Enterprises', 'Product Designer', '2015-04-10', '2017-09-15', 'Designed intuitive product interfaces with a focus on user experience and aesthetics.'),
(14, 'Innovation Labs', 'Lead Product Designer', '2017-10-01', '2021-12-31', 'Led product design teams to create customer-centric designs for multiple product lines.'),
(14, 'Techify Solutions', 'Full Stack Developer', '2016-11-01', '2019-05-10', 'Developed and maintained full-stack applications across various platforms.'),
(15, 'Innovative Technologies', 'Lead Developer', '2019-06-01', '2022-11-30', 'Led development teams for complex web and mobile applications with scalable architecture.'),
(16, 'SoftWave Solutions', 'Marketing Manager', '2017-01-25', '2019-06-30', 'Oversaw marketing strategies, including content creation, SEO, and social media outreach.'),
(16, 'Digital Innovations', 'Senior Marketing Strategist', '2019-07-01', '2022-12-31', 'Developed strategies to optimize brand presence and market penetration for new products.'),
(17, 'Prime Technologies', 'Senior Software Engineer', '2016-03-15', '2019-07-30', 'Developed software solutions for enterprise clients and ensured software scalability and performance.'),
(17, 'TechWorks Innovations', 'Lead Software Engineer', '2019-08-01', '2022-04-10', 'Led development teams and collaborated with cross-functional teams on large-scale software projects.'),
(18, 'Cloud Innovations Inc.', 'Cloud Engineer', '2017-08-01', '2020-04-15', 'Built cloud-native applications and managed infrastructure to ensure system uptime and performance.'),
(18, 'TechFlow Solutions', 'Cloud Solutions Architect', '2020-05-01', '2023-02-28', 'Designed cloud architectures that facilitated seamless digital transformations for clients.'),
(18, 'SoftNet Technologies', 'Data Analyst', '2018-02-01', '2020-11-30', 'Performed data analysis and business intelligence tasks to support strategic decision-making.'),
(19, 'DataVision Corp.', 'Lead Data Analyst', '2020-12-01', '2023-09-10', 'Led data analysis projects and created actionable insights to improve business processes.'),
(20, 'Cyber Solutions Ltd.', 'Cybersecurity Specialist', '2016-09-05', '2019-03-31', 'Designed and implemented security systems to protect digital assets from cyber threats.'),
(20, 'SecureNet Corp.', 'Senior Cybersecurity Engineer', '2019-04-01', '2022-07-15', 'Led teams in developing advanced security protocols and monitoring solutions for organizations.'),
(20, 'NextGen Cyber', 'Cybersecurity Consultant', '2022-08-01', '2024-04-15', 'Consulted on cybersecurity strategies, risk management, and compliance for clients in various sectors.'),
(21, 'TechNova Corp.', 'Frontend Developer', '2016-01-01', '2018-04-30', 'Developed and maintained user interfaces for web applications using modern JavaScript frameworks.'),
(21, 'App Innovations', 'Senior Frontend Developer', '2018-05-01', '2021-11-30', 'Led the frontend development team to create responsive, mobile-first applications.'),
(22, 'NextGen Systems', 'Product Manager', '2017-03-15', '2020-02-28', 'Managed cross-functional teams to launch new product features and optimize existing products.'),
(22, 'ProductFlow Technologies', 'Lead Product Manager', '2020-03-01', '2023-12-31', 'Led product strategy and roadmap, working closely with design and development teams to execute on high-impact initiatives.'),
(22, 'Skyline Solutions', 'Business Analyst', '2016-02-01', '2019-06-30', 'Gathered and analyzed business requirements to improve operational efficiencies and support business growth.'),
(23, 'SmartTech Systems', 'Senior Business Analyst', '2019-07-01', '2022-10-15', 'Conducted data-driven analysis to inform decision-making and optimize business processes.'),
(24, 'InnovativeTech Solutions', 'Software Engineer', '2017-09-15', '2020-04-10', 'Developed backend APIs and integrated with front-end components for high-performance applications.'),
(24, 'TechVision Labs', 'Lead Software Engineer', '2020-04-15', '2023-09-30', 'Led backend development team and ensured scalability of software products.'),
(25, 'CloudReach Technologies', 'Cloud Consultant', '2016-06-01', '2019-05-31', 'Provided expert advice to businesses on how to implement cloud-based systems for scalability and cost-effectiveness.'),
(25, 'CloudWorks Inc.', 'Cloud Solutions Architect', '2019-06-01', '2023-08-15', 'Designed and deployed cloud infrastructure solutions for large enterprises.'),
(26, 'DataSolutions', 'Data Scientist', '2017-02-10', '2020-03-20', 'Applied statistical models and machine learning techniques to solve complex business problems.'),
(26, 'AI Innovations', 'Senior Data Scientist', '2020-04-01', '2023-07-25', 'Led a team of data scientists to develop machine learning algorithms for predictive analytics.'),
(27, 'GreenTech Solutions', 'Project Manager', '2016-05-01', '2018-09-30', 'Managed project timelines and budgets while ensuring efficient delivery of technology solutions.'),
(28, 'TechFlow Dynamics', 'Senior Project Manager', '2018-10-01', '2022-12-31', 'Led cross-functional teams to deliver software projects on time and within budget, focusing on quality and client satisfaction.'),
(28, 'CyberSafe Solutions', 'Security Analyst', '2016-08-01', '2019-01-31', 'Monitored and protected company systems from potential security breaches and developed security policies.'),
(28, 'TechGuard Cybersecurity', 'Senior Security Analyst', '2019-02-01', '2022-04-10', 'Worked on vulnerability assessments, penetration testing, and incident response.'),
(29, 'NextTech Solutions', 'Software Developer', '2017-11-15', '2020-05-20', 'Developed software features for web and mobile applications using various programming languages and frameworks.'),
(29, 'CloudWorks Tech', 'Senior Software Developer', '2020-06-01', '2023-09-30', 'Contributed to the design and implementation of cloud-native software solutions.'),
(29, 'SmartGrid Solutions', 'Electrical Engineer', '2016-04-10', '2019-03-31', 'Worked on designing and maintaining electrical systems and components for smart grid technologies.'),
(30, 'GridTech Innovations', 'Senior Electrical Engineer', '2019-04-01', '2022-12-31', 'Led a team to design and implement advanced electrical systems for energy-efficient solutions.');



INSERT INTO mentee (name, age, email, career_objective, password) VALUES
('Alice Johnson', 23, 'alice.johnson@example.com', 'Become a full-stack developer', 'pass1234'),
('Brian Smith', 26, 'brian.smith@example.com', 'Break into data science', 'data4567'),
('Catherine Lee', 21, 'catherine.lee@example.com', 'Excel in UI/UX design', 'uiux2024'),
('Daniel White', 24, 'daniel.white@example.com', 'Start a career in cybersecurity', 'secure789'),
('Emily Davis', 22, 'emily.davis@example.com', 'Master front-end development', 'frontend1'),
('Franklin Harris', 25, 'franklin.harris@example.com', 'Become a DevOps engineer', 'devops22'),
('Grace Miller', 20, 'grace.miller@example.com', 'Learn mobile app development', 'mobile99'),
('Henry Thompson', 23, 'henry.thompson@example.com', 'Explore cloud computing', 'cloud123'),
('Isabella Garcia', 22, 'isabella.garcia@example.com', 'Enter machine learning field', 'mllearn8'),
('Jacob Martinez', 24, 'jacob.martinez@example.com', 'Become a backend specialist', 'backend5'),
('Katherine Moore', 21, 'katherine.moore@example.com', 'Work in AI research', 'aiwork44'),
('Liam Anderson', 27, 'liam.anderson@example.com', 'Improve problem-solving with code', 'code4fun'),
('Mia Taylor', 23, 'mia.taylor@example.com', 'Build scalable web applications', 'scaleweb'),
('Nathan Young', 22, 'nathan.young@example.com', 'Contribute to open-source', 'open123'),
('Olivia Scott', 24, 'olivia.scott@example.com', 'Transition into tech from finance', 'switch99'),
('Paul Rivera', 26, 'paul.rivera@example.com', 'Become a software architect', 'arch7890'),
('Quinn Bell', 22, 'quinn.bell@example.com', 'Build a career in product management', 'prodmgmt'),
('Rachel Adams', 25, 'rachel.adams@example.com', 'Specialize in cloud infrastructure', 'infra88'),
('Samuel Brooks', 21, 'samuel.brooks@example.com', 'Launch a mobile app startup', 'launchit'),
('Tina Foster', 23, 'tina.foster@example.com', 'Work on innovative AI products', 'aipower1');



INSERT INTO desired_skills (mentee_id, skill_id)
VALUES (1, 3), (1, 7), (1, 14), (1, 21),
(2, 5), (2, 11), (2, 19), (2, 22), (2, 28),
(3, 2), (3, 8), (3, 13), (3, 30),
(4, 4), (4, 10), (4, 15), (4, 25),
(5, 1), (5, 6), (5, 12), (5, 18), (5, 27),
(6, 3), (6, 9), (6, 14), (6, 20),
(7, 5), (7, 11), (7, 17),
(8, 2), (8, 8), (8, 16), (8, 23), (8, 30),
(9, 1), (9, 6), (9, 12), (9, 19),
(10, 4), (10, 7), (10, 13), (10, 26), (10, 29),
(11, 6), (11, 13), (11, 17), (11, 24),
(12, 1), (12, 10), (12, 19), (12, 27), (12, 30),
(13, 4), (13, 8), (13, 12),
(14, 3), (14, 7), (14, 14), (14, 21),
(15, 2), (15, 9), (15, 11), (15, 16), (15, 25),
(16, 5), (16, 15), (16, 22), (16, 28),
(17, 6), (17, 18), (17, 23),
(18, 1), (18, 13), (18, 20), (18, 26), (18, 29),
(19, 3), (19, 11), (19, 17), (19, 24),
(20, 2), (20, 10), (20, 19), (20, 27), (20, 30);



INSERT INTO mentee_industry_interests (mentee_id, specialization_id)
VALUES (1, 5), (1, 12), (1, 19),
(2, 3), (2, 14),
(3, 6), (3, 18), (3, 27),
(4, 2), (4, 15),
(5, 7), (5, 9), (5, 24),
(6, 1), (6, 16), (6, 25),
(7, 4), (7, 13),
(8, 10), (8, 17), (8, 30),
(9, 11), (9, 22),
(10, 8), (10, 23), (10, 29),
(11, 6), (11, 18),
(12, 2), (12, 12), (12, 21),
(13, 4), (13, 15), (13, 20),
(14, 1), (14, 19),
(15, 7), (15, 10), (15, 28),
(16, 5), (16, 14),
(17, 9), (17, 23), (17, 26),
(18, 3), (18, 11),
(19, 8), (19, 16), (19, 30),
(20, 13), (20, 25);



INSERT INTO favourite_mentors (mentee_id, mentor_id)
VALUES (1, 3), (1, 5), (1, 8),
(3, 4), (3, 7), (3, 9),
(4, 2),
(6, 12), (6, 15), (6, 18),
(8, 3), (8, 5), (8, 19),
(9, 6), (9, 9), (9, 20),
(10, 1),
(12, 2), (12, 10), (12, 15),
(13, 7), (13, 14), (13, 17),
(14, 8),
(15, 16), (15, 19),
(16, 9), (16, 14),
(17, 3), (17, 8), (17, 20),
(19, 7), (19, 11);


INSERT INTO mentor_availability (mentor_id, date, start_time, end_time, time_zone, is_recurring)VALUES
(1, '2025-06-10', '14:00:00', '16:00:00', 'UTC+05:30', false),
(1, '2025-06-15', '09:00:00', '11:00:00', 'UTC+05:30', true),
(2, '2025-06-14', '15:00:00', '17:00:00', 'UTC+01:00', false),
(2, '2025-06-16', '08:00:00', '10:00:00', 'UTC+01:00', true),
(3, '2025-06-11', '16:00:00', '18:00:00', 'UTC+02:00', false),
(3, '2025-06-13', '10:00:00', '12:00:00', 'UTC+02:00', true),
(3, '2025-06-15', '14:00:00', '16:00:00', 'UTC+02:00', false),
(4, '2025-06-12', '13:00:00', '15:00:00', 'UTC+03:00', true),
(4, '2025-06-14', '09:00:00', '11:00:00', 'UTC+03:00', false),
(4, '2025-06-16', '14:00:00', '16:00:00', 'UTC+03:00', false),
(5, '2025-06-13', '16:00:00', '18:00:00', 'UTC-04:00', false),
(5, '2025-06-15', '11:00:00', '13:00:00', 'UTC-04:00', true),
(6, '2025-06-12', '12:00:00', '14:00:00', 'UTC+00:00', false),
(6, '2025-06-16', '15:00:00', '17:00:00', 'UTC+00:00', true),
(7, '2025-06-11', '09:00:00', '11:00:00', 'UTC-05:00', true),
(7, '2025-06-13', '12:00:00', '14:00:00', 'UTC-05:00', false),
(7, '2025-06-15', '10:00:00', '12:00:00', 'UTC-05:00', false),
(8, '2025-06-12', '14:00:00', '16:00:00', 'UTC+04:00', false),
(8, '2025-06-14', '08:00:00', '10:00:00', 'UTC+04:00', false),
(8, '2025-06-16', '13:00:00', '15:00:00', 'UTC+04:00', true),
(9, '2025-06-11', '10:00:00', '12:00:00', 'UTC+06:00', false),
(9, '2025-06-13', '14:00:00', '16:00:00', 'UTC+06:00', true),
(9, '2025-06-15', '09:00:00', '11:00:00', 'UTC+06:00', false),
(10, '2025-06-12', '16:00:00', '18:00:00', 'UTC-03:00', true),
(10, '2025-06-16', '08:00:00', '10:00:00', 'UTC-03:00', false),
(11, '2025-06-11', '15:00:00', '17:00:00', 'UTC-06:00', false),
(11, '2025-06-13', '10:00:00', '12:00:00', 'UTC-06:00', false),
(11, '2025-06-15', '11:00:00', '13:00:00', 'UTC-06:00', true),
(12, '2025-06-12', '14:00:00', '16:00:00', 'UTC+07:00', false),
(12, '2025-06-14', '13:00:00', '15:00:00', 'UTC+07:00', true),
(12, '2025-06-16', '09:00:00', '11:00:00', 'UTC+07:00', false),
(13, '2025-06-11', '11:00:00', '13:00:00', 'UTC-07:00', false),
(13, '2025-06-13', '16:00:00', '18:00:00', 'UTC-07:00', false),
(14, '2025-06-12', '13:00:00', '15:00:00', 'UTC+08:00', false),
(14, '2025-06-14', '10:00:00', '12:00:00', 'UTC+08:00', false),
(14, '2025-06-16', '15:00:00', '17:00:00', 'UTC+08:00', true),
(15, '2025-06-11', '10:00:00', '12:00:00', 'UTC+09:00', true),
(15, '2025-06-13', '11:00:00', '13:00:00', 'UTC+09:00', false),
(15, '2025-06-15', '12:00:00', '14:00:00', 'UTC+09:00', false),
(16, '2025-06-12', '16:00:00', '18:00:00', 'UTC+10:00', false),
(16, '2025-06-14', '14:00:00', '16:00:00', 'UTC+10:00', true),
(16, '2025-06-16', '11:00:00', '13:00:00', 'UTC+10:00', false),
(17, '2025-06-1', '14:00:00', '16:00:00', 'UTC+11:00', false),
(17, '2025-06-13', '09:00:00', '11:00:00', 'UTC+11:00', false),
(17, '2025-06-15', '10:00:00', '12:00:00', 'UTC+11:00', true),
(18, '2025-06-12', '17:00:00', '19:00:00', 'UTC-02:00', true),
(18, '2025-06-14', '13:00:00', '15:00:00', 'UTC-02:00', false),
(18, '2025-06-16', '12:00:00', '14:00:00', 'UTC-02:00', false),
(19, '2025-06-11', '15:00:00', '17:00:00', 'UTC+12:00', false),
(19, '2025-06-13', '09:00:00', '11:00:00', 'UTC+12:00', false),
(19, '2025-06-15', '10:00:00', '12:00:00', 'UTC+12:00', true),
(20, '2025-06-12', '18:00:00', '20:00:00', 'UTC-01:00', false),
(20, '2025-06-14', '11:00:00', '13:00:00', 'UTC-01:00', false),
(20, '2025-06-16', '09:00:00', '11:00:00', 'UTC-01:00', true),
(21, '2025-06-11', '08:00:00', '10:00:00', 'UTC+13:00', true),
(21, '2025-06-13', '10:00:00', '12:00:00', 'UTC+13:00', false),
(21, '2025-06-15', '11:00:00', '13:00:00', 'UTC+13:00', false),
(22, '2025-06-12', '19:00:00', '21:00:00', 'UTC-06:00', false),
(22, '2025-06-14', '13:00:00', '15:00:00', 'UTC-06:00', true),
(23, '2025-06-11', '10:00:00', '12:00:00', 'UTC+14:00', true),
(23, '2025-06-13', '12:00:00', '14:00:00', 'UTC+14:00', false),
(23, '2025-06-15', '15:00:00', '17:00:00', 'UTC+14:00', false),
(24, '2025-06-12', '13:00:00', '15:00:00', 'UTC-09:00', false),
(24, '2025-06-14', '14:00:00', '16:00:00', 'UTC-09:00', true),
(24, '2025-06-16', '10:00:00', '12:00:00', 'UTC-09:00', false),
(25, '2025-06-11', '11:00:00', '13:00:00', 'UTC+15:00', false),
(25, '2025-06-13', '10:00:00', '12:00:00', 'UTC+15:00', true),
(25, '2025-06-15', '13:00:00', '15:00:00', 'UTC+15:00', false),
(26, '2025-06-12', '12:00:00', '14:00:00', 'UTC-08:00', true),
(26, '2025-06-14', '14:00:00', '16:00:00', 'UTC-08:00', false),
(26, '2025-06-16', '10:00:00', '12:00:00', 'UTC-08:00', false),
(27, '2025-06-11', '09:00:00', '11:00:00', 'UTC+16:00', false),
(27, '2025-06-15', '11:00:00', '13:00:00', 'UTC+16:00', false),
(28, '2025-06-12', '15:00:00', '17:00:00', 'UTC-07:00', true),
(28, '2025-06-14', '13:00:00', '15:00:00', 'UTC-07:00', false),
(28, '2025-06-16', '16:00:00', '18:00:00', 'UTC-07:00', false),
(29, '2025-06-11', '14:00:00', '16:00:00', 'UTC+17:00', true),
(29, '2025-06-13', '15:00:00', '17:00:00', 'UTC+17:00', false),
(29, '2025-06-15', '17:00:00', '19:00:00', 'UTC+17:00', false),
(30, '2025-06-12', '18:00:00', '20:00:00', 'UTC-10:00', true),
(30, '2025-06-16', '13:00:00', '15:00:00', 'UTC-10:00', false);


INSERT INTO reviews (mentor_id, mentee_id, rating, review_description, response, created_at)
VALUES
(1, 1, 5, 'Excellent mentor! Helped me understand difficult concepts and always provides valuable feedback.', 'Thank you for your kind words! Im glad I could help.', '2025-04-20 14:30:00'),
(19, 19, 4, 'Great session, the mentor was very knowledgeable but could have been more patient.', 'I appreciate the feedback and will work on being more patient. Thanks for sharing!', '2025-04-21 11:00:00'),
(2, 2, 3, 'The mentor was good but didnt provide much actionable advice during the session.', 'Sorry to hear you didnt find the advice helpful. I will make sure to be more focused next time.', '2025-04-19 10:15:00'),
(3, 3, 5, 'I learned so much from this session! The mentor was very clear and easy to understand.', 'Im glad the session was helpful! Looking forward to our next one.', '2025-04-22 16:45:00'),
(4, 4, 2, 'The mentor didnt have enough time to properly address all my questions.', 'I apologize for the rushed session. Ill make sure to manage time better next time.', '2025-04-18 12:30:00'),
(5, 5, 4, 'Good session, but I felt that we could have covered more topics in the time we had.', 'Thank you for your feedback. Ill aim to cover more in future sessions.', '2025-04-15 09:00:00'),
(6, 6, 5, 'Exceptional mentor! Helped me gain a deeper understanding of cloud computing concepts.', 'Im happy I could help you with cloud computing. Keep up the great work!', '2025-04-23 13:20:00'),
(7, 7, 3, 'The session was decent, but I was expecting more in-depth analysis.', 'Thank you for your honest feedback. I will aim to offer more detailed content in the future.', '2025-04-17 08:45:00'),
(8, 8, 5, 'Amazing mentor! Always explains things in a way that makes everything clear and easy to understand.', 'I really appreciate your review! Im happy that the explanations helped.', '2025-04-24 14:10:00'),
(9, 9, 4, 'Good experience overall, but I think we could have worked through more examples together.', 'Thanks for the feedback! Ill include more examples in future sessions.', '2025-04-20 10:00:00'),
(1, 1, 5, 'Excellent mentor! Helped me understand difficult concepts and always provides valuable feedback.', 'Thank you for your kind words! Im glad I could help.', '2025-04-20 14:30:00'),
(19, 19, 4, 'Great session, the mentor was very knowledgeable but could have been more patient.', 'I appreciate the feedback and will work on being more patient. Thanks for sharing!', '2025-04-21 11:00:00'),
(2, 2, 3, 'The mentor was good but didnt provide much actionable advice during the session.', 'Sorry to hear you didnt find the advice helpful. I will make sure to be more focused next time.', '2025-04-19 10:15:00'),
(3, 3, 5, 'I learned so much from this session! The mentor was very clear and easy to understand.', 'Im glad the session was helpful! Looking forward to our next one.', '2025-04-22 16:45:00'),
(4, 4, 2, 'The mentor didnt have enough time to properly address all my questions.', 'I apologize for the rushed session. Ill make sure to manage time better next time.', '2025-04-18 12:30:00'),
(5, 5, 4, 'Good session, but I felt that we could have covered more topics in the time we had.', 'Thank you for your feedback. Ill aim to cover more in future sessions.', '2025-04-15 09:00:00'),
(6, 6, 5, 'Exceptional mentor! Helped me gain a deeper understanding of cloud computing concepts.', 'Im happy I could help you with cloud computing. Keep up the great work!', '2025-04-23 13:20:00'),
(7, 7, 3, 'The session was decent, but I was expecting more in-depth analysis.', 'Thank you for your honest feedback. I will aim to offer more detailed content in the future.', '2025-04-17 08:45:00'),
(8, 8, 5, 'Amazing mentor! Always explains things in a way that makes everything clear and easy to understand.', 'I really appreciate your review! Im happy that the explanations helped.', '2025-04-24 14:10:00'),
(9, 9, 4, 'Good experience overall, but I think we could have worked through more examples together.', 'Thanks for the feedback! Ill include more examples in future sessions.', '2025-04-20 10:00:00'),
(10, 10, 5, 'Perfect! The mentor was very patient and made sure I understood everything thoroughly.', 'Thank you! Im glad I could make the learning process smoother for you.', '2025-04-25 15:50:00'),
(11, 11, 4, 'Great session, but I would have preferred a bit more engagement and discussion.', 'I appreciate your thoughts! I will work on making our sessions more interactive.', '2025-04-19 11:30:00'),
(12, 12, 3, 'The mentor was okay, but I didnt feel as though I got enough practical advice.', 'Ill make sure to include more practical advice in the next session. Thank you for the feedback.', '2025-04-16 17:25:00'),
(13, 13, 5, 'Fantastic session! The mentor was engaging and provided a lot of useful resources.', 'Im glad the resources were helpful. Let me know if you need anything else!', '2025-04-22 12:00:00'),
(14, 14, 4, 'The mentor was helpful but sometimes went off-topic.', 'Ill focus more on staying on track next time. Thanks for the constructive feedback!', '2025-04-14 16:30:00'),
(15, 15, 5, 'Excellent guidance and great advice. Really helped me with my project.', 'Thank you for the kind words! Happy to help!', '2025-04-20 09:15:00'),
(16, 16, 4, 'The mentor was knowledgeable, but I wished for more hands-on guidance.', 'Thanks for the feedback, I will incorporate more hands-on exercises next time.', '2025-04-21 10:20:00'),
(17, 17, 5, 'Great session! The mentor explained things clearly and helped me map out my career path.', 'Glad to hear that! Best of luck with your career!', '2025-04-22 11:30:00'),
(18, 18, 3, 'The session was okay, but I expected more in-depth information on the topic.', 'Thank you for your input! I will work on providing more depth in future sessions.', '2025-04-23 12:00:00'),
(19, 19, 4, 'Mentor provided helpful advice, but there were some technical difficulties.', 'Apologies for the technical issues. Ill make sure to fix them for future sessions.', '2025-04-24 13:10:00'),
(20, 1, 5, 'Absolutely fantastic session! Learned so much about backend development.', 'Happy to hear it! Keep up the good work!', '2025-04-25 14:05:00'),
(1, 19, 5, 'Wonderful session. The mentor was patient and answered all my questions in detail.', 'Thank you! Its great to know the session was helpful.', '2025-04-26 15:00:00'),
(19, 2, 4, 'Session was good, but I would have liked more practical examples.', 'Thanks for the suggestion! I will add more examples next time.', '2025-04-27 16:00:00'),
(2, 3, 3, 'The mentor was helpful but seemed rushed. Would prefer longer sessions.', 'Sorry for the rushed pace, Ill make sure to allocate more time next time.', '2025-04-28 17:00:00'),
(3, 4, 5, 'Amazing insights on data science! Looking forward to applying what I learned.', 'Glad to hear that! Keep experimenting with the new techniques!', '2025-04-29 18:10:00'),
(4, 5, 4, 'Very informative session, but there was a delay at the start.', 'Apologies for the delay. I will make sure to start on time in future.', '2025-04-30 19:20:00'),
(5, 6, 5, 'A fantastic mentor! They helped me understand complex topics with ease.', 'Thanks for the positive feedback! Keep up the good work!', '2025-05-01 20:30:00'),
(6, 7, 2, 'The session did not meet my expectations. Couldnt connect well with the mentor.', 'Sorry to hear that! I will take your feedback into account for future sessions.', '2025-05-02 21:40:00'),
(7, 8, 4, 'Good session, but I felt the mentor could have provided more resources for practice.', 'Thanks for your input! I will share more practice materials next time.', '2025-05-03 22:50:00'),
(8, 9, 5, 'Fantastic session! The mentor made everything so much clearer.', 'Im glad the session helped! Looking forward to your progress.', '2025-05-04 23:00:00'),
(9, 10, 3, 'It was an average session. The mentor was knowledgeable, but I would like to see more interaction.', 'Thank you for the feedback! I will work on making future sessions more interactive.', '2025-05-05 09:10:00'),
(10, 11, 4, 'Great advice, but the session felt a bit too theoretical. Could use some real-world examples.', 'Thank you for the feedback! I will include more examples in the next session.', '2025-05-06 10:20:00'),
(11, 12, 5, 'Incredibly helpful! I feel much more confident in my coding skills now.', 'Im so glad to hear that! Keep practicing, youre doing great!', '2025-05-07 11:30:00'),
(12, 13, 3, 'The mentor was good but not as engaging as I expected.', 'Thanks for sharing your thoughts. Ill work on making the sessions more engaging.', '2025-05-08 12:40:00'),
(13, 14, 4, 'The session was useful, but I would appreciate more focus on practical applications.', 'Thanks for your suggestion! I will work on making the sessions more hands-on.', '2025-05-09 13:50:00'),
(14, 15, 5, 'The mentor was very professional and helped me with real-world examples. Great session.', 'Thanks! Im glad the session was useful for you.', '2025-05-10 14:00:00'),
(15, 16, 4, 'It was a good session, but I felt like the mentor was a bit too focused on theory.', 'Thank you for the feedback! I will try to balance the theoretical aspects with more practical examples.', '2025-05-11 15:10:00'),
(16, 17, 5, 'Excellent session! I learned a lot about cybersecurity in a short amount of time.', 'Im glad to hear that! Keep learning and stay safe online!', '2025-05-12 16:20:00'),
(17, 18, 4, 'Great session, but there was some difficulty understanding the mentors explanations.', 'Sorry for the confusion! I will work on improving my explanations for future sessions.', '2025-05-13 17:30:00');



INSERT INTO appointments (
mentor_id, mentee_id, agenda, duration, status, date, created_at, start_time, end_time, note_by_mentee, reason_by_mentor_if_rejecting, review_id
) VALUES
(5, 1, 'Career guidance session', 30, 'COMPLETED', '2025-04-26', '2025-05-26 10:00:00', '10:00:00', '10:30:00', 'Great insights on career paths.', NULL, NULL),
(10, 1, 'Technical interview preparation', 45, 'COMPLETED', '2025-04-25', '2025-05-22 14:30:00', '14:30:00', '15:15:00', 'Received valuable tips on interview techniques.', NULL, NULL),
(3, 2, 'Project management strategies', 60, 'COMPLETED', '2025-04-25', '2025-05-22 09:00:00', '09:00:00', '10:00:00', 'Discussed effective project planning methods.', NULL, NULL),
(7, 2, 'Time management techniques', 30, 'COMPLETED', '2025-04-25', '2025-05-23 11:00:00', '11:00:00', '11:30:00', 'Learned about prioritization and scheduling.', NULL, NULL),
(12, 3, 'Resume building workshop', 90, 'COMPLETED', '2025-02-25', '2025-04-20 13:00:00', '13:00:00', '14:30:00', 'Assisted in crafting a compelling resume.', NULL, NULL),
(15, 4, 'Networking skills development', 45, 'COMPLETED', '2025-02-25', '2025-03-31 15:30:00', '15:30:00', '16:15:00', 'Enhanced networking strategies for career growth.', NULL, NULL),
(20, 5, 'Leadership qualities discussion', 60, 'COMPLETED', '2025-02-25', '2025-04-01 16:00:00', '16:00:00', '17:00:00', 'Explored key traits of effective leaders.', NULL, NULL),
(25, 6, 'Public speaking practice session', 30, 'COMPLETED', '2025-02-25', '2025-04-02 17:00:00', '17:00:00', '17:30:00', 'Improved confidence in public speaking.', NULL, NULL),
(30, 7, 'Conflict resolution techniques', 45, 'COMPLETED', '2025-02-25', '2025-04-03 18:00:00', '18:00:00', '18:45:00', 'Learned methods to handle workplace conflicts.', NULL, NULL),
(4, 8, 'Career transition advice', 60, 'COMPLETED', '2025-02-25', '2025-04-04 19:00:00', '19:00:00', '20:00:00', 'Guidance on transitioning to a new career path.', NULL, NULL),
(9, 9, 'Entrepreneurship basics', 30, 'COMPLETED', '2025-02-25', '2025-04-05 20:00:00', '20:00:00', '20:30:00', 'Introduced fundamental concepts of entrepreneurship.', NULL, NULL),
(14, 10, 'Work-life balance strategies', 45, 'COMPLETED', '2025-02-25', '2025-04-06 21:00:00', '21:00:00', '21:45:00', 'Discussed techniques to maintain work-life balance.', NULL, NULL),
(19, 11, 'Financial planning for professionals', 60, 'COMPLETED', '2025-02-25', '2025-04-07 22:00:00', '22:00:00', '23:00:00', 'Provided tips on managing personal finances.', NULL, NULL),
(24, 12, 'Career advancement planning', 30, 'COMPLETED', '2025-02-25', '2025-04-08 23:00:00', '23:00:00', '23:30:00', 'Assisted in creating a roadmap for career growth.', NULL, NULL),
(29, 13, 'Stress management techniques', 45, 'COMPLETED', '2025-02-25', '2025-04-09 10:00:00', '10:00:00', '10:45:00', 'Learned methods to cope with workplace stress.', NULL, NULL),
(2, 14, 'Effective communication skills', 60, 'COMPLETED', '2025-02-25', '2025-04-10 11:00:00', '11:00:00', '12:00:00', 'Enhanced verbal and non-verbal communication skills.', NULL, NULL),
(6, 15, 'Team collaboration strategies', 30, 'COMPLETED', '2025-02-25', '2025-04-11 12:00:00', '12:00:00', '12:30:00', 'Discussed ways to improve team collaboration.', NULL, NULL),
(11, 16, 'Career goal setting', 45, 'COMPLETED', '2025-02-25', '2025-04-12 13:00:00', '13:00:00', '13:45:00', 'Assisted in setting achievable career goals.', NULL, NULL),
(16, 17, 'Interviewing techniques workshop', 60, 'COMPLETED', '2025-02-25', '2025-04-13 14:00:00', '14:00:00', '15:00:00', 'Provided insights into effective interviewing techniques.', NULL, NULL),
(21, 18, 'Professional branding strategies', 30, 'COMPLETED', '2025-02-25', '2025-04-14 15:00:00', '15:00:00', '15:30:00', 'Guidance on building a strong professional brand.', NULL, NULL),
(8, 1, 'Follow-up on career goals', 30, 'COMPLETED', '2025-02-25', '2025-04-15 09:00:00', '09:00:00', '09:30:00', NULL, NULL, NULL),
(13, 2, 'Feedback on personal branding', 45, 'COMPLETED', '2025-02-25', '2025-04-16 10:30:00', '10:30:00', '11:15:00', 'Got great branding tips!', NULL, NULL),
(18, 3, 'Scholarship application review', 60, 'COMPLETED', '2025-02-25', '2025-04-17 11:00:00', '11:00:00', '12:00:00', NULL, NULL, NULL),
(23, 4, 'Discussing coding bootcamps', 30, 'COMPLETED', '2025-02-25', '2025-04-18 14:00:00', '14:00:00', '14:30:00', 'Helpful insights on bootcamps.', NULL, NULL),
(28, 5, 'Leadership workshop', 60, 'COMPLETED', '2025-02-25', '2025-04-19 15:30:00', '15:30:00', '16:30:00', NULL, NULL, NULL),
(1, 6, 'Graduate school advice', 90, 'COMPLETED', '2025-02-25', '2025-04-20 16:00:00', '16:00:00', '17:30:00', 'Helpful for planning applications.', NULL, NULL),
(6, 7, 'UX/UI design feedback session', 45, 'COMPLETED', '2025-02-25', '2025-04-21 17:00:00', '17:00:00', '17:45:00', 'Got clear UI/UX improvement tips.', NULL, NULL),
(11, 8, 'Mock interview session', 30, 'COMPLETED', '2025-02-25', '2025-04-22 18:00:00', '18:00:00', '18:30:00', 'Good practice session.', NULL, NULL),
(17, 9, 'Clarifying career doubts', 45, 'COMPLETED', '2025-02-25', '2025-04-23 19:00:00', '19:00:00', '19:45:00', NULL, NULL, NULL),
(22, 10, 'Internship search strategies', 60, 'COMPLETED', '2025-02-25', '2025-04-24 20:00:00', '20:00:00', '21:00:00', 'Mentor shared great job boards.', NULL, NULL),
(27, 11, 'Remote work readiness', 30, 'COMPLETED', '2025-02-25', '2025-04-25 21:00:00', '21:00:00', '21:30:00', 'Learned how to stay productive remotely.', NULL, NULL),
(3, 12, 'Advanced data analytics', 60, 'COMPLETED', '2025-02-25', '2025-04-26 09:30:00', '09:30:00', '10:30:00', NULL, NULL, NULL),
(7, 13, 'Career restart after break', 45, 'COMPLETED', '2025-02-25', '2025-04-27 10:00:00', '10:00:00', '10:45:00', 'Encouraging and useful session.', NULL, NULL),
(12, 14, 'Time blocking methods', 30, 'COMPLETED', '2025-02-25', '2025-04-28 11:30:00', '11:30:00', '12:00:00', 'Started applying it immediately.', NULL, NULL),
(16, 15, 'Technical resume improvement', 45, 'COMPLETED', '2025-02-25', '2025-04-29 12:00:00', '12:00:00', '12:45:00', 'Feedback was very precise.', NULL, NULL),
(21, 16, 'Discussion on side projects', 60, 'COMPLETED', '2025-02-25', '2025-04-30 13:00:00', '13:00:00', '14:00:00', 'Helped in prioritizing project ideas.', NULL, NULL),
(26, 17, 'Help with LinkedIn optimization', 30, 'COMPLETED', '2025-02-25', '2025-05-01 14:00:00', '14:00:00', '14:30:00', 'Now my profile stands out.', NULL, NULL),
(2, 18, 'How to ask for promotions', 45, 'COMPLETED', '2025-02-25', '2025-05-02 15:00:00', '15:00:00', '15:45:00', 'Confident after this session.', NULL, NULL),
(5, 19, 'Product management overview', 60, 'COMPLETED', '2025-02-25', '2025-05-03 16:00:00', '16:00:00', '17:00:00', 'Gained valuable product insights.', NULL, NULL),
(10, 20, 'Revisiting annual goals', 30, 'COMPLETED', '2025-02-25', '2025-05-04 17:00:00', '17:00:00', '17:30:00', NULL, NULL, NULL),
(1, 1, 'Career guidance session', 30, 'COMPLETED', '2025-04-28', '2025-03-28 10:00:00', '10:00:00', '10:30:00', 'Very helpful session.', NULL, NULL),
(2, 2, 'Technical interview preparation', 45, 'COMPLETED', '2025-04-25', '2025-03-25 09:30:00', '09:30:00', '10:15:00', 'Gained new perspective.', NULL, NULL),
(3, 3, 'Project management strategies', 60, 'COMPLETED', '2025-04-22', '2025-03-22 14:00:00', '14:00:00', '15:00:00', 'Mentor provided great tips.', NULL, NULL),
(4, 4, 'Resume building workshop', 90, 'COMPLETED', '2025-04-19', '2025-03-19 11:00:00', '11:00:00', '12:30:00', 'Will implement the suggestions.', NULL, NULL),
(5, 5, 'Networking skills development', 45, 'COMPLETED', '2025-04-15', '2025-03-15 15:00:00', '15:00:00', '15:45:00', 'Clarified many doubts.', NULL, NULL),
(6, 6, 'Time management techniques', 30, 'COMPLETED', '2025-03-30', '2025-02-28 10:30:00', '10:30:00', '11:00:00', 'Great experience overall.', NULL, NULL),
(7, 7, 'Public speaking practice session', 45, 'COMPLETED', '2025-03-27', '2025-02-25 13:00:00', '13:00:00', '13:45:00', 'Mentor was very knowledgeable.', NULL, NULL),
(8, 8, 'Leadership qualities discussion', 60, 'COMPLETED', '2025-03-23', '2025-02-21 16:00:00', '16:00:00', '17:00:00', 'Highly recommend this mentor.', NULL, NULL),
(9, 9, 'Entrepreneurship basics', 30, 'COMPLETED', '2025-03-19', '2025-02-18 09:00:00', '09:00:00', '09:30:00', 'Got actionable advice.', NULL, NULL),
(10, 10, 'Mock interview session', 45, 'COMPLETED', '2025-03-15', '2025-02-14 10:00:00', '10:00:00', '10:45:00', 'Learned something new.', NULL, NULL),
(11, 11, 'Career transition planning', 60, 'COMPLETED', '2025-02-28', '2025-01-28 11:00:00', '11:00:00', '12:00:00', 'Very practical advice.', NULL, NULL),
(12, 12, 'Time blocking workshop', 30, 'COMPLETED', '2025-02-24', '2025-01-24 12:00:00', '12:00:00', '12:30:00', 'Easy to follow framework.', NULL, NULL),
(13, 13, 'Work-life balance coaching', 45, 'COMPLETED', '2025-02-20', '2025-01-20 15:00:00', '15:00:00', '15:45:00', 'Insightful discussion.', NULL, NULL),
(14, 14, 'Effective communication', 60, 'COMPLETED', '2025-02-15', '2025-01-15 14:00:00', '14:00:00', '15:00:00', 'Clear and concise feedback.', NULL, NULL),
(15, 15, 'Professional branding', 30, 'COMPLETED', '2025-02-10', '2025-01-10 10:00:00', '10:00:00', '10:30:00', 'Helped refine my profile.', NULL, NULL),
(1, 3, 'Career planning for graduates', 45, 'COMPLETED', '2025-05-03', '2025-05-01 10:00:00', '10:00:00', '10:45:00', 'Got clarity on job search timeline.', NULL, NULL),
(4, 6, 'Technical interview tips', 30, 'COMPLETED', '2025-05-05', '2025-05-02 11:00:00', '11:00:00', '11:30:00', 'Simple and effective strategies.', NULL, NULL),
(2, 5, 'Resume and LinkedIn profile review', 60, 'COMPLETED', '2025-05-08', '2025-05-03 14:00:00', '14:00:00', '15:00:00', 'Profile looks much more professional now.', NULL, NULL),
(5, 8, 'Handling workplace challenges', 30, 'COMPLETED', '2025-05-10', '2025-05-04 09:00:00', '09:00:00', '09:30:00', 'Really good practical suggestions.', NULL, NULL),
(3, 2, 'Intro to product management', 45, 'COMPLETED', '2025-05-12', '2025-05-05 16:00:00', '16:00:00', '16:45:00', 'Excited to explore PM further.', NULL, NULL),
(6, 7, 'Time management tools demo', 30, 'COMPLETED', '2025-05-15', '2025-05-06 13:00:00', '13:00:00', '13:30:00', 'Trying out the suggested tools.', NULL, NULL),
(8, 1, 'Finding your first internship', 60, 'COMPLETED', '2025-05-18', '2025-05-08 10:00:00', '10:00:00', '11:00:00', 'Mentor shared a great job board list.', NULL, NULL),
(9, 4, 'Public speaking for shy professionals', 45, 'COMPLETED', '2025-05-20', '2025-05-10 15:30:00', '15:30:00', '16:15:00', 'I feel more confident already.', NULL, NULL),
(7, 9, 'Effective email communication', 30, 'COMPLETED', '2025-05-23', '2025-05-12 12:00:00', '12:00:00', '12:30:00', 'Now writing better messages at work.', NULL, NULL),
(10, 10, 'Setting short-term career goals', 60, 'COMPLETED', '2025-05-25', '2025-05-13 17:00:00', '17:00:00', '18:00:00', 'Helpful goal-setting framework.', NULL, NULL),
(2, 3, 'Career development planning', 45, 'COMPLETED', '2025-01-05', '2024-12-28 09:00:00', '09:00:00', '09:45:00', 'Valuable career advice.', NULL, NULL),
(4, 1, 'Technical skills upgrade', 60, 'COMPLETED', '2025-01-08', '2024-12-30 14:00:00', '14:00:00', '15:00:00', 'Learned new coding techniques.', NULL, NULL),
(1, 5, 'Networking strategies', 30, 'COMPLETED', '2025-01-10', '2024-12-31 10:30:00', '10:30:00', '11:00:00', 'Great tips on expanding network.', NULL, NULL),
(3, 4, 'Resume and portfolio review', 45, 'COMPLETED', '2025-01-12', '2025-01-02 13:00:00', '13:00:00', '13:45:00', 'Improved my resume significantly.', NULL, NULL),
(5, 2, 'Interview preparation', 60, 'COMPLETED', '2025-01-15', '2025-01-03 16:00:00', '16:00:00', '17:00:00', 'Felt confident after the session.', NULL, NULL),
(6, 6, 'Time management techniques', 30, 'COMPLETED', '2025-01-18', '2025-01-04 09:00:00', '09:00:00', '09:30:00', 'Useful time-blocking strategies.', NULL, NULL),
(7, 7, 'Public speaking workshop', 45, 'COMPLETED', '2025-01-20', '2025-01-05 11:00:00', '11:00:00', '11:45:00', 'Boosted my confidence.', NULL, NULL),
(8, 8, 'Leadership skills development', 60, 'COMPLETED', '2025-01-22', '2025-01-06 14:00:00', '14:00:00', '15:00:00', 'Great insights on leadership.', NULL, NULL),
(3, 2, 'End-of-year career review', 45, 'COMPLETED', '2024-12-03', '2024-11-25 09:00:00', '09:00:00', '09:45:00', 'Helpful summary of my progress.', NULL, NULL),
(5, 4, 'Skill gap analysis', 60, 'COMPLETED', '2024-12-06', '2024-11-26 14:00:00', '14:00:00', '15:00:00', 'Identified key areas to improve.', NULL, NULL),
(1, 3, 'Networking tips for the holidays', 30, 'COMPLETED', '2024-12-08', '2024-11-27 10:30:00', '10:30:00', '11:00:00', 'Great ideas for holiday networking.', NULL, NULL),
(4, 5, 'Resume update strategies', 45, 'COMPLETED', '2024-12-10', '2024-11-28 13:00:00', '13:00:00', '13:45:00', 'Made my resume more attractive.', NULL, NULL),
(2, 6, 'Interview preparation session', 60, 'COMPLETED', '2024-12-13', '2024-11-29 16:00:00', '16:00:00', '17:00:00', 'Felt more confident.', NULL, NULL),
(6, 7, 'Time management for end of year', 30, 'COMPLETED', '2024-12-16', '2024-11-30 09:00:00', '09:00:00', '09:30:00', 'Helped me plan my schedule.', NULL, NULL),
(7, 8, 'Public speaking tips', 45, 'COMPLETED', '2024-12-18', '2024-12-01 11:00:00', '11:00:00', '11:45:00', 'More confident speaking now.', NULL, NULL),
(8, 9, 'Leadership skills review', 60, 'COMPLETED', '2024-12-20', '2024-12-02 14:00:00', '14:00:00', '15:00:00', 'Understood how to lead better.', NULL, NULL),
(9, 10, 'Work-life balance strategies', 30, 'COMPLETED', '2024-12-23', '2024-12-03 10:00:00', '10:00:00', '10:30:00', 'Great advice on managing time.', NULL, NULL),
(10, 1, 'Professional branding workshop', 45, 'COMPLETED', '2024-12-26', '2024-12-04 13:00:00', '13:00:00', '13:45:00', 'Improved my LinkedIn profile.', NULL, NULL),
(11, 2, 'Goal setting for new year', 60, 'COMPLETED', '2024-12-28', '2024-12-05 10:00:00', '10:00:00', '11:00:00', 'Ready to tackle new challenges.', NULL, NULL),
(12, 3, 'Stress management techniques', 45, 'COMPLETED', '2024-12-29', '2024-12-06 14:00:00', '14:00:00', '14:45:00', 'Feeling more relaxed after session.', NULL, NULL),
(13, 4, 'Effective communication skills', 30, 'COMPLETED', '2024-12-30', '2024-12-07 09:30:00', '09:30:00', '10:00:00', 'Better at expressing my ideas.', NULL, NULL),
(3, 1, 'Job search strategies', 45, 'COMPLETED', '2024-11-04', '2024-10-28 09:00:00', '09:00:00', '09:45:00', 'Got actionable tips.', NULL, NULL),
(5, 2, 'Building professional network', 60, 'COMPLETED', '2024-11-07', '2024-10-29 14:00:00', '14:00:00', '15:00:00', 'Networking advice was excellent.', NULL, NULL),
(1, 3, 'Improving interview skills', 30, 'COMPLETED', '2024-11-09', '2024-10-30 10:30:00', '10:30:00', '11:00:00', 'Helpful mock interviews.', NULL, NULL),
(4, 4, 'Resume writing workshop', 45, 'COMPLETED', '2024-11-11', '2024-10-31 13:00:00', '13:00:00', '13:45:00', 'Resume looks much better now.', NULL, NULL),
(2, 5, 'Setting career goals', 60, 'COMPLETED', '2024-11-14', '2024-11-01 16:00:00', '16:00:00', '17:00:00', 'Clear direction established.', NULL, NULL),
(6, 6, 'Time management skills', 30, 'COMPLETED', '2024-11-17', '2024-11-02 09:00:00', '09:00:00', '09:30:00', 'Practical time-blocking techniques.', NULL, NULL),
(7, 7, 'Public speaking confidence', 45, 'COMPLETED', '2024-11-19', '2024-11-03 11:00:00', '11:00:00', '11:45:00', 'More confident on stage.', NULL, NULL),
(8, 8, 'Leadership skills overview', 60, 'COMPLETED', '2024-11-21', '2024-11-04 14:00:00', '14:00:00', '15:00:00', 'Better understanding of leadership.', NULL, NULL),
(9, 9, 'Work-life balance tips', 30, 'COMPLETED', '2024-11-24', '2024-11-05 10:00:00', '10:00:00', '10:30:00', 'Learned how to manage stress.', NULL, NULL),
(7, 6, 'Resume review and feedback', 45, 'COMPLETED', '2025-06-05', '2025-06-01 08:30:00', '08:30:00', '09:15:00', 'Good insights shared.', NULL, NULL),
(28, 14, 'Mock interview', 30, 'COMPLETED', '2025-06-05', '2025-06-01 10:00:00', '10:00:00', '10:30:00', 'Learned a lot.', NULL, NULL),
(13, 2, 'Leadership training', 60, 'COMPLETED', '2025-06-05', '2025-06-02 11:45:00', '11:45:00', '12:45:00', 'Will book another session soon.', NULL, NULL),
(4, 9, 'Skill development discussion', 30, 'COMPLETED', '2025-06-05', '2025-06-02 14:00:00', '14:00:00', '14:30:00', 'Mentor was engaging.', NULL, NULL),
(17, 7, 'Career guidance session', 30, 'COMPLETED', '2025-06-05', '2025-06-03 15:15:00', '15:15:00', '15:45:00', 'Very helpful session.', NULL, NULL),
(21, 12, 'Soft skills enhancement', 60, 'COMPLETED', '2025-06-05', '2025-06-02 16:30:00', '16:30:00', '17:30:00', 'Got answers to all my questions.', NULL, NULL),
(8, 18, 'Mentorship check-in', 45, 'COMPLETED', '2025-06-04', '2025-05-31 09:30:00', '09:30:00', '10:15:00', 'Good insights shared.', NULL, NULL),
(2, 1, 'Resume review and feedback', 30, 'COMPLETED', '2025-06-04', '2025-05-30 10:15:00', '10:15:00', '10:45:00', 'Learned a lot.', NULL, NULL),
(25, 15, 'Technical interview preparation', 60, 'COMPLETED', '2025-06-04', '2025-06-01 11:00:00', '11:00:00', '12:00:00', 'Mentor was engaging.', NULL, NULL),
(6, 4, 'Industry trends overview', 45, 'COMPLETED', '2025-06-04', '2025-05-30 13:45:00', '13:45:00', '14:30:00', 'Very helpful session.', NULL, NULL),
(20, 17, 'Soft skills enhancement', 30, 'COMPLETED', '2025-06-04', '2025-06-01 15:00:00', '15:00:00', '15:30:00', 'Will book another session soon.', NULL, NULL),
(11, 11, 'Leadership training', 60, 'COMPLETED', '2025-06-04', '2025-05-30 16:30:00', '16:30:00', '17:30:00', 'Got answers to all my questions.', NULL, NULL),
(19, 10, 'Career guidance session', 30, 'COMPLETED', '2025-06-03', '2025-05-29 09:00:00', '09:00:00', '09:30:00', 'Good insights shared.', NULL, NULL),
(9, 5, 'Skill development discussion', 45, 'COMPLETED', '2025-06-03', '2025-05-29 10:30:00', '10:30:00', '11:15:00', 'Very helpful session.', NULL, NULL),
(30, 3, 'Project management strategies', 60, 'COMPLETED', '2025-06-03', '2025-05-29 13:00:00', '13:00:00', '14:00:00', 'Learned a lot.', NULL, NULL),
(16, 13, 'Mock interview', 30, 'COMPLETED', '2025-06-03', '2025-05-28 15:15:00', '15:15:00', '15:45:00', 'Mentor was engaging.', NULL, NULL),
(5, 6, 'Technical interview preparation', 45, 'COMPLETED', '2025-06-03', '2025-05-28 16:30:00', '16:30:00', '17:15:00', 'Very helpful session.', NULL, NULL),
(3, 19, 'Resume review and feedback', 60, 'COMPLETED', '2025-06-02', '2025-05-27 09:15:00', '09:15:00', '10:15:00', 'Got answers to all my questions.', NULL, NULL),
(14, 8, 'Career guidance session', 30, 'COMPLETED', '2025-06-02', '2025-05-28 11:00:00', '11:00:00', '11:30:00', 'Learned a lot.', NULL, NULL),
(18, 2, 'Mentorship check-in', 30, 'COMPLETED', '2025-06-02', '2025-05-27 12:45:00', '12:45:00', '13:15:00', 'Will book another session soon.', NULL, NULL),
(27, 16, 'Leadership training', 45, 'COMPLETED', '2025-06-02', '2025-05-28 14:00:00', '14:00:00', '14:45:00', 'Good insights shared.', NULL, NULL),
(1, 20, 'Industry trends overview', 60, 'COMPLETED', '2025-06-02', '2025-05-27 15:30:00', '15:30:00', '16:30:00', 'Very helpful session.', NULL, NULL),
(12, 7, 'Soft skills enhancement', 45, 'COMPLETED', '2025-06-01', '2025-05-26 09:30:00', '09:30:00', '10:15:00', 'Mentor was engaging.', NULL, NULL),
(22, 14, 'Mock interview', 30, 'COMPLETED', '2025-06-01', '2025-05-27 11:00:00', '11:00:00', '11:30:00', 'Good insights shared.', NULL, NULL),
(10, 9, 'Technical interview preparation', 60, 'COMPLETED', '2025-06-01', '2025-05-27 13:00:00', '13:00:00', '14:00:00', 'Very helpful session.', NULL, NULL),
(26, 4, 'Project management strategies', 45, 'COMPLETED', '2025-06-01', '2025-05-27 14:30:00', '14:30:00', '15:15:00', 'Learned a lot.', NULL, NULL),
(23, 1, 'Career guidance session', 30, 'COMPLETED', '2025-06-01', '2025-05-27 16:00:00', '16:00:00', '16:30:00', 'Got answers to all my questions.', NULL, NULL),
(24, 5, 'Skill development discussion', 30, 'COMPLETED', '2025-05-31', '2025-05-26 08:30:00', '08:30:00', '09:00:00', 'Learned a lot.', NULL, NULL),
(15, 11, 'Resume review and feedback', 45, 'COMPLETED', '2025-05-31', '2025-05-27 10:00:00', '10:00:00', '10:45:00', 'Mentor was engaging.', NULL, NULL),
(13, 17, 'Career guidance session', 60, 'COMPLETED', '2025-05-31', '2025-05-26 11:15:00', '11:15:00', '12:15:00', 'Very helpful session.', NULL, NULL),
(6, 2, 'Soft skills enhancement', 30, 'COMPLETED', '2025-05-31', '2025-05-27 13:00:00', '13:00:00', '13:30:00', 'Good insights shared.', NULL, NULL),
(30, 8, 'Mock interview', 60, 'COMPLETED', '2025-05-31', '2025-05-27 14:30:00', '14:30:00', '15:30:00', 'Got answers to all my questions.', NULL, NULL),
(19, 4, 'Mentorship check-in', 45, 'COMPLETED', '2025-05-30', '2025-05-25 09:30:00', '09:30:00', '10:15:00', 'Will book another session soon.', NULL, NULL),
(3, 7, 'Technical interview preparation', 30, 'COMPLETED', '2025-05-30', '2025-05-26 11:00:00', '11:00:00', '11:30:00', 'Learned a lot.', NULL, NULL),
(10, 13, 'Leadership training', 60, 'COMPLETED', '2025-05-30', '2025-05-26 13:00:00', '13:00:00', '14:00:00', 'Very helpful session.', NULL, NULL),
(28, 16, 'Industry trends overview', 45, 'COMPLETED', '2025-05-30', '2025-05-26 14:30:00', '14:30:00', '15:15:00', 'Mentor was engaging.', NULL, NULL),
(2, 20, 'Career guidance session', 30, 'COMPLETED', '2025-05-30', '2025-05-26 16:00:00', '16:00:00', '16:30:00', 'Good insights shared.', NULL, NULL),
(11, 10, 'Resume review and feedback', 60, 'COMPLETED', '2025-05-29', '2025-05-24 09:00:00', '09:00:00', '10:00:00', 'Got answers to all my questions.', NULL, NULL),
(21, 18, 'Mock interview', 45, 'COMPLETED', '2025-05-29', '2025-05-25 10:30:00', '10:30:00', '11:15:00', 'Very helpful session.', NULL, NULL),
(5, 6, 'Project management strategies', 30, 'COMPLETED', '2025-05-29', '2025-05-24 12:00:00', '12:00:00', '12:30:00', 'Mentor was engaging.', NULL, NULL),
(27, 9, 'Skill development discussion', 60, 'COMPLETED', '2025-05-29', '2025-05-25 13:30:00', '13:30:00', '14:30:00', 'Learned a lot.', NULL, NULL),
(12, 15, 'Leadership training', 45, 'COMPLETED', '2025-05-29', '2025-05-25 15:00:00', '15:00:00', '15:45:00', 'Good insights shared.', NULL, NULL),
(9, 3, 'Career guidance session', 30, 'COMPLETED', '2025-05-28', '2025-05-23 08:45:00', '08:45:00', '09:15:00', 'Learned a lot.', NULL, NULL),
(14, 5, 'Mentorship check-in', 60, 'COMPLETED', '2025-05-28', '2025-05-24 10:00:00', '10:00:00', '11:00:00', 'Very helpful session.', NULL, NULL),
(16, 12, 'Technical interview preparation', 45, 'COMPLETED', '2025-05-28', '2025-05-24 11:30:00', '11:30:00', '12:15:00', 'Good insights shared.', NULL, NULL),
(18, 19, 'Soft skills enhancement', 30, 'COMPLETED', '2025-05-28', '2025-05-24 13:00:00', '13:00:00', '13:30:00', 'Got answers to all my questions.', NULL, NULL),
(7, 1, 'Project management strategies', 60, 'COMPLETED', '2025-05-28', '2025-05-24 14:30:00', '14:30:00', '15:30:00', 'Will book another session soon.', NULL, NULL),
(26, 17, 'Career guidance session', 30, 'COMPLETED', '2025-05-27', '2025-05-23 09:15:00', '09:15:00', '09:45:00', 'Mentor was engaging.', NULL, NULL),
(1, 8, 'Skill development discussion', 60, 'COMPLETED', '2025-05-27', '2025-05-23 10:30:00', '10:30:00', '11:30:00', 'Learned a lot.', NULL, NULL),
(29, 14, 'Resume review and feedback', 45, 'COMPLETED', '2025-05-27', '2025-05-23 12:00:00', '12:00:00', '12:45:00', 'Good insights shared.', NULL, NULL),
(22, 7, 'Mentorship check-in', 30, 'COMPLETED', '2025-05-27', '2025-05-23 14:00:00', '14:00:00', '14:30:00', 'Very helpful session.', NULL, NULL),
(20, 16, 'Leadership training', 60, 'COMPLETED', '2025-05-27', '2025-05-23 15:30:00', '15:30:00', '16:30:00', 'Got answers to all my questions.', NULL, NULL),
(8, 3, 'Career guidance session', 30, 'COMPLETED', '2025-06-03', '2025-06-01 09:00:00', '09:00:00', '09:30:00', 'Very helpful session.', NULL, NULL),
(14, 12, 'Technical interview preparation', 45, 'COMPLETED', '2025-06-03', '2025-06-01 10:15:00', '10:15:00', '11:00:00', 'Good interview tips.', NULL, NULL),
(25, 7, 'Mock interview', 60, 'COMPLETED', '2025-06-03', '2025-06-02 14:00:00', '14:00:00', '15:00:00', 'Got great feedback.', NULL, NULL),
(5, 18, 'Skill development discussion', 30, 'COMPLETED', '2025-06-03', '2025-06-01 15:30:00', '15:30:00', '16:00:00', 'Learned a lot.', NULL, NULL),
(11, 6, 'Project management strategies', 45, 'COMPLETED', '2025-06-03', '2025-06-02 11:00:00', '11:00:00', '11:45:00', 'Very insightful.', NULL, NULL),
(3, 10, 'Resume review and feedback', 60, 'COMPLETED', '2025-06-04', '2025-06-02 09:30:00', '09:30:00', '10:30:00', 'Helpful feedback.', NULL, NULL),
(20, 15, 'Leadership training', 45, 'COMPLETED', '2025-06-04', '2025-06-02 11:00:00', '11:00:00', '11:45:00', 'Engaging session.', NULL, NULL),
(7, 2, 'Mentorship check-in', 30, 'COMPLETED', '2025-06-04', '2025-06-03 12:00:00', '12:00:00', '12:30:00', 'Great advice.', NULL, NULL),
(28, 4, 'Soft skills enhancement', 60, 'COMPLETED', '2025-06-04', '2025-06-02 13:30:00', '13:30:00', '14:30:00', 'Learned new skills.', NULL, NULL),
(17, 9, 'Industry trends overview', 45, 'COMPLETED', '2025-06-04', '2025-06-03 15:00:00', '15:00:00', '15:45:00', 'Very informative.', NULL, NULL),
(19, 14, 'Career guidance session', 30, 'COMPLETED', '2025-06-05', '2025-06-03 08:30:00', '08:30:00', '09:00:00', 'Very motivating.', NULL, NULL),
(13, 1, 'Technical interview preparation', 60, 'COMPLETED', '2025-06-05', '2025-06-04 09:45:00', '09:45:00', '10:45:00', 'Great tips.', NULL, NULL),
(9, 20, 'Mock interview', 45, 'COMPLETED', '2025-06-05', '2025-06-04 11:30:00', '11:30:00', '12:15:00', 'Useful feedback.', NULL, NULL),
(21, 5, 'Skill development discussion', 30, 'COMPLETED', '2025-06-05', '2025-06-04 13:00:00', '13:00:00', '13:30:00', 'Good session.', NULL, NULL),
(26, 11, 'Resume review and feedback', 45, 'COMPLETED', '2025-06-05', '2025-06-04 14:15:00', '14:15:00', '15:00:00', 'Helpful pointers.', NULL, NULL);


INSERT INTO reports (
user_id, user_type, category_of_violation, explanation, created_at, status
) VALUES
(1, 'MENTEE', 'UNPROFESSIONAL_BEHAVIOR', 'Mentor interrupted frequently and was dismissive.', '2025-04-01 10:00:00', 'PENDING'),
(2, 'MENTOR', 'LACK_OF_RESPONSE', 'Mentee did not reply to scheduling requests.', '2025-04-02 11:15:00', 'IMPLEMENTED'),
(3, 'MENTEE', 'FAILURE_TO_CONFIRM', 'Mentor failed to confirm the session despite reminders.', '2025-04-03 09:45:00', 'PENDING'),
(4, 'MENTOR', 'BREACH_OF_PRIVACY', 'Mentee shared screenshots of private session.', '2025-04-04 14:30:00', 'WORKING_ON'),
(5, 'MENTEE', 'FAKE_REVIEWS', 'Mentor appears to have written their own reviews.', '2025-04-05 13:10:00', 'PENDING'),
(6, 'MENTOR', 'MISLEADING_INFORMATION', 'Mentee provided false details about background.', '2025-04-06 12:00:00', 'WORKING_ON'),
(7, 'MENTEE', 'BUG_REPORT', 'Report button shows error on submission.', '2025-04-07 15:20:00', 'WORKING_ON'),
(8, 'MENTOR', 'OTHER', 'Inappropriate background noise during call.', '2025-04-08 16:10:00', 'PENDING'),
(9, 'MENTEE', 'UNPROFESSIONAL_BEHAVIOR', 'Mentor made sarcastic remarks during feedback.', '2025-04-09 11:40:00', 'IMPLEMENTED'),
(10, 'MENTOR', 'LACK_OF_RESPONSE', 'Mentee failed to provide required documents.', '2025-04-10 17:30:00', 'PENDING'),
(11, 'MENTEE', 'FAILURE_TO_CONFIRM', 'Mentor didnt show up or respond.', '2025-04-11 18:00:00', 'WORKING_ON'),
(12, 'MENTOR', 'BREACH_OF_PRIVACY', 'Mentee shared private chat content on social media.', '2025-04-12 09:00:00', 'PENDING'),
(13, 'MENTEE', 'FAKE_REVIEWS', 'Reviews seem coordinated and unauthentic.', '2025-04-13 14:00:00', 'IMPLEMENTED'),
(14, 'MENTOR', 'MISLEADING_INFORMATION', 'Mentee misrepresented work experience.', '2025-04-14 16:30:00', 'PENDING'),
(15, 'MENTEE', 'BUG_REPORT', 'App crashed when trying to cancel a session.', '2025-04-15 10:30:00', 'IMPLEMENTED'),
(16, 'MENTOR', 'OTHER', 'Session was hijacked by third party.', '2025-04-16 11:15:00', 'PENDING'),
(17, 'MENTEE', 'UNPROFESSIONAL_BEHAVIOR', 'Mentor acted disinterested and rushed.', '2025-04-17 12:45:00', 'WORKING_ON'),
(18, 'MENTOR', 'LACK_OF_RESPONSE', 'Mentee went silent after agreeing to meet.', '2025-04-18 13:50:00', 'IMPLEMENTED'),
(19, 'MENTEE', 'FAILURE_TO_CONFIRM', 'Mentor ignored all booking attempts.', '2025-04-19 14:55:00', 'PENDING'),
(20, 'MENTOR', 'BUG_REPORT', 'Platform did not load the session history.', '2025-04-20 15:20:00', 'IMPLEMENTED');


INSERT INTO suggestions (
user_id, user_type, suggestions_type, description, status, note, created_at
) VALUES
(1, 'MENTEE', 'FEATURE_REQUEST', 'Add option to bookmark favorite mentors.', 'PENDING', NULL, '2025-04-01 09:00:00'),
(2, 'MENTEE', 'UI_IMPROVEMENT', 'Simplify the dashboard layout for quicker access.', 'REVIEWED', 'Being evaluated by design team.', '2025-04-02 10:30:00'),
(3, 'MENTEE', 'PERFORMANCE_IMPROVEMENT', 'App takes too long to load session history.', 'IMPLEMENTED', 'Optimized backend queries.', '2025-04-03 11:00:00'),
(4, 'MENTEE', 'SCHEDULING_ENHANCEMENT', 'Allow recurring session scheduling.', 'PENDING', NULL, '2025-04-04 13:15:00'),
(5, 'MENTEE', 'NOTIFICATION_IMPROVEMENT', 'Add WhatsApp notifications for session reminders.', 'REVIEWED', 'Pending third-party integration.', '2025-04-05 14:45:00'),
(6, 'MENTEE', 'COMMUNICATION_TOOL', 'Introduce live chat between mentor and mentee.', 'PENDING', NULL, '2025-04-06 16:20:00'),
(7, 'MENTEE', 'SECURITY_PRIVACY', 'Enable session password protection.', 'IMPLEMENTED', 'Rolled out in April update.', '2025-04-07 08:10:00'),
(8, 'MENTEE', 'ACCESSIBILITY', 'Add voice commands for visually impaired users.', 'PENDING', NULL, '2025-04-08 10:50:00'),
(9, 'MENTEE', 'OTHER', 'Enable language translation during sessions.', 'REVIEWED', 'Need external API evaluation.', '2025-04-09 12:00:00'),
(10, 'MENTOR', 'FEATURE_REQUEST', 'Add mentor availability analytics.', 'PENDING', NULL, '2025-04-10 14:00:00'),
(11, 'MENTOR', 'UI_IMPROVEMENT', 'Dark mode for better readability.', 'IMPLEMENTED', 'Released in version 2.1.', '2025-04-11 15:30:00'),
(12, 'MENTOR', 'PERFORMANCE_IMPROVEMENT', 'Session video playback buffering issue.', 'REVIEWED', 'CDN optimizations in progress.', '2025-04-12 09:40:00'),
(13, 'MENTOR', 'SCHEDULING_ENHANCEMENT', 'Allow mentees to request urgent slots.', 'PENDING', NULL, '2025-04-13 11:10:00'),
(14, 'MENTOR', 'NOTIFICATION_IMPROVEMENT', 'Send follow-up reminders post-session.', 'PENDING', NULL, '2025-04-14 13:00:00'),
(15, 'MENTOR', 'COMMUNICATION_TOOL', 'Add screen-sharing during live sessions.', 'REVIEWED', 'Security review ongoing.', '2025-04-15 15:15:00'),
(16, 'MENTOR', 'SECURITY_PRIVACY', 'Add audit logs for mentor account changes.', 'IMPLEMENTED', 'Auditing added in admin panel.', '2025-04-16 10:05:00'),
(17, 'MENTOR', 'ACCESSIBILITY', 'Keyboard navigation support on mobile.', 'PENDING', NULL, '2025-04-17 17:25:00'),
(18, 'MENTOR', 'OTHER', 'Introduce mentor tier system based on reviews.', 'REVIEWED', 'Product team reviewing model.', '2025-04-18 14:10:00'),
(19, 'MENTOR', 'FEATURE_REQUEST', 'Ability to export session notes as PDF.', 'PENDING', NULL, '2025-04-19 09:50:00'),
(20, 'MENTOR', 'UI_IMPROVEMENT', 'Sticky header for schedule page.', 'IMPLEMENTED', 'Included in April redesign.', '2025-04-20 16:45:00');



INSERT INTO notifications (
sender_id, sender_type, receiver_id, receiver_type, message, is_read, created_at
) VALUES
(5, 'MENTOR', 1, 'MENTEE', 'Your session has been confirmed for April 30 at 3 PM.', FALSE, '2025-04-28 10:15:00'),
(3, 'MENTOR', 2, 'MENTEE', 'Please upload your resume before the session.', TRUE, '2025-04-27 09:30:00'),
(8, 'MENTOR', 3, 'MENTEE', 'Reminder: Your session starts in 1 hour.', FALSE, '2025-04-29 08:00:00'),
(2, 'MENTOR', 4, 'MENTEE', 'Looking forward to our discussion on project planning.', TRUE, '2025-04-25 14:00:00'),
(9, 'MENTOR', 5, 'MENTEE', 'Session cancelled due to scheduling conflict. Please reschedule.', FALSE, '2025-04-26 12:20:00'),
(4, 'MENTOR', 6, 'MENTEE', 'Please confirm the session scheduled for May 1.', FALSE, '2025-04-29 16:45:00'),
(7, 'MENTOR', 7, 'MENTEE', 'Check the resources shared in the chat.', TRUE, '2025-04-28 11:05:00'),
(10, 'MENTOR', 8, 'MENTEE', 'Please review the follow-up notes Ive sent.', FALSE, '2025-04-30 09:10:00'),
(1, 'MENTOR', 9, 'MENTEE', 'Your request for a session has been approved.', TRUE, '2025-04-24 13:30:00'),
(6, 'MENTOR', 10, 'MENTEE', 'Reminder: Please complete your profile.', FALSE, '2025-04-29 18:00:00'),
(3, 'MENTOR', 11, 'MENTEE', 'Great session today. Let me know if you have further questions.', TRUE, '2025-04-28 17:20:00'),
(12, 'MENTOR', 12, 'MENTEE', 'Kindly update your time zone settings.', FALSE, '2025-04-27 10:45:00'),
(11, 'MENTOR', 13, 'MENTEE', 'Mentorship summary available now.', TRUE, '2025-04-26 15:10:00'),
(5, 'MENTOR', 14, 'MENTEE', 'Please fill out the post-session feedback form.', FALSE, '2025-04-29 08:30:00'),
(9, 'MENTOR', 15, 'MENTEE', 'Thanks for attending the session!', TRUE, '2025-04-30 10:00:00'),
(6, 'MENTOR', 16, 'MENTEE', 'Session invitation sent. Check your email.', FALSE, '2025-04-25 17:00:00'),
(2, 'MENTOR', 17, 'MENTEE', 'Your session recording is ready.', TRUE, '2025-04-26 14:40:00'),
(8, 'MENTOR', 18, 'MENTEE', 'Mentor availability updated for next week.', FALSE, '2025-04-28 13:55:00'),
(4, 'MENTOR', 19, 'MENTEE', 'Please schedule a follow-up session.', TRUE, '2025-04-29 10:20:00'),
(7, 'MENTOR', 20, 'MENTEE', 'New message in chat from your mentor.', FALSE, '2025-04-30 11:15:00');


SELECT * from admin;
SELECT * FROM skills;
SELECT * FROM specializations;
SELECT * FROM mentor;
SELECT * FROM mentor_skills;
SELECT * FROM work_experiences;
SELECT * FROM mentee;
SELECT * FROM desired_skills;
SELECT * FROM mentee_industry_interests;
SELECT * FROM favourite_mentors;
SELECT * FROM mentor_availability;
SELECT * FROM appointments;
SELECT * FROM reviews;
SELECT * FROM reports;
SELECT * FROM suggestions;
SELECT * FROM notifications;