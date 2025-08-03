Title:
Courses Program Management (Java Terminal Application)

Java Version: Java 8
 
**Features:**

1. Manage the Topics
   
  1.1. Add Topics to catalog

1.2. Update Topic

1.3. Delete Topic

1.4. Display all Topics

&nbsp;
 
2. Manage the Course

2.1. Add Course

2.2. Update Course

2.3. Delete Course

2.4. Display Course information

2.5. Display all Course

&nbsp;
 
3. Manage the Learner
   
3.1. Add Learner to Course

3.2. Enter scores for learners

3.3. Display Learner information

&nbsp;
 
4. Search information

4.1. Search Topic

4.2. Search Course

4.2.1. By Topic

4.2.2. By Name

&nbsp;
 
5. Save Topics, Courses and Learner to file.

6. Others- Quit
   
&nbsp;
 


This system contains the following functions:

**Data structure:**

➢ Topic information includes TopicID, name, type (long/short term), title, duration, etc….

➢ Course information includes CourseID, name, type (online/offline), title, begin date, end
date, tuition fee and topic, etc…

➢ Learner information includes codeID, name, date of birth, score, course, etc…

➢ Applying OOP principles, design with inheritance, polymorphism, interface or abstract class.

➢ For simplicity, a learner is considered to be taking only one course.

&nbsp;
 
**Function 1: Manage the Topics**

1.1.Add Topics to catalog

− Create a submenu.

− Remember that the constraints must be checked: code can not duplicate

− Add the new topic to the collection.

− Ask continuously create new topic or go back to the main menu.

1.2.Update Topic

- Create a submenu.

- Require to enter the topic code

- If the code does NOT EXIST, notify user "The topic does not exist". Otherwise, we can
start inputting new information about topic and update it.

- If new information is blank, then do not change old information.

- New information must be validated.

- The system must print out the result of the update.

- After updating, the program returns to the main screen.

1.3.Delete Topic

- Create a submenu

- Require to enter the topic code

- Before deleting, the system must show confirmation message.

- Show the result: success or fail.

- After deleting, the program returns to the main screen.

1.4.Display all Topics

- Show all the data in the topics' collection on the screen.

- Sorted in ascending order by name

&nbsp;
 
**Function 2: Manage the Course**

2.1.Add Course

- Create a submenu.

- Remember that the constraints must be checked: code can not duplicate

- Add the new course to the collection.

- Ask continuously create new course or go back to the main menu.

2.2.Update Course

- Create a submenu.

- Require to enter the course code

- If a code does NOT EXIST, notify user "The course does not exist". Otherwise, we
can start inputting new information about course and update.

- If new information is blank, then do not change old information.

- New information must be validated.

- The system must print out the result of the update.

- After updating, the program returns to the main screen.

2.3.Delete Course

- Create a submenu

- Require to enter the topic code

- Before deleting, the system must show confirmation message.

- Show the result: success or fail.

- After deleting, the program returns to the main screen.

2.4.Display Course information

- Show all the data in the course' collection on the screen.

- The information includes status of course, number of leaner pass or fail, incomes.

- Sorted in ascending order by begin date

&nbsp;
 
**Function 3: Manage the Learner**

 3.1.Add Learner to Course

- Create a submenu.

- Remember that the constraints must be checked by the size of class.

- Add the new Learner to the Course information.

- Ask continuously create new course or go back to the main menu.

3.2.Enter scores for learners

- Create a submenu.

- Require to enter the learner code

- If a code does NOT EXIST, notify user "The learner does not exist". Otherwise, we

can start inputting scores of learner and update.

- Remember new information must be validated.

- The system must print out the result of the update.

- After updating, the program returns to the main screen.

3.3.Display Learner information

- Show all the data of the learner on the screen.

- The information includes status of learner: pass or fail, gpa.

&nbsp;
 
**Function 4: Search information**

4.1.Search Topic

- Display all topics on the screen whose name contains the search information.

4.2.Search Course

- The information includes status of course, number of leaner pass or fail, tuition fees.

- By Topic: Display all courses on the screen that have the same topic code as the search

information

- By Name: Display all courses on the screen whose name contains the search information


&nbsp;
 
**Function 5: Save Topics, Courses and Learner to file.**

- Store the data of Topics, Courses and Learner to file.

- Reload data when the program starts.

