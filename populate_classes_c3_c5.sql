-- Populate classes for C3-T1 (Relational Databases - 5 lectures)
INSERT INTO classes (topic_id, title, learned_at) VALUES
((SELECT id FROM topics WHERE code='C3-T1'), 'Relational Databases & SQL — Lecture 1', '2025-07-14 10:00:00'),
((SELECT id FROM topics WHERE code='C3-T1'), 'Relational Databases & SQL — Lecture 2', '2025-08-23 11:00:00'),
((SELECT id FROM topics WHERE code='C3-T1'), 'Relational Databases & SQL — Lecture 3', '2025-09-16 09:00:00'),
((SELECT id FROM topics WHERE code='C3-T1'), 'Relational Databases & SQL — Lecture 4', '2025-10-22 14:00:00'),
((SELECT id FROM topics WHERE code='C3-T1'), 'Relational Databases & SQL — Lecture 5', '2025-11-05 10:00:00'),

-- Populate classes for C3-T2 (NoSQL Databases - 5 lectures)
((SELECT id FROM topics WHERE code='C3-T2'), 'NoSQL Databases & Document Stores — Lecture 1', '2025-08-10 12:00:00'),
((SELECT id FROM topics WHERE code='C3-T2'), 'NoSQL Databases & Document Stores — Lecture 2', '2025-09-19 13:00:00'),
((SELECT id FROM topics WHERE code='C3-T2'), 'NoSQL Databases & Document Stores — Lecture 3', '2025-07-26 14:00:00'),
((SELECT id FROM topics WHERE code='C3-T2'), 'NoSQL Databases & Document Stores — Lecture 4', '2025-10-08 09:00:00'),
((SELECT id FROM topics WHERE code='C3-T2'), 'NoSQL Databases & Document Stores — Lecture 5', '2025-11-12 11:00:00'),

-- Populate classes for C3-T3 (Query Optimization - 5 lectures)
((SELECT id FROM topics WHERE code='C3-T3'), 'Query Optimization & Indexing — Lecture 1', '2025-06-25 10:00:00'),
((SELECT id FROM topics WHERE code='C3-T3'), 'Query Optimization & Indexing — Lecture 2', '2025-07-24 11:00:00'),
((SELECT id FROM topics WHERE code='C3-T3'), 'Query Optimization & Indexing — Lecture 3', '2025-08-18 13:00:00'),
((SELECT id FROM topics WHERE code='C3-T3'), 'Query Optimization & Indexing — Lecture 4', '2025-09-14 09:00:00'),
((SELECT id FROM topics WHERE code='C3-T3'), 'Query Optimization & Indexing — Lecture 5', '2025-10-29 12:00:00'),

-- Populate classes for C3-T4 (Data Modeling - 5 lectures)
((SELECT id FROM topics WHERE code='C3-T4'), 'Data Modeling & ER Diagrams — Lecture 1', '2025-07-19 14:00:00'),
((SELECT id FROM topics WHERE code='C3-T4'), 'Data Modeling & ER Diagrams — Lecture 2', '2025-08-16 10:00:00'),
((SELECT id FROM topics WHERE code='C3-T4'), 'Data Modeling & ER Diagrams — Lecture 3', '2025-09-09 11:00:00'),
((SELECT id FROM topics WHERE code='C3-T4'), 'Data Modeling & ER Diagrams — Lecture 4', '2025-10-25 13:00:00'),
((SELECT id FROM topics WHERE code='C3-T4'), 'Data Modeling & ER Diagrams — Lecture 5', '2025-11-18 09:00:00'),

-- Populate classes for C3-T5 (Database Security & Transactions - 5 lectures)
((SELECT id FROM topics WHERE code='C3-T5'), 'Database Security & Transactions — Lecture 1', '2025-06-29 09:00:00'),
((SELECT id FROM topics WHERE code='C3-T5'), 'Database Security & Transactions — Lecture 2', '2025-07-30 11:00:00'),
((SELECT id FROM topics WHERE code='C3-T5'), 'Database Security & Transactions — Lecture 3', '2025-08-26 14:00:00'),
((SELECT id FROM topics WHERE code='C3-T5'), 'Database Security & Transactions — Lecture 4', '2025-09-25 10:00:00'),
((SELECT id FROM topics WHERE code='C3-T5'), 'Database Security & Transactions — Lecture 5', '2025-11-08 12:00:00'),

