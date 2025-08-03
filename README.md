# Course Management System

**📚 A Java console application for managing educational courses, topics, and learners**

## 🚀 Quick Overview
- **Technology**: Java 8, OOP principles with inheritance & polymorphism
- **Purpose**: Complete CRUD management system for educational institutions
- **Data Storage**: CSV file persistence

## ✨ Core Features
- **Topics**: Create, update, delete, and view course topics (long/short term)
- **Courses**: Manage courses with dates, fees, topics, and enrolled learners
- **Learners**: Assign students to courses with capacity management
- **Search**: Find courses by topic or name (unimplemented)
- **Data Persistence**: Save/load data from CSV files

## 🏗️ Architecture
- **DTO Layer**: Topic, Course, Learner entities
- **Business Logic**: CourseList, TopicList, LearnerList management
- **UI Layer**: Console-based menu system
- **Utilities**: Custom comparators for sorting

## 🔧 How to Run
**NetBeans IDE (Recommended):**
- Open project in NetBeans
- Press F6 to run the application
- Main class: `UI.Main`

**Command Line:**
```cmd
REM From project root, compile and run directly
javac -cp src src/UI/Main.java src/DTO/*.java src/Lists/*.java src/Comparators/*.java
java -cp src UI.Main
```

*Built as an assignment for LAB211 course*