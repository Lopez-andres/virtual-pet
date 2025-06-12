# 🐾 Virtual Pet - Final Project (Event-Driven Programming)

This project is a **desktop application in Java** that simulates an **interactive virtual pet**, developed as the final project for the course *Fundamentals of Object-Oriented Event-Driven Programming*. The application allows the user to care for a virtual pet, interact with it through graphical buttons, and monitor its state over time. It includes logic for data persistence, event handling, basic multithreading, and an intuitive GUI.

---

## 🧩 Main Features

1. **Pet Interaction**  
   The user can perform the following four activities:
   - 🍽️ Feed
   - 🏋️ Train
   - 🚿 Bathe
   - 🛏️ Put to sleep

2. **Dynamic Status Meters**  
   The pet has five meters that vary over time:
   - Hunger
   - Happiness
   - Dirtiness
   - Energy
   - Level

3. **Life and Level System**  
   - If the pet receives **poor attention for a long time**, it **dies**.
   - If the pet receives **consistent good attention**, it **levels up**.

4. **Visual Indicators**  
   The app displays images to represent the pet's current state based on its status meters.

5. **Data Persistence**  
   - The game state can be **saved and loaded** using object serialization.
   - At least **two separate saved games** can be stored and loaded.

---

## ⭐ Optional Features (Implemented)

- ✅ Use of **Threads** for an autosave feature every 2 minutes.
- ✅ Use of **Git and GitHub** for version control and collaboration.

---

## 🗂️ Project Structure

- `src/InformacionMascota.java` – Class that manages the pet’s information and binary file logic.
- `src/Mascota.java` – Main graphical interface and event-driven logic.
- `src/Login.java` – Initial screen to choose between creating or loading a pet.
- `src/ReproductorMusica.java` – Handles background music playback.
- `imagenes/` – Folder containing image assets for each pet state.
- `mascotas.bin` – Binary file used for game state persistence.

---

## ✅ Project Requirements Checklist

- [x] At least four pet interactions.
- [x] Time-dependent meters (hunger, energy, etc.).
- [x] Death and level-up logic based on user care.
- [x] Visual feedback when the pet is in a bad state.
- [x] Game state saved/loaded via binary file serialization.
- [x] Support for at least two different saved games.

---

## 📘 Project Report Includes

- UML Class Diagram.
- Full explanation of application functionality.
- Source code with comments.
- Screenshots of the application running.
- Logic description (event handling, autosaving with threads).

---

## 🧑‍🏫 Presentation

The application will be presented in class, demonstrating the full system functionality and explaining key technical and design decisions behind the implementation.

---

## 👨‍💻 Developer Info

**Student:** Andrés López  
**Course:** Fundamentals of Object-Oriented Event-Driven Programming  
**Institution:** Universidad del Valle  
**Group:** Individual (or specify if it was a team project)

---

## 🔗 GitHub Repository

[https://github.com/Lopez-andres/virtual-pet](https://github.com/Lopez-andres/virtual-pet)
