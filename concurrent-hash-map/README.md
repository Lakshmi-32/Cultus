# Custom Concurrent Hash Map Implementation

## Project Overview

This project implements a thread-safe concurrent hash map from scratch using Java.

The implementation uses separate chaining for collision resolution and supports concurrent access using `ReentrantReadWriteLock`.

The main objective is to create a custom hash map that supports multiple threads performing operations such as insertion, searching, and deletion while maintaining data consistency.

---

## Features

- Generic key-value storage
- Separate chaining for collision resolution
- Thread-safe `put()` operation
- Thread-safe `get()` operation
- Thread-safe `remove()` operation
- Dynamic resizing based on load factor
- Concurrent read operations
- Exclusive write operations
- Stress testing with multiple threads
- Capacity and load factor monitoring
- Custom hash function comparison

---

## Project Structure

```text
src/
│
├── ConcurrentHashMap.java
├── HashFunction.java
├── Main.java
└── StressTest.java