-- Populate classes for C4-T1 (Cloud Infrastructure - 5 lectures)
((SELECT id FROM topics WHERE code='C4-T1'), 'Cloud Infrastructure & Services — Lecture 1', '2025-07-01 10:00:00'),
((SELECT id FROM topics WHERE code='C4-T1'), 'Cloud Infrastructure & Services — Lecture 2', '2025-08-11 11:00:00'),
((SELECT id FROM topics WHERE code='C4-T1'), 'Cloud Infrastructure & Services — Lecture 3', '2025-09-05 13:00:00'),
((SELECT id FROM topics WHERE code='C4-T1'), 'Cloud Infrastructure & Services — Lecture 4', '2025-10-14 09:00:00'),
((SELECT id FROM topics WHERE code='C4-T1'), 'Cloud Infrastructure & Services — Lecture 5', '2025-11-09 14:00:00'),

-- Populate classes for C4-T2 (Containerization & Kubernetes - 5 lectures)
((SELECT id FROM topics WHERE code='C4-T2'), 'Containerization & Kubernetes — Lecture 1', '2025-06-27 12:00:00'),
((SELECT id FROM topics WHERE code='C4-T2'), 'Containerization & Kubernetes — Lecture 2', '2025-07-31 10:00:00'),
((SELECT id FROM topics WHERE code='C4-T2'), 'Containerization & Kubernetes — Lecture 3', '2025-08-28 11:00:00'),
((SELECT id FROM topics WHERE code='C4-T2'), 'Containerization & Kubernetes — Lecture 4', '2025-10-02 13:00:00'),
((SELECT id FROM topics WHERE code='C4-T2'), 'Containerization & Kubernetes — Lecture 5', '2025-11-11 09:00:00'),

-- Populate classes for C4-T3 (CI/CD Pipelines - 5 lectures)
((SELECT id FROM topics WHERE code='C4-T3'), 'CI/CD Pipelines & GitOps — Lecture 1', '2025-07-05 09:00:00'),
((SELECT id FROM topics WHERE code='C4-T3'), 'CI/CD Pipelines & GitOps — Lecture 2', '2025-08-04 14:00:00'),
((SELECT id FROM topics WHERE code='C4-T3'), 'CI/CD Pipelines & GitOps — Lecture 3', '2025-09-02 10:00:00'),
((SELECT id FROM topics WHERE code='C4-T3'), 'CI/CD Pipelines & GitOps — Lecture 4', '2025-10-07 11:00:00'),
((SELECT id FROM topics WHERE code='C4-T3'), 'CI/CD Pipelines & GitOps — Lecture 5', '2025-11-06 13:00:00'),

-- Populate classes for C4-T4 (Monitoring & Observability - 5 lectures)
((SELECT id FROM topics WHERE code='C4-T4'), 'Monitoring & Observability — Lecture 1', '2025-07-09 11:00:00'),
((SELECT id FROM topics WHERE code='C4-T4'), 'Monitoring & Observability — Lecture 2', '2025-08-08 13:00:00'),
((SELECT id FROM topics WHERE code='C4-T4'), 'Monitoring & Observability — Lecture 3', '2025-09-11 09:00:00'),
((SELECT id FROM topics WHERE code='C4-T4'), 'Monitoring & Observability — Lecture 4', '2025-10-16 10:00:00'),
((SELECT id FROM topics WHERE code='C4-T4'), 'Monitoring & Observability — Lecture 5', '2025-11-14 14:00:00'),

-- Populate classes for C4-T5 (Infrastructure as Code - 5 lectures)
((SELECT id FROM topics WHERE code='C4-T5'), 'Infrastructure as Code & Terraform — Lecture 1', '2025-06-28 13:00:00'),
((SELECT id FROM topics WHERE code='C4-T5'), 'Infrastructure as Code & Terraform — Lecture 2', '2025-07-27 12:00:00'),
((SELECT id FROM topics WHERE code='C4-T5'), 'Infrastructure as Code & Terraform — Lecture 3', '2025-08-31 09:00:00'),
((SELECT id FROM topics WHERE code='C4-T5'), 'Infrastructure as Code & Terraform — Lecture 4', '2025-10-05 14:00:00'),
((SELECT id FROM topics WHERE code='C4-T5'), 'Infrastructure as Code & Terraform — Lecture 5', '2025-11-10 11:00:00'),

