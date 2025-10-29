# 🧠 AI Guesser

> 🤖 Intelligent backend powered by **Spring Boot**, **Spring AI**, and **modern Java 17+ features** to play 20 questions.

---

## 🚀 Tech Stack

| Category           | Technologies                                                                                                                                                                                                                      |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Backend**        | ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)                             |
| **AI Integration** | ![AI](https://img.shields.io/badge/AI-%230080FF?style=for-the-badge&logo=openai&logoColor=white)                                                                                                                                  |
| **Docs**           | ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)                                                                                                                          |
| **Utilities**      | ![Lombok](https://img.shields.io/badge/Lombok-A50?style=for-the-badge)                                                                                                                                                            |
| **Testing**        | ![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge) ![Mockito](https://img.shields.io/badge/Mockito-46B6AC?style=for-the-badge) ![AssertJ](https://img.shields.io/badge/AssertJ-0069C0?style=for-the-badge) |

---

## 📝 Description

**AI Guesser** is a modern backend system that integrates **AI capabilities** directly into a **Spring Boot**
architecture. Allows the user to play the game `20 questions` with LLM.
It provides clean, testable, and scalable APIs designed with maintainability and clarity in mind.
Thanks to **Spring AI**, the service can process intelligent data, make contextual predictions, and interact through
AI-driven logic.

Core goals:

* ⚙️ Scalable and modular architecture
* 🧩 AI-enhanced service layer
* 🧪 High test coverage and clean code principles

---

## 🔐 Environment Variables

| Variable         | Description    | Example         |
|------------------|----------------|-----------------|
| `OPENAI_API_KEY` | OpenAI API key | `sk-yourapikey` |

---

## 💡 Interesting Techniques

* **Spring AI Integration** — Direct integration of AI modules within the service layer, enabling intelligent
  decision-making and contextual responses.
* **Advanced Java 17+ Features** — Uses modern Java syntax for cleaner, more expressive code.
* **Lombok for Boilerplate Reduction** — Streamlines POJOs with annotations like `@Data`, `@Builder`, and
  `@RequiredArgsConstructor`.
* **Swagger/OpenAPI Documentation** — Provides self-updating, interactive documentation for all REST endpoints.
* **Comprehensive Testing** — Uses **JUnit5**, **Mockito**, and **AssertJ** to ensure high reliability and maintainable
  code.

---

## 🏫️ Notable Technologies and Libraries

* **Spring AI** – Adds native AI model integration to Spring services.
* **Lombok** – Reduces repetitive Java boilerplate.
* **Swagger/OpenAPI** – Enables live, interactive REST documentation.
* **JUnit5** – Core testing framework.
* **Mockito** – Mocking framework for unit tests.
* **AssertJ** – Fluent assertions improving test readability.

---

## 🧩 Project Structure

```
/src
  /main/java/pl/sebastianklimas/ai-guesser
   /controller     → REST controllers (API layer)
   /service        → Business and AI logic
   /model          → Domain entities and DTOs
   /config         → Configuration (Spring AI, Swagger, etc.)
 /test/java/pl/sebastianklimas/ai-guesser
  /service
```

- **/controller**: defines REST endpoints
- **/service**: core algorithmic logic (subset generation, optimization)
- **/model**: defines data entities with Lombok and Swagger annotations
- **/config**: sets up Swagger/OpenAPI documentation
- **/test**: unit tests covering all core features

---

## 🧠 Key Features

✅ AI-driven logic via **Spring AI**  
✅ Lightweight and maintainable Spring Boot structure  
✅ Full **Swagger/OpenAPI** integration  
✅ Java 17 modern language constructs  
✅ High test coverage with **JUnit5**, **Mockito**, and **AssertJ**  
✅ Reduced boilerplate using **Lombok**
