-- Create assignments for each topic
INSERT INTO assignments (title, course_id) VALUES
-- C1 Topic Assignments
('Supervised Learning Implementation', (SELECT id FROM courses WHERE code='C1')),
('Unsupervised Learning Analysis', (SELECT id FROM courses WHERE code='C1')),
('Neural Network Architecture Design', (SELECT id FROM courses WHERE code='C1')),
('Model Evaluation Metrics Project', (SELECT id FROM courses WHERE code='C1')),
('Feature Engineering Pipeline', (SELECT id FROM courses WHERE code='C1')),

-- C2 Topic Assignments
('Java OOP Design Patterns', (SELECT id FROM courses WHERE code='C2')),
('Collections & Generics Implementation', (SELECT id FROM courses WHERE code='C2')),
('Concurrency & Multi-threading Challenge', (SELECT id FROM courses WHERE code='C2')),
('File I/O & NIO Systems', (SELECT id FROM courses WHERE code='C2')),
('JVM Performance Optimization', (SELECT id FROM courses WHERE code='C2')),

-- C3 Topic Assignments
('Relational Database Design', (SELECT id FROM courses WHERE code='C3')),
('NoSQL Document Store Implementation', (SELECT id FROM courses WHERE code='C3')),
('Query Optimization Challenge', (SELECT id FROM courses WHERE code='C3')),
('ER Diagram & Data Modeling', (SELECT id FROM courses WHERE code='C3')),
('Transaction & Security Implementation', (SELECT id FROM courses WHERE code='C3')),

-- C4 Topic Assignments
('Cloud Infrastructure Setup', (SELECT id FROM courses WHERE code='C4')),
('Kubernetes Deployment Project', (SELECT id FROM courses WHERE code='C4')),
('CI/CD Pipeline Configuration', (SELECT id FROM courses WHERE code='C4')),
('Monitoring & Alerting System', (SELECT id FROM courses WHERE code='C4')),
('Infrastructure as Code with Terraform', (SELECT id FROM courses WHERE code='C4')),

-- C5 Topic Assignments
('Data Pipeline Development', (SELECT id FROM courses WHERE code='C5')),
('Stream Processing with Kafka', (SELECT id FROM courses WHERE code='C5')),
('Data Warehouse Architecture', (SELECT id FROM courses WHERE code='C5')),
('Data Governance Framework', (SELECT id FROM courses WHERE code='C5')),
('Big Data Analytics Project', (SELECT id FROM courses WHERE code='C5'))
ON CONFLICT DO NOTHING;

-- Link each assignment to its corresponding topic
INSERT INTO assignment_topics (assignment_id, topic_id) VALUES
-- C1-T1: Supervised Learning Implementation
((SELECT id FROM assignments WHERE title='Supervised Learning Implementation'), (SELECT id FROM topics WHERE code='C1-T1')),

-- C1-T2: Unsupervised Learning Analysis
((SELECT id FROM assignments WHERE title='Unsupervised Learning Analysis'), (SELECT id FROM topics WHERE code='C1-T2')),

-- C1-T3: Neural Network Architecture Design
((SELECT id FROM assignments WHERE title='Neural Network Architecture Design'), (SELECT id FROM topics WHERE code='C1-T3')),

-- C1-T4: Model Evaluation Metrics Project
((SELECT id FROM assignments WHERE title='Model Evaluation Metrics Project'), (SELECT id FROM topics WHERE code='C1-T4')),

-- C1-T5: Feature Engineering Pipeline
((SELECT id FROM assignments WHERE title='Feature Engineering Pipeline'), (SELECT id FROM topics WHERE code='C1-T5')),

-- C2-T1: Java OOP Design Patterns
((SELECT id FROM assignments WHERE title='Java OOP Design Patterns'), (SELECT id FROM topics WHERE code='C2-T1')),

-- C2-T2: Collections & Generics Implementation
((SELECT id FROM assignments WHERE title='Collections & Generics Implementation'), (SELECT id FROM topics WHERE code='C2-T2')),

-- C2-T3: Concurrency & Multi-threading Challenge
((SELECT id FROM assignments WHERE title='Concurrency & Multi-threading Challenge'), (SELECT id FROM topics WHERE code='C2-T3')),

-- C2-T4: File I/O & NIO Systems
((SELECT id FROM assignments WHERE title='File I/O & NIO Systems'), (SELECT id FROM topics WHERE code='C2-T4')),

-- C2-T5: JVM Performance Optimization
((SELECT id FROM assignments WHERE title='JVM Performance Optimization'), (SELECT id FROM topics WHERE code='C2-T5')),

-- C3-T1: Relational Database Design
((SELECT id FROM assignments WHERE title='Relational Database Design'), (SELECT id FROM topics WHERE code='C3-T1')),

-- C3-T2: NoSQL Document Store Implementation
((SELECT id FROM assignments WHERE title='NoSQL Document Store Implementation'), (SELECT id FROM topics WHERE code='C3-T2')),

-- C3-T3: Query Optimization Challenge
((SELECT id FROM assignments WHERE title='Query Optimization Challenge'), (SELECT id FROM topics WHERE code='C3-T3')),

-- C3-T4: ER Diagram & Data Modeling
((SELECT id FROM assignments WHERE title='ER Diagram & Data Modeling'), (SELECT id FROM topics WHERE code='C3-T4')),

-- C3-T5: Transaction & Security Implementation
((SELECT id FROM assignments WHERE title='Transaction & Security Implementation'), (SELECT id FROM topics WHERE code='C3-T5')),

-- C4-T1: Cloud Infrastructure Setup
((SELECT id FROM assignments WHERE title='Cloud Infrastructure Setup'), (SELECT id FROM topics WHERE code='C4-T1')),

-- C4-T2: Kubernetes Deployment Project
((SELECT id FROM assignments WHERE title='Kubernetes Deployment Project'), (SELECT id FROM topics WHERE code='C4-T2')),

-- C4-T3: CI/CD Pipeline Configuration
((SELECT id FROM assignments WHERE title='CI/CD Pipeline Configuration'), (SELECT id FROM topics WHERE code='C4-T3')),

-- C4-T4: Monitoring & Alerting System
((SELECT id FROM assignments WHERE title='Monitoring & Alerting System'), (SELECT id FROM topics WHERE code='C4-T4')),

-- C4-T5: Infrastructure as Code with Terraform
((SELECT id FROM assignments WHERE title='Infrastructure as Code with Terraform'), (SELECT id FROM topics WHERE code='C4-T5')),

-- C5-T1: Data Pipeline Development
((SELECT id FROM assignments WHERE title='Data Pipeline Development'), (SELECT id FROM topics WHERE code='C5-T1')),

-- C5-T2: Stream Processing with Kafka
((SELECT id FROM assignments WHERE title='Stream Processing with Kafka'), (SELECT id FROM topics WHERE code='C5-T2')),

-- C5-T3: Data Warehouse Architecture
((SELECT id FROM assignments WHERE title='Data Warehouse Architecture'), (SELECT id FROM topics WHERE code='C5-T3')),

-- C5-T4: Data Governance Framework
((SELECT id FROM assignments WHERE title='Data Governance Framework'), (SELECT id FROM topics WHERE code='C5-T4')),

-- C5-T5: Big Data Analytics Project
((SELECT id FROM assignments WHERE title='Big Data Analytics Project'), (SELECT id FROM topics WHERE code='C5-T5'))
ON CONFLICT DO NOTHING;