-- Populate classes for C5-T1 (Data Pipelines - 5 lectures)
((SELECT id FROM topics WHERE code='C5-T1'), 'Data Pipelines & ETL — Lecture 1', '2025-07-02 10:00:00'),
((SELECT id FROM topics WHERE code='C5-T1'), 'Data Pipelines & ETL — Lecture 2', '2025-08-12 11:00:00'),
((SELECT id FROM topics WHERE code='C5-T1'), 'Data Pipelines & ETL — Lecture 3', '2025-09-01 13:00:00'),
((SELECT id FROM topics WHERE code='C5-T1'), 'Data Pipelines & ETL — Lecture 4', '2025-10-09 09:00:00'),
((SELECT id FROM topics WHERE code='C5-T1'), 'Data Pipelines & ETL — Lecture 5', '2025-11-04 14:00:00'),

-- Populate classes for C5-T2 (Stream Processing - 5 lectures)
((SELECT id FROM topics WHERE code='C5-T2'), 'Stream Processing & Kafka — Lecture 1', '2025-07-06 12:00:00'),
((SELECT id FROM topics WHERE code='C5-T2'), 'Stream Processing & Kafka — Lecture 2', '2025-08-05 10:00:00'),
((SELECT id FROM topics WHERE code='C5-T2'), 'Stream Processing & Kafka — Lecture 3', '2025-09-04 11:00:00'),
((SELECT id FROM topics WHERE code='C5-T2'), 'Stream Processing & Kafka — Lecture 4', '2025-10-11 13:00:00'),
((SELECT id FROM topics WHERE code='C5-T2'), 'Stream Processing & Kafka — Lecture 5', '2025-11-07 09:00:00'),

-- Populate classes for C5-T3 (Data Warehousing - 5 lectures)
((SELECT id FROM topics WHERE code='C5-T3'), 'Data Warehousing & Analytics — Lecture 1', '2025-07-10 09:00:00'),
((SELECT id FROM topics WHERE code='C5-T3'), 'Data Warehousing & Analytics — Lecture 2', '2025-08-09 14:00:00'),
((SELECT id FROM topics WHERE code='C5-T3'), 'Data Warehousing & Analytics — Lecture 3', '2025-09-03 10:00:00'),
((SELECT id FROM topics WHERE code='C5-T3'), 'Data Warehousing & Analytics — Lecture 4', '2025-10-12 11:00:00'),
((SELECT id FROM topics WHERE code='C5-T3'), 'Data Warehousing & Analytics — Lecture 5', '2025-11-02 13:00:00'),

-- Populate classes for C5-T4 (Data Governance - 5 lectures)
((SELECT id FROM topics WHERE code='C5-T4'), 'Data Governance & Compliance — Lecture 1', '2025-07-13 11:00:00'),
((SELECT id FROM topics WHERE code='C5-T4'), 'Data Governance & Compliance — Lecture 2', '2025-08-15 13:00:00'),
((SELECT id FROM topics WHERE code='C5-T4'), 'Data Governance & Compliance — Lecture 3', '2025-09-10 09:00:00'),
((SELECT id FROM topics WHERE code='C5-T4'), 'Data Governance & Compliance — Lecture 4', '2025-10-21 10:00:00'),
((SELECT id FROM topics WHERE code='C5-T4'), 'Data Governance & Compliance — Lecture 5', '2025-11-15 14:00:00'),

-- Populate classes for C5-T5 (Big Data Analytics - 5 lectures)
((SELECT id FROM topics WHERE code='C5-T5'), 'Big Data Analytics & Machine Learning — Lecture 1', '2025-07-17 13:00:00'),
((SELECT id FROM topics WHERE code='C5-T5'), 'Big Data Analytics & Machine Learning — Lecture 2', '2025-08-19 12:00:00'),
((SELECT id FROM topics WHERE code='C5-T5'), 'Big Data Analytics & Machine Learning — Lecture 3', '2025-09-12 09:00:00'),
((SELECT id FROM topics WHERE code='C5-T5'), 'Big Data Analytics & Machine Learning — Lecture 4', '2025-10-23 14:00:00'),
((SELECT id FROM topics WHERE code='C5-T5'), 'Big Data Analytics & Machine Learning — Lecture 5', '2025-11-16 11:00:00');
