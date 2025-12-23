# RAG Learning Application

A Retrieval-Augmented Generation (RAG) application for learning management, combining semantic search, SQL queries, and LLM-powered responses.

## Overview

This is a **Spring Boot application** (NOT a microservice) that implements a hybrid RAG system with:
- **PostgreSQL**: Store learning chunks, courses, topics, classes, and assignments
- **Qdrant**: Vector database for semantic similarity search with embeddings
- **Mistral 7B LLM**: Generate intelligent responses using retrieved context
- **Hybrid Retrieval**: Combines SQL queries for structured data + vector search for semantic matching

## Architecture

```
User Request
    ↓
Spring Boot API (Port 8080)
    ↓
Intent Classifier (FACTUAL/SEMANTIC/MIXED)
    ↓
Hybrid Retrieval Service:
  ├─ SQL Query (PostgreSQL) → Structured data
  ├─ Vector Search (Qdrant) → Semantic chunks
  └─ Cross-Encoder Reranking → Best results
    ↓
Prompt Builder + LLM Generation (Mistral 7B)
    ↓
JSON Response with Answer + Sources
```

## Prerequisites

- **Docker** and **Docker Compose** (for containerized setup)
- **Java 17+** (for local development)
- **Maven 3.8+** (for building)
- **Git** (for cloning repository)

## Quick Start

### Option 1: Run Everything with Docker Compose (Recommended)

```bash
cd rag-learning
docker-compose up -d
```

This starts:
- **PostgreSQL 15** (Port 5432)
- **Qdrant 1.12.0** (Port 6333)
- **Mistral 7B LLM** (Port 8081)
- **Spring Boot App** (Port 8080)

**Environment Variables:**
```bash
export DB_PASS="Snikitha05!"  # PostgreSQL password
```

Access the application: `http://localhost:8080`

### Option 2: Run Locally with Pre-existing Docker Services

If you already have PostgreSQL and Qdrant running in Docker:

```bash
cd rag-learning

# Set database password
export DB_PASS="Snikitha05!"

# Build and run Spring Boot app
mvn clean compile
mvn org.springframework.boot:spring-boot-maven-plugin:3.2.4:run
```

The app will start on `http://localhost:8080`

### Option 3: Run JAR After Building

```bash
cd rag-learning

# Build JAR
mvn clean package -DskipTests

# Start Docker services
docker-compose up -d

# Run the JAR with environment variables
export DB_PASS="Snikitha05!"
java -jar target/rag-learning-1.0-SNAPSHOT.jar
```

## Database Setup

### Initial Data Population

The application includes SQL scripts to populate the database:

```bash
# 1. Populate courses, topics, and chunks
docker exec rag-learning-postgres-1 psql -U postgres -d learning_db -f temp_chunks.sql

# 2. Populate classes (lectures) for all courses
docker exec rag-learning-postgres-1 psql -U postgres -d learning_db -f populate_classes.sql
docker exec rag-learning-postgres-1 psql -U postgres -d learning_db -f populate_classes_c3_c5.sql

# 3. Populate assignments
docker exec rag-learning-postgres-1 psql -U postgres -d learning_db -f populate_assignments.sql
```

### Database Schema

- **chunks**: Learning content (courses, topics, summaries, classes)
- **courses**: Course definitions (5 courses: C1-C5)
- **topics**: Topics within courses (5 per course = 25 total)
- **classes**: Learning classes/lectures (5 per topic = 125 total)
- **assignments**: Topic-based assignments (25 total)

## Embeddings Generation

The application uses **all-mpnet-base-v2** ONNX model for embeddings.

### Regenerate Embeddings

```bash
# Navigate to rag-learning directory
cd rag-learning

# Ensure all dependencies are compiled
mvn clean compile

# Run the embedding uploader
java -cp "target/classes;target/dependency/*" org.example.EmbeddingUploader chunks.jsonl
```

This uploads 305 vectors to Qdrant's `learning_chunks` collection.

## API Usage

### Query Endpoint

**POST** `/api/v1/query`

```json
{
  "query": "What are the main topics in Machine Learning?"
}
```

**Response:**
```json
{
  "answer": "The main topics in Machine Learning course include...",
  "sources": [
    {
      "chunk_id": "C1-T1",
      "chunk_type": "TOPIC",
      "title": "Supervised Learning",
      "source": "RAG"
    }
  ],
  "intent": "SEMANTIC",
  "confidence": 0.85
}
```

## Key Files

| File | Purpose |
|------|---------|
| `docker-compose.yml` | Container orchestration for all services |
| `pom.xml` | Maven dependencies and build configuration |
| `src/main/java/org/example/App.java` | Spring Boot entry point |
| `src/main/java/org/example/RetrievalService.java` | Hybrid RAG orchestration |
| `src/main/java/org/example/QdrantClient.java` | Vector search client |
| `src/main/resources/application.properties` | Application configuration |
| `chunks.jsonl` | Learning chunks in JSONL format for embedding |
| `temp_chunks.sql` | SQL script with 305 chunks |
| `populate_classes.sql` | SQL script for classes data |
| `populate_assignments.sql` | SQL script for assignments data |

## Configuration

### Environment Variables

```bash
DB_HOST=localhost              # PostgreSQL host
DB_PORT=5432                   # PostgreSQL port
DB_NAME=learning_db            # Database name
DB_USER=postgres               # PostgreSQL user
DB_PASS=Snikitha05!           # PostgreSQL password (REQUIRED)

QDRANT_HOST=localhost          # Qdrant host
QDRANT_PORT=6333              # Qdrant port

LLM_HOST=localhost             # Mistral LLM host
LLM_PORT=8081                 # Mistral LLM port
```

### Application Properties

Edit `src/main/resources/application.properties` for:
- Server port (default: 8080)
- Logging levels
- JDBC connection pooling

## Troubleshooting

### Port Already in Use
```bash
# Windows - Kill process on port 8080
Get-NetTCPConnection -LocalPort 8080 | Stop-Process -Force

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Qdrant Not Responding
```bash
# Check Qdrant health
curl http://localhost:6333/health

# Restart Qdrant
docker restart rag-learning-qdrant-1
```

### PostgreSQL Connection Failed
```bash
# Check PostgreSQL status
docker logs rag-learning-postgres-1

# Verify password
echo $DB_PASS
```

### LLM Server Not Available
```bash
# Check if llama-server is running
curl http://localhost:8081/health

# Restart the service in docker-compose.yml
```

## Development Notes

- **Framework**: Spring Boot 3.2.4 with Spring Data JPA
- **Language**: Java 17
- **Build Tool**: Maven 3.13.0
- **ORM**: Hibernate
- **Vector DB Client**: OKHttp3
- **Embeddings**: ONNX Runtime (HuggingFace Tokenizers)
- **Cross-Encoder**: ONNX-based reranking model

## Performance

- **Embedding Generation**: ~0.04s per chunk (305 chunks = ~13 seconds total)
- **Vector Search**: ~34ms per query (Qdrant dense retrieval)
- **LLM Generation**: ~2-5 seconds per response (Mistral 7B)
- **End-to-end Query**: ~6-10 seconds (embedding + search + generation)

## Notes

This repository does not include automated CI/CD workflows. If you later want CI/CD, add workflows under `.github/workflows/` to suit your needs.
