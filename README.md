# 🖧 Java Multi-Client Chat Application

A simple console-based chat server and client written in Java using sockets. The server supports multiple clients, and all messages are broadcast to other connected clients in real time.

---

## ✨ Features

- Multi-client support using threads
- Message broadcasting to all clients
- Graceful client connection and disconnection
- Thread-safe data structures (`ConcurrentHashMap`, `AtomicInteger`)
- Clean console output for messages
- Simple and easy to extend

---

## 🛠️ Requirements

- Java 8 or higher
- A terminal or command-line interface

---

## 📁 Project Structure

```

.
├── client.java    # Client-side code to connect and chat
├── server.java    # Server-side code to accept and broadcast messages
└── README.md      # Project documentation

````

---

## 🚀 How to Run

### 1. Clone or Download

```bash
git clone https://github.com/jeremiahcaleb/java-chat-app.git
cd java-chat-app
````

### 2. Compile the Code

```bash
javac server.java client.java
```

### 3. Start the Server

```bash
java server
```

### 4. Start One or More Clients (In New Terminal Windows)

```bash
java client
```

---

## 💬 Example Usage

* Start the server:

  ```
  Server started. Listening on port 12345
  ```

* Start a client:

  ```
  Connected to server.
  Type your message or 'quit' to exit.
  ```

* When Client 1 types:

  ```
  Hello everyone!
  ```

* Client 2 sees:

  ```
  Client 1: Hello everyone!
  ```

---

## 🧠 How It Works

* Each client runs in its own thread on the server.
* The server assigns each client a unique ID.
* Messages are broadcast to all connected clients except the sender.
* When a client types `quit`, it disconnects gracefully.

---
