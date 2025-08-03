# Spring AI: Auto-configuration of multiple ChatModel beans

This project demonstrates how to configure multiple `ChatModel` beans in a Spring application using the new "Programmating Bean Registration" feature introduced in Spring Framework 7.0.

The code included in this project is adapted from the `spring-ai-autoconfigure-model-ollama` module from the Spring AI project, which provides auto-configuration for Ollama chat models.

The goal of this project is to experiment with the new features and lay the foundation for implementing https://github.com/spring-projects/spring-ai/issues/3518.

## Pre-requisites

- Java 24
- Ollama installed with the `qwen3:4b`, `qwen3:8b`, and `gemma3:4b` models available.

## How to run

```shell
./gradlew bootRun
```

## How to test

You can test the application by sending HTTP GET requests to the following endpoints:

- `http://localhost:8080/chat` - Uses the default chat model Qwen 3 4B (generic chat tasks)
- `http://localhost:8080/chat/agent` - Uses the Qwen 3 8B model (specific for ficticious agentic tasks)
- `http://localhost:8080/chat/rag` - Uses the Gemma 3 4B model (specific for ficticious RAG tasks)
