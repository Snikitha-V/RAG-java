-- Populate classes for C1-T1 (Supervised Learning - 5 lectures)
INSERT INTO classes (topic_id, title, learned_at) VALUES
((SELECT id FROM topics WHERE code='C1-T1'), 'Supervised Learning — Lecture 1', '2025-06-14 12:00:00'),
((SELECT id FROM topics WHERE code='C1-T1'), 'Supervised Learning — Lecture 2', '2025-06-11 11:00:00'),
((SELECT id FROM topics WHERE code='C1-T1'), 'Supervised Learning — Lecture 3', '2025-07-21 09:00:00'),
((SELECT id FROM topics WHERE code='C1-T1'), 'Supervised Learning — Lecture 4', '2025-08-02 11:00:00'),
((SELECT id FROM topics WHERE code='C1-T1'), 'Supervised Learning — Lecture 5', '2025-06-26 10:00:00'),

-- Populate classes for C1-T2 (Unsupervised Learning - 5 lectures)
((SELECT id FROM topics WHERE code='C1-T2'), 'Unsupervised Learning — Lecture 1', '2025-11-05 12:00:00'),
((SELECT id FROM topics WHERE code='C1-T2'), 'Unsupervised Learning — Lecture 2', '2025-09-29 10:00:00'),
((SELECT id FROM topics WHERE code='C1-T2'), 'Unsupervised Learning — Lecture 3', '2025-11-13 14:00:00'),
((SELECT id FROM topics WHERE code='C1-T2'), 'Unsupervised Learning — Lecture 4', '2025-10-31 11:00:00'),
((SELECT id FROM topics WHERE code='C1-T2'), 'Unsupervised Learning — Lecture 5', '2025-07-28 14:00:00'),

-- Populate classes for C1-T3 (Neural Networks - 5 lectures)
((SELECT id FROM topics WHERE code='C1-T3'), 'Neural Networks & Deep Learning — Lecture 1', '2025-11-25 09:00:00'),
((SELECT id FROM topics WHERE code='C1-T3'), 'Neural Networks & Deep Learning — Lecture 2', '2025-07-28 11:00:00'),
((SELECT id FROM topics WHERE code='C1-T3'), 'Neural Networks & Deep Learning — Lecture 3', '2025-07-22 12:00:00'),
((SELECT id FROM topics WHERE code='C1-T3'), 'Neural Networks & Deep Learning — Lecture 4', '2025-10-13 09:00:00'),
((SELECT id FROM topics WHERE code='C1-T3'), 'Neural Networks & Deep Learning — Lecture 5', '2025-09-27 11:00:00'),

-- Populate classes for C1-T4 (Model Evaluation - 5 lectures)
((SELECT id FROM topics WHERE code='C1-T4'), 'Model Evaluation & Validation — Lecture 1', '2025-10-24 09:00:00'),
((SELECT id FROM topics WHERE code='C1-T4'), 'Model Evaluation & Validation — Lecture 2', '2025-10-06 11:00:00'),
((SELECT id FROM topics WHERE code='C1-T4'), 'Model Evaluation & Validation — Lecture 3', '2025-11-17 14:00:00'),
((SELECT id FROM topics WHERE code='C1-T4'), 'Model Evaluation & Validation — Lecture 4', '2025-08-06 11:00:00'),
((SELECT id FROM topics WHERE code='C1-T4'), 'Model Evaluation & Validation — Lecture 5', '2025-07-12 13:00:00'),

-- Populate classes for C1-T5 (Feature Engineering - 5 lectures)
((SELECT id FROM topics WHERE code='C1-T5'), 'Feature Engineering & Pipelines — Lecture 1', '2025-08-09 09:00:00'),
((SELECT id FROM topics WHERE code='C1-T5'), 'Feature Engineering & Pipelines — Lecture 2', '2025-07-28 11:00:00'),
((SELECT id FROM topics WHERE code='C1-T5'), 'Feature Engineering & Pipelines — Lecture 3', '2025-09-02 12:00:00'),
((SELECT id FROM topics WHERE code='C1-T5'), 'Feature Engineering & Pipelines — Lecture 4', '2025-06-30 10:00:00'),
((SELECT id FROM topics WHERE code='C1-T5'), 'Feature Engineering & Pipelines — Lecture 5', '2025-06-15 11:00:00'),

