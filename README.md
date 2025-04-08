# 🐶 Dog Shelter Software

A Java-based application to manage the intake, care, and adoption of dogs in a shelter. This project is a simulation of a real-world shelter system, enabling the efficient organization of dog profiles, adoption prioritization, and cost estimation.

---

## 📦 Features

- Add new dogs to the shelter.
- Automatically sort dogs based on age and shelter duration.
- Adopt dogs based on:
  - Longest time at the shelter.
  - Specific dog profile.
  - Priority within an age range.
- Search for:
  - Oldest dog.
  - Youngest dog.
  - Dog within a specific age range with the highest priority.
- Estimate upcoming veterinary costs for the shelter.
- Full iterable support over all dogs in the shelter.

---

## 📂 Project Structure

- `Dog.java`  
  Defines the `Dog` class, implementing:
  - Comparable interface (by age, then days in shelter)
  - Getters for age, vet cost, etc.
  - Custom `toString()` and `equals()` logic

- `DogShelter.java`  
  Implements a binary tree-like structure to manage dogs, with:
  - `shelter(Dog)` to add a dog
  - `adopt()` and `adopt(Dog)` to remove dogs
  - Methods like `findOldest()`, `findYoungest()`, and `findDogToAdopt(minAge, maxAge)`
  - Iteration support for easy traversal

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher

### Compilation
To compile:
```bash
javac Dog.java DogShelter.java
```

### Run (using a test file or main class)
Create a `Main.java` or use a test harness to call and interact with the shelter system:
```bash
java Main
```

---

## 🧠 Example Use Cases

```java
Dog d1 = new Dog("Buddy", 5, 30, 10, 120.0);
Dog d2 = new Dog("Max", 7, 25, 5, 90.0);
DogShelter shelter = new DogShelter(d1);
shelter.shelter(d2);
System.out.println("Oldest dog: " + shelter.findOldest());
System.out.println("Dog adopted: " + shelter.adopt());
```

---

## 🧪 Testing

- You can create JUnit tests or a `Main.java` to simulate adding, adopting, and querying dogs.
- Sample test operations might include:
  - Verifying tree balancing.
  - Ensuring correct prioritization by age and shelter days.
  - Checking vet cost aggregation.

---

## 📜 License

This project was created as part of an academic assignment and is distributed under the MIT License.

---

## ✍️ Author

Developed by Vitoria Lara for COMP 250 – Data Structures at McGill University.
