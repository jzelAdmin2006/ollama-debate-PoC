# Ollama Debate Tool PoC

This is a demo for my in-depth general education work, which I will complete in 2024. It is a tool that makes a democratic and a republican AI debate each other.

This project depends on the [RAG service](https://github.com/jzelAdmin2006/ollama-debate-PoC-rag) used for retreiving information from the DNC's and GOP's platform documents. The reason for this separation is that Spring AI apparently doesn't support multiple vector databases for a single application instance yet at the time of creation (see https://github.com/spring-projects/spring-ai/issues/468).

I've also created a [simple frontend](https://github.com/jzelAdmin2006/ollama-debate-PoC-frontend) for this application.

## Architecture

![Architecture](./architecture.drawio.svg)
<img src="./architecture.drawio.svg?raw=true&sanitize=true">