-- Populate classes for C2-T1 (Java Syntax & OOP - 5 lectures)
((SELECT id FROM topics WHERE code='C2-T1'), 'Java Syntax & OOP — Lecture 1', '2025-06-21 13:00:00'),
((SELECT id FROM topics WHERE code='C2-T1'), 'Java Syntax & OOP — Lecture 2', '2025-09-06 14:00:00'),
((SELECT id FROM topics WHERE code='C2-T1'), 'Java Syntax & OOP — Lecture 3', '2025-07-11 13:00:00'),
((SELECT id FROM topics WHERE code='C2-T1'), 'Java Syntax & OOP — Lecture 4', '2025-10-10 09:00:00'),
((SELECT id FROM topics WHERE code='C2-T1'), 'Java Syntax & OOP — Lecture 5', '2025-09-01 13:00:00'),

-- Populate classes for C2-T2 (Collections & Generics - 5 lectures)
((SELECT id FROM topics WHERE code='C2-T2'), 'Collections & Generics — Lecture 1', '2025-06-22 10:00:00'),
((SELECT id FROM topics WHERE code='C2-T2'), 'Collections & Generics — Lecture 2', '2025-07-21 11:00:00'),
((SELECT id FROM topics WHERE code='C2-T2'), 'Collections & Generics — Lecture 3', '2025-06-20 13:00:00'),
((SELECT id FROM topics WHERE code='C2-T2'), 'Collections & Generics — Lecture 4', '2025-09-18 14:00:00'),
((SELECT id FROM topics WHERE code='C2-T2'), 'Collections & Generics — Lecture 5', '2025-07-28 12:00:00'),

-- Populate classes for C2-T3 (Concurrency & Threads - 5 lectures)
((SELECT id FROM topics WHERE code='C2-T3'), 'Concurrency & Threads — Lecture 1', '2025-11-29 11:00:00'),
((SELECT id FROM topics WHERE code='C2-T3'), 'Concurrency & Threads — Lecture 2', '2025-07-28 11:00:00'),
((SELECT id FROM topics WHERE code='C2-T3'), 'Concurrency & Threads — Lecture 3', '2025-06-12 14:00:00'),
((SELECT id FROM topics WHERE code='C2-T3'), 'Concurrency & Threads — Lecture 4', '2025-10-17 11:00:00'),
((SELECT id FROM topics WHERE code='C2-T3'), 'Concurrency & Threads — Lecture 5', '2025-09-24 12:00:00'),

-- Populate classes for C2-T4 (I/O, NIO & Files - 5 lectures)
((SELECT id FROM topics WHERE code='C2-T4'), 'I/O, NIO & Files — Lecture 1', '2025-08-17 09:00:00'),
((SELECT id FROM topics WHERE code='C2-T4'), 'I/O, NIO & Files — Lecture 2', '2025-08-29 09:00:00'),
((SELECT id FROM topics WHERE code='C2-T4'), 'I/O, NIO & Files — Lecture 3', '2025-08-27 13:00:00'),
((SELECT id FROM topics WHERE code='C2-T4'), 'I/O, NIO & Files — Lecture 4', '2025-10-31 11:00:00'),
((SELECT id FROM topics WHERE code='C2-T4'), 'I/O, NIO & Files — Lecture 5', '2025-08-01 10:00:00'),

-- Populate classes for C2-T5 (JVM Internals & Performance - 5 lectures)
((SELECT id FROM topics WHERE code='C2-T5'), 'JVM Internals & Performance — Lecture 1', '2025-09-30 14:00:00'),
((SELECT id FROM topics WHERE code='C2-T5'), 'JVM Internals & Performance — Lecture 2', '2025-09-08 10:00:00'),
((SELECT id FROM topics WHERE code='C2-T5'), 'JVM Internals & Performance — Lecture 3', '2025-08-03 09:00:00'),
((SELECT id FROM topics WHERE code='C2-T5'), 'JVM Internals & Performance — Lecture 4', '2025-11-03 13:00:00'),
((SELECT id FROM topics WHERE code='C2-T5'), 'JVM Internals & Performance — Lecture 5', '2025-06-21 12:00:00');
